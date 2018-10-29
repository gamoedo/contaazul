package br.com.contaazul.boleto.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.contaazul.boleto.controller.request.BoletoRequest;
import br.com.contaazul.boleto.controller.response.BoletoDetalheResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponseList;
import br.com.contaazul.boleto.entity.Boleto;
import br.com.contaazul.boleto.entity.EnumStatus;
import br.com.contaazul.boleto.service.BoletoService;

@RestController
public class BoletoController {
	
	//@Autowired
	//BoletoDAO boletoDAO;
	
	@Autowired
	BoletoService boletoService;

	@PostMapping(value = "/rest/bankslips/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public BoletoResponse criarBoleto(@RequestBody @Valid BoletoRequest boletoRequest) {		
		
		return boletoService.criaBoleto(boletoRequest);		
	}
	
	@GetMapping(value = "/rest/bankslips/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)	
	public BoletoResponseList listarBoletos() {						
		return boletoService.listaBoletos();
	}
		
	@GetMapping(value = "/rest/bankslips/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public BoletoDetalheResponse verDetalhesBoleto(@RequestBody @PathVariable(name="id") String boletoId) {		
		return boletoService.veDetalhesBoleto(boletoId);							
	}	
	
	@PostMapping(value = "/rest/bankslips/{id}/payments", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public BoletoResponse pagarBoleto(@RequestBody @PathVariable(name="id") String boletoId) {
		return boletoService.pagaBoleto(boletoId);
	}
	
	@PostMapping(value = "/rest/bankslips/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public BoletoResponse cancelarBoleto(@RequestBody @PathVariable(name="id") String boletoId) {
		return boletoService.cancelaBoleto(boletoId);
	}

	
}
