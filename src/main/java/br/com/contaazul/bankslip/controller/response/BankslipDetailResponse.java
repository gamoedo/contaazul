package br.com.contaazul.bankslip.controller.response;

import br.com.contaazul.bankslip.entity.Bankslip;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BankslipDetailResponse extends BankslipResponse {
		
	private String payment_date;
	
	private String fine;
		
	public BankslipDetailResponse(Bankslip bankslip) {
		super(bankslip);
		this.payment_date = bankslip.getPaymentDate().toString();
		this.fine = bankslip.getFine().toString();
	}

}
