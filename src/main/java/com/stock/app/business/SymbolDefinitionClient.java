package com.stock.app.business;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stock.app.business.responses.GetAllStockDataResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class SymbolDefinitionClient {

    public List<GetAllStockDataResponse> getStockDataList() {
        String apiKey = "78FBEonrSPsUW8K76csYvx:1HAkKMAiUB2Iz6RhMwo0PA";
        String url = "https://api.collectapi.com/economy/hisseSenedi";

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("content-type", "application/json")
                .header("authorization", "apikey " + apiKey)
                .GET()
                .build();

        List<GetAllStockDataResponse> stockDataList = new ArrayList<>();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(responseBody).getAsJsonObject();
            if (jsonObject.has("result")) {
                JsonArray dataArray = jsonObject.getAsJsonArray("result");
                for (int i = 0; i < dataArray.size(); i++) {
                    JsonObject stockObject = dataArray.get(i).getAsJsonObject();
                    String code = stockObject.get("code").getAsString();
                    String text = stockObject.get("text").getAsString();
                    Double lastprice = stockObject.get("lastprice").getAsDouble();

                    GetAllStockDataResponse stockData = new GetAllStockDataResponse();
                    stockData.setCode(code);
                    stockData.setText(text);
                    stockData.setLastprice(lastprice);

                    stockDataList.add(stockData);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return stockDataList;
    }

}





