package br.com.contaazul.boleto.service;

import br.com.contaazul.boleto.controller.request.BoletoRequest;
import br.com.contaazul.boleto.controller.response.BoletoDetalheResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponseList;
import br.com.contaazul.boleto.exception.UnprocessableEntityException;

public interface BoletoService {

	BoletoResponse criaBoleto(BoletoRequest boletoRequest) throws UnprocessableEntityException;
	
	BoletoResponseList listaBoletos();
	
	BoletoDetalheResponse veDetalhesBoleto(String boletoId);
	
	BoletoResponse pagaBoleto(String boletoId);
	
	BoletoResponse cancelaBoleto(String boletoId);
	
}
