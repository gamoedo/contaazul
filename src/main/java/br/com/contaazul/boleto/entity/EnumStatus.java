package br.com.contaazul.boleto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumStatus {	
	
	PENDING(0), 
	PAID(1), 
	CANCELED(2);
	
	public final Integer codigo;
}
