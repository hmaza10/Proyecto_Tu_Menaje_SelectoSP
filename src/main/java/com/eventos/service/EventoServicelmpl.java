package com.eventos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import com.eventos.entity.Contrato;
import com.eventos.entity.Evento;
import com.eventos.entity.EventoServicio;
import com.eventos.entity.Servicio;
import com.eventos.repository.ContratoRepository;
import com.eventos.repository.EventoRepository;
import com.eventos.repository.EventoServicioRepository;
import com.eventos.repository.PagoRepository;
import com.eventos.repository.ServicioRepository;

@Service
public class EventoServicelmpl implements EventoService {

	@Autowired
	private EventoRepository repoEvento;
	
	@Autowired
	private ServicioRepository servicioRepository;

	@Autowired
	private EventoServicioRepository eventoServicioRepository;
	
	@Autowired
	private ContratoRepository contratoRepository;
	
	@Autowired
	private PagoRepository pagoRepository;
	
	@Autowired
	private ContratoService contratoService;
	
	
	@Override
	public List<Evento> listar() {
		return repoEvento.findAll();
	}

	@Override
	public Evento buscarPorId(Integer id) {
		return repoEvento.findById(id).orElse(null);
	}

	@Override
	public Evento guardar(Evento evento) {
		return repoEvento.save(evento);
	}

	@Override
	@Transactional
	public void eliminar(int id) {

	    pagoRepository
	            .deleteByEvento_IdEvento(id);

	    eventoServicioRepository
	            .deleteByEvento_IdEvento(id);

	    contratoRepository
	            .deleteByEvento_IdEvento(id);

	    repoEvento.deleteById(id);
	}

	@Override
	@Transactional
	public void guardarEventoConServicios(Evento evento,
	        List<Integer> serviciosIds) {

	    double total = 0;

	    if(serviciosIds != null){

	        for(Integer idServicio : serviciosIds){

	            Servicio servicio =
	                    servicioRepository
	                    .findById(idServicio)
	                    .orElse(null);

	            if(servicio != null){

	                total += servicio.getPrecio();
	            }
	        }
	    }

	    evento.setPresupuesto(total);

	    Evento eventoGuardado =
	            repoEvento.save(evento);

	    eventoServicioRepository
	            .deleteByEvento_IdEvento(
	                    eventoGuardado.getIdEvento());

	    if(serviciosIds != null){

	        for(Integer idServicio : serviciosIds){

	            Servicio servicio =
	                    servicioRepository
	                    .findById(idServicio)
	                    .orElse(null);

	            if(servicio != null){

	                EventoServicio es =
	                        new EventoServicio();

	                es.setEvento(eventoGuardado);

	                es.setServicio(servicio);

	                es.setCantidad(1);

	                es.setSubtotal(
	                        servicio.getPrecio());

	                eventoServicioRepository.save(es);
	            }
	        }
	    }

	    Contrato contrato =
	            contratoRepository.findByEvento_IdEvento( eventoGuardado.getIdEvento());

	    if(contrato == null){

	        contrato = new Contrato();

	        contrato.setEvento(eventoGuardado);

	        contrato.setFechaContrato(
	                LocalDate.now());
	    }

	    contrato.setDescripcion(
	            "Organización y ejecución del evento "
	            + eventoGuardado.getNombreEvento()
	    );

	    contrato.setTotalAcordado(
	            eventoGuardado.getPresupuesto());

	    contratoService.guardar(contrato);
	}

	@Override
	public List<Evento> listarPorFecha() {
		return repoEvento.findAllByOrderByFechaEventoAsc();
	}

	@Override
	public List<Object[]> contarEventosPorTipo() {
		return repoEvento.contarEventosPorTipo();
	}

	@Override
	public long contarPorEstado(String estado) {
		return repoEvento.countByEstado(estado);
	}

	@Override
	public List<Evento> listarConfirmados() {
		return repoEvento.findByEstadoOrderByFechaEventoAsc("Confirmado");
	}


}
