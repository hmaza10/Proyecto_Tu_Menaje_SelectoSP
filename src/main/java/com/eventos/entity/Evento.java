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
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "eventos")

public class Evento {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id_evento")
private int idEvento;
@Column(name = "nombre_evento")
private String nombreEvento;
@Column(name = "tipo_evento")
private String tipoEvento;
@Column(name = "fecha_evento")
private LocalDate fechaEvento;
@Column(name = "lugar")
private String lugar;
@Column(name = "cantidad_invitados")
private int cantidadInvitados;
@Column(name = "presupuesto")
private double presupuesto;
@Column(name = "estado")
private String estado;
@ManyToOne
@JoinColumn(name = "id_cliente")
private Cliente cliente;

public Evento() {
	
}

public int getIdEvento() {
	return idEvento;
}

public void setIdEvento(int idEvento) {
	this.idEvento = idEvento;
}

public String getNombreEvento() {
	return nombreEvento;
}

public void setNombreEvento(String nombreEvento) {
	this.nombreEvento = nombreEvento;
}

public String getTipoEvento() {
	return tipoEvento;
}

public void setTipoEvento(String tipoEvento) {
	this.tipoEvento = tipoEvento;
}

public LocalDate getFechaEvento() {
	return fechaEvento;
}

public void setFechaEvento(LocalDate fechaEvento) {
	this.fechaEvento = fechaEvento;
}

public String getLugar() {
	return lugar;
}

public void setLugar(String lugar) {
	this.lugar = lugar;
}

public int getCantidadInvitados() {
	return cantidadInvitados;
}

public void setCantidadInvitados(int cantidadInvitados) {
	this.cantidadInvitados = cantidadInvitados;
}

public double getPresupuesto() {
	return presupuesto;
}

public void setPresupuesto(double presupuesto) {
	this.presupuesto = presupuesto;
}

public String getEstado() {
	return estado;
}

public void setEstado(String estado) {
	this.estado = estado;
}

public Cliente getCliente() {
	return cliente;
}

public void setCliente(Cliente cliente) {
	this.cliente = cliente;
}

public long getDiasRestantes() {

    if(fechaEvento == null) {
        return 0;
    }

    return ChronoUnit.DAYS.between(
            LocalDate.now(),
            fechaEvento);
}


}
