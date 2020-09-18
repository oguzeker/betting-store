package com.betting.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private String id;
	private String name;
	private BigDecimal price;
	private Set<StoreDto> stores;

}
