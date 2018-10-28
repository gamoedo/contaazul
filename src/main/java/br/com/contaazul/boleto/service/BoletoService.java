package br.com.contaazul.boleto.service;

import java.util.Collection;

import br.com.contaazul.boleto.controller.request.BoletoRequest;
import br.com.contaazul.boleto.controller.response.BoletoResponse;

public interface BoletoService {

	BoletoResponse criaBoleto(BoletoRequest boletoRequest);
	
	Collection<BoletoResponse> listaBoletos();
	
	BoletoResponse veDetalhesBoleto(BoletoRequest boletoRequest);
	
	BoletoResponse pagaBoleto(BoletoRequest boletoRequest);
	
	BoletoResponse cancelaBoleto(BoletoRequest boletoRequest);
	
}
