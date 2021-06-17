import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Group extends MyComponent {
	private ArrayList<MyComponent> compList;
	
	public Group(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		compList = new ArrayList<MyComponent>();
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//그룹이 보이게 하기위해서 offset을 조금 두고 연두 테두리를 그린다
		g.setColor(Color.green);
		g.drawRect(getX()-1, getY()-1, getWidth()+2, getHeight()+2);
		
		g.setColor(Color.black);
		for(MyComponent c : compList) {
			//c.relativeDraw(this,g);
			c.relativeDraw(getX(), getY(), g);
		}
	}
	
	@Override
	public void relativeDraw(int targetX, int targetY, Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.green);
		g.drawRect(targetX + getX()-1, targetY + getY()-1, getWidth()+2, getHeight()+2);
		
		g.setColor(Color.black);
		for(MyComponent c : compList) {
			c.relativeDraw(targetX + getX(), targetY + getY(), g);
		}
	}
	
	@Override
	public void move(int x, int y) {
		super.move(x, y);
		//컴포넌트들은 상대위치를 기준으로 paint되기 때문에 move 필요 X
	}
	
	public void addComponent(MyComponent c) {
		compList.add(c);
	}
	
	public void initSize() {
		int posX = getEndX();
		int posY = getEndY();
		int endX = getX();
		int endY = getY();
		
		for(MyComponent c : compList) {			
			if(c.getX() < posX) posX = c.getX();
			if(c.getEndX() > endX) endX = c.getEndX();
			if(c.getY() < posY) posY = c.getY();
			if(c.getEndY() > endY) endY = c.getEndY();
		}
		
		setPosition(posX, posY);
		setSize(endX - posX, endY - posY);
		
		for(MyComponent c : compList) {			
			c.setPosition(c.getX() - getX(), c.getY() - getY());
		}
	}

	public ArrayList<MyComponent> cancledList() {
		for(MyComponent c : compList) {			
			c.setPosition(c.getX() + getX(), c.getY() + getY());
		}
		
		return compList;
	}
	
	@Override public MyComponent copy() {
		ArrayList<MyComponent> copyList = new ArrayList<>();
		for(MyComponent c : compList) {
			MyComponent copy = c.copy();
			copyList.add(copy);
		}
		Group g = (Group) super.copy();
		g.compList = copyList;
		
		return g;
	}

}
