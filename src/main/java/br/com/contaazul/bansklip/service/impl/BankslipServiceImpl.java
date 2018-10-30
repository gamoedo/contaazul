package br.com.contaazul.bansklip.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contaazul.bankslip.controller.request.BankslipPaymentRequest;
import br.com.contaazul.bankslip.controller.request.BankslipRequest;
import br.com.contaazul.bankslip.controller.response.BankslipDetailResponse;
import br.com.contaazul.bankslip.controller.response.BankslipResponse;
import br.com.contaazul.bankslip.controller.response.BankslipResponseList;
import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.entity.EnumStatus;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import br.com.contaazul.bankslip.repository.BankslipRepository;
import br.com.contaazul.bankslip.service.BankslipService;
import br.com.contaazul.bankslip.util.Utils;
import javassist.NotFoundException;

@Service
public class BankslipServiceImpl implements BankslipService{

	private final BigDecimal FINE_LESS_TEN_DAYS = BigDecimal.valueOf(0,005);
	private final BigDecimal FINE_MORE_TEN_DAYS = BigDecimal.valueOf(0,01);
	
	@Autowired
	BankslipRepository bankslipRepository;

	@Override
	public BankslipResponse createBankslip(BankslipRequest bankslipRequest) throws UnprocessableEntityException {

		Bankslip bankslip = new Bankslip();		
		
		try {		
			bankslip = bankslipRequest.toModel();			
		}catch(Exception e) {
			throw new UnprocessableEntityException();
		}
		
		bankslip.setStatus(EnumStatus.PENDING);				
		bankslipRepository.save(bankslip);
		
		return new BankslipResponse(bankslip);					
	}
	
	@Override
	public BankslipResponseList listBankslips() {
		List<Bankslip> listBankslips = bankslipRepository.findAll();
		return new BankslipResponseList(listBankslips);		
	}
	@Override
	public BankslipDetailResponse detailsBankslip(String bankslipId) {
		
		Bankslip bankslip = bankslipRepository.getOne(bankslipId);		
		BankslipDetailResponse bankslipDetailResponse = new BankslipDetailResponse(bankslip);
		
		LocalDate todayDate = LocalDate.now();
		Long daysOfLate = Duration.between(todayDate.atStartOfDay(), bankslip.getDueDate().atStartOfDay()).toDays();		
		
		if(daysOfLate > 1 && daysOfLate <= 10) {
			
			BigDecimal fine = (bankslip.getTotalInCents().multiply(FINE_LESS_TEN_DAYS))
													   .multiply(new BigDecimal(daysOfLate))
													   .setScale(0, BigDecimal.ROUND_DOWN);						
			bankslipDetailResponse.setFine(fine); 

		}else if(daysOfLate > 10) {
			BigDecimal fine = (bankslip.getTotalInCents().multiply(FINE_MORE_TEN_DAYS))
													   .multiply(new BigDecimal(daysOfLate))
													   .setScale(0, BigDecimal.ROUND_DOWN);						
			bankslipDetailResponse.setFine(fine); 
		}
		
		return bankslipDetailResponse;
	}

	@Override
	public BankslipResponse payBankslip(String bankslipId, BankslipPaymentRequest bankslipPaymentRequest) throws NotFoundException {
		Bankslip bankslip = bankslipRepository.getOne(bankslipId);
		
		if(bankslip == null) {
			throw new NotFoundException(""); 
		}		
		
		LocalDate paymentDate = Utils.convertStringToLocalDate(bankslipPaymentRequest.getPaymentDate(), Utils.patternDate);
		
		bankslip.setStatus(EnumStatus.PAID);							
		bankslip.setPaymentDate(paymentDate);
				
		bankslipRepository.save(bankslip);				
		
		return null;
	}

	@Override
	public BankslipResponse cancelBankslip(String bankslipId) throws NotFoundException {

		Bankslip bankslip = bankslipRepository.getOne(bankslipId);
		
		if(bankslip == null) {
			throw new NotFoundException(""); 
		}		
		
		bankslip.setStatus(EnumStatus.CANCELED);				
		bankslipRepository.save(bankslip);			
		
		return null;
	}

}
