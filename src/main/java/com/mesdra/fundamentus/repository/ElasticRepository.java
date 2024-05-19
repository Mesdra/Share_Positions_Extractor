package com.mesdra.fundamentus.repository;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesdra.fundamentus.config.PropertiesLoader;
import com.mesdra.fundamentus.exception.PropertiesException;
import com.mesdra.fundamentus.model.SharePosition;

public class ElasticRepository {

    public void save(List<SharePosition> positions) throws PropertiesException {
        try {
            RestHighLevelClient client = createClient();

            for (SharePosition sharePosition : positions) {

                IndexRequest indexRequest = new IndexRequest("shareposition");
                indexRequest.source(new ObjectMapper().writeValueAsString(sharePosition),
                        XContentType.JSON);
                client.index(indexRequest, RequestOptions.DEFAULT);

            }
            client.close();
        } catch (IOException e) {
            throw new PropertiesException("Erro inserir mensagem " + e.getMessage(), "");
        }

    }

    private RestHighLevelClient createClient() throws PropertiesException {
        Properties conf = PropertiesLoader.loadProperties();
        String host = conf.getProperty("elastic.host");
        String port = conf.getProperty("elastic.port");
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, Integer.valueOf(port), "http")));
    }

}
