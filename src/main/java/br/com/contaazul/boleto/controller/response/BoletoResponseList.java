package br.com.contaazul.boleto.controller.response;

import java.util.ArrayList;

import br.com.contaazul.boleto.entity.Boleto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoletoResponseList extends ArrayList<BoletoResponse> {
	
	private static final long serialVersionUID = 1L;

	public BoletoResponseList(Iterable<Boleto> boletos) {
		if (boletos != null) {
			for (Boleto boleto : boletos) {
				add(new BoletoResponse(boleto));
			}
		}
	}

}
