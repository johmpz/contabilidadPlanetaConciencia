package com.planeta.conciencia.contabilidad.service;

import com.planeta.conciencia.contabilidad.dto.FacturaRequestDTO;
import com.planeta.conciencia.contabilidad.entity.Factura;
import com.planeta.conciencia.contabilidad.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private Files files;

    public List<Factura> getFacturas(){
        return facturaRepository.findAll();
    }

    public String getPDFFile(Integer id){
        Factura factura = facturaRepository.getOne(id);
        return factura.getPdfPath();
    }

    public String getInvoiceImage(Integer id){
        Factura factura = facturaRepository.getOne(id);
        return factura.getImagePath();
    }

    public Factura saveInvoice(FacturaRequestDTO facturaRequest) throws IOException {

        Assert.notNull(facturaRequest.getPrice(), "Price shouldn't be null");

        Factura factura = new Factura();

        switch (facturaRequest.getInvMode()){
            case NOTA:
                Assert.notNull(facturaRequest.getImagePath(), "Image shouldn't be null");

                files.saveImageFiles(facturaRequest.getImagePath());

                factura.setImagePath(facturaRequest.getImagePath().getOriginalFilename());
                break;

            case FACTURA:
                Assert.notNull(facturaRequest.getPdfPath(), "PDF shouldn't be null");
                Assert.notNull(facturaRequest.getXmlPath(), "XML shouldn't be null");

                files.saveFacturaFiles(facturaRequest.getPdfPath(), facturaRequest.getXmlPath());

                factura.setPdfPath(facturaRequest.getPdfPath().getOriginalFilename());
                factura.setXmlPath(facturaRequest.getXmlPath().getOriginalFilename());

                break;
        }

        factura.setPrice(facturaRequest.getPrice());
        factura.setDate(new GregorianCalendar().getTime());
        return facturaRepository.save(factura);
    }
}
