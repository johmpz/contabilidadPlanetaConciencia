package com.planeta.conciencia.contabilidad.util;

import com.planeta.conciencia.contabilidad.entity.Factura;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.EOFException;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("file");
        modelAndView.getModel().put("message", "File too large!");
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }

    @ExceptionHandler(EOFException.class)
    public ResponseEntity<?> eofException(MaxUploadSizeExceededException exc, HttpServletRequest request,
                                                    HttpServletResponse response) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
