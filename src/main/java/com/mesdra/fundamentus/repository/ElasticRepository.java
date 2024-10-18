package com.mesdra.fundamentus.repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mesdra.fundamentus.config.PropertiesLoader;
import com.mesdra.fundamentus.exception.PropertiesException;
import com.mesdra.fundamentus.model.SharePosition;

public class ElasticRepository {

    public void save(List<SharePosition> positions) throws PropertiesException {
        try {
            RestHighLevelClient client = createClient();

            ObjectMapper objectMapper = new ObjectMapper();
            JavaTimeModule module = new JavaTimeModule();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            objectMapper.registerModule(module);

            for (SharePosition sharePosition : positions) {
                IndexRequest indexRequest = new IndexRequest("shareposition");
                indexRequest.source(objectMapper.writeValueAsString(sharePosition),
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
        String user = conf.getProperty("elastic.user");
        String pass = System.getenv("ELASTIC_PASSWORD");

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(user, pass));

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, Integer.valueOf(port), "http"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        return new RestHighLevelClient(builder);
    }

}
