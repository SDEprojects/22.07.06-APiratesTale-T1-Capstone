package com.company.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileGetter {

    public InputStream fileGetter(String fileName) {

        InputStream input = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);
        if (input == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return input;
        }
    }
}