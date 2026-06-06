package br.com.fiap.spacealert.controller;

import br.com.fiap.spacealert.model.Regiao;
import br.com.fiap.spacealert.repository.RegiaoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/regioes")
public class RegiaoController {

    private final RegiaoRepository regiaoRepository;

    public RegiaoController(RegiaoRepository regiaoRepository) {
        this.regiaoRepository = regiaoRepository;
    }

    @GetMapping
    public List<Regiao> listar() {
        return regiaoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Regiao> buscarPorId(@PathVariable Long id) {
        return regiaoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Regiao criar(@Valid @RequestBody Regiao regiao) {
        return regiaoRepository.save(regiao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Regiao> atualizar(@PathVariable Long id, @Valid @RequestBody Regiao dados) {
        return regiaoRepository.findById(id)
                .map(regiao -> {
                    regiao.setNome(dados.getNome());
                    regiao.setEstado(dados.getEstado());
                    regiao.setPais(dados.getPais());
                    regiao.setTipoMonitoramento(dados.getTipoMonitoramento());
                    return ResponseEntity.ok(regiaoRepository.save(regiao));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!regiaoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        regiaoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}