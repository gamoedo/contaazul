package br.com.contaazul.boleto.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Utils {
	
//	public static String converteLocalDateParaString(LocalDate localDate) {
//			
//		
//	}
	public static LocalDate converteStringParaLocalDate(String dataString, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);		
		LocalDate localDate = LocalDate.parse(dataString, formatter);
		return localDate;		
	}

}
