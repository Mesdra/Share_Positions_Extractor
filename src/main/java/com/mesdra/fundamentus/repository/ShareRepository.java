package com.mesdra.fundamentus.repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;

import com.mesdra.fundamentus.config.PropertiesLoader;
import com.mesdra.fundamentus.exception.DataException;
import com.mesdra.fundamentus.exception.PropertiesException;
import com.mesdra.fundamentus.model.SharePosition;

public class ShareRepository {

    public List<SharePosition> getList() throws PropertiesException,DataException {

        try {
            List<SharePosition> response = new ArrayList<>();

            Properties conf = PropertiesLoader.loadProperties();
            String testMode = conf.getProperty("test.mode");
            String fileDir = conf.getProperty("test.file.dir");
            testMode = testMode.trim();

            org.jsoup.nodes.Document doc = null;
            if (testMode.equals("1")) {
                String path = fileDir + "test.html";
                File file = new File(path);
                doc = Jsoup.parse(file, "ISO-8859-1");
            } else {
                String url = conf.getProperty("site.url");
                doc = Jsoup.connect(url).post();
            }

            org.jsoup.select.Elements rows = doc.select("tr");
            for (org.jsoup.nodes.Element row : rows) {
                org.jsoup.select.Elements columns = row.select("td");
                if (columns.size() == 21) {
                    response.add(new SharePosition(columns));
                }
            }
            return response;

        } catch (IOException e) {
            throw new DataException("Fail to convert message. " + e.getMessage());
        }

    }

}
