package br.com.contaazul.bankslip.controller.response;

import java.util.ArrayList;

import br.com.contaazul.bankslip.entity.Bankslip;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BankslipResponseList extends ArrayList<BankslipResponse> {
	
	private static final long serialVersionUID = 1L;

	public BankslipResponseList(Iterable<Bankslip> bankslips) {
		if (bankslips != null) {
			for (Bankslip bankslip : bankslips) {
				add(new BankslipResponse(bankslip));
			}
		}
	}

}
