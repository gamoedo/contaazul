package br.com.contaazul.bankslip.controller.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.exception.UnprocessableEntityException;
import br.com.contaazul.bankslip.util.Utils;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankslipRequest {

    @NotNull
    @JsonProperty("due_date")
    private String dueDate;
    @NotNull
    @JsonProperty("total_in_cents")
    private String totalInCents;
    @NotNull
    private String customer;

    public Bankslip toModel() {
	Bankslip bankslip = new Bankslip();
	
	if(Utils.convertStringToLocalDate(this.dueDate, Utils.patternDate) == null) {
	    throw new UnprocessableEntityException();
	}
	
	bankslip.setDueDate(Utils.convertStringToLocalDate(this.dueDate, Utils.patternDate));
	bankslip.setTotalInCents(new BigDecimal(this.totalInCents));
	bankslip.setCustomer(this.customer);

	return bankslip;
    }

}
