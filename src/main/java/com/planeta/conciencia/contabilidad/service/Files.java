package com.planeta.conciencia.contabilidad.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class Files {

    @Value("${planeta.paths.uploadedFiles:}")
    private String FILES_PATH;


    public void saveImageFiles(MultipartFile imagePath) throws IOException {

        File imageFile = new File(this.getDirectory() + File.separator + imagePath.getOriginalFilename());
        if (imageFile.exists()){
            throw new IOException("File already exists!");
        }
        imagePath.transferTo(imageFile);
    }

    public void saveFacturaFiles(MultipartFile pdfPath, MultipartFile xmlPath) throws IOException {
        File pdfFile = new File(this.getDirectory() + File.separator + pdfPath.getOriginalFilename());
        File xmlFile = new File(this.getDirectory() + File.separator + xmlPath.getOriginalFilename());

        if (pdfFile.exists() || xmlFile.exists()){
            throw new IOException("File already exists!");
        }

        pdfPath.transferTo(pdfFile);
        xmlPath.transferTo(xmlFile);
    }

    private String getDirectory(){
        String userHome = System.getProperty("user.home");
        String outputFolder = userHome + File.separator + FILES_PATH;
        File folder = new File(outputFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return outputFolder;
    }

}
