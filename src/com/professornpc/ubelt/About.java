package com.professornpc.ubelt;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * https://www.youtube.com/watch?v=NZ_fhAIOxfI
 */
public class About extends JFrame {
	
	public About() {
		setLayout(new FlowLayout());
		JButton button = new JButton("OK");
		add(button);
		setVisible(true);
		centreWindow(this);
		this.pack();
		
		if (SystemTray.isSupported()) {
			setDefaultCloseOperation(HIDE_ON_CLOSE);
			
			SystemTray systemTray = SystemTray.getSystemTray();
			
			URL resource = getClass().getResource("belt_16x16.png");
			TrayIcon trayIcon = 
				new TrayIcon(
					Toolkit.getDefaultToolkit().getImage(
							resource));
			
			
			final About me = this;
			

			
			MenuItem menuShow = new MenuItem("Show");
			menuShow.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					me.setVisible(true);
				}
			});
			
			MenuItem menuExit = new MenuItem("Exit");
			menuExit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			
			PopupMenu popupMenu = new PopupMenu();
			popupMenu.add(menuShow);
			popupMenu.add(menuExit);
			
			trayIcon.setPopupMenu(popupMenu);
			
			try {
				systemTray.add(trayIcon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			
		} else {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
		
		
		
		
	}
	
	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) dimension.getWidth() - frame.getWidth();//((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) dimension.getHeight() - frame.getHeight() - 80; //((dimension.getHeight() - frame.getHeight()) / 2);
		System.out.println("x=" + x);
		System.out.println("y=" + y);
		frame.setLocation(x, y);
	}
	
	public static void main(String[] args) {
		new About();
	}
}
