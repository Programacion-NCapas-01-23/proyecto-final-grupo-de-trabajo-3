package com.swifticket.web.services.implementations;

import com.swifticket.web.models.entities.Sponsor;
import com.swifticket.web.repositories.SponsorRepository;
import com.swifticket.web.services.SponsorServices;
import com.swifticket.web.utils.ImageUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private String saveSponsorImage(MultipartFile image) throws IOException {
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
    @Transactional(rollbackOn = Exception.class)
    public void save(String name, MultipartFile image) throws Exception {
        String imagePath = saveSponsorImage(image);
        Sponsor sponsor = new Sponsor(name, imagePath);
        repository.save(sponsor);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void update(int id, String name, MultipartFile image) throws Exception {
        Sponsor sponsor = repository.findById(id).orElse(null);
        if (sponsor == null) {
            throw new Exception("Sponsor not found");
        }

        String imagePath = saveSponsorImage(image);
        sponsor.setName(name);
        sponsor.setImage(imagePath);

        repository.save(sponsor);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(int id) throws Exception {
        repository.deleteById(id);
    }
}
