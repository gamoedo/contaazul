package br.com.contaazul.bankslip.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.contaazul.bankslip.controller.request.BankslipPaymentRequest;
import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.entity.EnumStatus;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import br.com.contaazul.bankslip.helper.JsonHelper;
import br.com.contaazul.bankslip.service.BankslipService;
import br.com.contaazul.bankslip.util.Utils;
import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@WebMvcTest(BankslipController.class)
@EnableSpringDataWebSupport
public class BankslipControllerUnitTest {

    @MockBean
    private BankslipService bankslipService;

    @Autowired
    private MockMvc mvc;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Bankslip bankslip;

    @Before
    public void setUp() throws UnprocessableEntityException, NotFoundException {
	bankslip = new Bankslip();
	bankslip.setId("84e8adbf-1a14-403b-ad73-d78ae19b59bf");
	bankslip.setCustomer("Ford Prefect Company");
	bankslip.setDueDate(Utils.convertStringToLocalDate("2018-05-10", "yyyy-MM-dd"));
	bankslip.setTotalInCents(BigDecimal.valueOf(99000));
	bankslip.setStatus(EnumStatus.PENDING);
	bankslip.setPaymentDate(null);

	doReturn(bankslip).when(bankslipService).createBankslip(any(Bankslip.class));
	doReturn(Arrays.asList(bankslip)).when(bankslipService).listBankslips();
	doReturn(bankslip).when(bankslipService).detailsBankslip(eq("84e8adbf-1a14-403b-ad73-d78ae19b59bf"));
	
	doThrow(new NotFoundException("")).when(bankslipService)
		.payBankslip(eq("123456789-1a14-403b-ad73-d78ae19b59bf"),(any(Bankslip.class)));
    }

