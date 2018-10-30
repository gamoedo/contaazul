package br.com.contaazul.bankslip.controller.response;

import br.com.contaazul.bankslip.entity.Bankslip;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankslipResponse {
	
	private String id;
		
	private String due_date;
	
	private String total_in_cents;
	
	private String customer;
	
	private String status;
	
	public BankslipResponse(Bankslip bankslip) {
		this.id = bankslip.getId();
		this.due_date = bankslip.getDueDate().toString();		
		this.total_in_cents = bankslip.getTotalInCents().toString();		
		this.customer = bankslip.getCustomer();		
		this.status = bankslip.getStatus().name();
	}

}
