package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.InformeDesempeño;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Service
public class PDFgeneradorService {
    public void export(HttpServletResponse response, Set<Contenido> contenidosAdeudados, Alumno alumno) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_BOLD);
        fontTitle.setSize(12);
        Font fontParagraph = FontFactory.getFont(FontFactory.TIMES);
        fontParagraph.setSize(12);
        Font fontDireccion = FontFactory.getFont(FontFactory.TIMES);
        fontParagraph.setSize(8);

        Paragraph pEscuela = new Paragraph("Escuela Industrial N°1 'Gral. Enrique Mosconi'", fontTitle);
        pEscuela.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph pDireccion = new Paragraph("Calle Estrada N° 439 - Caleta Olivia - Santa Cruz", fontDireccion);
        pDireccion.setAlignment(Paragraph.ALIGN_CENTER);



        Paragraph pTitulo = new Paragraph("INFORME DE DESEMPEÑO (Acuerdo 075/14)", fontParagraph);
        pTitulo.setAlignment(Paragraph.ALIGN_LEFT);


        Paragraph pAsignatura = new Paragraph("Asignatura: "+ contenidosAdeudados.stream().findFirst().get().getAsignatura().getNombre(), fontParagraph);
        pTitulo.setAlignment(Paragraph.ALIGN_LEFT);



        Paragraph pNombre = new Paragraph("Alumno:  " + alumno.getNombres() , fontParagraph);
        pNombre.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph pDNI = new Paragraph("DNI N°: " + alumno.getDni() , fontParagraph);
        pDNI.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph pCurso = new Paragraph("Curso : " +alumno.getCurso().getAnio() +" "+ alumno.getCurso().getDivision(), fontParagraph);
        pCurso.setAlignment(Paragraph.ALIGN_LEFT);
        Paragraph pTurno = new Paragraph("Turno : ", fontParagraph);
        pTurno.setAlignment(Paragraph.ALIGN_LEFT);

        // Let's create a Table object
        Table myTable = new Table(2); // 2 columns
        myTable.setPadding(2f);
        myTable.setSpacing(1f);
        myTable.setWidth(100f);
        myTable.setBorder(1);


        // Create the header of the table
        ArrayList<String> headerTable = new ArrayList<>();
        headerTable.add("Contenidos No Acreditados");
        headerTable.add("Descripción");


        headerTable.forEach(e -> {
            Cell current = new Cell(new Phrase(e, fontTitle));
            current.setHeader(true);
            current.setBackgroundColor(Color.LIGHT_GRAY);

            myTable.addCell(current);
        });

        // Then create a list of rows and add them to the table
       // LinkedHashMap<Integer, List<String>> listRows = new LinkedHashMap<>();


      //  listRows.put(1, Arrays.asList("Miguel","Surname"));
        //listRows.put(2, Arrays.asList("Username1","Surname2"));


        contenidosAdeudados.forEach((contenido) -> {
            String nombre = contenido.getNombre();
            String descripcion = contenido.getDescripcion();


            myTable.addCell(new Cell(new Phrase(nombre, fontDireccion)));
            myTable.addCell(new Cell(new Phrase(descripcion, fontTitle)));

        });

        document.add(pEscuela);
        document.add(pDireccion);
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(pTitulo);
        document.add(pAsignatura);
        document.add(new Paragraph(Chunk.NEWLINE));
        document.add(pNombre);
        document.add(pDNI);
        document.add(pCurso);
        document.add(pTurno);
        document.add(myTable);



        document.close();
    }
}
