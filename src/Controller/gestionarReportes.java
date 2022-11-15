package Controller;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class gestionarReportes {
    static File repPath = new File("./reports");

    public static void getColeccionReporte(String col) {
        try {
            Connection con = gestionarConexion.getConexion();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nombre", col);
            JasperReport report = JasperCompileManager
                    .compileReport(repPath.getAbsolutePath() + "/libreria_coleccion.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, con);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    repPath.getAbsolutePath() + "/libreria_coleccion.pdf");

        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    public static void getNombreReporte(String name) {
        try {
            Connection con = gestionarConexion.getConexion();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("nombre", name);
            JasperReport report = JasperCompileManager
                    .compileReport(repPath.getAbsolutePath() + "/libreria_nombre.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, con);
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    repPath.getAbsolutePath() + "/libreria_nombre.pdf");

        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

}
