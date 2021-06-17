import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class MyButton implements MyComponent {
	protected String name;
	protected int x, y, width, height;
	private Dimension prefSize;
	protected List<MyActionListener> listenerList;
	private Font font;
	
	public MyButton(String name) {
		this.name = name;
		
		Canvas c = new Canvas(); //Graphics를 얻기 위한 임시 개체
		font = new Font("Dialog",Font.PLAIN,12); //default font
		FontMetrics fm = c.getFontMetrics(font);
		
		prefSize = new Dimension(34 + fm.stringWidth(name), 10 + fm.getHeight()); //FlowLayout적용 시 기본 적용되는 Size
		listenerList = new ArrayList<MyActionListener>();
	}
	
	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//사각형과 버튼이름 그리기
	@Override
	public void draw(Graphics g) {
		g.drawRect(x, y, width, height);
		drawAlignString(name, x, y, width, height, g);
	}
	
	@Override
	public void layoutDraw(int x, int y, Dimension size, Graphics g) {
		int fixedWidth = (int)size.getWidth();
		int fixedHeight = (int)size.getHeight();
		g.drawRect(x, y, (int)size.getWidth(), (int)size.getHeight());
		drawAlignString(name, x, y, fixedWidth, fixedHeight, g);
	}
	
	//가운데 정렬 스트링
	@Override
	public void drawAlignString(String str, int x, int y, int width, int height, Graphics g) {
		FontMetrics fm = g.getFontMetrics(g.getFont());
		int textWidth = fm.stringWidth(str);
		int textX = x + (width - textWidth) / 2;
		int textHeight = fm.getMaxAscent();
		int textY = y + height - (height - textHeight) / 2;
		
		g.drawString(str, textX, textY);
	}
	
	public void addListener(MyActionListener al) {
		this.listenerList.add(al);
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
	//버튼을 클릭했다면 버튼실행처리를 하고 true를 반환
	@Override
	public boolean isClicked(MouseEvent start, MouseEvent end) {
		//모든 버튼들에게 클릭했는지 물어보고, 클릭했다면 이벤트처리하고 true 반환
		if(this.contains(end.getX(), end.getY()) && this.contains(start.getX(), start.getY())) {
			//Pressed와 Released가 동일한 버튼 위치에서 이루어졌다면 click 처리
			this.click(new MyEvent(end,this));					
			return true;
		} else if(this.contains(start.getX(), start.getY())) {
			//Pressed할 때만 버튼을 클릭해서 아무것도 안함
			return true;
		} else if(this.contains(end.getX(), end.getY())) {
			//Released할 때만 버튼을 클릭해서 아무것도 안함
			return true;
		}
		
		return false; //버튼쪽은 얼씬도 안했다
	}
		
	public void click(MyEvent e) {
		for(MyActionListener l : listenerList) {
			l.actionPerformed(e);
		}
	}

	public String getName() {
		return name;
	}
	
	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		return this.prefSize;
	}
	
	@Override
	public void setPreferredSize(Dimension size) {
		this.prefSize = size;
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		throw(new UnsupportedOperationException());
	}

	public void setSize(int width, int height) {
		if(width >= 0 && height >= 0) {
			this.width = width;
			this.height = height;
		}
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public MouseListener getDefaultMouseListener(MyFrame mf) {
		// TODO Auto-generated method stub
		return new DefaultMouseListener(mf);
	}

	public int getX() {
		// TODO Auto-generated method stub
		return this.x;
	}
}
