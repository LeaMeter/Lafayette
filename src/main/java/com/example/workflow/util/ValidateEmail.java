package com.example.workflow.util;

import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidateEmail {

	public static boolean validateEmails(List<String> emails) {

		EmailValidator validator = EmailValidator.getInstance();

		return emails.stream().allMatch(email -> validator.isValid(email));
	}

}
