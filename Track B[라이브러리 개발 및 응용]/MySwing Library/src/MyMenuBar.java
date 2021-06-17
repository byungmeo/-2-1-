import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyMenuBar extends MyContainer {
	private int width, height;
	private int OffsetX, OffsetY;
	private int compWidth, compHeight;
	private MyFrame mf;
	ArrayList<MyMenu> menuList;
	
	public MyMenuBar() {
		height = 20;
		OffsetX = 8; OffsetY = 31;
		compWidth = 60; compHeight = 20;
		menuList = new ArrayList<MyMenu>();
	}
	
	public void addMenu(MyMenu m) {
		m.setLocation(compWidth * menuList.size() + OffsetX, OffsetY);
		this.menuList.add(m);
		m.setSize(compWidth, compHeight);
		m.setMenuBar(this);
		m.fixItemLocation();
	}
	
	@Override
	public void draw(Graphics g) {
		if(mf == null) return;
		width = mf.getWidth();
		g.drawRect(OffsetX, OffsetY, width, height);
		
		for(int i = 0; i < menuList.size(); i++) {
			MyComponent c = menuList.get(i);
			c.draw(g);
		}
	}

	@Override
	public void layoutDraw(int d, int y, Dimension size, Graphics g) {
		throw(new UnsupportedOperationException());
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return new Dimension(this.width, this.height);
	}

	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		throw(new UnsupportedOperationException());
	}

	@Override
	public MyComponent add(MyComponent c) {
		// TODO Auto-generated method stub
		System.out.println("MyMenuBar add 구현 안해놓았음");
		return null;
	}

	public void setFrame(MyFrame myFrame) {
		this.mf = myFrame;
		this.width = mf.getWidth();
	}


	@Override
	public void drawAlignString(String str, int x, int y, int width, int height, Graphics g) {
		throw(new UnsupportedOperationException());
	}

	public boolean isClicked(MouseEvent start, MouseEvent end) {
		for(MyMenu menu : menuList) {
			if(menu.isClicked(start, end) == true) {
				return true;
			}
		}
		return false; //메뉴쪽은 얼씬도 안했다
	}
	
	public void setActiveMenu(MyMenu menu) {
		mf.setActiveMenu(menu);
	}
	
	public MyMenu getActiveMenu() {
		return mf.getActiveMenu();
	}

	@Override
	public boolean contains(int click_x, int click_y) {
		throw(new UnsupportedOperationException());
	}

	@Override
	public void setLocation(int x, int y) {
		throw(new UnsupportedOperationException());
	}
 
}
