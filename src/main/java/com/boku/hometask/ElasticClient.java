package com.boku.hometask;

import com.boku.hometask.util.SslHelper;
import com.boku.hometask.util.ZonedDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZonedDateTime;
import java.util.List;
import javax.net.ssl.SSLContext;

public class ElasticClient {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeTypeAdapter())
            .create();

    private static final String TARGET_INDEX = "alias-some-index";
    private static final String ELASTIC_URL = "https://localhost:9200/";
    private static final String ELASTIC_API_KEY = "NS0wUU5JZ0JrTHVIWi1LMk0wWk06SEh3b0FqajdSb3UyUmk1YXBVbHBhZw==";

    private HttpClient httpClient = buildClient();

    public void sendToElastic(List<TrafficRecord> allRows) throws Exception {
        String body = makeRequestJson(allRows);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URL(ELASTIC_URL + TARGET_INDEX + "/_bulk").toURI())
                .method("POST", HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .headers("Authorization", "ApiKey " + ELASTIC_API_KEY).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() > 299) {
            throw new RuntimeException("Request failed " + response.statusCode() + " " + response.body());
        }
    }

    private String makeRequestJson(List<TrafficRecord> allRows) {
        String indexJson = "{ \"create\":{ } }";
        List<String> jsons = allRows.stream().map(r -> String.format("%s\n%s\n", indexJson, GSON.toJson(r))).toList();
        return String.join("", jsons);
    }

    private HttpClient buildClient() {
        try {
            SSLContext sslContext = SslHelper.makeIgnorantSslContext();
            return HttpClient.newBuilder().sslContext(sslContext).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
