package br.com.contaazul.bankslip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.contaazul.bankslip.entity.Bankslip;

@Repository
public interface BankslipRepository extends JpaRepository<Bankslip, String>{
		
}
