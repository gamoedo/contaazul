package br.com.contaazul.bankslip.controller.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.util.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BankslipDetailResponse extends BankslipResponse {
	
	@DateTimeFormat(pattern=Utils.patternDate)
	private LocalDate paymentDate;
	
	private BigDecimal fine;
		
	public BankslipDetailResponse(Bankslip bankslip) {
		super(bankslip);
		this.paymentDate = bankslip.getPaymentDate();
		this.fine = BigDecimal.ZERO;
	}

}
