package com.swifticket.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ErrorHandler {
	public Map<String, List<String>> mapErrors(List<FieldError> fieldErrors) {
		HashMap<String, List<String>> errorMap = new HashMap<>();
	
		fieldErrors.forEach(error -> {
			String key = error.getField();
			
			List<String> itemErrors = errorMap.get(key);
			if (itemErrors == null)
				itemErrors = new ArrayList<>();
			
			itemErrors.add(error.getDefaultMessage());
			errorMap.put(key, itemErrors);
		});
		
		return errorMap;
	}
}
