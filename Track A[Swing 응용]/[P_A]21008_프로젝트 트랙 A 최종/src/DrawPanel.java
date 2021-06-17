import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
class DrawPanel extends JPanel implements MouseListener, Serializable {
	private ArrayList<MyComponent> compList;
	
//	transient private DrawMode drawMode; //사각형을 그릴지, 타원을 그릴지...
//	transient private int MainPanel.thickMode; //새로 그리는 도형의 선 두께를 1로할지 3으로할지..
//	transient private Color MainPanel.fillColor; //도형안을 무슨 색으로 채울지..
//	transient private Color MainPanel.outColor; //도형의 선을 무슨 색으로 할지..
	
	transient private MouseEvent start; //마우스를 눌렀을때 위치정보를 저장
	
	public DrawPanel() {
		this.addMouseListener(this);
		
		compList = new ArrayList<MyComponent>();
//		MainPanel.drawMode = DrawMode.NONE;
//		MainPanel.thickMode = 1;
//		MainPanel.fillColor = null;
//		MainPanel.outColor = Color.black;
	}
	
	public DrawPanel(ArrayList<MyComponent> list) {
		this.addMouseListener(this);
		
//		compList = new ArrayList<MyComponent>();
		MainPanel.drawMode = DrawMode.NONE;
		MainPanel.thickMode = 1;
		MainPanel.fillColor = null;
		MainPanel.outColor = Color.black;
	}
	
