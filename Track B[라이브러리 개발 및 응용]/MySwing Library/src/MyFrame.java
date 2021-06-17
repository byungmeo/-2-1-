import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;



public class MyFrame extends JFrame {
	private List<MyComponent> api_compList = new ArrayList<MyComponent>();
	private MyLayout layout = null;
	private MyMenuBar menuBar = null;
	private MyToolBar toolBar = null;
	private MyMenu activeMenu = null;
	
	public MyFrame() {	
		this.addMouseListener(new DefaultMouseListener(this));
	}
	
	@Override public void paint(Graphics g) {
		super.paint(g);
		
		if(menuBar != null) menuBar.draw(g);
		if(toolBar != null) toolBar.draw(g);
		
		if(layout != null) {
			layout.draw(api_compList, g);
		} else {
			for(MyComponent c : api_compList)
				c.draw(g);
		}
		
		if(activeMenu != null) {
			activeMenu.drawItems(g);
		}
	}
	
	public void addMenuBar(MyMenuBar mb) {
		this.menuBar = mb;
		menuBar.setFrame(this);
		if(this.toolBar != null) {
			toolBar.fixPosition(mb);
		}
	}
	
	public void addToolBar(MyToolBar tb) {
		this.toolBar = tb;
		if(this.menuBar != null) {
			this.toolBar.fixPosition(menuBar);
		}
	}
	
	public MyComponent add(MyComponent c) {
		this.api_compList.add(c);
		return c;
	}
	
	public MyComponent add(MyButton c) {
		this.api_compList.add(c);
		return c;
	}
	

	public MyLayout getMyLayout() { return this.layout; } //레이아웃 리턴
	public List<MyComponent> getMyComponents() { return this.api_compList; }
	
	public int getTopArea( ) {
		int barCompHeight = 0;
		if(toolBar != null) {
			barCompHeight+=toolBar.getSize().getHeight();
		}
		if(menuBar != null) {
			barCompHeight+=menuBar.getSize().getHeight();
		}
		return this.getInsets().top + barCompHeight;
	}
	
	//레이아웃 설정
	public void setLayout(MyLayout ml) { 
		ml.setContainer(this);
		this.layout = ml;
	} 

	public MyToolBar getToolBar() {
		// TODO Auto-generated method stub
		return this.toolBar;
	}

	public MyMenuBar getMyMenuBar() {
		// TODO Auto-generated method stub
		return this.menuBar;
	}

	public void setActiveMenu(MyMenu menu) {
		// TODO Auto-generated method stub
		this.activeMenu = menu;
	}
	
	public MyMenu getActiveMenu() {
		return this.activeMenu;
	}
}