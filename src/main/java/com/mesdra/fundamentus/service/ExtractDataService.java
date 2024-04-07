package com.mesdra.fundamentus.service;

import java.io.IOException;
import java.util.List;

import com.mesdra.fundamentus.model.SharePosition;
import com.mesdra.fundamentus.repository.ShareRepository;

public class ExtractDataService {

    public void extract() throws IOException {

        ShareRepository repo = new ShareRepository();

        List<SharePosition> shareList = repo.getList();

        System.out.println(shareList.toString());

    }
}
