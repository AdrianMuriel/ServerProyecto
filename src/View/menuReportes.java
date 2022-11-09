package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import Controller.gestionarConexion;
import Model.Colecciones;

public class menuReportes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitulo;
	private static ArrayList<Colecciones> listaColecciones = new ArrayList<>();
	private File repPath = new File(getClass().getResource("/data/reports/").getPath());

	ClassLoader clLoad = this.getClass().getClassLoader();
	File defaultProp = new File(clLoad.getResource("data/language/default.properties").getFile());

	private HelpSet helpset = null;
	private HelpBroker browser = null;
	private static URL helpURL;
	public static Locale language;
	private static Properties properties = new Properties();

	/**
	 * Launch the application.
	 */
	public static void iniciar(ArrayList<Colecciones> listaCol) {
		try {

			for (int i = 0; i < listaCol.size(); i++) {
				Colecciones c = listaCol.get(i);
				listaColecciones.add(c);
			}
			menuReportes dialog = new menuReportes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public menuReportes() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblTitulo = new JLabel("Titulo");

		JLabel lblColeccion = new JLabel("Coleccion");

		txtTitulo = new JTextField();
		txtTitulo.setText("Comic Ejemplo 1");
		txtTitulo.setColumns(10);

		JComboBox<Colecciones> cmbColeccion = new JComboBox<Colecciones>();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblTitulo)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(txtTitulo, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
								.addGap(16)
								.addComponent(lblColeccion)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cmbColeccion, 0, 171, Short.MAX_VALUE)
								.addContainerGap()));
		gl_contentPanel.setVerticalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblTitulo)
										.addComponent(txtTitulo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(cmbColeccion, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblColeccion))
								.addContainerGap(187, Short.MAX_VALUE)));
		contentPanel.setLayout(gl_contentPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnTitulo = new JButton("Buscar por Titulo");
		btnTitulo.setActionCommand("");
		buttonPane.add(btnTitulo);
		getRootPane().setDefaultButton(btnTitulo);

		JButton btnColeccion = new JButton("Buscar por Coleccion");
		btnColeccion.setActionCommand("");
		buttonPane.add(btnColeccion);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				for (Colecciones c : listaColecciones) {
					cmbColeccion.addItem(c);
				}
				try {
					FileInputStream isFile = new FileInputStream(defaultProp);
					properties.load(isFile);
					String locLang = String.valueOf(properties.get("LANG"));
					traducirPrograma(locLang);
					String lang[] = locLang.split("_");
					Locale.setDefault(Locale.of(locLang));

					switch (lang[0]) {
						case "es":
							helpURL = this.getClass().getResource("/data/help/help.hs");
							break;
						case "gl":
							helpURL = this.getClass().getResource("/data/help/help_gl_ES.hs");
							break;
					}
					helpset = new HelpSet(null, helpURL);
					browser = helpset.createHelpBroker();
					browser.enableHelpKey(getContentPane(), "reportes", helpset);
				} catch (HelpSetException | IOException ex1) {
					ex1.printStackTrace();
				}
			}
		});
		btnColeccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gestionarConexion.conectar();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("nombre", cmbColeccion.getSelectedItem());
					JasperReport report = JasperCompileManager
							.compileReport(repPath.getCanonicalPath() + "/libreria_coleccion.jrxml");
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, map,
							gestionarConexion.getConexion());
					JasperExportManager.exportReportToPdfFile(jasperPrint,
							repPath.getCanonicalPath() + "/libreria_coleccion.pdf");
					gestionarConexion.cerrarConexion();

					JasperViewer.viewReport(jasperPrint, false);
				} catch (JRException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnTitulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gestionarConexion.conectar();
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("nombre", txtTitulo.getText());
					JasperReport report = JasperCompileManager
							.compileReport(repPath.getCanonicalPath() + "/libreria_nombre.jrxml");
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, map,
							gestionarConexion.getConexion());
					JasperExportManager.exportReportToPdfFile(jasperPrint,
							repPath.getCanonicalPath() + "/libreria_nombre.pdf");
					gestionarConexion.cerrarConexion();

					JasperViewer.viewReport(jasperPrint, false);
				} catch (JRException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	// ----------------------------------------------------------------------------------------------------------------------------
	/**
	 * Este metodo traduce el programa dependiendo del idioma recibido
	 * 
	 * @param idioma Idioma al cual se va a traducir el programa
	 */
	private void traducirPrograma(String idioma) {
		try {
			FileInputStream isFile = new FileInputStream(defaultProp);
			properties.load(isFile);
			properties.setProperty("LANG", String.valueOf(idioma));
			FileOutputStream osFile = new FileOutputStream(defaultProp);
			properties.store(osFile, null);
			osFile.close();

			properties.load(isFile);
			String lang[] = String.valueOf(properties.get("LANG")).split("_");
			Locale.setDefault(Locale.of(idioma));
			language = Locale.of(lang[0], lang[1]);

			Locale.setDefault(language);

			switch (lang[0]) {
				case "es":
					helpURL = this.getClass().getResource("/data/help/help.hs");
					break;
				case "gl":
					helpURL = this.getClass().getResource("/data/help/help_gl_ES.hs");
					break;
			}
			helpset = new HelpSet(null, helpURL);
			browser = helpset.createHelpBroker();
			browser.enableHelpKey(getContentPane(), "crear_modificar", helpset);
		} catch (IOException | HelpSetException e1) {
			e1.printStackTrace();
		}
	}
}
