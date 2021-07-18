package de.alex.management.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.alex.management.ui.forms.LehrerForm;
import de.alex.management.ui.forms.SchuelerForm;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class Window {

	public JFrame frame;
	private JTextField textField;
	public JScrollPane scrollPane = new JScrollPane();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
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
	public Window() {
		initialize();
	}
	
	public void update(UpdateEnum en) {
		UpdateUtil.updateScroll(en);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Arial", Font.PLAIN, 14));
		frame.setResizable(false);
		frame.setBounds(100, 100, 875, 581);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("SchülerManagement");
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Hinzuf\u00FCgen");
		
		scrollPane.setBounds(10, 11, 837, 344);
		frame.getContentPane().add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Zeige");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 360, 67, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Firma einbeziehen");
		chckbxNewCheckBox.setFont(new Font("Arial", Font.PLAIN, 13));
		chckbxNewCheckBox.setBounds(452, 501, 179, 29);
		frame.getContentPane().add(chckbxNewCheckBox);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 13));
		textField.setBounds(10, 501, 431, 29);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {				
				updateList();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {				
				updateList();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {

			}
			
			private void updateList() {
				if(UpdateUtil.prev == UpdateEnum.Lehrer) {
					UpdateUtil.lehrer(textField.getText().trim());
				} else if (UpdateUtil.prev == UpdateEnum.Sch1) {
					UpdateUtil.schueler1(textField.getText().trim(), chckbxNewCheckBox.isSelected());
				} else if (UpdateUtil.prev == UpdateEnum.Sch2) {
					UpdateUtil.schueler2(textField.getText().trim(), chckbxNewCheckBox.isSelected());
				}
			}
		});
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Sch\u00FCler (sort. nach Name)");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(UpdateEnum.Sch1);
				btnNewButton_1.setEnabled(true);
				textField.setEnabled(true);
				textField.setText("");
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 13));
		btnNewButton.setBounds(12, 383, 190, 36);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnSchuelernachKlasse = new JButton("Sch\u00FCler (sort. nach Klasse)");
		btnSchuelernachKlasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(UpdateEnum.Sch2);
				btnNewButton_1.setEnabled(true);
				textField.setEnabled(true);
				textField.setText("");
			}
		});
		btnSchuelernachKlasse.setFont(new Font("Arial", Font.PLAIN, 13));
		btnSchuelernachKlasse.setBounds(12, 425, 190, 36);
		frame.getContentPane().add(btnSchuelernachKlasse);
		
		JButton btnLehrer = new JButton("Lehrer");
		btnLehrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(UpdateEnum.Lehrer);
				btnNewButton_1.setEnabled(true);
				textField.setEnabled(true);
				textField.setText("");
			}
		});
		btnLehrer.setFont(new Font("Arial", Font.PLAIN, 13));
		btnLehrer.setBounds(207, 383, 115, 36);
		frame.getContentPane().add(btnLehrer);
		
		JButton btnKlassen = new JButton("Klassen");
		btnKlassen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(UpdateEnum.Klasse);
				btnNewButton_1.setEnabled(false);
				textField.setEnabled(false);
				textField.setText("");
			}
		});
		btnKlassen.setFont(new Font("Arial", Font.PLAIN, 13));
		btnKlassen.setBounds(207, 425, 115, 36);
		frame.getContentPane().add(btnKlassen);
		
		JButton btnFcher = new JButton("F\u00E4cher");
		btnFcher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(UpdateEnum.Fach);
				btnNewButton_1.setEnabled(false);
				textField.setEnabled(false);
				textField.setText("");
			}
		});
		btnFcher.setFont(new Font("Arial", Font.PLAIN, 13));
		btnFcher.setBounds(326, 383, 115, 36);
		frame.getContentPane().add(btnFcher);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(UpdateUtil.prev == UpdateEnum.Sch1 || UpdateUtil.prev == UpdateEnum.Sch2) {
					SchuelerForm form = new SchuelerForm(null);
					form.frame.setVisible(true);
				} else {
					LehrerForm form = new LehrerForm(null);
					form.frame.setVisible(true);
				}
			}
		});
		btnNewButton_1.setFont(new Font("Arial", Font.BOLD, 13));
		btnNewButton_1.setBounds(527, 403, 104, 36);
		frame.getContentPane().add(btnNewButton_1);
		
		JLabel lblSuche = new JLabel("Suche");
		lblSuche.setFont(new Font("Arial", Font.PLAIN, 14));
		lblSuche.setBounds(10, 473, 67, 20);
		frame.getContentPane().add(lblSuche);
	}
}
