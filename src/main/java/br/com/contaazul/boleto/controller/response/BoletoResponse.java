package br.com.contaazul.boleto.controller.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.contaazul.boleto.entity.Boleto;
import br.com.contaazul.boleto.entity.EnumStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BoletoResponse {
	
	private String id;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dueDate;
	
	private BigDecimal totalInCents;
	
	private String customer;
	
	private EnumStatus status;
	
	public BoletoResponse(Boleto boleto) {
		this.id = boleto.getId();
		this.dueDate = boleto.getDueDate();		
		this.totalInCents = boleto.getTotalInCents();		
		this.customer = boleto.getCustomer() ;		
		this.status = boleto.getStatus();
	}

}
