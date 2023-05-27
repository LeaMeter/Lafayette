package com.example.workflow.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.web.client.RestClientException;

import com.example.workflow.exceptions.ExchangeRateError;
import com.example.workflow.pojo.HnbExchangeInfo;
import com.example.workflow.service.CalculatePriceService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

@Named
public class CalculatePriceEUR implements JavaDelegate {

	private static final String MONEY_EUR = "moneyEUR";
	private static final String MIDDLE_RATE = "middleRate";
	private static final String API_REQUEST_FAILED = "Failed to retrieve exchange rate data.";
	private static final String FAILED_TO_RETRIEVE_MIDDLE_RATE = "Failed to retrieve middle exchange rate";

	protected static final Logger LOGGER = Logger.getLogger(CalculatePriceEUR.class.getName());

	private final CalculatePriceService calculatePriceService;

	public CalculatePriceEUR(CalculatePriceService calculatePriceService) {
		this.calculatePriceService = calculatePriceService;
	}

	@Override
	public void execute(DelegateExecution delegateExecution) {

		try {
			Map<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("valuta", "USD");

			final HnbExchangeInfo usdHnbExchangeInfo = calculatePriceService.getHnbExchangeInfo(queryParams);

			if (usdHnbExchangeInfo == null) {
				delegateExecution.setVariable(MONEY_EUR, API_REQUEST_FAILED);
				throw new ExchangeRateError(MONEY_EUR, API_REQUEST_FAILED);
			}

			String middleRate = usdHnbExchangeInfo.getMiddleRate();
			if (middleRate == null) {
				delegateExecution.setVariable(MONEY_EUR, FAILED_TO_RETRIEVE_MIDDLE_RATE);
				throw new ExchangeRateError(MONEY_EUR, FAILED_TO_RETRIEVE_MIDDLE_RATE);
			}

			String money = (String) delegateExecution.getVariable("money");
			String formattedEurRate = getMiddleEurRate(middleRate, money);

			delegateExecution.setVariable(MONEY_EUR, formattedEurRate);
			delegateExecution.setVariable(MIDDLE_RATE, middleRate);

		} catch (RestClientException exception) {
			delegateExecution.setVariable(MONEY_EUR, API_REQUEST_FAILED);

			LOGGER.log(Level.WARNING, exception.getLocalizedMessage());
			throw new ExchangeRateError(MONEY_EUR, API_REQUEST_FAILED);
		}

	}

	public String getMiddleEurRate(String middleRate, String money) {
		String formattedRate = middleRate.replace(",", ".");
		String formattedMoney = money.replace(",", ".");

		double doubleMiddleRate = Double.parseDouble(formattedRate);
		double moneyDouble = Double.parseDouble(formattedMoney);
		double eurRate = moneyDouble * (1.00 / doubleMiddleRate);

		return String.format("%.2f", eurRate);
	}

}
