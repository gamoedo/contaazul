package br.com.contaazul.boleto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.contaazul.boleto.entity.Boleto;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, String>{
		
}
