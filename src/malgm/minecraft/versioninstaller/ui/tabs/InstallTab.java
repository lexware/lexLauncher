package malgm.minecraft.versioninstaller.ui.tabs;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.*;

import malgm.minecraft.versioninstaller.reader.MVIDocumentReader;
import malgm.minecraft.versioninstaller.util.*;

public class InstallTab implements ActionListener{
	
	private boolean installing = false, successful = true;
	
	private MVIDocumentReader mviDocReader = new MVIDocumentReader();
	private Installer installer = new Installer();
	
	private JPanel panel = new JPanel();
	private JPanel textfield = new JPanel();
	private JPanel preview = new JPanel();
	
	private JTextField field;
	
	private JButton install;
	private JTextArea info;
	private JScrollPane scrollPane;
	
	public void render(JFrame frame) throws IOException {
		
		// set background color for both jpanels
		textfield.setBackground(Color.CYAN);
		preview.setBackground(Color.CYAN);
		
		// MVI logo
		BaseTab basetab = new BaseTab();
		basetab.render(panel);
		
		// Text field for url
		field = new JTextField(20);
		field.setToolTipText("Enter the URL for your modpack/version");
		textfield.add(field);
		
		//install button
		install = new JButton("Install");
		install.setMnemonic(KeyEvent.VK_ENTER);
		install.addActionListener(this);
		textfield.add(install);
		
		// text area
		info = new JTextArea();
		info.setFont(new Font("Sans Serif", 2, 12));
		info.append("Welcome to the Minecraft version Installer!\n");
		info.append("Enter the URL for your modpack/version and\nclick Install\n");
		info.setEditable(false);
		
		//scrollpane for text area
		scrollPane = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		preview.add(scrollPane);
		
		frame.add(panel);
		frame.add(textfield);
		frame.add(preview);
		
	}
	
	public void setVisible(boolean visible) {
		panel.setVisible(visible);
		textfield.setVisible(visible);
		preview.setVisible(visible);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.install) {
			install(field.getText());
		}
	}
	
	public void install(String modurl) {
		if(!installing) {
			installing = true;
			mviDocReader.readDoc(modurl);
			info.append("Installing " + mviDocReader.getName() + " " + mviDocReader.getVersion() + "\n");
			Utils utils = new Utils();
			utils.createDirectory(installer.getDirectory());
			utils.createDirectory(installer.getDirectory() + mviDocReader.getFileName());
			try {
				String directory = installer.getDirectory() + "/" + mviDocReader.getFileName();
				installer.downloadFile(mviDocReader.getJar(), directory, mviDocReader.getFileName()+".jar");
				installer.downloadFile(mviDocReader.getJson(), directory, mviDocReader.getFileName()+".json");
				successful = true;
			} catch (IOException e) {
				info.append("Failed to install " + mviDocReader.getName() + " " + mviDocReader.getVersion() +"\n");
				successful = false;
			}
			if(successful) {
				info.append("Installed " + mviDocReader.getName() + " " + mviDocReader.getVersion() + "\n");
			}
			installing = false;
			successful = false;
		}
	}

}
