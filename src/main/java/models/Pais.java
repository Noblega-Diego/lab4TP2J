package models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "pais")
public class Pais {
    @Id
    @Column(name = "codigopais")
    private Long codigo;
    @Column(name = "nombrepais")
    private String nombre;
    @Column(name = "capitalPais")
    private String capital;
    @Column(name = "region")
    private String region;
    @Column(name = "poblacion")
    private Long poblacion;
    @Column(name = "longitud")
    private Long longitud;
    @Column(name = "latitud")
    private Long latitud;

    public Pais(){}
}
