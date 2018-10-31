package br.com.contaazul.bankslip.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.contaazul.bankslip.entity.Bankslip;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BankslipDetailResponse extends BankslipResponse {
		
	@JsonProperty("payment_date")
	private String paymentDate;
	
	private String fine;
		
	public BankslipDetailResponse(Bankslip bankslip) {
		super(bankslip);
		
		if(bankslip.getPaymentDate() != null) {
			this.paymentDate = bankslip.getPaymentDate().toString();
		}
		
		this.fine = bankslip.getFine().toString();
	}

}
