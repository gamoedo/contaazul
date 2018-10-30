package br.com.contaazul.bankslip.controller.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.util.Utils;
import lombok.Getter;

@Getter
public class BankslipRequest {

	@NotNull
	private String dueDate;
	@NotNull
	private String totalInCents;
	@NotNull
	private String customer;
	
	public Bankslip toModel() {
		Bankslip bankslip = new Bankslip();
		
		bankslip.setDueDate(Utils.convertStringToLocalDate(this.dueDate, Utils.patternDate));
		bankslip.setTotalInCents(new BigDecimal(this.totalInCents));
		bankslip.setCustomer(this.customer);
		return bankslip;
	}
	
}
