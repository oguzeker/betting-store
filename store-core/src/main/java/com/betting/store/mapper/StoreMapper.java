package com.betting.store.mapper;

import com.betting.store.controller.request.CreateStoreRequest;
import com.betting.store.controller.response.AddItemsToStoreResponse;
import com.betting.store.controller.response.CreateStoreResponse;
import com.betting.store.controller.response.GetStoreResponse;
import com.betting.store.dto.ProductDto;
import com.betting.store.entity.Product;
import com.betting.store.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    Store mapRequest(CreateStoreRequest request);

    CreateStoreResponse mapEntityToCreateStoreResponse(Store entity);

    @Mappings({
            @Mapping(target = "products", qualifiedByName = "product-custom")
    })
    AddItemsToStoreResponse mapEntityToAddItemToStoreResponse(Store entity);

    @Mappings({
            @Mapping(target = "products", qualifiedByName = "product-custom")
    })
    GetStoreResponse mapEntityToGetStoreResponse(Store entity);

    @Named("product-custom")
    @Mappings({
            @Mapping(target = "stores", expression = "java(null)")
    })
    ProductDto mapProductEntityCustom(Product entity);

}
