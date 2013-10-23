package net.sf.memoranda.ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import net.sf.memoranda.EventNotificationListener;
import net.sf.memoranda.EventsScheduler;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.Local;
import snoozesoft.systray4j.SysTrayMenu;
import snoozesoft.systray4j.SysTrayMenuEvent;
import snoozesoft.systray4j.SysTrayMenuIcon;
import snoozesoft.systray4j.SysTrayMenuItem;
import snoozesoft.systray4j.SysTrayMenuListener;
/**
 * 
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */

/*$Id: App.java,v 1.28 2007/03/20 06:21:46 alexeya Exp $*/
public class App implements SysTrayMenuListener {
	// boolean packFrame = false;

	static AppFrame frame = null;
	static SysTrayMenu menu = null;

	static SysTrayMenuIcon icon = null;
	static SysTrayMenuIcon iconEv = null;
	static SysTrayMenuItem itemExit = null;
	static SysTrayMenuItem itemMinimize = null;
	static SysTrayMenuItem itemRestore = null;

	public static boolean sysTrayDisabled = true;
	
	public static final String GUIDE_URL = "http://memoranda.sourceforge.net/guide.html";
	public static final String BUGS_TRACKER_URL = "http://sourceforge.net/tracker/?group_id=90997&atid=595566";
	public static final String WEBSITE_URL = "http://memoranda.sourceforge.net";

	private JFrame splash = null;

	static {
		if (!Configuration.get("DISABLE_SYSTRAY").equals("yes")) {
			icon =
				new SysTrayMenuIcon(
					AppFrame.class.getResource(
						"resources/icons/jnotes"
							+ SysTrayMenuIcon.getExtension()));
			iconEv =
				new SysTrayMenuIcon(
					AppFrame.class.getResource(
						"resources/icons/jnotes_ev"
							+ SysTrayMenuIcon.getExtension()));
			itemExit = new SysTrayMenuItem(Local.getString("Exit"), "exit");
			itemMinimize =
				new SysTrayMenuItem(Local.getString("Minimize"), "minimize");
			itemRestore =
				new SysTrayMenuItem(Local.getString("Restore"), "restore");
			menu = new SysTrayMenu(icon, "Memoranda");
			sysTrayDisabled = false;
			if (!SysTrayMenu.isAvailable()) {
				System.out.println("[DEBUG] SysTray is now disabled.");
				Configuration.put("DISABLE_SYSTRAY", "yes");
				Configuration.put("ASK_ON_EXIT", "yes");
				Configuration.put("ON_CLOSE", "exit");
				Configuration.saveConfig();
				sysTrayDisabled = true;
			}
		} else
			/* DEBUG */
			System.out.println("System tray icon is disabled");
	}

	/*========================================================================*/ 
	/* Note: Please DO NOT edit the version/build info manually!
       The actual values are substituted by the Ant build script using 
       'version' property and datestamp.*/

	public static final String VERSION_INFO = "@VERSION@";
	public static final String BUILD_INFO = "@BUILD@";
	
	/*========================================================================*/

	public static AppFrame getFrame() {
		return frame;
	}

	public void show() {
		if (frame.isVisible()) {
			frame.toFront();
			frame.requestFocus();
		} else
			init();
	}

