package br.com.contaazul.bankslip.controller.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.contaazul.bankslip.entity.Bankslip;
import br.com.contaazul.bankslip.util.Utils;
import lombok.Getter;

@Getter
public class BankslipPaymentRequest {

    @NotNull
    @JsonProperty("payment_date")
    private String paymentDate;

    public Bankslip toModel() {
	Bankslip bankslip = new Bankslip();
	bankslip.setPaymentDate(Utils.convertStringToLocalDate(this.paymentDate, Utils.patternDate));

	return bankslip;
    }

}
