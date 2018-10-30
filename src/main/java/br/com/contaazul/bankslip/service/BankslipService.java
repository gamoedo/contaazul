package br.com.contaazul.bankslip.service;

import br.com.contaazul.bankslip.controller.request.BankslipPaymentRequest;
import br.com.contaazul.bankslip.controller.request.BankslipRequest;
import br.com.contaazul.bankslip.controller.response.BankslipDetailResponse;
import br.com.contaazul.bankslip.controller.response.BankslipResponse;
import br.com.contaazul.bankslip.controller.response.BankslipResponseList;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import javassist.NotFoundException;

public interface BankslipService {

	BankslipResponse createBankslip(BankslipRequest bankslipRequest) throws UnprocessableEntityException;
	
	BankslipResponseList listBankslips();
	
	BankslipDetailResponse detailsBankslip(String bankslipId) throws NotFoundException;
	
	void payBankslip(String bankslipId, BankslipPaymentRequest bankslipPaymentRequest) throws NotFoundException;
	
	void cancelBankslip(String bankslipId) throws NotFoundException;
	
}
