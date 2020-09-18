package com.betting.store.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddItemsToStoreRequest {

    private String storeId;
    private Set<String> productIds;

}
