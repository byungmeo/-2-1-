import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MyMenu extends MyContainer {
	private String name;
	private int x, y;
	private int width, height;
	private int compWidth, compHeight;
	private ArrayList<MyComponent> compList;
	private MyActionListener defaultListener;
	private MyMenuBar menuBar;
	private MyMenu parentMenu;
	private MyMenu activeMenu;
	
	public MyMenu(String name) {
		this.name = name;
		compList = new ArrayList<MyComponent>();
		compWidth = 100; compHeight = 25;
		this.setActionListener(new DefaultMenuActionListener());
	}

	public void addMenu(MyMenu m) {
		m.setLocation(x + width, y + (compHeight * this.compList.size()));
		this.compList.add(m);
		m.setSize(compWidth, compHeight);
		m.setMenu(this);
		m.fixItemLocation();
	}

	private void setMenu(MyMenu myMenu) {
		this.parentMenu = myMenu;
	}

	public void addMenuItem(MyMenuItem i) {
		i.setLocation(x + width, y + (compHeight * this.compList.size()));
		this.compList.add(i);
		i.setSize(compWidth, compHeight);
		i.setMenu(this);
	}
	
	public void fixItemLocation() {
		for(int i = 0; i < compList.size(); i++) {
			MyComponent c = compList.get(i);
			
			c.setLocation(x + width, y + (compHeight * i));
			if(c.getClass() == MyMenu.class) {
				MyMenu m = (MyMenu)c;
				m.fixItemLocation();
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		if(parentMenu != null) {
			g.setColor(Color.GRAY);
			g.fillRect(x, y, width, height);
		} else {
			g.drawRect(x, y, width, height);
		}
		
		g.setColor(Color.black);
		drawAlignString(name, x, y, width, height, g);
		if(parentMenu != null && parentMenu.getActiveMenu() != null) {
			if(parentMenu.getActiveMenu() == this) {
				drawItems(g);
			}
		}
	}
	
	public void drawItems(Graphics g) {
		g.setColor(Color.GRAY);
		for(int i = 0; i < compList.size(); i++) {
			MyComponent c = compList.get(i);
			c.draw(g);
		}
		g.setColor(Color.black);
	}

	@Override
	public void layoutDraw(int d, int y, Dimension size, Graphics g) {
		throw(new UnsupportedOperationException());
	}

	@Override
	public Dimension getSize() {
		throw(new UnsupportedOperationException());
	}

	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		throw(new UnsupportedOperationException());
	}

	@Override
	public MyComponent add(MyComponent c) {
		// TODO Auto-generated method stub
		System.out.println("MyMenu add 구현 안해놓았음");
		return null;
	}

	@Override
	public void drawAlignString(String str, int x, int y, int width, int height, Graphics g) {
		// TODO Auto-generated method stub
		FontMetrics fm = g.getFontMetrics(g.getFont());
		int textWidth = fm.stringWidth(str);
		int textX = x + (width - textWidth) / 2;
		int textHeight = fm.getMaxAscent();
		int textY = y + height - (height - textHeight) / 2;
		
		g.drawString(str, textX, textY);
	}

	public void setMenuBar(MyMenuBar myMenuBar) {
		this.menuBar = myMenuBar;
	}

	public void setSize(int compWidth, int compHeight) {
		this.width = compWidth;
		this.height = compHeight;
	}

	//클릭한 위치가 버튼위치에 해당하는지
	public boolean contains(int click_x, int click_y) {
		int x_start = this.x;
		int y_start = this.y;
		int x_end = width + this.x;
		int y_end = height + this.y;
		
		//System.out.println("x_start : " + x_start + " x_end : " + x_end);
		//System.out.println("y_start : " + y_start + " y_end : " + y_end);
		boolean isContainX = (boolean)(x_start <= click_x && click_x <= x_end);
		boolean isContainY = (boolean)(y_start <= click_y && click_y <= y_end);
		
		return (isContainX && isContainY);
	}
	
	public void click(MyEvent e) {
		System.out.println(this.name + " 클릭");
		if(defaultListener != null) defaultListener.actionPerformed(e);
	}
	
	public boolean isClicked(MouseEvent start, MouseEvent end) {
		if(this.contains(start.getX(), start.getY())) {
			this.click(new MyEvent(end,this));		
			return true;
		} else if(this.contains(end.getX(), end.getY())) {
			//Released할 때만 버튼을 클릭해서 아무것도 안함
			return true;
		}
		
		
		
		if(parentMenu != null) {
			for(MyComponent c : compList) {
				if(c.getClass() == MyMenu.class) {
					if(c.isClicked(start, end) == true) {
						return true;
					}
				}
			}
			
			if(parentMenu.getActiveMenu() != null) {
				if(parentMenu.getActiveMenu() == this) {
					for(MyComponent c : compList) {
						if(c.isClicked(start, end) == true) {
							return true;
						}
					}
					
					parentMenu.setActiveMenu(null);
					return false;
				}
			}
		}
		
		if(menuBar != null) {
			if(menuBar.getActiveMenu() != null) {
				if(menuBar.getActiveMenu() == this) {
					for(MyComponent c : compList) {
						if(c.isClicked(start, end) == true) {
							return true;
						}
					}
					
					menuBar.setActiveMenu(null);
					return false;
				}
			}
		}
		
		return false; //버튼쪽은 얼씬도 안했다
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private void setActionListener(DefaultMenuActionListener dmal) {
		this.defaultListener = dmal;
	}
	
	class DefaultMenuActionListener implements MyActionListener {
		@Override
		public void actionPerformed(MyEvent e) {
			// TODO Auto-generated method stub
			if(menuBar != null) {
				if(menuBar.getActiveMenu() != null) {
					if(menuBar.getActiveMenu() == e.getMenuSource()) {
						menuBar.setActiveMenu(null);
						return;
					}
				}
				menuBar.setActiveMenu(e.getMenuSource());
			} else {
				if(parentMenu.getActiveMenu() != null) {
					if(parentMenu.getActiveMenu() == e.getMenuSource()) {
						parentMenu.setActiveMenu(null);
						return;
					}
				}
				parentMenu.setActiveMenu(e.getMenuSource());
			}
		}
	}

	public void closeMenu() {
		if(menuBar != null) {
			menuBar.setActiveMenu(null);
		} else {
			parentMenu.setActiveMenu(null);
			parentMenu.closeMenu();
		}
	}
	
	public void setActiveMenu(MyMenu menu) {
		// TODO Auto-generated method stub
		this.activeMenu = menu;
	}

	public MyMenu getActiveMenu() {
		// TODO Auto-generated method stub
		if(parentMenu != null) {
			if(parentMenu.getActiveMenu() != null) {
				if(parentMenu.getActiveMenu() == this) {
					return parentMenu.getActiveMenu();
				}
			}
		}
		return this.activeMenu;
	}

	public MyMenuBar getMenuBar() {
		if(this.menuBar == null && this.parentMenu != null) {
			return parentMenu.getMenuBar();
		}
		return this.menuBar;
	}
}
