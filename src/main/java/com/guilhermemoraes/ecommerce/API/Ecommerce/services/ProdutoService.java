package com.guilhermemoraes.ecommerce.API.Ecommerce.services;

import com.guilhermemoraes.ecommerce.API.Ecommerce.dtos.ProdutoDetalhadoDto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.dtos.ProdutosListarDto;
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

    public List<ProdutosListarDto> listarProdutos(){
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> new ProdutosListarDto(produto.getId(), produto.getNome(), produto.getPreco(), produto.getQuantidadeEstoque()))
                .collect(Collectors.toList());
    }

    public ProdutoDetalhadoDto obterProdutoPorId(Long id){
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        return optionalProduto.map(produto ->
                new ProdutoDetalhadoDto(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getQuantidadeEstoque()
                )
        ).orElse(null);
    }

    public Produto adicionarProduto(ProdutoDetalhadoDto produtoDto) {
        Produto produto = new Produto( produtoDto.nome(), produtoDto.descricao(), produtoDto.preco(), produtoDto.quantidadeEstoque());
        return produtoRepository.save(produto);
    }


    public Produto atualizarProduto(Long id, ProdutoDetalhadoDto produto){
        if (produtoRepository.existsById(id)){
            Produto produtoExistente = new Produto(id, produto.nome(), produto.descricao(), produto.preco(), produto.quantidadeEstoque());
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
