package com.planeta.conciencia.contabilidad.controller;

import com.planeta.conciencia.contabilidad.dto.FacturaRequestDTO;
import com.planeta.conciencia.contabilidad.entity.Factura;
import com.planeta.conciencia.contabilidad.exception.InvalidDateRangeFacturas;
import com.planeta.conciencia.contabilidad.service.FacturaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController()
@RequestMapping(value = "/api")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    public static final Logger LOGGER = LoggerFactory.getLogger(FacturaController.class);

    @PostMapping(path ="/factura")
    public ResponseEntity<?> insertNota(@ModelAttribute FacturaRequestDTO files){
        LOGGER.info("inserting invoice: " + files);
        try{
            return new ResponseEntity<>(facturaService.saveInvoice(files), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getPDFFile")
    public void getPDF(@RequestParam(value = "id") Integer id){
        LOGGER.info("Getting PDF of invoice: " + id);
        facturaService.getPDFFile(id);
    }

    @GetMapping(path = "/facturas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getFacturas(@RequestParam int month, @RequestParam int year){

        LOGGER.info("Getting all invoices of period: " + month + " of " + year);

        List<Factura> facturas = null;
        try {
            facturas = facturaService.getFacturas(month, year);
            LOGGER.info("Got " + facturas.size() + " invoices");
            return new ResponseEntity<>(facturas, HttpStatus.OK);
        } catch (InvalidDateRangeFacturas invalidDateRangeFacturas) {
            String erroMessages = "No hay facturas con la fecha seleccionada.";
            LOGGER.error(erroMessages);
            return new ResponseEntity<>(erroMessages, HttpStatus.BAD_REQUEST);
        }
    }
}
