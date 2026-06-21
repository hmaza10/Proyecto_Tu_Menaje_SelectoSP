package com.eventos.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eventos.entity.Evento;
import com.eventos.entity.Pago;
import com.eventos.repository.PagoRepository;
import com.eventos.service.EventoService;
import com.eventos.service.PagoService;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AgendaController {

	@Autowired
    private EventoService eventoService;
	
	@Autowired
	private PagoService pagoService;

	@GetMapping("/agenda")
	public String agenda(Model model) {

	    List<Evento> eventos = eventoService.listarConfirmados();

	    Map<Integer, String> estadosPago = new HashMap<>();

	    int totalPagados = 0;
	    int totalPendientes = 0;

	    for (Evento e : eventos) {

	        double totalPagado =pagoService.obtenerTotalPagadoPorEvento(e.getIdEvento());

	        String estado = totalPagado >= e.getPresupuesto()? "Pagado": "Pendiente";

	        estadosPago.put(e.getIdEvento(),estado);

	        if (estado.equals("Pagado")) {
	            totalPagados++;
	        } else {
	            totalPendientes++;
	        }
	    }

	    model.addAttribute("eventos", eventos);
	    model.addAttribute("estadosPago", estadosPago);

	    model.addAttribute("totalEventos",eventoService.listar().size());

	    model.addAttribute("totalPagados",totalPagados);

	    model.addAttribute("totalPendientesPago",totalPendientes);

	    return "agenda/agendaEventos";
	}
    
    @GetMapping("/agenda/exportarExcel")
    public void exportarExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.setHeader("Content-Disposition","attachment; filename=Agenda_Eventos.xlsx");

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Eventos");

        Row encabezado = sheet.createRow(0);

        encabezado.createCell(0).setCellValue("ID");
        encabezado.createCell(1).setCellValue("Fecha");
        encabezado.createCell(2).setCellValue("Evento");
        encabezado.createCell(3).setCellValue("Tipo");
        encabezado.createCell(4).setCellValue("Cliente");
        encabezado.createCell(5).setCellValue("Lugar");
        encabezado.createCell(6).setCellValue("Invitados");
        encabezado.createCell(7).setCellValue("Estado");
        encabezado.createCell(8).setCellValue("Presupuesto");

        List<Evento> eventos = eventoService.listarConfirmados();

        int fila = 1;

        for (Evento e : eventos) {

            Row row = sheet.createRow(fila++);

            row.createCell(0).setCellValue(e.getIdEvento());

            row.createCell(1).setCellValue(e.getFechaEvento().toString());

            row.createCell(2).setCellValue(e.getNombreEvento());

            row.createCell(3).setCellValue(e.getTipoEvento());

            row.createCell(4).setCellValue(e.getCliente().getNombre() + " " +e.getCliente().getApellido());

            row.createCell(5).setCellValue(e.getLugar());

            row.createCell(6).setCellValue(e.getCantidadInvitados());

            row.createCell(7).setCellValue(e.getEstado());

            row.createCell(8).setCellValue(e.getPresupuesto());
        }

        for(int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());

        workbook.close();
    }
}
