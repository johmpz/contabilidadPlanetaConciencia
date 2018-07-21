package com.planeta.conciencia.contabilidad.controller;

import com.planeta.conciencia.contabilidad.dto.FacturaRequestDTO;
import com.planeta.conciencia.contabilidad.entity.Factura;
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

    public static final Logger logger = LoggerFactory.getLogger(FacturaController.class);

    @PostMapping(path ="/factura")
    public ResponseEntity<?> insertNota(@ModelAttribute FacturaRequestDTO files){
        logger.info("inserting invoice: " + files);
        try{
            return new ResponseEntity<>(facturaService.saveInvoice(files), HttpStatus.OK);
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getPDFFile")
    public void getPDF(@RequestParam(value = "id") Integer id){
        logger.info("Getting PDF of invoice: " + id);
        facturaService.getPDFFile(id);
    }

    @GetMapping(path = "/facturas", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Factura>> getFacturas(){

        logger.info("Getting all invoices");

        List<Factura> facturas = facturaService.getFacturas();

        logger.info("Got " + facturas.size() + " invoices");

        if (facturas != null){
            return new ResponseEntity<>(facturas, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
