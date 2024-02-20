package com.guilhermemoraes.ecommerce.API.Ecommerce.controllers;

import com.guilhermemoraes.ecommerce.API.Ecommerce.dtos.ProdutoDto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.dtos.ProdutoListarDto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.models.Produto;
import com.guilhermemoraes.ecommerce.API.Ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public List<ProdutoListarDto> listarProdutos(){
        return produtoService.listarProdutos();
    }

    @GetMapping("/{id}")
    public ProdutoDto obterProdutoDetalhadoPorId(@PathVariable Long id){
        return produtoService.obterProdutoPorId(id);
    }

    @PostMapping("/adicionar")
    public ResponseEntity<Produto> criarProduto(@RequestBody ProdutoDto productDTO) {
        Produto produtoSalvo = produtoService.adicionarProduto(productDTO);
        return ResponseEntity.ok(produtoSalvo);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Produto> alterarProduto (@PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoAlterado = produtoService.atualizarProduto(id, produto);
        if (produtoAlterado != null){
            return ResponseEntity.ok(produtoAlterado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deletar = produtoService.excluirProduto(id);
        if (deletar) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}