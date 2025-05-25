package org.example.diplomski.services;

import org.springframework.transaction.annotation.Transactional;
import org.example.diplomski.data.entites.ImageData;
import org.example.diplomski.exceptions.EmailNotFoundException;
import org.example.diplomski.repositories.ImageDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class DeleteImageService {


    private final ImageDataRepository imageDataRepository;


    private final ImageDataRepository repository;

    public DeleteImageService(ImageDataRepository imageDataRepository, ImageDataRepository repository) {
        this.imageDataRepository = imageDataRepository;
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteImageById(Long id) {
        ImageData dbImageData = repository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException(id.toString()));
        imageDataRepository.delete(dbImageData);
    }
}
