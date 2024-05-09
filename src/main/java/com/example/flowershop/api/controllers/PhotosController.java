
package com.example.flowershop.api.controllers;

import com.example.flowershop.core.service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@AllArgsConstructor
@Controller
public class PhotosController {
  private final PhotoService photoService;
  @GetMapping("/photos/{fileName:.+}")
  public ResponseEntity<Resource> getPhoto(@PathVariable String fileName, HttpServletRequest request) {
    Resource resource = photoService.loadFileAsResource(fileName);

    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
      System.out.print("Could not determine file type.");
    }

    if(contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}
