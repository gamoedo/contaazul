package br.com.contaazul.bankslip.entity;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class BankslipUnitTest {

    @Test
    public void shouldCreateBankslip() throws Exception {
	EqualsVerifier.forClass(Bankslip.class).verify();
    }

    @Test
    public void testToString() {
	Bankslip bankslip = new Bankslip();
	bankslip.setId("TestId");
	bankslip.setCustomer("customerTest");
	String expected = "Bankslip(id=TestId, dueDate=null, totalInCents=null, customer=customerTest, status=null, paymentDate=null, fine=0)";
	assertEquals(expected, bankslip.toString());
    }
    
    @Test
    public void testEnumStatusCode() {		
	Integer expected = 2;
	assertEquals(expected, EnumStatus.CANCELED.getCode());
    }

}
