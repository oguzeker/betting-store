package com.betting.store.service.impl;

import com.betting.store.api.ProductApiClient;
import com.betting.store.controller.request.CreateProductRequest;
import com.betting.store.controller.response.CreateProductResponse;
import com.betting.store.controller.response.GetProductResponse;
import com.betting.store.entity.Product;
import com.betting.store.mapper.ProductMapper;
import com.betting.store.repository.ProductRepository;
import com.betting.store.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;


@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ProductApiClient client;

    public List<GetProductResponse> getRemoteAPIResponse() {
        log.info("getRemoteAPIResponse-begin");

        List<GetProductResponse> response = client.getProducts().stream()
                .map(mapper::mapResponse)
                .collect(Collectors.toList());

        log.info("getRemoteAPIResponse-end {}", kv("response", response));
        return response;
    }

    public Page<GetProductResponse> getAll(int pageIndex, int pageSize) {
        log.info("getAll-begin {} {}", kv("pageIndex", pageIndex), kv("pageSize", pageSize));

        Page<GetProductResponse> response = repository.findAll(PageRequest.of(pageIndex, pageSize))
                .map(mapper::mapEntityToGetProductResponse);

        log.info("getAll-end {}", kv("response", response));
        return response;
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {
        log.info("createProduct-begin {}", kv("request", request));

        Product entity = repository.save(mapper.mapRequest(request));
        CreateProductResponse response = mapper.mapEntityToCreateProductResponse(entity);

        log.info("createProduct-end {}", kv("response", response));
        return response;
    }

}
