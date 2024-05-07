package com.mesdra.fundamentus.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mesdra.fundamentus.exception.PropertiesException;

public class PropertiesLoader {

  private static final String FILE_NAME = "application.properties";

  public static Properties loadProperties() throws PropertiesException {

    try {

      Properties configuration = new Properties();
      InputStream inputStream = PropertiesLoader.class
          .getClassLoader()
          .getResourceAsStream(FILE_NAME);
      configuration.load(inputStream);
      inputStream.close();
      return configuration;
    } catch (IOException e) {
      throw new PropertiesException(e.getMessage(), FILE_NAME);
    }
  }
}