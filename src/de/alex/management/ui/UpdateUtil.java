package de.alex.management.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;

import de.alex.management.Main;
import de.alex.management.Util;
import de.alex.management.types.Lehrer;
import de.alex.management.types.Schueler;
import de.alex.management.ui.forms.LehrerForm;
import de.alex.management.ui.forms.SchuelerForm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

public class UpdateUtil {
	
	public static UpdateEnum prev = UpdateEnum.Sch1;
	
	private static boolean isSearchGoodEnough(String input, String search) {
		return FuzzySearch.partialRatio(input.toLowerCase(), search.toLowerCase()) > 75 || FuzzySearch.ratio(input.toLowerCase(), search.toLowerCase()) > 75;
	}
	
	public static void updateScroll(UpdateEnum en) {
		prev = en;
//		Main.getWindow().scrollPane.removeAll();
		Main.getWindow().scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		Main.getWindow().scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		Main.getWindow().scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		switch(en) {
			case Sch1:
				schueler1("", false);
				break;
			case Sch2:
				schueler2("", false);
				break;
			case Lehrer:
				lehrer("");
				break;
			case Fach:
				fach();
				break;
			case Klasse:
				klasse( );
				break;
		}
	}
	
	public static void schueler1(String search, boolean firma) {
		int height = 2;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		Object[] schueler;
		if(search.isEmpty()) {
			schueler = Main.getManager().schueler.stream().sorted(Comparator.comparing(Schueler::getName)).toArray();
		} else {
			schueler = Main.getManager().schueler.stream().sorted(Comparator.comparing(Schueler::getName)).filter(c -> isSearchGoodEnough((firma ? c.getName() + " - " + c.getFirma() : c.getName()), search)).toArray();
		}
		
		for(Object obj : schueler) {
			Schueler sch = (Schueler) obj;
			JLabel label = new JLabel(String.format("%s | Klasse: %s | Ausbildung: %s | Firma: %s", sch.getName(), sch.getKlasse(), sch.getGebiet(), sch.getFirma()));
			label.setFont(new Font("Verdana", Font.BOLD, 11));
			label.setBounds(2, height, 850, 14);
			label.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {		
					label.setFont(new Font("Verdana", Font.BOLD, 11));
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {	
					label.setFont(new Font("Verdana", Font.BOLD, 12));
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					SchuelerForm form = new SchuelerForm(sch);
					form.frame.setVisible(true);
				}
			});
			panel.add(label);
			height += 19;
		}
		Main.getWindow().scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(900, height + 5));
		Main.getWindow().scrollPane.repaint();
		Main.getWindow().scrollPane.revalidate();
	}
	
	public static void schueler2(String search, boolean firma) {
		int height = 2;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		Object[] schueler;
		if(search.isEmpty()) {
			schueler = Main.getManager().schueler.stream().sorted(Comparator.comparing(Schueler::getKlasse).thenComparing(Schueler::getName)).toArray();
		} else {
			schueler = Main.getManager().schueler.stream().sorted(Comparator.comparing(Schueler::getKlasse).thenComparing(Schueler::getName)).filter(c -> isSearchGoodEnough((firma ? c.getName() + " - " + c.getFirma() : c.getName()), search)).toArray();
		}
		
		for(Object obj : schueler) {
			Schueler sch = (Schueler) obj;
			JLabel label = new JLabel(String.format("%s | Klasse: %s | Ausbildung: %s | Firma: %s", sch.getName(), sch.getKlasse(), sch.getGebiet(), sch.getFirma()));
			label.setFont(new Font("Verdana", Font.BOLD, 11));
			label.setBounds(2, height, 850, 14);
			label.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {		
					label.setFont(new Font("Verdana", Font.BOLD, 11));
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {	
					label.setFont(new Font("Verdana", Font.BOLD, 12));
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					SchuelerForm form = new SchuelerForm(sch);
					form.frame.setVisible(true);
				}
			});
			panel.add(label);
			height += 19;
		}
		Main.getWindow().scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(900, height + 5));
		Main.getWindow().scrollPane.repaint();
		Main.getWindow().scrollPane.revalidate();
	}
	
	public static void lehrer(String search) {
		int height = 2;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		Object[] lehrer;
		if(search.isEmpty()) {
			lehrer = Main.getManager().lehrer.stream().sorted(Comparator.comparing(Lehrer::getName)).toArray();
		} else {
			lehrer = Main.getManager().lehrer.stream().sorted(Comparator.comparing(Lehrer::getName)).filter(c -> isSearchGoodEnough(c.getName(), search)).toArray();
		}
		for(Object obj : lehrer) {
			Lehrer leh = (Lehrer) obj;
			String unterrichtet = "";
			for(String combo : leh.fachKlasseCombo) {
				unterrichtet += ", " + combo.split(";")[1].trim() + " in Klasse " + combo.split(";")[0].trim();
			}
			JLabel label = new JLabel(String.format("Lehrer: %s | Unterrichtet: %s", leh.name, unterrichtet.substring(2)));
			label.setFont(new Font("Verdana", Font.BOLD, 11));
			label.setBounds(2, height, 850, 12);
			label.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {		
					label.setFont(new Font("Verdana", Font.BOLD, 11));
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {	
					label.setFont(new Font("Verdana", Font.BOLD, 12));
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					LehrerForm form = new LehrerForm(leh);
					form.frame.setVisible(true);
				}
			});
			panel.add(label);
			height += 18;
		}
		Main.getWindow().scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(900, height + 5));
		Main.getWindow().scrollPane.repaint();
	}
	
	public static void fach() {
		int height = 2;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JViewport viewport = new JViewport();
		viewport.add(panel);
		List<String> faecher = Util.getFaecher();
		Collections.sort(faecher);
		for(String s : faecher) {
			JLabel label = new JLabel(s);
			label.setFont(new Font("Verdana", Font.BOLD, 11));
			label.setBounds(2, height, 850, 12);
			panel.add(label);
			height += 18;
		}
		panel.setPreferredSize(new Dimension(-1, height + 5));
		Main.getWindow().scrollPane.setViewport(viewport);
		Main.getWindow().scrollPane.repaint();
	}
	
	public static void klasse() {
		int height = 2;
		JPanel panel = new JPanel();
		panel.setLayout(null);
		JViewport viewport = new JViewport();
		viewport.add(panel);
		List<String> klassen = Util.getKlassen();
		Collections.sort(klassen);
		for(String s : klassen) {
			JLabel label = new JLabel(s + " (" + Util.getSchuelerInKlasse(s) + " Schüler)");
			label.setFont(new Font("Verdana", Font.BOLD, 11));
			label.setBounds(2, height, 850, 12);
			panel.add(label);
			height += 18;
		}
		panel.setPreferredSize(new Dimension(-1, height + 5));
		Main.getWindow().scrollPane.setViewport(viewport);
		Main.getWindow().scrollPane.repaint();
		
	}

}
