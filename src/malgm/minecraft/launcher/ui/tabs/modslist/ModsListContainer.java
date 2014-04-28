package malgm.minecraft.launcher.ui.tabs.modslist;

import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.*;

import malgm.minecraft.launcher.reader.ModslistReader;

public class ModsListContainer extends JPanel implements TableModelListener {

	private static final long serialVersionUID = 1L;
	
	private JTable jt;
	
	private String[] columns = {"Mod name", "Version", "Installed"};
	private String[][] data = {
			{"Example mod", "0.0.1", "Update"}
	};
	
	private ModslistReader modsListReader = new ModslistReader("https://dl.dropbox.com/s/dj5nv5zkqlw1tes/modslist.xml");
	
	public ModsListContainer() {
		init();
	}
	
	public void init() {
		inputData(modsListReader.getInfoURLs());
		
		jt = new JTable(data, columns);
		jt.setPreferredScrollableViewportSize(new Dimension(880, 420));
		jt.getModel().addTableModelListener(this);
		jt.getTableHeader().setReorderingAllowed(false);
		JScrollPane jsp = new JScrollPane(jt);
		
		//TableColumn modname = jt.getColumnModel().getColumn(1);
		//TableColumn installed = jt.getColumnModel().getColumn(2);
		//TableColumn install = jt.getColumnModel().getColumn(2);
		
		add(jsp);
	}

	private void inputData(String[] infoURLs) {
		data[0] = infoURLs;
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		
	}

}