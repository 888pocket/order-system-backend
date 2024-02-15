package com.joosangah.productservice.mapper;

import com.joosangah.productservice.common.util.GenericMapper;
import com.joosangah.productservice.domain.dto.response.ProductResponse;
import com.joosangah.productservice.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper extends GenericMapper<ProductResponse, Product> {

}
