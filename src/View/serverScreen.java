package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.net.ServerSocket;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class serverScreen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static server s1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			serverScreen dialog = new serverScreen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public serverScreen() {
		setTitle("Server Screen");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblServerStatus = new JLabel("SERVER OFF");
		lblServerStatus.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblServerStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerStatus.setForeground(new Color(255, 0, 0));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap()
						.addComponent(lblServerStatus, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
						.addContainerGap()));
		gl_contentPanel.setVerticalGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup().addContainerGap()
						.addComponent(lblServerStatus, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(139, Short.MAX_VALUE)));
		contentPanel.setLayout(gl_contentPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblServerStatus.setText("SERVER ON");
				lblServerStatus.setForeground(new Color(0, 255, 0));
				ServerSocket servidor;
				try {
					servidor = new ServerSocket(5000);
					s1 = new server(servidor);
					s1.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnStart.setActionCommand("");
		buttonPane.add(btnStart);
		getRootPane().setDefaultButton(btnStart);

		JButton btnStop = new JButton("STOP");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblServerStatus.setText("SERVER OFF");
				lblServerStatus.setForeground(new Color(255, 0, 0));
				s1.stopServer();
			}
		});
		btnStop.setActionCommand("");
		buttonPane.add(btnStop);

	}
}
