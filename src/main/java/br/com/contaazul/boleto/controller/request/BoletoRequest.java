package br.com.contaazul.boleto.controller.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import br.com.contaazul.boleto.entity.Boleto;
import br.com.contaazul.boleto.util.Utils;
import lombok.Getter;

@Getter
public class BoletoRequest {

	//TODO Verificar se tem como colocar como data e validar no Request
	@NotNull
	private String dueDate;
	@NotNull
	private String totalInCents;
	@NotNull
	private String customer;
	
	public Boleto toModel() {
		Boleto boleto = new Boleto();
		String pattern = "yyyy-MM-dd";
		
		boleto.setDueDate(Utils.converteStringParaLocalDate(this.dueDate, pattern));
		boleto.setTotalInCents(new BigDecimal(this.totalInCents));
		boleto.setCustomer(this.customer);
		return boleto;
	}
	
}
