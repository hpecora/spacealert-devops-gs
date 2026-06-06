package br.com.fiap.spacealert.controller;

import br.com.fiap.spacealert.model.Alerta;
import br.com.fiap.spacealert.model.Regiao;
import br.com.fiap.spacealert.repository.AlertaRepository;
import br.com.fiap.spacealert.repository.RegiaoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaRepository alertaRepository;
    private final RegiaoRepository regiaoRepository;

    public AlertaController(AlertaRepository alertaRepository, RegiaoRepository regiaoRepository) {
        this.alertaRepository = alertaRepository;
        this.regiaoRepository = regiaoRepository;
    }

    @GetMapping
    public List<Alerta> listar() {
        return alertaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Long id) {
        return alertaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/regiao/{regiaoId}")
    public ResponseEntity<Alerta> criar(@PathVariable Long regiaoId, @Valid @RequestBody Alerta alerta) {
        return regiaoRepository.findById(regiaoId)
                .map(regiao -> {
                    alerta.setRegiao(regiao);
                    return ResponseEntity.ok(alertaRepository.save(alerta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/regiao/{regiaoId}")
    public ResponseEntity<Alerta> atualizar(
            @PathVariable Long id,
            @PathVariable Long regiaoId,
            @Valid @RequestBody Alerta dados
    ) {
        Alerta alerta = alertaRepository.findById(id).orElse(null);
        Regiao regiao = regiaoRepository.findById(regiaoId).orElse(null);

        if (alerta == null || regiao == null) {
            return ResponseEntity.notFound().build();
        }

        alerta.setTitulo(dados.getTitulo());
        alerta.setDescricao(dados.getDescricao());
        alerta.setNivel(dados.getNivel());
        alerta.setRegiao(regiao);

        return ResponseEntity.ok(alertaRepository.save(alerta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!alertaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        alertaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}