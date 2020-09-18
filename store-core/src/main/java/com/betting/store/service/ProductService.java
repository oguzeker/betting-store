package com.betting.store.service;

import com.betting.store.controller.request.CreateProductRequest;
import com.betting.store.controller.response.CreateProductResponse;
import com.betting.store.controller.response.GetProductResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    List<GetProductResponse> getRemoteAPIResponse();

    Page<GetProductResponse> getAll(int pageIndex, int pageSize);

    CreateProductResponse createProduct(CreateProductRequest request);

}
