package com.swifticket.web.services;

public interface SystemStateService {
	void toggleStatus() throws Exception;
	int getStatus();
}
