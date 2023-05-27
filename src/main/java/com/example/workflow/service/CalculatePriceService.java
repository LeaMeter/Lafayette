package com.example.workflow.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.workflow.constants.ApiEndpoints;
import com.example.workflow.pojo.HnbExchangeInfo;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculatePriceService {

	private static final int USD_RATE_INDEX = 0;

	private final RestTemplate restTemplate;

	public CalculatePriceService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public HnbExchangeInfo getHnbExchangeInfo(Map<String, String> queryParams) throws RestClientException {
		String query = buildQuery(queryParams);

		return restTemplate.getForObject(query, HnbExchangeInfo[].class)[USD_RATE_INDEX];
	}

	private String buildQuery(Map<String, String> queryParams) {
		if (queryParams.isEmpty()) {
			return ApiEndpoints.EUR_EXCHANGE_BASE;
		}

		return queryParams.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining("&", ApiEndpoints.EUR_EXCHANGE_BASE + "?", ""));
	}
}
