package com.guilhermemoraes.ecommerce.API.Ecommerce.services;

import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Carrinho;
import com.guilhermemoraes.ecommerce.API.Ecommerce.models.ItemCarrinho;
import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Produto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.repositories.CarrinhoRepository;
import com.guilhermemoraes.ecommerce.API.Ecommerce.repositories.ItemCarrinhoRepository;
import com.guilhermemoraes.ecommerce.API.Ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoService {

    @Autowired
    CarrinhoRepository carrinhoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItemCarrinhoRepository itemCarrinhoRepository;

    public Carrinho criarCarrinho(Carrinho carrinho) {
        return carrinhoRepository.save(carrinho);
    }

    public List<Carrinho> listarCarrinhos() {
        List<Carrinho> carrinhos = carrinhoRepository.findAll();
        return carrinhos;
    }

    public Carrinho adicionarItemAoCarrinho(Long idCarrinho, Long produtoId, int quantidade) {
        Carrinho carrinho = carrinhoRepository.findById(idCarrinho).orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (quantidade <= 0 || quantidade > produto.getQuantidadeEstoque()) {
            throw new RuntimeException("Quantidade inválida! Temos apenas " + produto.getQuantidadeEstoque() + " peças em estoque.");
        }

        Optional<ItemCarrinho> itemExistente = carrinho.getListaCarrinho().stream()
                .filter(item -> item.getProduto().equals(produto))
                .findFirst();

        if (itemExistente.isPresent()){
            ItemCarrinho itemCarrinhoExistente = itemExistente.get();
            int novaQuantidade = itemCarrinhoExistente.getQuantidadeProduto() + quantidade;
            itemCarrinhoExistente.setQuantidadeProduto(novaQuantidade);
        }else {
            ItemCarrinho novoItemCarrinho = new ItemCarrinho();
            novoItemCarrinho.setProduto(produto);
            novoItemCarrinho.setCarrinho(carrinho);
            novoItemCarrinho.setQuantidadeProduto(quantidade);
            itemCarrinhoRepository.save(novoItemCarrinho);
            System.out.println(carrinho);
            System.out.println(produto);
            carrinho.getListaCarrinho().add(novoItemCarrinho);
        }

        atualizarValorTotal(carrinho);
        atualizarQuantidadeEstoque(carrinho);
        return carrinhoRepository.save(carrinho);
    }

    private void atualizarValorTotal(Carrinho carrinho){
        double valorTotal = carrinho.getListaCarrinho().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidadeProduto())
                .sum();
        carrinho.setValorTotal(valorTotal);
    }

    private void atualizarQuantidadeEstoque(Carrinho carrinho){

        for (ItemCarrinho itemCarrinho : carrinho.getListaCarrinho()){
            Produto produto = itemCarrinho.getProduto();
            int quantidadeNocCarrinho = itemCarrinho.getQuantidadeProduto();

            if (quantidadeNocCarrinho <= 0 || quantidadeNocCarrinho > produto.getQuantidadeEstoque()){
                throw new RuntimeException("Quantidade no carrinho inválida");
            }

            int novaQuantidadeEstoque = produto.getQuantidadeEstoque() - quantidadeNocCarrinho;
            produto.setQuantidadeEstoque(novaQuantidadeEstoque);
        }

        carrinhoRepository.save(carrinho);
    }
}