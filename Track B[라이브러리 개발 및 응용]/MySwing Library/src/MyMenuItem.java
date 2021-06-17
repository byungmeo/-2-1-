import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class MyMenuItem extends MyButton {
	private MyMenu menu;
	
	public MyMenuItem(String name) {
		super(name);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		drawAlignString(name, x, y, width, height, g);
	}

//	@Override
//	public void drawAlignString(String str, int x, int y, int width, int height, Graphics g) {
//		FontMetrics fm = g.getFontMetrics(g.getFont());
//		int textWidth = fm.stringWidth(str);
//		int textX = x + (width - textWidth) / 2;
//		int textHeight = fm.getMaxAscent();
//		int textY = y + height - (height - textHeight) / 2;
//		
//		g.drawString(str, textX, textY);
//	}

//	public void setSize(int compWidth, int compHeight) {
//		// TODO Auto-generated method stub
//		this.width = compWidth;
//		this.height = compHeight;
//	}

	public void setMenu(MyMenu myMenu) {
		// TODO Auto-generated method stub
		this.menu = myMenu;
	}
//	@Override
//	public boolean isClicked(MouseEvent start, MouseEvent end) {
//		//모든 버튼들에게 클릭했는지 물어보고, 클릭했다면 이벤트처리하고 true 반환
//		if(this.contains(end.getX(), end.getY()) && this.contains(start.getX(), start.getY())) {
//			//Pressed와 Released가 동일한 버튼 위치에서 이루어졌다면 click 처리
//			this.click(new MyEvent(end,this));					
//			return true;
//		} else if(this.contains(start.getX(), start.getY())) {
//			//Pressed할 때만 버튼을 클릭해서 아무것도 안함
//			return true;
//		} else if(this.contains(end.getX(), end.getY())) {
//			//Released할 때만 버튼을 클릭해서 아무것도 안함
//			return true;
//		}
//		
//		return false; //버튼쪽은 얼씬도 안했다
//	}
	
	@Override
	public void click(MyEvent e) {
		super.click(e);
		System.out.println(this.name + " 클릭");
//		for(MyActionListener l : listenerList) {
//			l.actionPerformed(e);
//		}
		menu.closeMenu();
	}
	
	
//	@Override
//	public boolean contains(int click_x, int click_y) {
//		int x_start = this.x;
//		int y_start = this.y;
//		int x_end = width + this.x;
//		int y_end = height + this.y;
//		
//		boolean isContainX = (boolean)(x_start <= click_x && click_x <= x_end);
//		boolean isContainY = (boolean)(y_start <= click_y && click_y <= y_end);
//		
//		return (isContainX && isContainY);
//	}
	
//	@Override
//	public void setLocation(int x, int y) {
//		// TODO Auto-generated method stub
//		this.x = x;
//		this.y = y;
//	}

	
	@Override public void setPreferredSize(Dimension size) {throw(new UnsupportedOperationException());}
	@Override public void layoutDraw(int d, int y, Dimension size, Graphics g) { throw(new UnsupportedOperationException()); }
	@Override public Dimension getSize() { throw(new UnsupportedOperationException()); }
	@Override public Dimension getPreferredSize() { throw(new UnsupportedOperationException()); }
}
