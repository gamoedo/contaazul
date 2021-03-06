package br.com.contaazul.bankslip.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnprocessableEntityException extends RuntimeException {

    private static final long serialVersionUID = -4160704396501040909L;

    private Long code;

    public UnprocessableEntityException(Long code, String msg) {
	super(msg);
	this.code = code;
    }
}