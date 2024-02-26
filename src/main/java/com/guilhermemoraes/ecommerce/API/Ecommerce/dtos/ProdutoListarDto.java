package com.guilhermemoraes.ecommerce.API.Ecommerce.dtos;

import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Produto;

public class ProdutoListarDto{

    private Long idProduto;
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public ProdutoListarDto() {
    }

    public ProdutoListarDto(Produto produto) {
        this.idProduto = produto.getIdProduto();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}