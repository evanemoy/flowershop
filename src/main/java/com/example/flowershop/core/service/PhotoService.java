package com.example.flowershop.core.service;

import com.example.flowershop.core.exception.PhotoNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class PhotoService {
  private final static String BASE_PATH = "src/main/item_photos";

  //returns name of saved photo
  public String savePhoto(MultipartFile file) throws IOException {
    var generatedName = generateNewName(file);
    Files.write(Paths.get(BASE_PATH, generatedName), file.getBytes());
    return generatedName;
  }

  private String generateNewName(MultipartFile file) {
    var uuid = UUID.randomUUID().toString();
    var extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    return uuid + extension;
  }

  public Resource loadFileAsResource(String fileName) {
    try {
      Path filePath = Paths.get(BASE_PATH, fileName);
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new PhotoNotFoundException("File not found " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new PhotoNotFoundException("File not found " + fileName);
    }
  }

  public void deleteFileByName(String fileName) throws IOException {
    Path filePath = Paths.get(BASE_PATH, fileName);
    if (Files.exists(filePath)) {
      Files.delete(filePath);
    } else {
      throw new PhotoNotFoundException("Нет такого фото:" + fileName);
    }
  }
}
