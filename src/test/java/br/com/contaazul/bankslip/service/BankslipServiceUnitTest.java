package br.com.contaazul.bankslip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.entity.EnumStatus;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import br.com.contaazul.bankslip.repository.BankslipRepository;
import br.com.contaazul.bankslip.util.Utils;
import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BankslipServiceUnitTest {

    @MockBean
    private BankslipRepository bankslipRepository;

    @Autowired
    private BankslipService bankslipService;

    private Bankslip bankslip;

    private final String id = "c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89";

    @Before
    public void setUp() throws UnprocessableEntityException, NotFoundException {
	bankslip = new Bankslip();
	bankslip.setId("c2dbd236-3fa5-4ccc-9c12-bd0ae1d6dd89");
	bankslip.setCustomer("Ford Prefect Company");
	bankslip.setDueDate(Utils.convertStringToLocalDate("2018-05-10", "yyyy-MM-dd"));
	bankslip.setTotalInCents(BigDecimal.valueOf(99000));
	bankslip.setStatus(EnumStatus.PENDING);
	bankslip.setPaymentDate(Utils.convertStringToLocalDate("2018-05-13", "yyyy-MM-dd"));

	doReturn(bankslip).when(bankslipRepository).getOne(id);
    }

    @Test
    public void shouldCreateBankslip() throws Exception {
	bankslipService.createBankslip(new Bankslip());

	verify(bankslipRepository, times(1)).save(any(Bankslip.class));
    }

    @Test
    public void shouldPayBankslip() throws Exception {

	bankslipService.payBankslip(id, bankslip);

	assertThat(bankslip.getStatus()).isEqualTo(EnumStatus.PAID);

    }

    @Test
    public void shouldCancelBankslip() throws Exception {

	bankslipService.cancelBankslip(id);

	assertThat(bankslip.getStatus()).isEqualTo(EnumStatus.CANCELED);

    }

    @Test
    public void shouldDetailWithFineValueLessTenDays() throws NotFoundException {
	bankslip.setStatus(EnumStatus.PAID);

	bankslip = bankslipService.detailsBankslip(id);
	assertThat(bankslip.getFine()).isEqualTo(new BigDecimal(1485));

    }
    
    @Test
    public void shouldDetailWithFineValueMoreTenDays() throws NotFoundException {
	bankslip.setStatus(EnumStatus.PAID);
	bankslip.setPaymentDate(Utils.convertStringToLocalDate("2018-05-30", "yyyy-MM-dd"));

	bankslip = bankslipService.detailsBankslip(id);
	assertThat(bankslip.getFine()).isEqualTo(new BigDecimal(19800));

    }

    @Test
    public void shouldDetailWithoutPaymentBankslip() throws Exception {
	bankslip.setPaymentDate(null);
	bankslip.setStatus(EnumStatus.PENDING);

	bankslip = bankslipService.detailsBankslip(id);
	assertThat(bankslip.getFine()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void shouldDetailWithPaymentOnDayBankslip() throws Exception {
	bankslip.setDueDate(Utils.convertStringToLocalDate("2018-05-10", "yyyy-MM-dd"));
	bankslip.setPaymentDate(Utils.convertStringToLocalDate("2018-05-10", "yyyy-MM-dd"));
	bankslip.setStatus(EnumStatus.PAID);

	bankslip = bankslipService.detailsBankslip(id);
	assertThat(bankslip.getFine()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void shouldListBankslip() throws Exception {

	bankslipService.listBankslips();

	Bankslip bankslip2 = new Bankslip();
	bankslip2.setId("84e8adbf-1a14-403b-ad73-d78ae19b59bf");
	bankslip2.setCustomer("Zaphod Company");
	bankslip2.setDueDate(Utils.convertStringToLocalDate("2018-02-01", "yyyy-MM-dd"));
	bankslip2.setTotalInCents(BigDecimal.valueOf(200000));
	bankslip2.setStatus(EnumStatus.PAID);
	bankslip2.setPaymentDate(null);

	doReturn(Arrays.asList(bankslip, bankslip2)).when(bankslipRepository).findAll();

	List<Bankslip> listBankslips = bankslipService.listBankslips();

	assertThat(listBankslips, hasItems(bankslip, bankslip2));

	assertThat(listBankslips, containsInAnyOrder(bankslip, bankslip2));

    }
    
    @Test(expected=NotFoundException.class)
    public void shouldThrowNotFoundPayBankslip() throws Exception {

	String idNotFound = "55e8adcc-1a14-403b-ad73-d78ae19b59aa";
	
	bankslipService.payBankslip(idNotFound, bankslip);

    }

}
