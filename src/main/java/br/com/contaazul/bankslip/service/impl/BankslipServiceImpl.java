package br.com.contaazul.bankslip.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contaazul.bankslip.controller.request.BankslipPaymentRequest;
import br.com.contaazul.bankslip.controller.request.BankslipRequest;
import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.entity.EnumStatus;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import br.com.contaazul.bankslip.repository.BankslipRepository;
import br.com.contaazul.bankslip.service.BankslipService;
import br.com.contaazul.bankslip.util.Utils;
import javassist.NotFoundException;

@Service
public class BankslipServiceImpl implements BankslipService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final BigDecimal FINE_LESS_TEN_DAYS = BigDecimal.valueOf(0.005);
	private final BigDecimal FINE_MORE_TEN_DAYS = BigDecimal.valueOf(0.01);

	@Autowired
	BankslipRepository bankslipRepository;

	@Override
	public Bankslip createBankslip(BankslipRequest bankslipRequest) throws UnprocessableEntityException {

		Bankslip bankslip = new Bankslip();

		try {
			bankslip = bankslipRequest.toModel();
		} catch (Exception e) {
			logger.info("createBankslip: Conversion failed.");
			throw new UnprocessableEntityException();
		}

		bankslip.setStatus(EnumStatus.PENDING);
		logger.info("createBankslip: Saving bankslip in the repository");
		bankslipRepository.save(bankslip);

		logger.info("createBankslip: Returning bankslip with ID '"+ bankslip.getId() +"' already saved");
		return bankslip;
	
	}

	@Override
	public List<Bankslip> listBankslips() {
		
		logger.info("listBankslips: Finding all the bankslips in the repository");
		List<Bankslip> listBankslips = bankslipRepository.findAll();
		logger.info("listBankslips: Returning a list of bankslips. Size: " + listBankslips.size());
		return listBankslips;
	
	}

	@Override
	public Bankslip detailsBankslip(String bankslipId) throws NotFoundException {

		Bankslip bankslip = getBankslipFromId(bankslipId);

		if (bankslip.getStatus() == EnumStatus.PAID) {
			
			logger.info("detailsBankslip: Calculating the days of late ");
			
			Long daysOfLate = Duration
					.between(bankslip.getDueDate().atStartOfDay(), bankslip.getPaymentDate().atStartOfDay()).toDays();

			logger.info("detailsBankslip: Days Of Late to bankslip payment: " + daysOfLate);
			
			if (daysOfLate > 1 && daysOfLate <= 10) {

				logger.info("detailsBankslip: Calculating the amount fine at 0,5%: ");
				
				BigDecimal fine = (bankslip.getTotalInCents().multiply(FINE_LESS_TEN_DAYS))
						.multiply(new BigDecimal(daysOfLate)).setScale(0, BigDecimal.ROUND_DOWN);
				bankslip.setFine(fine);

			} else if (daysOfLate > 10) {
				
				logger.info("detailsBankslip: Calculating the amount fine at 1,0%: ");
				
				BigDecimal fine = (bankslip.getTotalInCents().multiply(FINE_MORE_TEN_DAYS))
						.multiply(new BigDecimal(daysOfLate)).setScale(0, BigDecimal.ROUND_DOWN);
				bankslip.setFine(fine);
			}
			
		}

		logger.info("detailsBankslip: Returning the bankslip detail with ID: '" + bankslip.getId() +"'");
		return bankslip;
	
	}

	@Override
	public void payBankslip(String bankslipId, BankslipPaymentRequest bankslipPaymentRequest)
			throws NotFoundException {
		
		Bankslip bankslip = getBankslipFromId(bankslipId);

		logger.info("payBankslip: Converting the bankslipPaymentRequest and getting the paymentDate");
		LocalDate paymentDate = Utils.convertStringToLocalDate(bankslipPaymentRequest.getPayment_date(),
				Utils.patternDate);

		bankslip.setStatus(EnumStatus.PAID);
		bankslip.setPaymentDate(paymentDate);
		
		logger.info("payBankslip: Saving bankslip with paymentDate and status PAID in the repository");
		bankslipRepository.save(bankslip);
	
	}

	@Override
	public void cancelBankslip(String bankslipId) throws NotFoundException {

		Bankslip bankslip = getBankslipFromId(bankslipId);
		bankslip.setStatus(EnumStatus.CANCELED);
		
		logger.info("payBankslip: Saving bankslip with status CANCELED in the repository");
		bankslipRepository.save(bankslip);

	}

	protected Bankslip getBankslipFromId(String bankslipId) throws NotFoundException {

		logger.info("getBankslipFromId: Finding bankslipID '"+ bankslipId +"' in the repository");
		
		Bankslip bankslip = bankslipRepository.findById(bankslipId).orElse(null);

		if (bankslip == null) {
			throw new NotFoundException("");
		}

		logger.info("getBankslipFromId: Returning bankslipID '"+ bankslipId +"'");
		return bankslip;

	}

}
