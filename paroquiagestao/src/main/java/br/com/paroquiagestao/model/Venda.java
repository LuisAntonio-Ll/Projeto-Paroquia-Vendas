package br.com.paroquiagestao.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Venda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    private Integer quantidade;
    private Double valorTotal;

    @ManyToOne(optional = false)
    private Produto produto;

    @ManyToOne(optional = false)
    private Grupo grupo;

    public Double calcularTotal() {
        return quantidade * produto.getPrecoVenda();
    }
}