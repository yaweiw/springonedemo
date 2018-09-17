package com.example.springonedemo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class ImageAnalysisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageAnalysisService.class);

    private String apiKey = "6cbdab0344de4c6aba54caa60fcxxxxx";
    private String uriBase = "https://eastus.api.cognitive.microsoft.com/vision/v1.0/analyze";

    @Cacheable("imageAnalysis")
    public String analyze(String imageURL) throws URISyntaxException, IOException, JSONException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder(uriBase);
        // Request parameters. All of them are optional.
        builder.setParameter("visualFeatures", "Categories,Description,Color");
        builder.setParameter("language", "en");
        // Prepare the URI for the REST API call.
        URI uri = builder.build();
        HttpPost request = new HttpPost(uri);
        // Request headers.
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", apiKey);
        // Request body.
        StringEntity reqEntity = new StringEntity("{\"url\":\"" + imageURL + "\"}");
        request.setEntity(reqEntity);
        // Execute the REST API call and get the response entity.
        HttpResponse response = httpclient.execute(request);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            if (entity != null)
            {
                JSONObject json = new JSONObject(EntityUtils.toString(entity));
                return json.getJSONObject("description").get("captions").toString();
            }
        }
        LOGGER.info("Analysis result is null");
        return "";
    }
}
