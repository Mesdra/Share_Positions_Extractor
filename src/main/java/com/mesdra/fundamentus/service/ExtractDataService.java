package com.mesdra.fundamentus.service;

import java.io.IOException;
import java.util.List;

import com.mesdra.fundamentus.exception.DataException;
import com.mesdra.fundamentus.exception.PropertiesException;
import com.mesdra.fundamentus.model.SharePosition;
import com.mesdra.fundamentus.repository.ElasticRepository;
import com.mesdra.fundamentus.repository.ShareRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtractDataService {

    public void extract() throws IOException {
        ShareRepository repoShare = new ShareRepository();
        ElasticRepository repoElastic = new ElasticRepository();

        try {
            log.info("Start Extraction");
            List<SharePosition> shareList = repoShare.getList();
            if (!shareList.isEmpty()) {
                log.info("Inserting the list of {} elements in database", shareList.size());
                repoElastic.save(shareList);
                log.info("Data saved with success, finished process", shareList.size());
            } else {
                log.info("Data not find on site.");
            }

        } catch (PropertiesException e) {
            log.error("Error to load properties {} , with filename {}", e.getMessage(), e.getPropertieFileName());
        } catch (DataException e) {
            log.error("Error in the process {} ", e.getMessage());
        }

    }
}
