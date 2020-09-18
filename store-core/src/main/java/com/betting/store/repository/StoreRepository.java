package com.betting.store.repository;

import com.betting.store.entity.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends MongoRepository<Store, String> {

    @Query(value = "{}", fields = "{'products' : 0}")
    List<Store> getAllStoresWithoutProductInfo();

}
