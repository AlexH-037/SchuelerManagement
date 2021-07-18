package de.alex.management;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.alex.management.ui.UpdateEnum;
import de.alex.management.ui.UpdateUtil;
import de.alex.management.ui.Window;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

public class Main {
	
	private static Manager manager;
	private static Window window;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new MaterialLookAndFeel());
			MaterialLookAndFeel.changeTheme(new JMarsDarkTheme());
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		manager = new Manager();
		manager.load();
		
		window = new Window();
		window.frame.setVisible(true);
		UpdateUtil.updateScroll(UpdateEnum.Sch1);
	}
	
	public static Manager getManager() {
		return manager;
	}
	
	public static Window getWindow() {
		return window;
	}

}
