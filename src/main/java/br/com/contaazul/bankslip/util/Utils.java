package br.com.contaazul.bankslip.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.contaazul.bankslip.controller.request.BankslipRequest;

public abstract class Utils {

    static Logger logger = LoggerFactory.getLogger(Utils.class);

    public static final String patternDate = "yyyy-MM-dd";

    public static LocalDate convertStringToLocalDate(String stringDate, String pattern) {

	logger.info("convertStringToLocalDate: Converting the date '" + stringDate + "' with pattern '" + pattern + "'");

	LocalDate localDate;

	try {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
	    localDate = LocalDate.parse(stringDate, formatter);
	    logger.info("convertStringToLocalDate: Converted Date: " + localDate);
	} catch (DateTimeParseException dtpe) {
	    logger.error("convertStringToLocalDate: DateTimeParseException - ERROR trying to convert date. Returning NULL ");
	    logger.error("convertStringToLocalDate: Error message: " + dtpe.getMessage());
	    return null;
	} catch (DateTimeException dte) {
	    logger.error("convertStringToLocalDate: DateTimeException - ERROR trying to convert date. Returning NULL ");
	    logger.error("convertStringToLocalDate: Error message: " + dte.getMessage());
	    return null;
	}

	return localDate;
    }
    
}
