package br.com.contaazul.bankslip.entity;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class BankslipUnitTest {

	@Test
	public void shouldCreateBankslip() throws Exception {
		EqualsVerifier.forClass(Bankslip.class).verify();
	}
	
}
