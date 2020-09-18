package com.betting.store.controller;

import com.betting.store.controller.request.CreateProductRequest;
import com.betting.store.controller.response.CreateProductResponse;
import com.betting.store.controller.response.GetProductResponse;
import com.betting.store.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("product")
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<Page<GetProductResponse>> getAllProducts(@RequestParam int pageIndex,
                                                                   @RequestParam int pageSize) {
        return ResponseEntity.ok(service.getAll(pageIndex, pageSize));
    }

    @GetMapping("api")
    public ResponseEntity<List<GetProductResponse>> getRemoteAPIResponse() {
        return ResponseEntity.ok(service.getRemoteAPIResponse());
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }

}
