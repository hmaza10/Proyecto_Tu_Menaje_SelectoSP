package com.eventos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicios")

public class Servicio {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_servicio")
private int idServicio;
@Column(name = "nombre_servicio")
private String nombreServicio;
@Column(name = "descripcion")
private String descripcion;
@Column(name = "precio")
private double precio;
@Column(name = "estado")
private String estado;
@ManyToOne
@JoinColumn(name = "id_proveedor")
private Proveedor idProveedor;

public Servicio() {

}

public int getIdServicio() {
	return idServicio;
}

public void setIdServicio(int idServicio) {
	this.idServicio = idServicio;
}

public String getNombreServicio() {
	return nombreServicio;
}

public void setNombreServicio(String nombreServicio) {
	this.nombreServicio = nombreServicio;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public double getPrecio() {
	return precio;
}

public void setPrecio(double precio) {
	this.precio = precio;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}

public Proveedor getIdProveedor() {
	return idProveedor;
}

public void setIdProveedor(Proveedor idProveedor) {
	this.idProveedor = idProveedor;
}



}
