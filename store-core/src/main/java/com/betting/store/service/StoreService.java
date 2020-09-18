package com.betting.store.service;

import com.betting.store.controller.request.AddItemsToStoreRequest;
import com.betting.store.controller.request.CreateStoreRequest;
import com.betting.store.controller.response.AddItemsToStoreResponse;
import com.betting.store.controller.response.CreateStoreResponse;
import com.betting.store.controller.response.GetStoreResponse;
import com.betting.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StoreService {

    CreateStoreResponse createStore(CreateStoreRequest request);

    Page<GetStoreResponse> getAll(int pageIndex, int pageSize);

    AddItemsToStoreResponse addItemsToStore(AddItemsToStoreRequest request);

    List<GetStoreResponse> getAllStoresWithoutProductInfo();

}
