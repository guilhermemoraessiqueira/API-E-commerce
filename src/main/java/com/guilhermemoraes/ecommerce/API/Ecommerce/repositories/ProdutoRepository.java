package com.guilhermemoraes.ecommerce.API.Ecommerce.repositories;

import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findAll();
}