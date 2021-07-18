package de.alex.management.types;

import java.util.ArrayList;

public class Lehrer {
	
	public ArrayList<String> fachKlasseCombo = new ArrayList<String>();
	public String name;
	
	public ArrayList<String> getFachKlasseCombo() {
		return fachKlasseCombo;
	}
	
	public String getName() {
		return name;
	}
}
