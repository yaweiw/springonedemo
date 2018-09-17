package com.example.springonedemo;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class BlobResourceStorage implements ResourceStorage {
    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public Resource load(String blobLocation) {
        return this.resourceLoader.getResource(blobLocation);
    }

    @Override
    public void store(String blobLocation, InputStream blobInputStream) throws IOException {
        Resource blob = load(blobLocation);
        OutputStream os = ((WritableResource)blob).getOutputStream();
        IOUtils.copy(blobInputStream, os);
        os.close();
        blobInputStream.close();
    }
}
