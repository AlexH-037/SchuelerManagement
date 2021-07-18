package de.alex.management;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.alex.management.types.Lehrer;
import de.alex.management.types.Schueler;

public class Util {
	
	// Simple Methode um den Text einer URL auszulesen
	public static List<String> readWordlist(String url) {
		final List<String> lines = new ArrayList<String>();
		try {
			URL website = new URL(url);
			URLConnection connection = website.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String str;
			while ((str = in.readLine()) != null) {
				// Nur Namen mit unter 8 Buchstaben nehmen damit die Namen nicht zu lange werden (& kaputte Zeichen vermeiden)
				if (str.length() < 8 && !str.contains("�")) {
					// Wenn CSV
					if(str.contains(",")) {
						lines.add(str.split(",")[0]);
					} else {
						lines.add(str);
					}
				}
			}
			in.close();
		} catch (Exception e) {
			//ignore
		}
		return lines;
	}
	
	public static ArrayList<String> getKlassen() {
		ArrayList<String> klassen = new ArrayList<String>();
		for(Schueler sch : Main.getManager().schueler) {
			if(!klassen.contains(sch.getKlasse())) {
				klassen.add(sch.getKlasse());
			}
		}
		for(Lehrer leh : Main.getManager().lehrer) {
			for (String s : leh.fachKlasseCombo) {
				String klasse = s.split(";")[0];
				if (!klassen.contains(klasse)) {
					klassen.add(klasse);
				}
			}
		}
		return klassen;
	}
	
	public static ArrayList<String> getFaecher() {
		ArrayList<String> faecher = new ArrayList<String>();
		for(Lehrer leh : Main.getManager().lehrer) {
			for (String s : leh.fachKlasseCombo) {
				String fach = s.split(";")[1];
				if (!faecher.contains(fach)) {
					faecher.add(fach);
				}
			}
		}
		return faecher;
	}

}
