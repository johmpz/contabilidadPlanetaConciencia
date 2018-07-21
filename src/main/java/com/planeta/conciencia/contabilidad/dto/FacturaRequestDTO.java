package com.planeta.conciencia.contabilidad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaRequestDTO {

    private MultipartFile imagePath;

    private MultipartFile pdfPath;

    private MultipartFile xmlPath;

    private Double price;

    private InvoiceMode invMode;
}