	@Override 
	public void paint(Graphics g) {
		super.paint(g);
		
		for(MyComponent c : compList) {
			c.draw(g);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		start = e; //저장해놓는다
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(MainPanel.drawMode == DrawMode.NONE) return;
		
		int posX = getPos(start.getX(),e.getX());
		int posY = getPos(start.getY(),e.getY());
		int width = getScale(start.getX(), e.getX());
		int height = getScale(start.getY(), e.getY());
		
//		//제자리이거나, width나 height 둘 중 하나가 0인경우 도형을 추가하지 않습니다.
//		if(width == 0 || height == 0) return;
		
		//drawMode 상태에 따라 사각형을 그릴지, 타원을 그릴지, 그룹화를 할 지 정합니다.
		if(MainPanel.drawMode == DrawMode.RECT || MainPanel.drawMode == DrawMode.OVAL) {
			Figure fig = null;
			if(MainPanel.drawMode == DrawMode.RECT) {
				Rectangle r = new Rectangle(posX,posY,width,height);
				this.compList.add(r); //사각형
				fig = r;
			} else if(MainPanel.drawMode == MainPanel.drawMode.OVAL) {
				Oval o = new Oval(posX,posY,width,height);
				this.compList.add(o); //타원
				fig = o;
			}
			
			fig.setOption(MainPanel.thickMode, MainPanel.fillColor, MainPanel.outColor);
			
			extendCanvas(new Point(fig.getX(), fig.getY()));
			extendCanvas(new Point(fig.getEndX(), fig.getEndY()));
		} else if(MainPanel.drawMode == DrawMode.GROUP) { //그룹화
			int startX = posX;
			int endX = posX + width;
			int startY = posY;
			int endY = posY + height;
			
			Group g = new Group(posX, posY, width, height); //우선 드래그 영역을 기준으로 Group을 생성합니다
			
			//compList에서 삭제할 Component들의 리스트입니다.
			//Component들이 한 그룹에 속하게 되면 해당 그룹이 compList에 추가되며,
			//그룹에 속할 Component들은 해당 그룹에 삽입되고, compList에서 삭제됩니다.
			ArrayList<MyComponent> removeList = new ArrayList<MyComponent>(); 
			
			//compList에 있는 Component들에게 자신이 Group영역에 "완전히" 포함되는지 물어봅니다.
			for(MyComponent c : compList) {
				if(c.isContainsOnRect(startX, endX, startY, endY)) { //포함된다면?
					g.addComponent(c); //그룹의 컴포넌트 리스트에 add합니다
					removeList.add(c); //foreach 수행 중 compList에서 삭제를 수행하면 오류가 있어 리스트에 추가합니다.
				}
			}
			
			g.initSize(); //그룹의 크기를 조정하고 그룹에 속해있는 컴포넌트의 위치를 상대위치로 전환합니다
			
			//만약 Group영역에 "완전히" 포함된 도형이 2개 이상일 경우,
			if(removeList.size() >= 2) {
				//해당 Component들을 compList에서 삭제합니다.
				for(MyComponent c : removeList) {
					compList.remove(c);
				}
				this.compList.add(g); //그룹을 compList의 Component로 추가합니다
			}
		} else if(MainPanel.drawMode == DrawMode.CANCLE_GROUP) { //클릭한 지점에 위치한 첫 번째 그룹을 해제합니다. (상위만)
			//compList의 Component 들에게 클릭 좌표가 자신을 가리키는지 물어봅니다
			ArrayList<MyComponent> cancledComp = null;
			
			for(MyComponent c : compList) {
				if(c.getClass() == Group.class) {
					if(c.Contains(e.getX(), e.getY()) == true) {
						cancledComp = ((Group) c).cancledList();
						compList.remove(c);
						break;
					}
				}
			}
			
			if(cancledComp != null) {
				for(MyComponent c : cancledComp) {
					compList.add(c);
				}
			}
		} else if(MainPanel.drawMode == DrawMode.MOVE) { //이동
			MyComponent selected = null;
			
			//compList의 Component 들에게 클릭 좌표가 자신을 가리키는지 물어봅니다
			for(MyComponent c : compList) {
				if(c.Contains(start.getX(), start.getY())) {
					selected = c;
					break; //가장 처음 발견된 (먼저 추가된) 컴포넌트를 선택하고 반복문을 빠져나옵니다 
				}
			}
			
			//선택된 도형이 있다면
			if(selected != null) {
				selected.move(e.getX() - start.getX(), e.getY() - start.getY()); //드래그 한 거리만큼 이동합니다\
				extendCanvas(new Point(selected.getX(), selected.getY()));
				extendCanvas(new Point(selected.getEndX(), selected.getEndY()));
			}
		} else if(MainPanel.drawMode == DrawMode.COPY) { //복사
			MyComponent selected = null;
			
			//compList의 Component 들에게 클릭 좌표가 자신을 가리키는지 물어봅니다
			for(MyComponent c : compList) {
				if(c.Contains(start.getX(), start.getY())) {
					selected = c;
					break; //가장 처음 발견된 (먼저 추가된) 컴포넌트를 선택하고 반복문을 빠져나옵니다 
				}
			}
			
			//선택된 도형이 있다면
			if(selected != null) {
				MyComponent copyComp = selected.copy();
				copyComp.move(e.getX() - start.getX(), e.getY() - start.getY()); //드래그 한 거리만큼 이동합니다
				compList.add(copyComp);
				extendCanvas(new Point(copyComp.getX(), copyComp.getY()));
				extendCanvas(new Point(copyComp.getEndX(), copyComp.getEndY()));
			}
		}
		
		//마우스를 떼면 바로 도형이 그려지도록 합니다.
		this.repaint();
	}
	
	public void extendCanvas(Point p) {
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		Dimension canvasSize = getPreferredSize();
		int width = (int) canvasSize.getWidth();
		int height = (int) canvasSize.getHeight();
		
		if(x >= width) {
			width += x - width + 10; //10의 여유를 주고 확장
			setPreferredSize(new Dimension(width, height));
		} else if(x <= 0) {
			for(MyComponent c : compList) {
				c.move(Math.abs(x) + 10, 0);
			}
			setPreferredSize(new Dimension(width + (Math.abs(x) + 10), height));
		}
		
		if(y >= height) {
			height += y - height + 10; //10의 여유를 주고 확장
			setPreferredSize(new Dimension(width, height));
		} else if(y <= 0) {
			for(MyComponent c : compList) {
				c.move(0, Math.abs(y) + 10);
			}
			setPreferredSize(new Dimension(width, height + (Math.abs(y) + 10)));
		}
		
		revalidate();
		repaint();
		return;
	}
	
	//작은 쪽이 컴포넌트의 x 또는 y가 됩니다.
	private int getPos(int pos1, int pos2) {
		if(pos1 < pos2) return pos1;
		
		return pos2;
	}
	
	//컴포넌트의 width 또는 height를 구하는 함수입니다.
	private int getScale(int pos1, int pos2) {
		if(pos1 < pos2) return pos2 - pos1;
		
		return pos1 - pos2;
	}
	
//	public void setDrawMode(DrawMode d) {
//		MainPanel.drawMode = d;
//	}
	
	
	public ArrayList<MyComponent> getCompList() {
		if(compList != null) return this.compList;
		return null;
	}
	
	@Override public void mouseClicked(MouseEvent arg0) {;} //not use
	@Override public void mouseEntered(MouseEvent arg0) {;} //not use
	@Override public void mouseExited(MouseEvent arg0) {;} //not use

	public void loadCompList(ArrayList<MyComponent> list) {
		this.compList = list;
	}
}
