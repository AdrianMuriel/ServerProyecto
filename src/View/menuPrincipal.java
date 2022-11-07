package View;

/**
 * Este es el menú principal de la aplicación.
 * En el se visualizará la lista de los cómics
 * que existan en la Base de Datos.
 * @author Adrián Muriel Zamora 
 */

//-------- IMPORTS ------------------------------------------------------------
//-------- PAQUETES -----------------------------------------------------------
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.help.*;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout.Alignment;
import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

//-------- CLASES -------------------------------------------------------------
import Model.*;
import Controller.*;

public class menuPrincipal extends JFrame {

	// ----------- Cursores -----------------------------------------------------
	private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
	private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	private Cursor selectorCursor = new Cursor(Cursor.HAND_CURSOR);
	// ----------- Panel --------------------------------------------------------
	private JPanel contentPane;
	private static ArrayList<JMenu> listMenu = new ArrayList<>();
	private static ArrayList<JLabel> listLabels = new ArrayList<>();
	private static ArrayList<JButton> listButtons = new ArrayList<>();
	private static ArrayList<JMenuItem> listMenuItem = new ArrayList<>();
	// ----------- Help ---------------------------------------------------------
	private HelpSet hlpSet;
	private HelpBroker hlpBroker;
	private static URL helpURL;
	// ----------- Otros --------------------------------------------------------
	String msgAvisoCierre = "¿Estas seguro de que deseas cerrar el programa?";
	String titleAvisoCierre = "EL PROGRAMA SE VA A CERRAR";
	URL defaultProp = getClass().getResource("/data/language/default.properties");

	public static Locale language;
	private static Properties properties = new Properties();

