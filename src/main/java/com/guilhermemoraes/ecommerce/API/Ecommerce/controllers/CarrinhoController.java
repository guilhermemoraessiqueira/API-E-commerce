package com.guilhermemoraes.ecommerce.API.Ecommerce.controllers;

import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Carrinho;
import com.guilhermemoraes.ecommerce.API.Ecommerce.models.ItemCarrinho;
import com.guilhermemoraes.ecommerce.API.Ecommerce.services.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    CarrinhoService carrinhoService;

    @PostMapping("/criar")
    public ResponseEntity criarCarrinho(@RequestBody Carrinho carrinhoDto){
        Carrinho carrinho = carrinhoService.criarCarrinho(carrinhoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoDto);
    }

    @GetMapping("/listar")
    public List<Carrinho> listarCarrinhos(){
        return carrinhoService.listarCarrinhos();
    }

    @PostMapping("/adicionar/{idCarrinho}")
    public ResponseEntity adicionarProdutoCarrinho(
            @PathVariable Long idCarrinho,
            @RequestParam Long produtoId,
            @RequestParam int quantidade){

        Carrinho carrinhoAtualizado = carrinhoService.adicionarItemAoCarrinho(idCarrinho, produtoId, quantidade);
        return ResponseEntity.ok(carrinhoAtualizado);
    }
}