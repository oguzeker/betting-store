package com.betting.store.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatesApiRequest {

    private Currency base;
    private List<Currency> symbols;

}
