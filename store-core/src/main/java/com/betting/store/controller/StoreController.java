package com.betting.store.controller;

import com.betting.store.controller.request.AddItemsToStoreRequest;
import com.betting.store.controller.request.CreateStoreRequest;
import com.betting.store.controller.response.AddItemsToStoreResponse;
import com.betting.store.controller.response.CreateStoreResponse;
import com.betting.store.controller.response.GetStoreResponse;
import com.betting.store.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("store")
public class StoreController {

    private final StoreService service;

    @GetMapping
    public ResponseEntity<Page<GetStoreResponse>> getAllStores(@RequestParam int pageIndex,
                                                               @RequestParam int pageSize) {
        return ResponseEntity.ok(service.getAll(pageIndex, pageSize));
    }

    @GetMapping("no-product-info")
    public ResponseEntity<List<GetStoreResponse>> getAllStoresWithoutProductInfo() {
        return ResponseEntity.ok(service.getAllStoresWithoutProductInfo());
    }

    @PostMapping
    public ResponseEntity<CreateStoreResponse> createStore(@RequestBody CreateStoreRequest request) {
        return ResponseEntity.ok(service.createStore(request));
    }

    @PutMapping
    public ResponseEntity<AddItemsToStoreResponse> addItemsToStore(@RequestBody AddItemsToStoreRequest request) {
        return ResponseEntity.ok(service.addItemsToStore(request));
    }

}
