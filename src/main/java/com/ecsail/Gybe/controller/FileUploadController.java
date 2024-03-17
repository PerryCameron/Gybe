package com.ecsail.Gybe.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadController {

    private final String uploadDirectory = "/home/ecsc/boat_images";

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
}
