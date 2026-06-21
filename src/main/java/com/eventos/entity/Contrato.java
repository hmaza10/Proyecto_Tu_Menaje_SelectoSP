package com.eventos.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contratos")

public class Contrato {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_contrato")
private int idContrato;
@ManyToOne
@JoinColumn(name = "id_evento")
private Evento evento;
@Column(name = "fecha_contrato")
private LocalDate fechaContrato;
@Column(name = "descripcion")
private String descripcion;
@Column(name = "total_acordado")
private double totalAcordado;
public Contrato() {

}
public int getIdContrato() {
	return idContrato;
}
public void setIdContrato(int idContrato) {
	this.idContrato = idContrato;
}
public Evento getEvento() {
	return evento;
}
public void setEvento(Evento evento) {
	this.evento = evento;
}
public LocalDate getFechaContrato() {
	return fechaContrato;
}
public void setFechaContrato(LocalDate fechaContrato) {
	this.fechaContrato = fechaContrato;
}
public String getDescripcion() {
	return descripcion;
}
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
public double getTotalAcordado() {
	return totalAcordado;
}
public void setTotalAcordado(double totalAcordado) {
	this.totalAcordado = totalAcordado;
}


}
