package com.betting.store.mapper;

import com.betting.store.api.response.ProductApiResponse;
import com.betting.store.controller.request.CreateProductRequest;
import com.betting.store.controller.response.CreateProductResponse;
import com.betting.store.controller.response.GetProductResponse;
import com.betting.store.dto.StoreDto;
import com.betting.store.entity.Product;
import com.betting.store.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapRequest(CreateProductRequest request);

    CreateProductResponse mapEntityToCreateProductResponse(Product entity);

    GetProductResponse mapResponse(ProductApiResponse response);

    @Mappings({
            @Mapping(target = "stores", qualifiedByName = "store-custom")
    })
    GetProductResponse mapEntityToGetProductResponse(Product entity);

    @Named("store-custom")
    @Mappings({
            @Mapping(target = "products", expression = "java(null)")
    })
    StoreDto mapStoreEntityCustom(Store entity);

}
