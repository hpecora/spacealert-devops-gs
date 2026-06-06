package br.com.fiap.spacealert.repository;

import br.com.fiap.spacealert.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}