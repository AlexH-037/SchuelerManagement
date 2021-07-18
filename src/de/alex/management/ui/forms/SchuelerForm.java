package de.alex.management.ui.forms;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import de.alex.management.Main;
import de.alex.management.types.Schueler;
import de.alex.management.ui.UpdateEnum;

public class SchuelerForm {

	public JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SchuelerForm window = new SchuelerForm(null);
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
	public SchuelerForm(Schueler sch) {
		initialize(sch);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Schueler sch) {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 370, 322);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle(sch == null ? "Schueler erstellen" : "Schueler bearbeiten");
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel.setBounds(12, 12, 64, 20);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 13));
		textField.setBounds(12, 35, 330, 23);
		if(sch != null) {
			textField.setText(sch.getName());
		}
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblKlasse = new JLabel("Klasse");
		lblKlasse.setFont(new Font("Arial", Font.PLAIN, 13));
		lblKlasse.setBounds(12, 70, 64, 20);
		frame.getContentPane().add(lblKlasse);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Arial", Font.PLAIN, 13));
		textField_1.setColumns(10);
		if(sch != null) {
			textField_1.setText(sch.getKlasse());
		}
		textField_1.setBounds(12, 93, 330, 23);
		frame.getContentPane().add(textField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Firma");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_1_1.setBounds(12, 128, 64, 20);
		frame.getContentPane().add(lblNewLabel_1_1);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Arial", Font.PLAIN, 13));
		textField_2.setColumns(10);
		if(sch != null) {
			textField_2.setText(sch.getFirma());
		}
		textField_2.setBounds(12, 151, 330, 23);
		frame.getContentPane().add(textField_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Ausbildungsgebiet");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_1_1_1.setBounds(12, 186, 164, 20);
		frame.getContentPane().add(lblNewLabel_1_1_1);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Arial", Font.PLAIN, 13));
		textField_3.setColumns(10);
		if(sch != null) {
			textField_3.setText(sch.getGebiet());
		}
		textField_3.setBounds(12, 209, 330, 23);
		frame.getContentPane().add(textField_3);
		
		JButton btnNewButton = new JButton("Speichern");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(textField.getText().isEmpty() || textField_1.getText().isEmpty() || textField_2.getText().isEmpty() || textField_3.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Bitte alle Felder ausf¸llen!", "Fehler", MessageType.ERROR.ordinal());
					return;
				}
				int index = sch != null ? Main.getManager().schueler.indexOf(sch) : -1;
				
				Schueler newSch = new Schueler();
				newSch.name = textField.getText();
				newSch.klasse = textField_1.getText();
				newSch.firma = textField_2.getText();
				newSch.gebiet = textField_3.getText();
				
				// Wenn Schueler bearbeitet wird, ersetzen, wenn nicht, hinzuf¸gen
				if(index > -1) {
					Main.getManager().schueler.set(index, newSch);
				} else {
					Main.getManager().schueler.add(newSch);
				}
				
				// Haupt Fenster aktuallisieren und diesen Dialog schlieﬂen
				Main.getWindow().update(UpdateEnum.Sch1);
				Main.getManager().save();
				frame.dispose();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 13));
		btnNewButton.setBounds(12, 244, 104, 32);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnLschen = new JButton("L\u00F6schen");
		btnLschen.setEnabled(sch != null);
		btnLschen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getManager().schueler.remove(sch);
				Main.getWindow().update(UpdateEnum.Sch1);
				frame.dispose();
			}
		});
		btnLschen.setFont(new Font("Arial", Font.PLAIN, 13));
		btnLschen.setBounds(125, 244, 104, 32);
		frame.getContentPane().add(btnLschen);
		
		JButton btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnAbbrechen.setFont(new Font("Arial", Font.PLAIN, 13));
		btnAbbrechen.setBounds(238, 244, 104, 32);
		frame.getContentPane().add(btnAbbrechen);
	}

}
