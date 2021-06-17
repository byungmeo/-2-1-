import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

enum DrawMode{NONE,RECTANGLE,OVAL,LINE}

class AppFrame extends MyFrame {
	private ArrayList<Figure> figList;
	private DrawMode drawMode;
	private MyToolBar appToolBar;
	
	//버튼 선언
	private MyButton btnRectangle;
	private MyButton btnOval;
	private MyButton btnLine;
	
	private MyMenuBar appMenuBar;
	
	private MyMenu appMenu1;
	private MyMenu appMenu1_1;
	private MyMenuItem item1_1;
	private MyMenuItem item1_2;
	private MyMenuItem item_Rect;
	private MyMenuItem item_Oval;
	private MyMenuItem item_Line;
	
	private MyMenu appMenu2;
	private MyMenuItem itemTest1;
	private MyMenuItem itemTest2;
	
	public AppFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(1200,300);
		this.setSize(1000,500);
		this.setVisible(true);
		
		figList = new ArrayList<Figure>();
		drawMode = DrawMode.NONE;
		
		InitComponent();
	}

	private void InitComponent() {
		appMenuBar = new MyMenuBar();
		addMenuBar(appMenuBar);
		
		appMenu1 = new MyMenu("메뉴1");
		item1_1 = new MyMenuItem("아이템1_1");
		appMenu1_1 = new MyMenu("메뉴1_1");
		item_Rect = new MyMenuItem("Rect");
		item_Oval = new MyMenuItem("Oval");
		item_Line = new MyMenuItem("Line");
		item1_2 = new MyMenuItem("아이템1_2");
		appMenu1.addMenuItem(item1_1);
		appMenu1.addMenu(appMenu1_1);
		appMenu1_1.addMenuItem(item_Rect);
		appMenu1_1.addMenuItem(item_Oval);
		appMenu1_1.addMenuItem(item_Line);
		appMenu1.addMenuItem(item1_2);
		item_Rect.addListener(new FigureActionListener());
		item_Oval.addListener(new FigureActionListener());
		item_Line.addListener(new FigureActionListener());
		appMenuBar.addMenu(appMenu1);	
		
		
		appMenu2 = new MyMenu("메뉴2");
		itemTest1 = new MyMenuItem("테스트1");
		itemTest2 = new MyMenuItem("테스트2");
		appMenu2.addMenuItem(itemTest1);
		appMenu2.addMenuItem(itemTest2);
		appMenuBar.addMenu(appMenu2);
		
		appToolBar = new MyToolBar();
		btnRectangle = new MyButton("Rect");
		btnOval = new MyButton("Oval");
		btnLine = new MyButton("Line");
		appToolBar.add(btnRectangle);
		appToolBar.add(btnOval);
		appToolBar.add(btnLine);
		appToolBar.setActionListener(new FigureActionListener());
		addToolBar(appToolBar);
		
		this.addMouseListener(new MyMouseListener());
		repaint();
	}
	
	@Override public void paint(Graphics g) {
		super.paint(g);
		
		if(figList != null) {
			for(int i = 0; i < figList.size(); i++)
				figList.get(i).draw(g);
		}
	}
	
	public void addFigure(Figure fig) { this.figList.add(fig); } //도형 추가
	public void setDrawMode(DrawMode d) { this.drawMode = d; } //그리기모드 설정
	
	//사각형, 타원, 선분 버튼 액션리스너
	class FigureActionListener implements MyActionListener {
		//버튼을 또다시 클릭하면 그리기 모드가 해제됨 (Toggle)
		@Override
		public void actionPerformed(MyEvent e) {
			if(e.getSource() == btnRectangle || e.getSource() == item_Rect) {
				if(drawMode == DrawMode.RECTANGLE) setDrawMode(DrawMode.NONE);
				else setDrawMode(DrawMode.RECTANGLE);
			} else if(e.getSource() == btnOval || e.getSource() == item_Oval) {
				if(drawMode == DrawMode.OVAL) setDrawMode(DrawMode.NONE);
				else setDrawMode(DrawMode.OVAL);
			} else if(e.getSource() == btnLine || e.getSource() == item_Line) {
				if(drawMode == DrawMode.LINE) setDrawMode(DrawMode.NONE);
				else setDrawMode(DrawMode.LINE);
			}
		}
	}
	
//	Inner Class 방식
	class MyMouseListener implements MouseListener {
		MouseEvent start;
		
		@Override
		public void mousePressed(MouseEvent e) {
			start = e;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(start.getY() < getTopArea() || e.getY() < getTopArea()) {
//				System.out.println("프로그램 바 침범");
				return;
			}
			
			int posX = getPos(start.getX(),e.getX());
			int posY = getPos(start.getY(),e.getY());
			int width = getScale(start.getX(), e.getX());
			int height = getScale(start.getY(), e.getY());
			
			//드래그를 안했으면 그리지 않습니다
			if(width == 0 && height == 0) {
//				System.out.println("제자리걸음으로 출력안함");
				return;
			}
			
			if(drawMode == DrawMode.RECTANGLE) {
				addFigure(new Rectangle(posX,posY,width,height));
			} else if(drawMode == DrawMode.OVAL) {
				addFigure(new Oval(posX,posY,width,height));
			} else if(drawMode == drawMode.LINE) {
				addFigure(new Line(start.getX(),start.getY(),e.getX(),e.getY()));
			} else {
//				System.out.println("그리기 모드가 아닙니다.");
				return;
			}

			//도형이 바로 보이도록 repaint
			repaint();
		}

		//도형의 posX 또는 posY를 구하는 함수입니다.
		public int getPos(int pos1, int pos2) {
			if(pos1 < pos2) return pos1;
			
			return pos2;
		}
		
		//도형의 width 또는 height를 구하는 함수입니다.
		public int getScale(int pos1, int pos2) {
			if(pos1 < pos2) return pos2 - pos1;
			
			return pos1 - pos2;
		}
		
		@Override public void mouseClicked(MouseEvent e) {;} //not use
		@Override public void mouseEntered(MouseEvent e) {;} //not use
		@Override public void mouseExited(MouseEvent e) {;} //not use
	}//MyMouseListener
}
public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppFrame mf = new AppFrame();
	}
}
