package com.planeta.conciencia.contabilidad.service;

import com.planeta.conciencia.contabilidad.dto.FacturaRequestDTO;
import com.planeta.conciencia.contabilidad.entity.Factura;
import com.planeta.conciencia.contabilidad.exception.InvalidDateRangeFacturas;
import com.planeta.conciencia.contabilidad.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private Files files;

    public List<Factura> getFacturas(final int month, final int year) throws InvalidDateRangeFacturas {

        LocalDate ldStart = LocalDate.of(year,month,01);
        LocalDate ldEnd = LocalDate.of(year, month, ldStart.lengthOfMonth());

        return facturaRepository.getFacturasFrom(ldStart.atStartOfDay(ZoneId.systemDefault()),
                ldEnd.atStartOfDay(ZoneId.systemDefault())).orElseThrow(
                        () -> new InvalidDateRangeFacturas("No hay facturas con la fecha seleccionada"));
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
        factura.setCreated(ZonedDateTime.now());
        return facturaRepository.save(factura);
    }
}
