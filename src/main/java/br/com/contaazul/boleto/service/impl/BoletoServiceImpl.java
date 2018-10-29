package br.com.contaazul.boleto.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contaazul.boleto.controller.request.BoletoRequest;
import br.com.contaazul.boleto.controller.response.BoletoDetalheResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponse;
import br.com.contaazul.boleto.controller.response.BoletoResponseList;
import br.com.contaazul.boleto.dao.BoletoDAO;
import br.com.contaazul.boleto.entity.Boleto;
import br.com.contaazul.boleto.entity.EnumStatus;
import br.com.contaazul.boleto.service.BoletoService;

@Service
public class BoletoServiceImpl implements BoletoService{

	private final BigDecimal MULTA_MENOS_DEZ_DIAS = BigDecimal.valueOf(0,005);
	private final BigDecimal MULTA_MAIS_DEZ_DIAS = BigDecimal.valueOf(0,01);
	
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
	public BoletoResponseList listaBoletos() {
		List<Boleto> listaBoletos = boletoDAO.findAll();
		return new BoletoResponseList(listaBoletos);		
	}
	@Override
	public BoletoDetalheResponse veDetalhesBoleto(String boletoId) {
		
		Boleto boleto = boletoDAO.getOne(boletoId);		
		BoletoDetalheResponse boletoDetalheResponse = new BoletoDetalheResponse(boleto);
		
		LocalDate dataHoje = LocalDate.now();
		Long diasAtraso = Duration.between(dataHoje.atStartOfDay(), boleto.getDueDate().atStartOfDay()).toDays();		
		
		if(diasAtraso > 1 && diasAtraso <= 10) {
			
			BigDecimal fine = (boleto.getTotalInCents().multiply(MULTA_MENOS_DEZ_DIAS))
													   .multiply(new BigDecimal(diasAtraso))
													   .setScale(0, BigDecimal.ROUND_DOWN);						
			boletoDetalheResponse.setFine(fine); 

		}else if(diasAtraso > 10) {
			BigDecimal fine = (boleto.getTotalInCents().multiply(MULTA_MAIS_DEZ_DIAS))
													   .multiply(new BigDecimal(diasAtraso))
													   .setScale(0, BigDecimal.ROUND_DOWN);						
			boletoDetalheResponse.setFine(fine); 
		}
		
		return boletoDetalheResponse;
	}

	@Override
	public BoletoResponse pagaBoleto(String boletoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoletoResponse cancelaBoleto(String boletoId) {
		// TODO Auto-generated method stub
		return null;
	}




}
