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
@Table(name = "pagos")

public class Pago {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_pago")
private int idPago;
@ManyToOne
@JoinColumn(name = "id_evento")
private Evento evento;
@Column(name = "fecha_pago")
private LocalDate fechaPago;
@Column(name = "monto")
private double monto;
@Column(name = "metodo_pago")
private String metodoPago;


public Pago() {

}

public int getIdPago() {
	return idPago;
}

public void setIdPago(int idPago) {
	this.idPago = idPago;
}

public Evento getEvento() {
	return evento;
}

public void setEvento(Evento evento) {
	this.evento = evento;
}

public LocalDate getFechaPago() {
	return fechaPago;
}

public void setFechaPago(LocalDate fechaPago) {
	this.fechaPago = fechaPago;
}

public double getMonto() {
	return monto;
}

public void setMonto(double monto) {
	this.monto = monto;
}

public String getMetodoPago() {
	return metodoPago;
}

public void setMetodoPago(String metodoPago) {
	this.metodoPago = metodoPago;
}

}
