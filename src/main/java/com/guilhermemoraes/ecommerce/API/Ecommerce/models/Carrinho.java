package com.guilhermemoraes.ecommerce.API.Ecommerce.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ManyToOne
//    @JoinColumn(name = "usuario_id")
//    private Usuario usuario;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCarrinho;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<ItemCarrinho> listaCarrinho = new ArrayList<>();

    private double valorTotal = 0;
}