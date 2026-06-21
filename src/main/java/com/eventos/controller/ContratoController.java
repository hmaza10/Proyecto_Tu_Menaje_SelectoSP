package com.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eventos.entity.Contrato;
import com.eventos.entity.EventoServicio;
import com.eventos.repository.EventoServicioRepository;
import com.eventos.service.ContratoService;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/gestioncontrato")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;
    
    @Autowired
    private EventoServicioRepository eventoServicioRepository;

    @GetMapping("/lista")
    public String listar(Model model) {
        model.addAttribute("contratos", contratoService.listar());
        return "contrato/listaContratos";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable int id, Model model) {

        model.addAttribute("contrato",contratoService.buscarPorId(id));

        return "contrato/detalleContrato";
    }
    
    @GetMapping("/pdf/{id}")
    public void generarPdf(@PathVariable int id, HttpServletResponse response) throws Exception {

        Contrato contrato = contratoService.buscarPorId(id);

        List<EventoServicio> servicios = eventoServicioRepository.findByEvento_IdEvento(contrato.getEvento().getIdEvento());

        response.setContentType("application/pdf");

        response.setHeader("Content-Disposition","attachment; filename=Contrato_"+ contrato.getIdContrato()+ ".pdf");

        Document document = new Document();

        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        //ENCABEZADO EMPRESA

        Paragraph empresa =new Paragraph("TU MENAJE SELECTO\n");

        empresa.setAlignment(Element.ALIGN_RIGHT);

        document.add(empresa);

        document.add(new Paragraph(" "));

        //TITULO

        Font titulo = new Font(Font.HELVETICA,18,Font.BOLD);

        Paragraph encabezado = new Paragraph("CONTRATO DE SERVICIO",titulo);

        encabezado.setAlignment(Element.ALIGN_CENTER);

        document.add(encabezado);

        document.add(new Paragraph(" "));

        document.add(new Paragraph("Contrato N°: "+ contrato.getIdContrato()));

        document.add(new Paragraph("Fecha Contrato: "+ contrato.getFechaContrato()));

        document.add(new Paragraph(" "));

        //CLIENTE

        Font subtitulo = new Font(Font.HELVETICA,14,Font.BOLD);

        document.add(new Paragraph("DATOS DEL CLIENTE",subtitulo));

        document.add(
                new Paragraph("Nombre: "+ contrato.getEvento().getCliente().getNombre()+ " " + contrato.getEvento().getCliente().getApellido()));

        document.add(new Paragraph("Correo: "+ contrato.getEvento().getCliente().getCorreo()));

        document.add(new Paragraph("Teléfono: "+ contrato.getEvento().getCliente().getTelefono()));

        document.add(new Paragraph(" "));

        //EVENTO

        document.add(new Paragraph("DATOS DEL EVENTO",subtitulo));

        document.add(new Paragraph("Evento: "+ contrato.getEvento().getNombreEvento()));

        document.add(new Paragraph("Tipo Evento: "+ contrato.getEvento().getTipoEvento()));

        document.add(new Paragraph("Fecha Evento: "+ contrato.getEvento().getFechaEvento()));

        document.add(new Paragraph("Lugar: "+ contrato.getEvento().getLugar()));

        document.add(new Paragraph("Cantidad Invitados: "+ contrato.getEvento().getCantidadInvitados()));

        document.add(new Paragraph(" "));

        // SERVICIOS

        document.add(
                new Paragraph(
                        "SERVICIOS CONTRATADOS",
                        subtitulo));

        PdfPTable tabla =
                new PdfPTable(4);

        tabla.setWidthPercentage(100);

        tabla.addCell("Servicio");
        tabla.addCell("Proveedor");
        tabla.addCell("Cantidad");
        tabla.addCell("Subtotal");

        for(EventoServicio es : servicios){

            tabla.addCell(
                    es.getServicio()
                      .getNombreServicio());

            tabla.addCell(
                    es.getServicio()
                      .getIdProveedor()
                      .getNombreEmpresa());

            tabla.addCell(
                    String.valueOf(
                            es.getCantidad()));

            tabla.addCell(
                    "S/ "
                    + es.getSubtotal());
        }

        document.add(tabla);

        document.add(new Paragraph(" "));

        // DESCRIPCION

        document.add(
                new Paragraph(
                        "DESCRIPCIÓN DEL SERVICIO",
                        subtitulo));

        document.add(
                new Paragraph(
                        contrato.getDescripcion()));

        document.add(new Paragraph(" "));

        // CLAUSULAS

        document.add(
                new Paragraph(
                        "CONDICIONES",
                        subtitulo));

        document.add(
                new Paragraph(
                        "1. La empresa se compromete a brindar los servicios contratados en la fecha acordada."));

        document.add(
                new Paragraph(
                        "2. El cliente acepta las condiciones establecidas para la realización del evento."));

        document.add(
                new Paragraph(
                        "3. Toda modificación posterior estará sujeta a disponibilidad y coordinación previa."));

        document.add(
                new Paragraph(
                        "4. El incumplimiento de cualquiera de las partes podrá generar la resolución del presente contrato."));

        document.add(new Paragraph(" "));

        // TOTAL

        Font totalFont =
                new Font(
                        Font.HELVETICA,
                        14,
                        Font.BOLD);

        document.add(
                new Paragraph(
                        "TOTAL ACORDADO: S/ "
                        + contrato.getTotalAcordado(),
                        totalFont));

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // FIRMAS

        PdfPTable firmas =
                new PdfPTable(2);

        firmas.setWidthPercentage(100);

        firmas.addCell(
                "________________________\n"
                + "TU MENAJE SELECTO");

        firmas.addCell(
                "________________________\n"
                + "CLIENTE");

        document.add(firmas);

        document.close();
    }
    
}
