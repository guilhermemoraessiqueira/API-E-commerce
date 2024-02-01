package com.guilhermemoraes.ecommerce.API.Ecommerce.dtos;

public record ProdutoDetalhadoDto(
         Long id,
         String nome,
         String descricao,
         double preco,
         int quantidadeEstoque
) {
}
