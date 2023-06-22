package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.repositories.SponsorRepository;
import com.swifticket.web.services.SponsorServices;
import com.swifticket.web.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class SponsorServicesImpl implements SponsorServices {
    private final SponsorRepository repository;
    private final Environment environment;
    private final ImageUtils imageUtils;

    @Autowired
    public SponsorServicesImpl(SponsorRepository repository, Environment environment, ImageUtils imageUtils) {
        this.repository = repository;
        this.environment = environment;
        this.imageUtils = imageUtils;
    }

    // Method to save image to disk and return the image path
    private String saveSponsorImage(MultipartFile image) {
        String imageDirectory = environment.getProperty("sponsor.image.upload.path");
        return imageUtils.saveImage(image, imageDirectory);
    }

    @Override
    public List<Sponsor> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Sponsor> findAll(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return repository.findByNameContains(name, pageable);
    }

    @Override
    public Sponsor findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Sponsor findByName(String name) {
        return repository.findOneByName(name);
    }

    @Override
    public Sponsor findOneByNameAndImage(String name, String image) {
        return repository.findOneByNameAndImage(name, image);
    }

    @Override
    public void save(String name, String image) {
        Sponsor sponsor = new Sponsor(name, image);
        repository.save(sponsor);
    }

    @Override
    public void update(int id, String name, MultipartFile image) {
        try {
            Sponsor sponsor = repository.findById(id).orElse(null);
            if (sponsor == null) {
                throw new IllegalArgumentException("Sponsor not found");
            }

            String imagePath = saveSponsorImage(image);
            sponsor.setName(name);
            sponsor.setImage(imagePath);

            repository.save(sponsor);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update sponsor: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete sponsor: " + e.getMessage());
        }
    }
}
