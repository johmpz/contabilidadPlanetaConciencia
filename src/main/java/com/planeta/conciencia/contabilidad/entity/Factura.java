package com.planeta.conciencia.contabilidad.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity
@Table(indexes = {
        @Index(columnList = "id", unique = true)
})
@Data
@ToString
@EqualsAndHashCode
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private User user;

    private String pdfPath;

    private String xmlPath;

    private String imagePath;

    @Column(nullable = false)
    private ZonedDateTime created;

    private Double price;
}
