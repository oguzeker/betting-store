package com.betting.store.service.impl;

import com.betting.store.controller.request.AddItemsToStoreRequest;
import com.betting.store.controller.request.CreateStoreRequest;
import com.betting.store.controller.response.AddItemsToStoreResponse;
import com.betting.store.controller.response.CreateStoreResponse;
import com.betting.store.controller.response.GetStoreResponse;
import com.betting.store.entity.Product;
import com.betting.store.entity.Store;
import com.betting.store.exception.StoreCoreError;
import com.betting.store.exception.StoreCoreException;
import com.betting.store.mapper.StoreMapper;
import com.betting.store.repository.ProductRepository;
import com.betting.store.repository.StoreRepository;
import com.betting.store.service.StoreService;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.kv;


@Slf4j
@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final StoreMapper mapper;

    public Page<GetStoreResponse> getAll(int pageIndex, int pageSize) {
        log.info("getAll-begin {} {}", kv("pageIndex", pageIndex), kv("pageSize", pageSize));

        Page<GetStoreResponse> response = storeRepository.findAll(PageRequest.of(pageIndex, pageSize))
                .map(mapper::mapEntityToGetStoreResponse);

        log.info("getAll-end {}", kv("response", response));
        return response;
    }

    public CreateStoreResponse createStore(CreateStoreRequest request) {
        log.info("createStore-begin {}", kv("request", request));

        Store entity = storeRepository.save(mapper.mapRequest(request));
        CreateStoreResponse response = mapper.mapEntityToCreateStoreResponse(entity);

        log.info("createStore-end {}", kv("response", response));
        return response;
    }

    public AddItemsToStoreResponse addItemsToStore(AddItemsToStoreRequest request) {
        log.info("addItemsToStore-begin {}", kv("request", request));
        String storeId = request.getStoreId();
        final Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreCoreException(StoreCoreError.STORE_NOT_FOUND, storeId));

        AddItemsToStoreResponse response = updateSubEntitiesAndEntity(request.getProductIds(), store);

        log.info("addItemsToStore-end {}", kv("response", response));
        return response;
    }

    public List<GetStoreResponse> getAllStoresWithoutProductInfo() {
        log.info("addItemsToStore-begin");

        List<GetStoreResponse> response = storeRepository.getAllStoresWithoutProductInfo().stream()
                .map(mapper::mapEntityToGetStoreResponse)
                .collect(Collectors.toList());

        log.info("addItemsToStore-end {}", kv("response", response));
        return response;
    }

    private AddItemsToStoreResponse updateSubEntitiesAndEntity(Set<String> productIds, final Store store) {
        log.info("addItemToStore-begin {} {}", kv("productIds", productIds), kv("store", store));
        MutableBoolean isStoreModified = new MutableBoolean();
        Set<String> failedProductIds = Sets.newHashSet();

        productIds.forEach(productId -> {
            productRepository.findById(productId).ifPresentOrElse(
                    product -> processProduct(product, store, isStoreModified),
                    () -> failedProductIds.add(productId));
        });

        Store modifiedStore = store;
        if (isStoreModified.booleanValue()) {
            modifiedStore = storeRepository.save(store);
        }

        AddItemsToStoreResponse response = mapper.mapEntityToAddItemToStoreResponse(modifiedStore);
        response.setFailedProductIds(failedProductIds);

        log.info("addItemToStore-end {}", kv("response", response));
        return response;
    }

    private void processProduct(final Product product, final Store store, final MutableBoolean isStoreModified) {
        addItemToSet(product.getStores(), store,
                tempStore -> product.setStores(Sets.newHashSet(tempStore)), isStoreModified);

        if (isStoreModified.booleanValue()) {
            productRepository.save(product);

            addItemToSet(store.getProducts(), product,
                    tempProduct -> store.setProducts(Sets.newHashSet(tempProduct)), isStoreModified);
        }
    }

    private <T> void addItemToSet(Set<T> set, T item, Consumer<T> consumer, MutableBoolean isSetModified) {
        Optional.ofNullable(set).ifPresentOrElse(
                tempSet -> {
                    boolean isAdded = tempSet.add(item);
                    isSetModified.setValue(!isSetModified.booleanValue() ? isAdded : isSetModified.getValue());
                },
                () -> {
                    isSetModified.setTrue();
                    consumer.accept(item);
                });
    }

}
