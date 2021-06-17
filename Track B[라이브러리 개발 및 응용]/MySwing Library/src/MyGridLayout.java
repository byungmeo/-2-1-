import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

public class MyGridLayout implements MyLayout {
	private int rows;
	private int cols;
	private int fixedCols;
	private MyContainer container; //Container에 적용하는걸 염두에 두었지만, 프로젝트에선 쓸 일이 없는걸로 함!
	private MyFrame frame; //MyFrame까지는 JFrame을 상속받는걸로 하였기때문에..
	
	public MyGridLayout(int rows, int cols) {
		if(rows <= 0) throw(new IllegalArgumentException("rows and cols cannot both be zero"));
		this.rows = rows;
		this.cols = cols;
		this.fixedCols = cols;
	}
	
	@Override
	public void setContainer(MyContainer c) {
		// TODO Auto-generated method stub
		if(frame != null) {
			frame.setLayout((MyLayout)null);
			this.frame = null;
		}
		this.container = c;
	}

	@Override
	public void setContainer(MyFrame f) {
		// TODO Auto-generated method stub
		if(container != null) {
			container.setLayout((MyLayout)null);
			this.container = null;
		}
		this.frame = f;
	}
	
	@Override
	public Dimension getContainerSize() {
		if(container != null) return container.getSize();
		else if(frame != null) {
			//프레임의 타이틀바 높이를 고려합니다.
			return new Dimension(frame.getWidth(), frame.getHeight() - frame.getTopArea());
		}
		return null;
	}

	@Override
	public void draw(List<MyComponent> compList, Graphics g) {
		Dimension containerSize = getContainerSize();
		int frameTitle = 0;
		if(frame != null) frameTitle = frame.getTopArea();
		
		
		if(containerSize == null) return;
		
		//설정치보다 컴포넌트가 많을 경우 cols를 조정합니다.
		while(fixedCols * rows < compList.size()) {
			fixedCols += (compList.size() - (cols*rows)) / (fixedCols*rows) + 1;
		}
		
		int x = (int)containerSize.getWidth() / fixedCols;
		int y = (int)containerSize.getHeight() / rows;
		
		//컴포넌트를 레이아웃에 맞춰 그립니다.
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < fixedCols; j++) {
				if((i * fixedCols) + j >= compList.size()) return;
				compList.get((i * fixedCols) + j).layoutDraw(j * x, i * y + frameTitle, new Dimension(x,y), g);
			}
		}
	}

}
