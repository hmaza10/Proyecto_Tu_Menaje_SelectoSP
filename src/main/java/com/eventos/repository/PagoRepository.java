package com.eventos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.eventos.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer>{

	void deleteByEvento_IdEvento(int idEvento);
	
	Optional<Pago> findTopByEvento_IdEventoOrderByIdPagoDesc(int idEvento);
	
	@Query("SELECT COALESCE(SUM(p.monto),0) FROM Pago p WHERE p.evento.idEvento = :idEvento")
	Double obtenerTotalPagadoPorEvento(int idEvento);
	
	List<Pago> findAllByOrderByIdPagoDesc();
}


