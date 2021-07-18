package de.alex.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import de.alex.management.types.Lehrer;
import de.alex.management.types.Schueler;

public class Manager {
	
	public Gson gson = new GsonBuilder().setPrettyPrinting().create();
	public Random rand = new Random();
	private File directory = new File(System.getProperty("user.home"), "Alex-SchuelerManagement");
	
	public CopyOnWriteArrayList<Lehrer> lehrer = new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<Schueler> schueler = new CopyOnWriteArrayList<>();
	
	public List<String> vornamen;
	public List<String> nachnamen;
	
	public void load() {
		vornamen = Util.readWordlist("https://raw.githubusercontent.com/fxnn/vornamen/master/Vornamen_Koeln_2017.csv");
		nachnamen = Util.readWordlist("https://raw.githubusercontent.com/HBehrens/phonet4n/master/src/Tests/data/nachnamen.txt");
		if(!directory.exists() || directory.listFiles().length < 1) {
			// Ordner für die Dateien erstellen (passiert nichts wenn schon vorhanden)
			directory.mkdirs();
			// Ein paar Beispiele hinzufügen
			addSamples();
			save();
			// Nicht weiter ausführen da Ordner ursprünglich nicht existiert hat
			return;
		}
		// (mögliche) Fehler auffangen und ausgeben
		try {
			// Lehrer und Schueler über JSON aus der Datei parsen
			lehrer = gson.fromJson(FileUtils.readFileToString(getFileFromDir("lehrer.json")), new TypeToken<CopyOnWriteArrayList<Lehrer>>(){}.getType());
			schueler = gson.fromJson(FileUtils.readFileToString(getFileFromDir("schueler.json")), new TypeToken<CopyOnWriteArrayList<Schueler>>(){}.getType());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {		
		// (mögliche) Fehler auffangen und ausgeben
		try {
			// Lehrer und Schueler als Datei speichern
			FileUtils.write(getFileFromDir("lehrer.json"), gson.toJson(lehrer, new TypeToken<CopyOnWriteArrayList<Lehrer>>(){}.getType()), false);
			FileUtils.write(getFileFromDir("schueler.json"), gson.toJson(schueler, new TypeToken<CopyOnWriteArrayList<Schueler>>(){}.getType()), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File getFileFromDir(String name) {
		return new File(directory, name);
	}
	
	private void addSamples() {
		ArrayList<String> klassen = new ArrayList<>();
		klassen.add("10FI1");
		klassen.add("10FI2");
		klassen.add("11FI1");
		klassen.add("12FI1");
		ArrayList<String> faecher = new ArrayList<>();
		faecher.add("DEU");
		faecher.add("PUG");
		faecher.add("AWP");
		faecher.add("ITS");
		faecher.add("ITT");
		
		// Lehrer erstellen
		ArrayList<String> vorhandeneCombos = new ArrayList();
		
		for(int x = 0; x < 5; x++) {
			Lehrer leh = new Lehrer();
			leh.name = Main.getManager().vornamen.get(rand.nextInt(Main.getManager().vornamen.size())) + " " + Main.getManager().nachnamen.get(rand.nextInt(Main.getManager().nachnamen.size()));
			// Zufällige Klasse-Fach Combos hinzufügen
			for(int i = 0; i < 2; i++) {
				String combo = "";
				// Sicher gehen dass nicht schon existiert
				boolean exists = false;
				while(!exists) {
					combo = String.format("%s;%s", klassen.get(rand.nextInt(klassen.size())), faecher.get(rand.nextInt(faecher.size())));
					if(!vorhandeneCombos.contains(combo)) {
						vorhandeneCombos.add(combo);
						exists = true;
						break;
					}
				}
				leh.fachKlasseCombo.add(combo);
			}
			lehrer.add(leh);
		}
		
		// Schueler erstellen
		for(int x = 0; x < 25; x++) {
			Schueler sch = new Schueler();
			sch.name = Main.getManager().vornamen.get(rand.nextInt(Main.getManager().vornamen.size())) + " " + Main.getManager().nachnamen.get(rand.nextInt(Main.getManager().nachnamen.size()));
			sch.klasse = klassen.get(rand.nextInt(klassen.size()));
			sch.gebiet = "Fachinformatiker - Anwendungsentwicklung";
			sch.firma = "Firma " + rand.nextInt(6);
			schueler.add(sch);
		}
	}

}