	public static void iniciar() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menuPrincipal frame = new menuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try/catch
			} // END run()
		});
	} // END iniciar()

	public menuPrincipal() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		setMinimumSize(new Dimension(450, 350));
		setIconImage(
				Toolkit.getDefaultToolkit().getImage(menuPrincipal.class.getResource("/images/icons/computer.png")));
		setTitle("Lista de Comics - ProyectoDAM");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ----------------------------------------------------------------------------------------------------------------------------
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/pencil.png")));

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/trash.png")));

		JComboBox<Comics> cmbComics = new JComboBox<Comics>();
		JLabel lblPortada = new JLabel("");
		lblPortada.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortada.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/no_image.png")));

		JLabel lblFecha = new JLabel("Fecha de Adquisición");

		JLabel lblColeccion = new JLabel("Colección del cómic");

		JLabel lblStock = new JLabel("Cantidad en stock");

		JLabel lblPrecio = new JLabel("Precio del Cómic");

		JLabel lblEstado = new JLabel("Estado del Cómic");
		GroupLayout groupLayout;

		JButton btnReload = new JButton("");
		btnReload.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/refresh.png")));

		{
			groupLayout = new GroupLayout(getContentPane());
			groupLayout.setHorizontalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(btnModificar, GroupLayout.PREFERRED_SIZE, 198,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(lblEstado, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 207,
													Short.MAX_VALUE)
											.addComponent(lblPrecio, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 207,
													Short.MAX_VALUE)
											.addComponent(lblStock, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 207,
													Short.MAX_VALUE)
											.addComponent(lblColeccion, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
													207, Short.MAX_VALUE)
											.addComponent(lblFecha, GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
											.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
													.addComponent(cmbComics, GroupLayout.PREFERRED_SIZE, 166,
															GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED,
															GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(btnReload, GroupLayout.PREFERRED_SIZE, 35,
															GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblPortada, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
											.addComponent(btnEliminar, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
									.addContainerGap()));
			groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
													.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
															.addComponent(btnReload, 0, 0, Short.MAX_VALUE)
															.addComponent(cmbComics))
													.addGap(18)
													.addComponent(lblFecha)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(lblColeccion)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(lblStock)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(lblPrecio)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(lblEstado))
											.addComponent(lblPortada, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
											.addComponent(btnModificar)
											.addComponent(btnEliminar))
									.addContainerGap()));
			getContentPane().setLayout(groupLayout);
		}
		// ----------------------------------------------------------------------------------------------------------------------------
		JMenuBar mnBarraDeMenu = new JMenuBar();
		setJMenuBar(mnBarraDeMenu);

		JMenu mnArchivo = new JMenu("Archivo");
		mnBarraDeMenu.add(mnArchivo);

		JMenuItem mnItBuscarComic = new JMenuItem("Buscar Cómic");
		mnItBuscarComic.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/search.png")));
		mnArchivo.add(mnItBuscarComic);

		JMenuItem mnItNuevoComic = new JMenuItem("Nuevo Cómic");
		mnItNuevoComic.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/add.png")));
		mnArchivo.add(mnItNuevoComic);

		JMenuItem mnItSalir = new JMenuItem("Salir");
		mnItSalir.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/exit.png")));
		mnArchivo.add(mnItSalir);

		JMenu mnAjustes = new JMenu("Ajustes");
		mnBarraDeMenu.add(mnAjustes);

		JMenuItem mnItConexion = new JMenuItem("Conexión");
		mnItConexion.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/connection.png")));
		mnAjustes.add(mnItConexion);

		JMenu mnIdioma = new JMenu("Idioma");
		mnIdioma.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/language.png")));
		mnAjustes.add(mnIdioma);

		JMenuItem mnItSpanish = new JMenuItem("Español");
		mnItSpanish.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/spanish.png")));
		mnIdioma.add(mnItSpanish);

		JMenuItem mnItGalician = new JMenuItem("Gallego");
		mnItGalician.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/galician.png")));
		mnIdioma.add(mnItGalician);

		JMenu mnAyuda = new JMenu("Ayuda");
		mnBarraDeMenu.add(mnAyuda);

		JMenuItem mnItGetHelp = new JMenuItem("Obtener Ayuda");
		mnItGetHelp.setIcon(new ImageIcon(menuPrincipal.class.getResource("/images/icons/help.png")));
		mnAyuda.add(mnItGetHelp);
		// --------------------------------------------------------------------
		// Aquí nombro cada elementos para poder traducirlos en otro método
		// --------------------------------------------------------------------
		{
			mnAyuda.setName("mnAyuda");
			mnAjustes.setName("mnAjustes");
			mnArchivo.setName("mnArchivo");

			lblColeccion.setName("lblColeccion");
			lblEstado.setName("lblEstado");
			lblFecha.setName("lblFecha");
			lblPrecio.setName("lblPrecio");
			lblStock.setName("lblStock");

			mnItSalir.setName("mnItSalir");
			mnIdioma.setName("mnIdioma");
			mnItGetHelp.setName("mnItGetHelp");
			mnItSpanish.setName("mnItSpanish");
			mnItGalician.setName("mnItGalician");
			mnItConexion.setName("mnItConexion");
			mnItNuevoComic.setName("mnItNuevoComic");
			mnItBuscarComic.setName("mnItBuscarComic");

			btnEliminar.setName("btnEliminar");
			btnModificar.setName("btnModificar");
		}
		// --------------------------------------------------------------------
		// Aquí añado todos los elementos traducibles a una lista para facilitar la
		// traducción
		// --------------------------------------------------------------------
		{
			// --------------------------------------------------------------
			listButtons.add(btnEliminar);
			listButtons.add(btnModificar);
			// --------------------------------------------------------------
			listLabels.add(lblColeccion);
			listLabels.add(lblEstado);
			listLabels.add(lblFecha);
			listLabels.add(lblPrecio);
			listLabels.add(lblStock);
			// ----------------------------------------------------------------
			listMenu.add(mnAjustes);
			listMenu.add(mnArchivo);
			listMenu.add(mnAyuda);
			listMenu.add(mnIdioma);
			// --------------------------------------------------------------
			listMenuItem.add(mnItBuscarComic);
			listMenuItem.add(mnItConexion);
			listMenuItem.add(mnItGalician);
			listMenuItem.add(mnItSpanish);
			listMenuItem.add(mnItGetHelp);
			listMenuItem.add(mnItNuevoComic);
			listMenuItem.add(mnItSalir);
			// --------------------------------------------------------------
		}

		// ----------------------------------------------------------------------------------------------------------------------------

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				setCursor(waitCursor);
				try {
					// ------------------- TRADUCCION --------------------------------------
					properties.load(defaultProp.openStream());
					String locLang = String.valueOf(properties.getProperty("LANG"));
					traducirPrograma(locLang, mnItGetHelp);
					// ------------------- LISTADO -----------------------------------------
					listarComics(
							cmbComics,
							lblFecha,
							lblPortada,
							lblStock,
							lblPrecio,
							lblColeccion,
							lblEstado);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setCursor(defaultCursor);
			} // END windowOpened

			@Override
			public void windowClosing(WindowEvent e) {
				setCursor(waitCursor);
				setCursor(defaultCursor);
			} // END windowClosing
		});
		mnItSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarPrograma();
			} // END mnItSalir
		});
		mnItSpanish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traducirPrograma("es_ES", mnItGetHelp);
			} // END mnItSpanish
		});
		mnItGalician.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				traducirPrograma("gl_ES", mnItGetHelp);
			} // END mnItGalician
		});
		cmbComics.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				setCursor(waitCursor);
				if (cmbComics.getItemCount() > 0) {
					Comics c = (Comics) cmbComics.getSelectedItem();
					seleccionarComic(
							c,
							lblFecha,
							lblStock,
							lblPrecio,
							lblEstado);
					seleccionarPortada(c, lblPortada);
					seleccionarColeccion(c, lblColeccion);
				}
				setCursor(defaultCursor);
			}
		});
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCursor(waitCursor);
				listarComics(
						cmbComics,
						lblFecha,
						lblPortada,
						lblStock,
						lblPrecio,
						lblColeccion,
						lblEstado);
				setCursor(defaultCursor);
			}// END btnReload
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setCursor(waitCursor);
				Comics c = (Comics) cmbComics.getSelectedItem();
				gestionarConexion.conectar();
				int result = gestionarSockets.gestCom.removeComic(c);
				if (result > 0) {
					gestionarConexion.cerrarConexion();
				}
				cmbComics.removeItemAt(cmbComics.getSelectedIndex());
				setCursor(defaultCursor);
			} // END btnEliminar
		});
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			} // END btnModificar
		});
		mnItNuevoComic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			} // END mnItNuevoComic
		});
		mnItBuscarComic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			} // END mnItBuscarComic
		});
		mnItConexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			} // END mnItConexion
		});
		cmbComics.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(selectorCursor);
			}// END cmbComics mouseEntered

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(defaultCursor);
			}// END cmbComics mouseExited
		});
	} // END menuPrincipal()

	/**
	 * Cierra el programa mediante una confirmación
	 */
	private void cerrarPrograma() {
		int opcion = JOptionPane.showConfirmDialog(null, msgAvisoCierre, titleAvisoCierre, 0, 2);
		if (opcion == JOptionPane.YES_OPTION) {
			System.exit(0);
		} // if
	}

	/**
	 * 
	 * @param img        El blob de la BD que contiene el icono a convertir
	 * 
	 * @param lblPortada La etiqueta que muestra la imagen
	 * 
	 * @return Devuelve la imagen como icono para las etiquetas
	 */

	private ImageIcon blobToImgIcon(Blob img, JLabel lblPortada) {
		ImageIcon imageIcon = null;
		try {
			int blobLength = (int) img.length();
			byte[] blobAsBytes = img.getBytes(1, blobLength);
			final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
			imageIcon = new ImageIcon(bufferedImage);
			Image image = imageIcon.getImage();
			Image newimg = image.getScaledInstance(lblPortada.getWidth(),
					lblPortada.getHeight(),
					java.awt.Image.SCALE_SMOOTH);
			imageIcon = new ImageIcon(newimg);
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} // try/catch
		return imageIcon;
	} // END blobToImgIcon(img,lblPortada)

	private void listarComics(
			JComboBox<Comics> cmbComics,
			JLabel lblFecha,
			JLabel lblPortada,
			JLabel lblStock,
			JLabel lblPrecio,
			JLabel lblColeccion,
			JLabel lblEstado) {

		gestionarConexion.conectar();
		ArrayList<Comics> listaComics = gestionarSockets.gestCom.listarComics();
		gestionarConexion.cerrarConexion();

		cmbComics.removeAllItems();
		for (Comics c : listaComics) {
			cmbComics.addItem(c);
		} // for

		Comics c = (Comics) cmbComics.getSelectedItem();
		seleccionarComic(
				c,
				lblFecha,
				lblStock,
				lblPrecio,
				lblEstado);
		seleccionarPortada(c, lblPortada);
		seleccionarColeccion(c, lblColeccion);
	} // END listarComics(cmbComics)

	private void seleccionarComic(
			Comics c,
			JLabel lblFecha,
			JLabel lblStock,
			JLabel lblPrecio,
			JLabel lblEstado) {

		lblFecha.setText(c.getFecha() + "");
		lblStock.setText(c.getCantidad() + " unidades");
		lblEstado.setText(c.getEstado());
		lblPrecio.setText(c.getPrecio() + "€");
	}

	private void seleccionarPortada(
			Comics c,
			JLabel lblPortada) {

		Blob blob = c.getImagen();
		ImageIcon imageIcon = blobToImgIcon(blob, lblPortada);

		lblPortada.setIcon(imageIcon);

		lblPortada.setIcon(imageIcon);
	}

	private void seleccionarColeccion(Comics c, JLabel lblColeccion) {
		gestionarConexion.conectar();
		Colecciones col = gestionarSockets.gestCol.obtenerColeccion(c);
		gestionarConexion.cerrarConexion();
		lblColeccion.setText(col.getTitulo());
	}

	/**
	 * Este metodo traduce el programa dependiendo del idioma recibido
	 * 
	 * @param idioma      Idioma al cual se va a traducir el programa
	 * @param mnItGetHelp Botón del menú que abre la ayuda, a parte de el F1
	 */
	private void traducirPrograma(String idioma, JMenuItem mnItGetHelp) {
		try {
			properties.load(defaultProp.openStream());
			properties.setProperty("LANG", String.valueOf(idioma));
			FileOutputStream osFile = new FileOutputStream(new File(defaultProp.getPath()));
			defaultProp.openStream().transferTo(osFile);
			properties.store(osFile, null);
			osFile.close();

			properties.load(defaultProp.openStream());
			String lang[] = String.valueOf(properties.getProperty("LANG")).split("_");
			Locale.setDefault(Locale.of(idioma));

			switch (lang[0]) {
				case "es":
					helpURL = this.getClass().getResource("/data/help/help.hs");
					break;
				case "gl":
					helpURL = this.getClass().getResource("/data/help/help_gl_ES.hs");
					break;
			} // switch

			hlpSet = new HelpSet(null, helpURL);
			hlpBroker = hlpSet.createHelpBroker();
			hlpBroker.enableHelpOnButton(mnItGetHelp, "principal", hlpSet);
			hlpBroker.enableHelpKey(getContentPane(), "principal", hlpSet);

			gestionarIdioma.traducirBotones(listButtons);
			gestionarIdioma.traducirMenu(listMenu, listMenuItem);
		} catch (IOException | HelpSetException e1) {
			e1.printStackTrace();
		} // try/catch
	}// END traducirPrograma(idioma)

} // END menuPrincipal class