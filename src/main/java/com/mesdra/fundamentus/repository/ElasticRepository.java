package com.mesdra.fundamentus.repository;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesdra.fundamentus.config.PropertiesLoader;
import com.mesdra.fundamentus.exception.PropertiesException;
import com.mesdra.fundamentus.model.SharePosition;

public class ElasticRepository {

    public void save(List<SharePosition> positions) throws PropertiesException {

        RestHighLevelClient client = createClient();

        for (SharePosition sharePosition : positions) {
            try {
            IndexRequest indexRequest = new IndexRequest("sharePosition");
            indexRequest.source(new ObjectMapper().writeValueAsString(sharePosition), XContentType.JSON);
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println("response id: "+indexResponse.getId());
            System.out.println("response name: "+indexResponse.getResult().name());
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
