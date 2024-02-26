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

    public Carrinho adicionarItemAoCarrinho(Long idCarrinho, Long idProduto, int quantidade) {
        Carrinho carrinho = carrinhoRepository.findById(idCarrinho)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

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
            carrinho.getListaCarrinho().add(novoItemCarrinho);
        }

        atualizarValorTotal(carrinho);
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho removerItemDoCarrinho(Long idCarrinho, Long idItemCarrinho){

        Carrinho carrinho = carrinhoRepository.findById(idCarrinho)
                .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

        ItemCarrinho itemCarrinho = itemCarrinhoRepository.findById(idItemCarrinho)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        List<ItemCarrinho> listaCarrinho = carrinho.getListaCarrinho();

        listaCarrinho.removeIf(item -> item.equals(itemCarrinho));

        double valorTotalAtualizado = carrinho.getValorTotal() - (itemCarrinho.getProduto().getPreco() * itemCarrinho.getQuantidadeProduto());

        carrinho.setValorTotal(valorTotalAtualizado);
        itemCarrinhoRepository.deleteById(idItemCarrinho);

        carrinhoRepository.save(carrinho);
        return carrinho;
    }

    public Carrinho atualizarQuantidadeItemCarrinho(Long idCarrinho, Long idItemCarrinho){

        Carrinho carrinhoCarregado = carrinhoRepository.findById(idCarrinho)
                    .orElseThrow(() -> new RuntimeException("Carrinho não encontrado"));

            List<ItemCarrinho> listaCarrinho = carrinhoCarregado.getListaCarrinho();

            listaCarrinho.replaceAll(item ->{
                if (item.getIdItemCarrinho().equals(idItemCarrinho)){
                    int novaQuantidade = item.getQuantidadeProduto() - 1;
                    item.setQuantidadeProduto(Math.max(novaQuantidade, 0));
                }
                itemCarrinhoRepository.save(item);
                return item;
            });

            atualizarValorTotal(carrinhoCarregado);
            return carrinhoRepository.save(carrinhoCarregado);
    }

    private void atualizarValorTotal(Carrinho carrinho){
        double valorTotal = carrinho.getListaCarrinho().stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidadeProduto())
                .sum();
        carrinho.setValorTotal(valorTotal);
    }

    public Optional<Carrinho> buscarPorId(Long idCarinho) {
        Optional<Carrinho> carrinho = carrinhoRepository.findById(idCarinho);
        return carrinho;
    }

//    private void atualizarQuantidadeEstoque(Carrinho carrinho){
//
//        for (ItemCarrinho itemCarrinho : carrinho.getListaCarrinho()){
//            Produto produto = itemCarrinho.getProduto();
//            int quantidadeNocCarrinho = itemCarrinho.getQuantidadeProduto();
//
//            if (quantidadeNocCarrinho <= 0 || quantidadeNocCarrinho > produto.getQuantidadeEstoque()){
//                throw new RuntimeException("Quantidade no carrinho inválida");
//            }
//
//            int novaQuantidadeEstoque = produto.getQuantidadeEstoque() - quantidadeNocCarrinho;
//            produto.setQuantidadeEstoque(novaQuantidadeEstoque);
//        }
//
//        carrinhoRepository.save(carrinho);
//    }
}