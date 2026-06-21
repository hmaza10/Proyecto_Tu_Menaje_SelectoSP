package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import com.eventos.entity.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer>{

	List<Evento> findAllByOrderByFechaEventoAsc();
	
	long countByEstado(String estado);
	
	@Query("""
			SELECT e.tipoEvento, COUNT(e)
			FROM Evento e
			GROUP BY e.tipoEvento
			""")
			List<Object[]> contarEventosPorTipo();
			
	List<Evento> findByEstadoOrderByFechaEventoAsc(String estado);
	
	
}