    @Test
    public void shouldCreateBankslip() throws Exception {
	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_request.json");
	String response = JsonHelper.getResponseFileAsString("bankslip/create_bankslip_response_success.json");

	mvc.perform(post("/rest/bankslips/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isCreated()).andExpect(content().json(response));

	verify(bankslipService, times(1)).createBankslip(any(Bankslip.class));
    }

    @Test
    public void shouldListBankslip() throws Exception {

	Bankslip bankslip2 = new Bankslip();
	bankslip2.setId("c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89");
	bankslip2.setCustomer("Zaphod Company");
	bankslip2.setDueDate(Utils.convertStringToLocalDate("2018-02-01", "yyyy-MM-dd"));
	bankslip2.setTotalInCents(BigDecimal.valueOf(200000));
	bankslip2.setStatus(EnumStatus.PAID);
	bankslip2.setPaymentDate(null);

	doReturn(Arrays.asList(bankslip, bankslip2)).when(bankslipService).listBankslips();

	String response = JsonHelper.loadResponse("bankslip/list_bankslip_response.json");

	mvc.perform(get("/rest/bankslips/").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(content().json(response));

	verify(bankslipService, times(1)).listBankslips();
    }

    @Test
    public void shouldDetailWithoutPaymentBankslip() throws Exception {

	String response = JsonHelper.getResponseFileAsString("bankslip/create_bankslip_notpaid_response_detail.json");

	mvc.perform(get("/rest/bankslips/84e8adbf-1a14-403b-ad73-d78ae19b59bf").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andExpect(content().json(response));

	verify(bankslipService, times(1)).detailsBankslip(eq("84e8adbf-1a14-403b-ad73-d78ae19b59bf"));
    }

    @Test
    public void shouldDetailWithPaymentBankslip() throws Exception {

	bankslip.setStatus(EnumStatus.PAID);
	bankslip.setPaymentDate(Utils.convertStringToLocalDate("2018-05-13", "yyyy-MM-dd"));
	bankslip.setFine(BigDecimal.valueOf(1485));

	String response = JsonHelper.getResponseFileAsString("bankslip/create_bankslip_paid_response_detail.json");

	mvc.perform(get("/rest/bankslips/84e8adbf-1a14-403b-ad73-d78ae19b59bf").accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk()).andExpect(content().json(response));

	verify(bankslipService, times(1)).detailsBankslip(eq("84e8adbf-1a14-403b-ad73-d78ae19b59bf"));
    }

    @Test
    public void shouldPayBankslip() throws Exception {

	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_payment_request.json");

	bankslip.setStatus(EnumStatus.PAID);
	bankslip.setPaymentDate(Utils.convertStringToLocalDate("2018-05-15", "yyyy-MM-dd"));

	mvc.perform(post("/rest/bankslips/84e8adbf-1a14-403b-ad73-d78ae19b59bf/payments").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request)).andExpect(status().isNoContent());

	verify(bankslipService, times(1)).payBankslip(eq("84e8adbf-1a14-403b-ad73-d78ae19b59bf"), (any(Bankslip.class)));
    }

    @Test
    public void shouldCancelBankslip() throws Exception {
	mvc.perform(delete("/rest/bankslips/84e8adbf-1a14-403b-ad73-d78ae19b59bf")).andExpect(status().isNoContent());

	verify(bankslipService, times(1)).cancelBankslip("84e8adbf-1a14-403b-ad73-d78ae19b59bf");
    }

    @Test
    public void shouldThrowUnprocessableEntityWhenInvalidDueDate() throws Exception {
	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_request_with_invalid_duedate.json");
	String response = JsonHelper.loadResponse("bankslip/unprocessable_entity.json");

	mvc.perform(post("/rest/bankslips/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isUnprocessableEntity()).andExpect(content().json(response));

    }

    @Test
    public void shouldThrowUnprocessableEntityWhenInvalidTotalInCents() throws Exception {
	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_request_with_invalid_totalincents.json");
	String response = JsonHelper.loadResponse("bankslip/unprocessable_entity.json");

	mvc.perform(post("/rest/bankslips/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isUnprocessableEntity()).andExpect(content().json(response));

    }

    @Test
    public void shouldThrowBadRequestWithoutCustomer() throws Exception {
	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_request_without_customer.json");
	String response = JsonHelper.loadResponse("bankslip/bad_request.json");

	mvc.perform(post("/rest/bankslips/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isBadRequest()).andExpect(content().json(response));

    }

    @Test
    public void shouldThrowBadRequestWithoutDueDate() throws Exception {
	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_request_without_duedate.json");
	String response = JsonHelper.loadResponse("bankslip/bad_request.json");

	mvc.perform(post("/rest/bankslips/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request).accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isBadRequest()).andExpect(content().json(response));

    }

    @Test
    public void shouldThrowBadRequestWithoutTotalInCents() throws Exception {
	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_request_without_totalincents.json");
	String response = JsonHelper.loadResponse("bankslip/bad_request.json");

	mvc.perform(post("/rest/bankslips/").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		.content(request).accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
	.andExpect(status().isBadRequest())
	.andExpect(content().json(response));

    }

    @Test
    public void shouldThrowNotFoundWithIncorrectID() throws Exception {

	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_payment_request.json");
	String response = JsonHelper.loadResponse("bankslip/not_found.json");

	bankslip.setStatus(EnumStatus.PAID);
	bankslip.setPaymentDate(Utils.convertStringToLocalDate("2018-05-15", "yyyy-MM-dd"));

	mvc.perform(post("/rest/bankslips/123456789-1a14-403b-ad73-d78ae19b59bf/payments")
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		.content(request))
		.andExpect(status().isNotFound())
		.andExpect(content().json(response));

	
	
	verify(bankslipService, times(1)).payBankslip(eq("123456789-1a14-403b-ad73-d78ae19b59bf"), (any(Bankslip.class)));
    }
    
    @Test
    public void shouldThrowNotFoundWithIncorrectPaymentDate() throws Exception {

	String request = JsonHelper.getRequestFileAsString("bankslip/create_bankslip_payment_request_with_invalid_paymentdate.json");
	String response = JsonHelper.loadResponse("bankslip/not_found.json");

	mvc.perform(post("/rest/bankslips/123456789-1a14-403b-ad73-d78ae19b59bf/payments")
		.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
		.content(request))
		.andExpect(status().isNotFound())
		.andExpect(content().json(response));

	verify(bankslipService, times(1)).payBankslip(eq("123456789-1a14-403b-ad73-d78ae19b59bf"), (any(Bankslip.class)));
    }

}