	public App(boolean fullmode) {
		super();
		if (fullmode)
			fullmode = !Configuration.get("START_MINIMIZED").equals("yes");
		/* DEBUG */
		if (!fullmode)
			System.out.println("Minimized mode");
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			showSplash();
		System.out.println(VERSION_INFO);
		try {
			if (Configuration.get("LOOK_AND_FEEL").equals("system"))
				UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
			else if (Configuration.get("LOOK_AND_FEEL").equals("kunststoff"))
				UIManager.setLookAndFeel(
					"com.incors.plaf.kunststoff.KunststoffLookAndFeel");
			else if (Configuration.get("LOOK_AND_FEEL").equals("default"))
				UIManager.setLookAndFeel(
					UIManager.getCrossPlatformLookAndFeelClassName());					
			else if (
				Configuration.get("LOOK_AND_FEEL").toString().length() > 0)
				UIManager.setLookAndFeel(
					Configuration.get("LOOK_AND_FEEL").toString());

		} catch (Exception e) {		    
			new ExceptionDialog(e, "Error when initializing a pluggable look-and-feel. Default LF will be used.", "Make sure that specified look-and-feel library classes are on the CLASSPATH.");
		}
		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("")) {
			String fdow;
			if (Calendar.getInstance().getFirstDayOfWeek() == 2)
				fdow = "mon";
			else
				fdow = "sun";
			Configuration.put("FIRST_DAY_OF_WEEK", fdow);
			Configuration.saveConfig();
			/* DEBUG */
			System.out.println("[DEBUG] first day of week is set to " + fdow);
		}
		if (!sysTrayDisabled) {
			icon.addSysTrayMenuListener(this);
			iconEv.addSysTrayMenuListener(this);
			itemExit.addSysTrayMenuListener(this);
			itemMinimize.addSysTrayMenuListener(this);
			itemRestore.addSysTrayMenuListener(this);
			menu.addItem(itemExit, 0);
			menu.addSeparator(1);
			menu.addItem(itemRestore, 2);
			EventsScheduler.addListener(new EventNotificationListener() {
				public void eventIsOccured(net.sf.memoranda.Event ev) {
				}
				public void eventsChanged() {
					if (EventsScheduler.isEventScheduled()) {
						net.sf.memoranda.Event ev =
							EventsScheduler.getFirstScheduledEvent();
						menu.setToolTip(
							ev.getTimeString() + " - " + ev.getText());
						menu.setIcon(iconEv);
					} else {
						menu.setToolTip("Memoranda");
						menu.setIcon(icon);
					}
				}
			});
		}
		EventsScheduler.init();
		frame = new AppFrame();
		if (fullmode) {
			init();
		}
		if (!Configuration.get("SHOW_SPLASH").equals("no"))
			splash.dispose();
	}

	void init() {
		if (!sysTrayDisabled) {
			menu.removeAll();
			menu.addItem(itemExit, 0);
			menu.addSeparator(1);
			menu.addItem(itemMinimize, 2);
		}
		/*
		 * if (packFrame) { frame.pack(); } else { frame.validate(); }
		 * 
		 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 * 
		 * Dimension frameSize = frame.getSize(); if (frameSize.height >
		 * screenSize.height) { frameSize.height = screenSize.height; } if
		 * (frameSize.width > screenSize.width) { frameSize.width =
		 * screenSize.width; }
		 * 
		 * 
		 * Make the window fullscreen - On Request of users This seems not to
		 * work on sun's version 1.4.1_01 Works great with 1.4.2 !!! So update
		 * your J2RE or J2SDK.
		 */
		/* Used to maximize the screen if the JVM Version if 1.4 or higher */
		/* --------------------------------------------------------------- */
		double JVMVer =
			Double
				.valueOf(System.getProperty("java.version").substring(0, 3))
				.doubleValue();

		frame.pack();
		if (JVMVer >= 1.4) {
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		} else {
			frame.setExtendedState(Frame.NORMAL);
		}
		/* --------------------------------------------------------------- */
		/* Added By Jeremy Whitlock (jcscoobyrs) 07-Nov-2003 at 15:54:24 */

		// Not needed ???
		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();

	}

	public static void closeWindow() {
		if (frame == null)
			return;
		frame.dispose();
		//long m1 = Runtime.getRuntime().freeMemory();
		//frame = null;
		if (!sysTrayDisabled) {
			menu.removeAll();
			menu.addItem(itemExit, 0);
			menu.addSeparator(1);
			menu.addItem(itemRestore, 2);
		} else
			System.out.println(
				"Memoranda is active in a 'daemon mode'. To restore the window, run the application again.");
		//CurrentProject.free();
		System.gc(); // ??? Are we really need it?
		//long m2 = Runtime.getRuntime().freeMemory();
		/* DEBUG */ //System.out.println("Window closed. "+(m2-m1)/1024);
	}

	/**
	 * @see snoozesoft.systray4j.SysTrayMenuListener#menuItemSelected(snoozesoft.systray4j.SysTrayMenuEvent)
	 */
	public void menuItemSelected(SysTrayMenuEvent e) {
		if (e.getActionCommand().equals("restore"))
			show();
		else if (e.getActionCommand().equals("minimize"))
			closeWindow();
		else if (e.getActionCommand().equals("exit")) {
			if (frame != null)
				frame.doExit();
			else
				System.exit(0);
		}
	}

	/**
	 * @see snoozesoft.systray4j.SysTrayMenuListener#iconLeftClicked(snoozesoft.systray4j.SysTrayMenuEvent)
	 */
	public void iconLeftClicked(SysTrayMenuEvent arg0) {
		show();
	}

	/**
	 * @see snoozesoft.systray4j.SysTrayMenuListener#iconLeftDoubleClicked(snoozesoft.systray4j.SysTrayMenuEvent)
	 */
	public void iconLeftDoubleClicked(SysTrayMenuEvent arg0) {
		show();
	}

	/**
	 * Method showSplash.
	 */
	private void showSplash() {
		splash = new JFrame();
		ImageIcon spl =
			new ImageIcon(App.class.getResource("resources/splash.png"));
		JLabel l = new JLabel();
		l.setSize(400, 300);
		l.setIcon(spl);
		splash.getContentPane().add(l);
		splash.setSize(400, 300);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		splash.setLocation(
			(screenSize.width - 400) / 2,
			(screenSize.height - 300) / 2);
		splash.setUndecorated(true);
		splash.setVisible(true);
	}
}