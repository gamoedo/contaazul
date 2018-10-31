package br.com.contaazul.bankslip.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.contaazul.bankslip.controller.request.BankslipRequest;
import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import br.com.contaazul.bankslip.repository.BankslipRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankslipServiceUnitTest {

	@MockBean
	private BankslipRepository bankslipRepository;
	
	@Autowired
	private BankslipService bankslipService;
	
	@Test
	public void shouldCreateBankslip() throws Exception {		
		bankslipService.createBankslip(new Bankslip());
				
		verify(bankslipRepository, times(1)).save(any(Bankslip.class));
	}
	
	@Test(expected=UnprocessableEntityException.class)
	public void shouldThrowUnprocessableEntityWhenParametersAreIncorrect() {

		BankslipRequest bankslipRequest = new BankslipRequest();
		//bankslipRequest.set
		
	}
//	
//	@Test
//	public void shouldListBankslip() throws Exception {	
//		
//	}
//	
//	@Test
//	public void shouldDetailWithoutPaymentBankslip() throws Exception {		
//
//	}
//	
//	@Test
//	public void shouldDetailWithPaymentBankslip() throws Exception {		
//
//	}
//	
//	@Test
//	public void shouldPayBankslip() throws Exception {
//		
//	}
//	
//	@Test
//	public void shouldCancelBankslip() throws Exception {
//
//	}
//	
}
