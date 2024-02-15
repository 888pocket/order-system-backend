package com.joosangah.productservice.controller;

import com.joosangah.productservice.domain.dto.response.ProductResponse;
import com.joosangah.productservice.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/list")
    public List<ProductResponse> loadProducts() {
        return productService.loadProducts();
    }
}
