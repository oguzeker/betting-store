package com.betting.store;

import com.betting.store.api.ProductApiClient;
import com.betting.store.api.response.ProductApiResponse;
import com.betting.store.controller.response.GetProductResponse;
import com.betting.store.entity.Product;
import com.betting.store.mapper.ProductMapper;
import com.betting.store.repository.ProductRepository;
import com.betting.store.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@CoreTest
public class ProductServiceTest extends TestBase {

    @InjectMocks
    ProductServiceImpl subject;
    @Mock
    ProductRepository repository;
    @Mock
    ProductMapper mapper;
    @Mock
    ProductApiClient client;

    @Test
    public void getRemoteAPIResponse_Test() {
        List<ProductApiResponse> apiResponseList = getProductApiResponseList();
        GetProductResponse productResponse = getGetProductResponse();

        when(client.getProducts()).thenReturn(apiResponseList);
        when(mapper.mapResponse(any(ProductApiResponse.class))).thenReturn(productResponse);

        List<GetProductResponse> response = subject.getRemoteAPIResponse();

        assert response.size() == 1;
        assert response.get(0).getId().equals(ID);
        assert response.get(0).getName().equals(NAME);
        assert response.get(0).getPrice().equals(PRICE);
        assert response.get(0).getStores().size() == 1;

        verify(mapper, times(1)).mapResponse(any(ProductApiResponse.class));
        verify(client, times(1)).getProducts();
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(client);

        assertThat(response)
                .hasSize(1)
                .contains(productResponse);
    }

    @Test
    public void getAll_Test() {
        GetProductResponse getProductResponse = getGetProductResponse();
        List<GetProductResponse> getProductResponseList = getGetProductResponseList();
        PageRequest pageRequest = PageRequest.of(PAGE_INDEX, PAGE_SIZE);

        List<Product> productList = getProductList();
        Page<Product> productPage = new PageImpl<>(productList);

        when(repository.findAll(pageRequest)).thenReturn(productPage);
        when(mapper.mapEntityToGetProductResponse(any(Product.class))).thenReturn(getProductResponse);

        Page<GetProductResponse> response = subject.getAll(PAGE_INDEX, PAGE_SIZE);

        assert response.getTotalPages() == 1;
        assert response.getTotalElements() == 1;

        verify(mapper, times(1)).mapEntityToGetProductResponse(any(Product.class));
        verify(repository, times(0)).findAll();
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(repository);

        assertThat(response)
                .contains(getProductResponse);
    }

}