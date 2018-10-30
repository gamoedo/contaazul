package br.com.contaazul.bankslip.controller.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;

@Getter
public class BankslipPaymentRequest {

	@NotNull
	private String paymentDate;

}
