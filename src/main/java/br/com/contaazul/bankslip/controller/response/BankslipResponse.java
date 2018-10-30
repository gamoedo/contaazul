package br.com.contaazul.bankslip.controller.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.entity.EnumStatus;
import br.com.contaazul.bankslip.util.Utils;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankslipResponse {
	
	private String id;
	
	@DateTimeFormat(pattern=Utils.patternDate)
	private LocalDate dueDate;
	
	private BigDecimal totalInCents;
	
	private String customer;
	
	private EnumStatus status;
	
	public BankslipResponse(Bankslip bankslip) {
		this.id = bankslip.getId();
		this.dueDate = bankslip.getDueDate();		
		this.totalInCents = bankslip.getTotalInCents();		
		this.customer = bankslip.getCustomer() ;		
		this.status = bankslip.getStatus();
	}

}
