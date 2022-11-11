package Controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class gestionarReportes {
    private static File repPath = new File(gestionarReportes.class.getResource("/reports/").getPath());

    public static void getColeccionReporte(String col) {
        try {
            System.out.println(repPath.getCanonicalPath());
            Connection con = gestionarConexion.getConexion();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nombre", col);
            JasperReport report = JasperCompileManager
                    .compileReport(repPath.getCanonicalPath() + "/libreria_coleccion.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, con);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    repPath.getCanonicalPath() + "/libreria_coleccion.pdf");

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void getNombreReporte(String name) {
        try {
            System.out.println(repPath.getCanonicalPath());
            Connection con = gestionarConexion.getConexion();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nombre", name);
            JasperReport report = JasperCompileManager
                    .compileReport(repPath.getCanonicalPath() + "/libreria_nombre.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, con);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    repPath.getCanonicalPath() + "/libreria_nombre.pdf");

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | IOException e1) {
            e1.printStackTrace();
        }
    }

}
