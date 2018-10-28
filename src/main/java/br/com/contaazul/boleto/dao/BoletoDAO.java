package br.com.contaazul.boleto.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.contaazul.boleto.entity.Boleto;

@Repository
public interface BoletoDAO extends JpaRepository<Boleto, String>{
		
}
