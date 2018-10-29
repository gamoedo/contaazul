package br.com.contaazul.boleto.controller.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.contaazul.boleto.entity.Boleto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BoletoDetalheResponse extends BoletoResponse {
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate paymentDate;
	
	private BigDecimal fine;
		
	public BoletoDetalheResponse(Boleto boleto) {	
		this.paymentDate = boleto.getPaymentDate();
		this.fine = BigDecimal.ZERO;
	}

}
