import java.awt.Graphics;
import java.io.Serializable;

public abstract class MyComponent implements Serializable, Cloneable {
	private int posX, posY, width, height;
	
	public MyComponent(int posX, int posY, int width, int height) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;	
	}
	
	//특정 좌표가 컴포넌트에 포함되는가?
	public boolean Contains(int click_x, int click_y) {
		boolean isContainX = (boolean)(getX() <= click_x && click_x <= getEndX());
		boolean isContainY = (boolean)(getY() <= click_y && click_y <= getEndY());
		
		return (isContainX && isContainY);
	}
	
	//컴포넌트가 특정 사각형 영역에 "완전히" 포함되는가?
	public boolean isContainsOnRect(int rectStartX, int rectEndX, int rectStartY, int rectEndY) {
		boolean isContainX = false;
		boolean isContainY = false;
		
		if(getX() >= rectStartX && getEndX() <= rectEndX) isContainX = true;
		if(getY() >= rectStartY && getEndY() <= rectEndY) isContainY = true;
		
		return (isContainX && isContainY);
	}
	
	//x,y만큼 이동합니다
	public void move(int x, int y) {
		this.posX += x;
		this.posY += y;
	}
	
	//position을 변경합니다
	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
	}
	
	public void setX(int x) {
		this.posX = x;
	}
	
	public void setY(int y) {
		this.posY = y;
	}
	
	//size를 변경합니다
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getEndX() {
		return posX + width;
	}
	
	public int getY() {
		return posY;
	}
	
	public int getEndY() {
		return posY + height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	abstract public void draw(Graphics g);
	abstract public void relativeDraw(int targetX, int targetY, Graphics g); //상대위치로 draw
	
	public MyComponent copy() {
		MyComponent copyComp = null;
		try {
			copyComp = (MyComponent)this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return copyComp;
	}
}
