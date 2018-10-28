package br.com.contaazul.boleto.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.contaazul.boleto.controller.request.BoletoRequest;
import br.com.contaazul.boleto.controller.response.BoletoResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponseList;
import br.com.contaazul.boleto.dao.BoletoDAO;
import br.com.contaazul.boleto.entity.Boleto;
import br.com.contaazul.boleto.entity.EnumStatus;

@RestController
public class BoletoController {
	
	@Autowired
	BoletoDAO boletoDAO;
	
	@GetMapping(value = "/rest/bankslips/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public BoletoResponseList getTemplate() {
						
		List<Boleto> listaBoletos = boletoDAO.findAll();		
		
		return new BoletoResponseList(listaBoletos);
	}
	
	@PostMapping(value = "/rest/bankslips/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public BoletoResponse criarBoleto(@RequestBody @Valid BoletoRequest boletoRequest) {
		
		Boleto boleto = new Boleto();		
		
		boleto = boletoRequest.toModel();
		boleto.setStatus(EnumStatus.PENDING);
				
		boletoDAO.save(boleto);
		
		return new BoletoResponse(boleto);		
	}

}
