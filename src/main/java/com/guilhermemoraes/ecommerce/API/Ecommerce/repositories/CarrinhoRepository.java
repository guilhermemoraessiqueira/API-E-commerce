package com.guilhermemoraes.ecommerce.API.Ecommerce.repositories;

import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
}