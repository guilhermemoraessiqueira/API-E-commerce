package com.guilhermemoraes.ecommerce.API.Ecommerce.repositories;

import com.guilhermemoraes.ecommerce.API.Ecommerce.models.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
}
