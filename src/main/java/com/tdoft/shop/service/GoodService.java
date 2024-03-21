package com.tdoft.shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.tdoft.shop.entity.Good;
import com.tdoft.shop.entity.file.Image;
import com.tdoft.shop.exception.GoodNotFoundException;
import com.tdoft.shop.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class GoodService {

    @Value("${amazon.s3.bucket}")
    private String bucketName = "tdoft.tk";

    @Autowired
    private GoodRepository repository;

    @Autowired
    private AmazonS3 amazonS3Client;

    public Page<Good> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Good> findById(Long id) {
        return repository.findById(id);
    }

    public Good save(Good good, MultipartFile fileImage) {
        try {
            good = repository.save(good);

            Image image = new Image();
            image.setContentType(fileImage.getContentType());
            image.setFileName(fileImage.getOriginalFilename());
            image.setS3BucketFilePath("good-" + good.getId() + "-" + fileImage.getOriginalFilename());
            good.setImage(image);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(fileImage.getContentType());
            amazonS3Client.putObject(bucketName, image.getS3BucketFilePath(), fileImage.getInputStream(), metadata);

            return repository.save(good);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to save image to the selected good");
        }
    }

    public Good save(Good good) {
        return repository.save(good);
    }

    public void deleteById(Long id) {
        Good good = repository.findById(id)
                .orElseThrow(() -> new GoodNotFoundException(id));

        if (good.getImage() != null) {
            amazonS3Client.deleteObject(bucketName, good.getImage().getS3BucketFilePath());
        }
        repository.deleteById(id);
    }

}
