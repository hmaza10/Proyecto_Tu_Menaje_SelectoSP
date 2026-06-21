package com.eventos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores")

public class Proveedor {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_proveedor")
private Integer idProveedor;
@Column(name = "nombre_empresa")
private String nombreEmpresa;
@Column(name = "contacto")
private String contacto;
@Column(name = "telefono")
private String telegono;
@Column(name = "correo")
private String correo;
@Column(name = "direccion")
private String direccion;
@Column(name = "estado")
private String estado;

public Proveedor() {

}

public Integer getIdProveedor() {
	return idProveedor;
}

public void setIdProveedor(Integer idProveedor) {
	this.idProveedor = idProveedor;
}

public String getNombreEmpresa() {
	return nombreEmpresa;
}

public void setNombreEmpresa(String nombreEmpresa) {
	this.nombreEmpresa = nombreEmpresa;
}

public String getContacto() {
	return contacto;
}

public void setContacto(String contacto) {
	this.contacto = contacto;
}

public String getTelegono() {
	return telegono;
}

public void setTelegono(String telegono) {
	this.telegono = telegono;
}

public String getCorreo() {
	return correo;
}

public void setCorreo(String correo) {
	this.correo = correo;
}

public String getDireccion() {
	return direccion;
}

public void setDireccion(String direccion) {
	this.direccion = direccion;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}



}
