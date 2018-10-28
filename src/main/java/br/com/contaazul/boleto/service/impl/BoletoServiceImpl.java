package br.com.contaazul.boleto.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contaazul.boleto.controller.request.BoletoRequest;
import br.com.contaazul.boleto.controller.response.BoletoResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponseList;
import br.com.contaazul.boleto.dao.BoletoDAO;
import br.com.contaazul.boleto.entity.Boleto;
import br.com.contaazul.boleto.entity.EnumStatus;
import br.com.contaazul.boleto.service.BoletoService;

@Service
public class BoletoServiceImpl implements BoletoService{

	@Autowired
	BoletoDAO boletoDAO;

	@Override
	public BoletoResponse criaBoleto(BoletoRequest boletoRequest) {

		Boleto boleto = new Boleto();		
		
		boleto = boletoRequest.toModel();
		boleto.setStatus(EnumStatus.PENDING);
				
		boletoDAO.save(boleto);
		
		return new BoletoResponse(boleto);					
	}
	
	@Override
	public Collection<BoletoResponse> listaBoletos() {
		List<Boleto> listaBoletos = boletoDAO.findAll();
		return new BoletoResponseList(listaBoletos);		
	}
	@Override
	public BoletoResponse veDetalhesBoleto(BoletoRequest boletoRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoletoResponse pagaBoleto(BoletoRequest boletoRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoletoResponse cancelaBoleto(BoletoRequest boletoRequest) {
		// TODO Auto-generated method stub
		return null;
	}




}
