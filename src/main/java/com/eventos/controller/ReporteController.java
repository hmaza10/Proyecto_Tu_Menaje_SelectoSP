package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.eventos.entity.Evento;
import com.eventos.service.ClienteService;
import com.eventos.service.ContratoService;
import com.eventos.service.EventoService;
import com.eventos.service.PagoService;
import com.eventos.service.UsuarioService;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReporteController {

	@Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private ContratoService contratoService;
    
    @Autowired
    private PagoService pagoService;

    @GetMapping("/reportes")
    public String dashboard(Model model) {

        model.addAttribute("totalUsuarios",usuarioService.listar().size());

        model.addAttribute("totalClientes",clienteService.listar().size());

        model.addAttribute("totalEventos",eventoService.listar().size());

        model.addAttribute("totalContratos",contratoService.listar().size());

        model.addAttribute("eventosProximos",eventoService.listarPorFecha());

        double ingresosTotales = contratoService.listar().stream().mapToDouble(c -> c.getTotalAcordado()).sum();

        model.addAttribute("ingresosTotales",ingresosTotales);

        List<Object[]> eventosPorTipo =eventoService.contarEventosPorTipo();

        model.addAttribute("eventosPorTipo",eventosPorTipo);

        int pagados = 0;
        int pendientes = 0;

        Map<Integer, String> estadosPago =new HashMap<>();

        for(Evento evento : eventoService.listar()) {

            double totalPagado =pagoService.obtenerTotalPagadoPorEvento(evento.getIdEvento());

            if(totalPagado >= evento.getPresupuesto()) {

                pagados++;

                estadosPago.put(evento.getIdEvento(),"Pagado");

            } else {

                pendientes++;

                estadosPago.put(evento.getIdEvento(),"Pendiente");
            }
        }

        model.addAttribute("pagados", pagados);
        model.addAttribute("pendientes", pendientes);

        model.addAttribute("estadosPago",estadosPago);

        return "reportes/dashboard";
    }
    
    @GetMapping("/reportes/exportar-eventos")
    public void exportarEventosExcel(HttpServletResponse response)
            throws Exception {

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        response.setHeader("Content-Disposition","attachment; filename=Reporte_Eventos.xlsx");

        List<Evento> eventos = eventoService.listar();

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Eventos");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Evento");
        header.createCell(2).setCellValue("Cliente");
        header.createCell(3).setCellValue("Tipo");
        header.createCell(4).setCellValue("Fecha");
        header.createCell(5).setCellValue("Lugar");
        header.createCell(6).setCellValue("Invitados");
        header.createCell(7).setCellValue("Presupuesto");

        int fila = 1;

        for(Evento e : eventos){

            Row row = sheet.createRow(fila++);

            row.createCell(0).setCellValue(e.getIdEvento());

            row.createCell(1).setCellValue(e.getNombreEvento());

            row.createCell(2).setCellValue(e.getCliente().getNombre()+ " "+ e.getCliente().getApellido());

            row.createCell(3).setCellValue(e.getTipoEvento());

            row.createCell(4).setCellValue(e.getFechaEvento().toString());

            row.createCell(5).setCellValue(e.getLugar());

            row.createCell(6).setCellValue(e.getCantidadInvitados());

            row.createCell(7).setCellValue(e.getPresupuesto());
        }

        for(int i = 0; i < 8; i++){
            sheet.autoSizeColumn(i);
        }

        workbook.write(response.getOutputStream());

        workbook.close();
    }
}