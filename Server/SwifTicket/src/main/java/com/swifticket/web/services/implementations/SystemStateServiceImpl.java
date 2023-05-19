package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.SystemState;
import com.swifticket.web.repositories.SystemStateRepository;
import com.swifticket.web.services.SystemStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemStateServiceImpl implements SystemStateService {
    private final SystemStateRepository repository;

    @Autowired
    public SystemStateServiceImpl(SystemStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public void toggleStatus() throws Exception {
        SystemState systemState = repository.findById(1).orElse(null);
        if (systemState != null) {
            int status = systemState.getState();
            // Change Status
            systemState.setState((status == 0) ? 1 : 0);
            repository.save(systemState);
        }
    }

    @Override
    public int getStatus() {
        SystemState systemState = repository.findById(1).orElse(null);
        return (systemState != null) ? systemState.getState() : 0;
    }
}
