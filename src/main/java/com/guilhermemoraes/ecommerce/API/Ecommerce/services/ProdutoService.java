package com.guilhermemoraes.ecommerce.API.Ecommerce.services;

import com.guilhermemoraes.ecommerce.API.Ecommerce.dtos.ProdutoDto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.dtos.ProdutoListarDto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Produto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<ProdutoListarDto> listarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(ProdutoListarDto::new)
                .collect(Collectors.toList());
    }

    public ProdutoDto obterProdutoPorId(Long id){
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        return produtoOptional.map(produto ->
                new ProdutoDto(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getQuantidadeEstoque()
                )
        ).orElse(null);
    }

    public Produto adicionarProduto(ProdutoDto produtoDto) {
        Produto produto = new Produto(
                produtoDto.getId(),
                produtoDto.getNome(),
                produtoDto.getDescricao(),
                produtoDto.getPreco(),
                produtoDto.getQuantidadeEstoque()
        );
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produto){
        if (produtoRepository.existsById(id)){
            Produto produtoExistente = new Produto(id, produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQuantidadeEstoque());
            return produtoRepository.save(produtoExistente);
        }
        return null;
    }

    public boolean excluirProduto(Long id){
        if(produtoRepository.existsById(id)){
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}