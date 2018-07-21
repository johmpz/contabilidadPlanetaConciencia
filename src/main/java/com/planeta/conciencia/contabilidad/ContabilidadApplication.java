package com.planeta.conciencia.contabilidad;

import com.planeta.conciencia.contabilidad.service.FacturaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class ContabilidadApplication {

	public static void main(String[] args) {

//        new File(FacturaService.uploadingdir).mkdirs();
		SpringApplication.run(ContabilidadApplication.class, args);
	}
}
