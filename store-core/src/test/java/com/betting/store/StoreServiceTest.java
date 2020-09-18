package com.betting.store;

import com.betting.store.controller.request.AddItemsToStoreRequest;
import com.betting.store.entity.Store;
import com.betting.store.exception.StoreCoreError;
import com.betting.store.exception.StoreCoreException;
import com.betting.store.mapper.StoreMapper;
import com.betting.store.repository.ProductRepository;
import com.betting.store.repository.StoreRepository;
import com.betting.store.service.impl.StoreServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@CoreTest
public class StoreServiceTest extends TestBase {

    @InjectMocks
    StoreServiceImpl subject;

    @Mock
    StoreRepository storeRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    StoreMapper mapper;

    @Test
    public void addItemsToStore_Test() {
        AddItemsToStoreRequest request = getAddItemsToStoreRequest();
        Optional<Store> store = Optional.empty();

        when(storeRepository.findById(request.getStoreId())).thenReturn(store);

        StoreCoreException thrown = assertThrows(StoreCoreException.class,
                () -> subject.addItemsToStore(request)
        );

        assert thrown.getError() == StoreCoreError.STORE_NOT_FOUND;
        assert Arrays.asList(thrown.getArgs()).contains(request.getStoreId());

        verify(storeRepository, times(1)).findById(request.getStoreId());
        verifyNoMoreInteractions(storeRepository);
    }

}