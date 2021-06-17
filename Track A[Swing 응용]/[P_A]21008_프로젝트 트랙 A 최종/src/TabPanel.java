import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class TabPanel extends JTabbedPane {
	private ArrayList<Component> dpList;
	
	public TabPanel() {
		super();
		dpList = new ArrayList<Component>();
		
	}
	
	@Override
	public void addTab(String title, Component newDp) {
		
		if(newDp.getClass() == DrawPanel.class) {
			dpList.add((DrawPanel)newDp);
		}
		
		JScrollPane scroll;
		
		scroll = new JScrollPane(newDp);
		scroll.setPreferredSize(new Dimension(1400,925));
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getHorizontalScrollBar().setUnitIncrement(16);
		
//		add(scroll);
		
		
		super.addTab(title, scroll);
	}

	public Component getDrawPanel(int i) {
		// TODO Auto-generated method stub
		return dpList.get(i);
	}
	
	@Override
	public void removeTabAt(int i) {
		super.removeTabAt(i);
	}
	
	public ArrayList<Component> getPanelList() {
		return this.dpList;
	}
}
