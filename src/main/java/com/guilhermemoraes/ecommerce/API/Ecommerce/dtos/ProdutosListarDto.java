package com.guilhermemoraes.ecommerce.API.Ecommerce.dtos;

public record ProdutosListarDto(
        Long id,
        String nome,
        double preco,
        int quantidadeEstoque
) {
}
