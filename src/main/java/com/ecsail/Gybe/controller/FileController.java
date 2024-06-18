package com.ecsail.Gybe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final String uploadDirectory = System.getProperty("user.home")  + "/boat_images";

    @GetMapping("/viewfiles")
    public String listUploadedFiles(Model model) {
        // Add logic to list files from 'uploadDirectory' and add them to the model
        return "view";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        // Add logic to save the file to 'uploadDirectory'
        return "redirect:/";
    }

    public ResponseEntity<InputStreamResource> downloadFile(String filename) {
        // Define the file path
        String filePath = uploadDirectory + "/" + filename;
        File file = new File(filePath);
        logger.info("Retrieving file: " + file);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // Create an InputStreamResource
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<InputStreamResource> downloadFileEndpoint(@PathVariable String filename) {
        return downloadFile(filename);
    }
}
