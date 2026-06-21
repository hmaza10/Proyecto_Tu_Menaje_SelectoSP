package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eventos.entity.Evento;
import com.eventos.entity.Pago;
import com.eventos.service.EventoService;
import com.eventos.service.PagoService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;



import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/gestionpago")
public class PagoController {

    @Autowired
    private PagoService pagoService;
    
    @Autowired
    private EventoService eventoService;

    @GetMapping("/lista")
    public String listar(Model model) {

        model.addAttribute("pagos", pagoService.listar());

        model.addAttribute("pagoService", pagoService);

        return "pago/listaPagos";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        Pago pago = new Pago();
        pago.setEvento(new Evento());

        model.addAttribute("pago", pago);
        model.addAttribute("eventos", eventoService.listar());

        model.addAttribute("presupuesto", 0.0);
        model.addAttribute("totalPagado", 0.0);
        model.addAttribute("saldoPendiente", 0.0);

        return "pago/registrarPago";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Pago pago) {
        pagoService.guardar(pago);
        return "redirect:/gestionpago/lista";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {

        Pago pago = pagoService.buscarPorId(id);

        if (pago.getEvento() == null) {
            pago.setEvento(new Evento());
        }

        double presupuesto = pago.getEvento().getPresupuesto();

        double totalPagado = pagoService.obtenerTotalPagadoPorEvento(pago.getEvento().getIdEvento());

        double saldoPendiente = presupuesto - totalPagado;

        model.addAttribute("pago", pago);
        model.addAttribute("eventos", eventoService.listar());

        model.addAttribute("presupuesto", presupuesto);
        model.addAttribute("totalPagado", totalPagado);
        model.addAttribute("saldoPendiente", saldoPendiente);

        return "pago/registrarPago";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        pagoService.eliminar(id);
        return "redirect:/gestionpago/lista";
    }
    
    @GetMapping("/datosEvento/{idEvento}")
    @ResponseBody
    public Map<String, Object> obtenerDatosEvento(@PathVariable int idEvento){

        Evento evento = eventoService.buscarPorId(idEvento);

        double presupuesto = evento.getPresupuesto();

        double totalPagado = pagoService.obtenerTotalPagadoPorEvento(idEvento);

        double saldoPendiente = presupuesto - totalPagado;

        Map<String, Object> datos = new HashMap<>();

        datos.put("presupuesto", presupuesto);
        datos.put("totalPagado", totalPagado);
        datos.put("saldoPendiente", saldoPendiente);

        return datos;
    }
    
    @GetMapping("/resumen")
    public String resumenPagos(Model model) {

        List<Map<String, Object>> resumenes =new ArrayList<>();

        for(Evento evento : eventoService.listar()) {

            double presupuesto = evento.getPresupuesto();

            double totalPagado = pagoService.obtenerTotalPagadoPorEvento(evento.getIdEvento());

            double saldo = presupuesto - totalPagado;

            Map<String, Object> fila = new HashMap<>();

            fila.put("evento",evento.getNombreEvento());

            fila.put("cliente",evento.getCliente().getNombre()+ " "+ evento.getCliente().getApellido());

            fila.put("presupuesto",presupuesto);

            fila.put("totalPagado",totalPagado);

            fila.put("saldoPendiente",saldo);

            fila.put("estado",saldo <= 0? "Pagado": "Pendiente");

            resumenes.add(fila);
        }

        model.addAttribute("resumenes",resumenes);

        return "pago/resumenPagos";
    }
    
    @GetMapping("/resumen/excel")
    public ResponseEntity<byte[]> exportarExcel() {

        try {

            XSSFWorkbook workbook =
                    new XSSFWorkbook();

            Sheet hoja =
                    workbook.createSheet(
                            "Resumen Pagos");

            Row cabecera =
                    hoja.createRow(0);

            cabecera.createCell(0)
                    .setCellValue("Evento");

            cabecera.createCell(1)
                    .setCellValue("Cliente");

            cabecera.createCell(2)
                    .setCellValue("Presupuesto");

            cabecera.createCell(3)
                    .setCellValue("Total Pagado");

            cabecera.createCell(4)
                    .setCellValue("Saldo Pendiente");

            cabecera.createCell(5)
                    .setCellValue("Estado");

            int fila = 1;

            for(Evento evento : eventoService.listar()) {

                double presupuesto =
                        evento.getPresupuesto();

                double totalPagado =
                        pagoService.obtenerTotalPagadoPorEvento(
                                evento.getIdEvento());

                double saldo =
                        presupuesto - totalPagado;

                String estado =
                        saldo <= 0
                        ? "Pagado"
                        : "Pendiente";

                Row row =
                        hoja.createRow(fila++);

                row.createCell(0)
                   .setCellValue(
                           evento.getNombreEvento());

                row.createCell(1)
                   .setCellValue(
                           evento.getCliente()
                                 .getNombre()
                           + " "
                           + evento.getCliente()
                                 .getApellido());

                row.createCell(2)
                   .setCellValue(
                           presupuesto);

                row.createCell(3)
                   .setCellValue(
                           totalPagado);

                row.createCell(4)
                   .setCellValue(
                           saldo);

                row.createCell(5)
                   .setCellValue(
                           estado);
            }

            for(int i=0; i<6; i++) {

                hoja.autoSizeColumn(i);
            }

            ByteArrayOutputStream out =
                    new ByteArrayOutputStream();

            workbook.write(out);

            workbook.close();

            HttpHeaders headers =
                    new HttpHeaders();

            headers.add(
                    "Content-Disposition",
                    "attachment; filename=ResumenPagos.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(
                            MediaType.APPLICATION_OCTET_STREAM)
                    .body(out.toByteArray());

        } catch(Exception e) {

            e.printStackTrace();

            return ResponseEntity.internalServerError()
                    .build();
        }
    }
    
}

