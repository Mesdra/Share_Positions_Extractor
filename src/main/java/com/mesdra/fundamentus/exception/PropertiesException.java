package com.mesdra.fundamentus.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PropertiesException extends Exception {

    private String propertieFileName;

    public PropertiesException(String message, String propertieFileName) {
        super(message);
        this.propertieFileName = propertieFileName;
    }

}
