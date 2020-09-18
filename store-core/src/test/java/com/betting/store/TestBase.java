package com.betting.store;

import com.betting.store.api.response.ProductApiResponse;
import com.betting.store.controller.request.AddItemsToStoreRequest;
import com.betting.store.controller.response.GetProductResponse;
import com.betting.store.dto.ProductDto;
import com.betting.store.dto.StoreDto;
import com.betting.store.entity.Product;
import com.betting.store.entity.Store;
import org.assertj.core.util.Sets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TestBase {

    public static final int PAGE_INDEX = 0;
    public static final int PAGE_SIZE = 10;
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final BigDecimal PRICE = BigDecimal.ONE;

    public List<GetProductResponse> getGetProductResponseList() {
        List<GetProductResponse> list = new ArrayList<>();
        list.add(getGetProductResponse());

        return list;
    }

    public GetProductResponse getGetProductResponse() {
        return GetProductResponse.builder()
                .id(ID)
                .name(NAME)
                .price(PRICE)
                .stores(getStoreDtoSet())
                .build();
    }

    public StoreDto getStoreDto() {
        return StoreDto.builder()
                .id(ID)
                .name(NAME)
                .products(getProductDtoSet())
                .build();
    }

    public Set<StoreDto> getStoreDtoSet() {
        Set<StoreDto> set = Sets.newHashSet();
        set.add(getStoreDto());

        return set;
    }

    public Set<ProductDto> getProductDtoSet() {
        Set<ProductDto> set = Sets.newHashSet();
        set.add(getProductDtoNoStores());

        return set;
    }

    public ProductDto getProductDto() {
        return ProductDto.builder()
                .id(ID)
                .name(NAME)
                .price(PRICE)
                .stores(getStoreDtoSet())
                .build();
    }

    public ProductDto getProductDtoNoStores() {
        return ProductDto.builder()
                .id(ID)
                .name(NAME)
                .price(PRICE)
                .stores(null)
                .build();
    }

    public List<ProductApiResponse> getProductApiResponseList() {
        List<ProductApiResponse> list = new ArrayList<>();
        list.add(getProductApiResponse());

        return list;
    }

    public ProductApiResponse getProductApiResponse() {
        return ProductApiResponse.builder()
                .id(ID)
                .name(NAME)
                .price(PRICE)
                .build();
    }

    public Product getProduct() {
        return Product.builder()
                .id(ID)
                .price(PRICE)
                .name(NAME)
                .stores(getStoreSet())
                .build();
    }

    public Product getProductNoStores() {
        return Product.builder()
                .id(ID)
                .price(PRICE)
                .name(NAME)
                .stores(null)
                .build();
    }

    public List<Product> getProductList() {
        List<Product> list = new ArrayList<>();
        list.add(getProductNoStores());

        return list;
    }

    public Set<Product> getProductSet() {
        Set<Product> set = Sets.newHashSet();
        set.add(getProductNoStores());

        return set;
    }

    public Store getStore() {
        return Store.builder()
                .name(NAME)
                .id(ID)
                .products(getProductSet())
                .build();
    }

    public Store getStoreNoProducts() {
        return Store.builder()
                .name(NAME)
                .id(ID)
                .products(null)
                .build();
    }

    public Set<Store> getStoreSet() {
        Set<Store> set = Sets.newHashSet();
        set.add(getStoreNoProducts());

        return set;
    }

    public AddItemsToStoreRequest getAddItemsToStoreRequest() {
        return AddItemsToStoreRequest.builder()
                .productIds(Sets.newHashSet(Collections.singletonList(ID)))
                .storeId(ID)
                .build();
    }

}