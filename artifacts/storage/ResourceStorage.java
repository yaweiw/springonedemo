package com.example.springonedemo;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public interface ResourceStorage {
    Resource load(String location);
    void store(String location, InputStream inputStream) throws IOException;
}
