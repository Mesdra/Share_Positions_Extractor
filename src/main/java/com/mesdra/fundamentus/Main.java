package com.mesdra.fundamentus;

import java.io.IOException;

import com.mesdra.fundamentus.service.ExtractDataService;

public class Main {
    public static void main(String[] args) throws IOException {

        ExtractDataService ex = new ExtractDataService();
        ex.extract();
        System.exit(0);
    }
}