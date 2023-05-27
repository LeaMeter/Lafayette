package com.example.workflow.delegates;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.example.workflow.service.EmailService;
import com.example.workflow.util.ValidateEmail;

import javax.mail.MessagingException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

@Named
public class SendEmail implements JavaDelegate {
	private static final String MONEY_EUR = "moneyEUR";
	private static final String COUNTRY = "country";
	private static final String EMAIL = "email";
	private static final String INVALID_EMAIL = "invalid_email";
	private static final String INVALID_EMAIL_MESSAGE = "Wrong email address.";
	private static final String EMAIL_SEND_SUCCESFFULLY = "Mail Sent Successfully to ";
	private static final String SUBJECT = "Revolution details";

	protected static final Logger LOGGER = Logger.getLogger(SendEmail.class.getName());

	private final EmailService emailService;

	public SendEmail(EmailService emailService) {
		this.emailService = emailService;
	}

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {

		final String moneyEur = (String) delegateExecution.getVariable(MONEY_EUR);
		final String country = (String) delegateExecution.getVariable(COUNTRY);
		final String recipient = (String) delegateExecution.getVariable(EMAIL);
		final String sender = emailService.getSender();

		final String body = createMailBody(moneyEur, country);

		if (!ValidateEmail.validateEmails(List.of(sender, recipient))) {
			throw new BpmnError(INVALID_EMAIL, INVALID_EMAIL_MESSAGE);
		}

		try {
			emailService.sendMail(sender, recipient, SUBJECT, body.toString());
			LOGGER.log(Level.INFO, EMAIL_SEND_SUCCESFFULLY + recipient);

		} catch (MessagingException exception) {
			LOGGER.log(Level.WARNING, exception.getLocalizedMessage());
			throw new BpmnError(INVALID_EMAIL, INVALID_EMAIL_MESSAGE);
		}

	}

	private String createMailBody(String moneyEur, String country) {
		return new StringBuilder().append("Money in EUR: ").append(moneyEur).append(" Country: ").append(country)
				.toString();
	}

}
