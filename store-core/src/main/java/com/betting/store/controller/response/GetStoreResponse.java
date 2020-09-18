package com.betting.store.controller.response;

import com.betting.store.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStoreResponse {

    private String id;
    private String name;
    private Set<ProductDto> products;

}
