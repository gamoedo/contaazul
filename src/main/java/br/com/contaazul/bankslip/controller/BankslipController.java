package br.com.contaazul.bankslip.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.contaazul.bankslip.controller.request.BankslipPaymentRequest;
import br.com.contaazul.bankslip.controller.request.BankslipRequest;
import br.com.contaazul.bankslip.controller.response.BankslipDetailResponse;
import br.com.contaazul.bankslip.controller.response.BankslipResponse;
import br.com.contaazul.bankslip.controller.response.BankslipResponseList;
import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import br.com.contaazul.bankslip.service.BankslipService;
import javassist.NotFoundException;

@RestController
public class BankslipController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BankslipService bankslipService;

    @PostMapping(value = "/rest/bankslips/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public BankslipResponse createBankslip(@RequestBody @Valid BankslipRequest bankslipRequest) throws UnprocessableEntityException {
	logger.info("createBankslip: Receiving bankslipRequest");

	Bankslip bankslip;

	try {
	    logger.info("createBankslip: Converting bankslipRequest to entity bankslip");
	    bankslip = bankslipRequest.toModel();
	} catch (Exception e) {
	    logger.info("createBankslip: Conversion failed.");
	    throw new UnprocessableEntityException();
	}

	bankslip = bankslipService.createBankslip(bankslip);
	logger.info("createBankslip: Converting bankslipResponse to entity bankslip");
	return new BankslipResponse(bankslip);
    }

    @GetMapping(value = "/rest/bankslips/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BankslipResponseList listBankslips() {
	logger.info("listBankslips: Producing listBankslips");
	List<Bankslip> listBankslips = bankslipService.listBankslips();
	return new BankslipResponseList(listBankslips);
    }

    @GetMapping(value = "/rest/bankslips/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public BankslipDetailResponse detailsBankslip(@RequestBody @PathVariable(name = "id") String bankslipId) throws NotFoundException {

	logger.info("detailsBankslip: Receiving bankslipId");
	Bankslip bankslip = bankslipService.detailsBankslip(bankslipId);
	logger.info("detailsBankslip: Converting entity bankslip to bankslipDetailResponse");
	return new BankslipDetailResponse(bankslip);

    }

    @PostMapping(value = "/rest/bankslips/{id}/payments", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void payBankslip(@RequestBody @Valid BankslipPaymentRequest bankslipPaymentRequest, @PathVariable(name = "id") String bankslipId) throws NotFoundException {

	logger.info("payBankslip: Receiving bankslipPaymentRequest and bankslipId");

	Bankslip bankslip;

	logger.info("createBankslip: Converting bankslipPaymentRequest to entity bankslip");
	bankslip = bankslipPaymentRequest.toModel();

	if (bankslip.getPaymentDate() == null) {
	    logger.info("createBankslip: Conversion failed.");
	    throw new UnprocessableEntityException();
	}

	bankslipService.payBankslip(bankslipId, bankslip);
    }

    @DeleteMapping(value = "/rest/bankslips/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void cancelBankslip(@RequestBody @PathVariable(name = "id") String bankslipId) throws NotFoundException {

	logger.info("cancelBankslip: Receiving bankslipId");
	bankslipService.cancelBankslip(bankslipId);
    
    }

}
