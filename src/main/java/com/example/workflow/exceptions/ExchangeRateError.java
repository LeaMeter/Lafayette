package com.example.workflow.exceptions;

import org.camunda.bpm.engine.ProcessEngineException;

import static org.camunda.bpm.engine.impl.util.EnsureUtil.ensureNotEmpty;

public class ExchangeRateError extends ProcessEngineException {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMessage;

	public ExchangeRateError(String errorCode) {
		super(exceptionMessage(errorCode, null));
		setErrorCode(errorCode);
	}

	public ExchangeRateError(String errorCode, String message) {
		super(exceptionMessage(errorCode, message));
		setErrorCode(errorCode);
		setMessage(message);
	}

	public ExchangeRateError(String errorCode, String message, Throwable cause) {
		super(exceptionMessage(errorCode, message), cause);
		setErrorCode(errorCode);
		setMessage(message);
	}

	public ExchangeRateError(String errorCode, Throwable cause) {
		super(exceptionMessage(errorCode, null), cause);
		setErrorCode(errorCode);
	}

	private static String exceptionMessage(String errorCode, String message) {
		if (message == null) {
			return "";
		} else {
			return message + " (errorCode='" + errorCode + "')";
		}
	}

	protected void setErrorCode(String errorCode) {
		ensureNotEmpty("Error Code", errorCode);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		return super.toString() + " (errorCode='" + errorCode + "')";
	}

	protected void setMessage(String errorMessage) {
		ensureNotEmpty("Error Message", errorMessage);
		this.errorMessage = errorMessage;
	}

	@Override
	public String getMessage() {
		return errorMessage;
	}
}
