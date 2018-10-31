package br.com.contaazul.bankslip.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.contaazul.bankslip.entity.Bankslip;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BankslipResponse {

    private String id;

    @JsonProperty("due_date")
    private String dueDate;

    @JsonProperty("total_in_cents")
    private String totalInCents;

    private String customer;

    private String status;

    public BankslipResponse(Bankslip bankslip) {

	this.id = bankslip.getId();
	this.dueDate = bankslip.getDueDate().toString();
	this.totalInCents = bankslip.getTotalInCents().toString();
	this.customer = bankslip.getCustomer();
	this.status = bankslip.getStatus().name();

    }

}
