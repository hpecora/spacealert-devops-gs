package br.com.fiap.spacealert.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alertas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotBlank
    private String nivel;

    private LocalDateTime dataRegistro = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "regiao_id")
    private Regiao regiao;
}