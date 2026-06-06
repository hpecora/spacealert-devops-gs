package br.com.fiap.spacealert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "regioes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Regiao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String estado;

    @NotBlank
    private String pais;

    private String tipoMonitoramento;

    @JsonIgnore
    @OneToMany(mappedBy = "regiao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alerta> alertas = new ArrayList<>();
}