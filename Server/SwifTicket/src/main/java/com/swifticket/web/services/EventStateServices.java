package com.swifticket.web.services;

import java.util.List;

import com.swifticket.web.models.entities.EventState;

public interface EventStateServices {
	List<EventState> findAll();
	EventState findById(int id);
}
