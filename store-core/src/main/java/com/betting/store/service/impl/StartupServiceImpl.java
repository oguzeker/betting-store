package com.betting.store.service.impl;

import com.betting.store.entity.Product;
import com.betting.store.entity.Store;
import com.betting.store.repository.ProductRepository;
import com.betting.store.repository.StoreRepository;
import com.betting.store.service.StartupService;
import com.github.javafaker.Faker;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class StartupServiceImpl implements StartupService {

    private static final int NUMBER_TEN = 10;
    private static final int NUMBER_ZERO = 0;
    private static final int NUMBER_FIVE = 5;
    private static final Faker FAKER = new Faker();

    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    @PostConstruct
    public void initialize() {
        log.info("init-begin");

        persistStore();

        log.info("init-end");
    }

    private void persistStore() {
        int count = NUMBER_ZERO;
        while (count < NUMBER_TEN) {
            Store store = Store.builder()
                    .name(FAKER.company().name())
                    .build();

            storeRepository.save(store);
            persistProduct(store);

            count++;
        }
    }

    private void persistProduct(Store store) {
        Set<Product> set = Sets.newHashSet();
        int count = NUMBER_ZERO;
        while (count < NUMBER_FIVE) {
            Product product = Product.builder()
                    .name(FAKER.commerce().productName())
                    .price(new BigDecimal(FAKER.commerce().price()))
                    .stores(Sets.newHashSet(store))
                    .build();

            productRepository.save(product);

            set.add(product);
            count++;
        }

        store.setProducts(set);
        storeRepository.save(store);
    }

}