package com.betting.store.api;

import com.betting.store.api.response.ProductApiResponse;
import feign.Feign.Builder;

import java.util.List;

public class ProductApiClient {

    private final static String API_PATH = "/v3/bc582afc-fbc3-4000-9aab-c89b4818d638";

    private volatile static ProductApiClient instance;
    private final ProductApi productApi;

    private ProductApiClient(String ratesApiUrl, Builder builder) {
        productApi = builder.target(ProductApi.class, ratesApiUrl);
    }

    public static ProductApiClient getInstance(String ratesApiUrl, Builder builder) {
        synchronized (ProductApiClient.class) {
            if (instance == null) {
                instance = new ProductApiClient(ratesApiUrl, builder);
            }
            return instance;
        }
    }

    public List<ProductApiResponse> getProducts() {
        return productApi.getProducts(API_PATH);
    }

}
