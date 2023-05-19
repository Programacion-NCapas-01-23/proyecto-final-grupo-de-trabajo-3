package com.swifticket.web.services.implementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swifticket.web.models.entities.EventState;
import com.swifticket.web.repositories.EventStateRepository;
import com.swifticket.web.services.EventStateServices;

@Service
public class EventStateServiceImpl implements EventStateServices {
	
	@Autowired
	private EventStateRepository repository;


	@Override
	public EventState findById(int id) {
		return repository.findById(id).orElse(null);
	}


	@Override
	public List<EventState> findAll() {
		return repository.findAll();
	}

}
