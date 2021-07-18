package de.alex.management.ui.forms;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import de.alex.management.Main;
import de.alex.management.types.Lehrer;
import de.alex.management.ui.UpdateEnum;

public class LehrerForm {

	public JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LehrerForm window = new LehrerForm(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LehrerForm(Lehrer leh) {
		initialize(leh);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Lehrer leh) {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 370, 335);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setTitle(leh != null ? "Lehrer bearbeiten" : "Lehrer erstellen");
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 11, 59, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 13));
		textField.setBounds(10, 33, 334, 23);
		if(leh != null) {
			textField.setText(leh.getName());
		}
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblFach = new JLabel("Klasse-Fach-Combos");
		lblFach.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFach.setBounds(10, 68, 192, 14);
		frame.getContentPane().add(lblFach);
		
		JTextPane txtpnBeispielfi = new JTextPane();
		txtpnBeispielfi.setText("Beispiel:\r\n\r\n11FI1 - PUG\r\n10FI2 - AWP\r\n\r\n(aka. unterichtet PUG in Klasse 11FI1)");
		txtpnBeispielfi.setEditable(false);
		txtpnBeispielfi.setFont(new Font("Arial", Font.PLAIN, 12));
		txtpnBeispielfi.setBounds(10, 93, 334, 84);
		frame.getContentPane().add(txtpnBeispielfi);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 184, 332, 68);
		frame.getContentPane().add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Arial", Font.PLAIN, 13));
		if(leh != null && !leh.fachKlasseCombo.isEmpty()) {
			for(String s : leh.fachKlasseCombo) {
				textPane.setText(textPane.getText() + s.replaceAll(";", "-") + "\n");
			}
		}
		scrollPane.setViewportView(textPane);
		
		JButton btnNewButton = new JButton("Speichern");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Ein Name muss gegeben sein", "Fehler", 0);
					return;
				}
				int index = leh != null ? Main.getManager().lehrer.indexOf(leh) : -1;
				
				Lehrer newLeh = new Lehrer();
				newLeh.name = textField.getText();
				if(!textPane.getText().trim().isEmpty()) {
					if(!textPane.getText().trim().contains("\n")) {
						newLeh.fachKlasseCombo.add(textPane.getText().trim().replaceAll("-", ";"));
					} else {
						for(String s : textPane.getText().trim().split("\n")) {
							newLeh.fachKlasseCombo.add(s.trim().replaceAll("-", ";"));
						}
					}
				}
				
				// Wenn Lehrer bearbeitet wird, ersetzen, wenn nicht, hinzuf¸gen
				if(index > -1) {
					Main.getManager().lehrer.set(index, newLeh);
				} else {
					Main.getManager().lehrer.add(newLeh);
				}
				
				// Haupt Fenster aktuallisieren und diesen Dialog schlieﬂen
				Main.getWindow().update(UpdateEnum.Lehrer);
				Main.getManager().save();
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 13));
		btnNewButton.setBounds(10, 257, 104, 32);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLschen = new JButton("L\u00F6schen");
		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getManager().lehrer.remove(leh);
				Main.getWindow().update(UpdateEnum.Lehrer);
				frame.dispose();
			}
		});
		btnLschen.setFont(new Font("Arial", Font.PLAIN, 13));
		btnLschen.setEnabled(false);
		btnLschen.setBounds(123, 257, 104, 32);
		frame.getContentPane().add(btnLschen);
		
		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnAbbrechen.setFont(new Font("Arial", Font.PLAIN, 13));
		btnAbbrechen.setBounds(236, 257, 104, 32);
		frame.getContentPane().add(btnAbbrechen);
		
	}
}
