import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyToolBar extends MyContainer {
	private int OffsetX, OffsetY;
	private int btnWidth, btnHeight;
	ArrayList<MyButton> btnList;
	
	public MyToolBar() {
		OffsetX = 8; OffsetY = 31;
		btnWidth = 75; btnHeight = 25;
		btnList = new ArrayList<MyButton>();
	}
	
	public void addButton(MyButton btn) {
		btn.setBounds((btnList.size() * btnWidth) + OffsetX, OffsetY, btnWidth, btnHeight);
		this.btnList.add(btn);
	}
	
	public void setActionListener(MyActionListener al) {
		for(MyButton btn : btnList) {
			btn.addListener(al);
		}
	}

	public void drawButtons(Graphics g) {
		for(int i = 0; i < btnList.size(); i++) {
			btnList.get(i).draw(g);
		}
	}

	@Override
	public void draw(Graphics g) {
		drawButtons(g);
	}

	@Override
	public MyComponent add(MyComponent c) {
		if(c.getClass() == MyButton.class) {
			addButton((MyButton) c);
		}
		return c;
	}

	@Override
	public void layoutDraw(int x, int y, Dimension size, Graphics g) {
		throw(new UnsupportedOperationException());
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return new Dimension(this.btnWidth, this.btnHeight);
	}

	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		throw(new UnsupportedOperationException());
	}

	public void fixPosition(MyMenuBar mb) {
		this.OffsetY += mb.getSize().getHeight();
		for(MyButton btn : btnList) {
			btn.setLocation(btn.getX(), this.OffsetY);
		}
	}

	@Override
	public void drawAlignString(String str, int x, int y, int width, int height, Graphics g) {
		throw(new UnsupportedOperationException());
	}

	@Override
	public boolean isClicked(MouseEvent start, MouseEvent end) {
		for(MyButton btn : btnList) {
			if(btn.isClicked(start, end) == true) {
				return true;
			}
		}
		
		return false; //버튼쪽은 얼씬도 안했다
	}

	@Override
	public boolean contains(int click_x, int click_y) {
		int x_start = OffsetX;
		int y_start = OffsetY;
		int x_end = btnWidth * this.btnList.size();
		int y_end = btnHeight;
		
		boolean isContainX = (boolean)(x_start <= click_x && click_x <= x_end);
		boolean isContainY = (boolean)(y_start <= click_y && click_y <= y_end);
		
		return (isContainX && isContainY);
	}

	@Override
	public void setLocation(int x, int y) {
		throw(new UnsupportedOperationException());
	}
}
