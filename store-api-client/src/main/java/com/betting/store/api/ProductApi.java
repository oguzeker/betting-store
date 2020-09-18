package com.betting.store.api;

import com.betting.store.api.response.ProductApiResponse;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface ProductApi {

    @RequestLine("GET {path}")
    List<ProductApiResponse> getProducts(@Param("path") String path);

}
