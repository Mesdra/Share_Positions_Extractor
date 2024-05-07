package com.mesdra.fundamentus.repository;

import java.util.List;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.mesdra.fundamentus.config.PropertiesLoader;
import com.mesdra.fundamentus.exception.PropertiesException;
import com.mesdra.fundamentus.model.SharePosition;

public class ElasticRepository {

      public void save(List<SharePosition> positions) throws PropertiesException {
        Properties conf = PropertiesLoader.loadProperties();
        String testMode = conf.getProperty("test.mode");
        String fileDir = conf.getProperty("test.file.dir");
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

    }
    
}
