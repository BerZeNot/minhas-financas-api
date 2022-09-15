package com.pgoliveira.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgoliveira.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
