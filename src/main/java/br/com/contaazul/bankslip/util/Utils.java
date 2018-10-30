package br.com.contaazul.bankslip.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Utils {

	public static final String patternDate = "yyyy-MM-dd";
	
	public static LocalDate convertStringToLocalDate(String stringDate, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);		
		LocalDate localDate = LocalDate.parse(stringDate, formatter);
		return localDate;		
	}

}
