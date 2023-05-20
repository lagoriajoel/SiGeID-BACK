package com.informes.informesbackend.Services;

import com.informes.informesbackend.Models.Entities.Alumno;
import com.informes.informesbackend.Models.Entities.Contenido;
import com.informes.informesbackend.Models.Entities.InformeDesempeño;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class jasperReportService {

    public ResponseEntity<Resource> exportInvoice(Optional<Alumno> alumno, Set<Contenido> contenidosAdeudados) {




        if (alumno.isPresent())
            try {
                final Alumno alumno1 = alumno.get();
                final File file = ResourceUtils.getFile("classpath:reportPDF.jasper");
                final File imgLogo = ResourceUtils.getFile("classpath:LOGO_EICO.png");
                final JasperReport report = (JasperReport) JRLoader.loadObject(file);

                final HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("nombre", alumno1.getNombreCompleto());
                parameters.put("dni", alumno1.getDni());
                parameters.put("anio", alumno1.getCurso().getAnio());
                parameters.put("turno", alumno1.getCurso().getTurno());

                parameters.put("division", alumno1.getCurso().getDivision());
                parameters.put("cicloLectivo", alumno1.getCurso().getCicloLectivo());
                parameters.put("asignatura", contenidosAdeudados.stream().findFirst().get().getAsignatura().getNombre());



                parameters.put("logo", new FileInputStream(imgLogo));
               parameters.put("ds", new JRBeanCollectionDataSource((Collection<?>) contenidosAdeudados));

                JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
                byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
                String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
                StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
                ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                        .filename(stringBuilder
                                .append("generateDate:")
                                .append(sdf)
                                .append(".pdf")
                                .toString())
                        .build();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentDisposition(contentDisposition);
                return ResponseEntity.ok().contentLength((long) reporte.length)
                        .contentType(MediaType.APPLICATION_PDF)
                        .headers(headers).body(new ByteArrayResource(reporte));
            } catch (Exception e) {
                e.printStackTrace();
            }
        else {
            return ResponseEntity.noContent().build(); //No se encontro el reporte
        }

        return null;


    }
}
