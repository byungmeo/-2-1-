import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

public class MyFlowLayout implements MyLayout {
	public static final int LEFT = 0;
	public static final int CENTER = 1;
	public static final int RIGHT = 2;
	
	private int align;
	private int hGap;
	private int vGap;
	
	private MyContainer container; //Container에 적용하는걸 염두에 두었지만, 프로젝트에선 쓸 일이 없는걸로 함!
	private MyFrame frame; //MyFrame까지는 JFrame을 상속받는걸로 하였기때문에..
	
	public MyFlowLayout() {
		this.align = MyFlowLayout.CENTER;
		this.hGap = 5;
		this.vGap = 5;
	}

	public MyFlowLayout(int align) {
		if(align < 0 || align > 2) {
			this.align = MyFlowLayout.CENTER;
		}
		this.align = align;
		this.hGap = 5;
		this.vGap = 5;
	}
	
	public MyFlowLayout(int align, int hGap, int vGap) {
		this.align = align;
		this.hGap = hGap;
		this.vGap = vGap;
	}
	
	@Override
	public Dimension getContainerSize() {
		// TODO Auto-generated method stub
		if(container != null) return container.getSize();
		else if(frame != null) {
			//프레임의 타이틀바 높이를 고려합니다.
			return new Dimension(frame.getWidth(), frame.getHeight() - frame.getTopArea());
		}
		return null;
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

	//한 행에 대한 정보를 담고있음
	class RowInfo {
		public int rowCompCount;
		public int cumulativeWidth;
		public int maxHeigt;
		
		public RowInfo(int rowCompCount, int cumulativeWidth, int maxHeight) {
			this.rowCompCount = rowCompCount;
			this.cumulativeWidth = cumulativeWidth;
			this.maxHeigt = maxHeight;
		}
	}
	
	@Override
	public void draw(List<MyComponent> compList, Graphics g) { //프로젝트에서는 CENTER만 구현합니다.
		if(frame == null && container == null) return;
		if(compList.size() <= 0) return;
		
		int frameTitle = 0;
		if(frame != null) frameTitle = frame.getTopArea();
		
		int start = 0;
		int cumulativeHeight = 0;
		while(true) {
			RowInfo rowInfo = findRowInfo(compList, start);
			
			if(rowInfo == null) {
				return;
			}
			
			
			
			int x = (frame.getWidth() - rowInfo.cumulativeWidth) / 2;
			int y = frameTitle + vGap + cumulativeHeight; //base
			
			
			
			for(int i = start; i < start + rowInfo.rowCompCount; i++) {
				MyComponent c = compList.get(i);
				Dimension dim = c.getPreferredSize();
				c.layoutDraw(x, y, dim, g);
				x += dim.getWidth() + hGap;
			}
			
			start += rowInfo.rowCompCount;
			cumulativeHeight += rowInfo.maxHeigt + vGap;
		}
	}

	private RowInfo findRowInfo(List<MyComponent> compList, int start) {
		if(start >= compList.size()) return null;
		
		int width = 0;
		
		if(frame != null) {
			width = frame.getWidth() - (hGap*2);
		}
		
		int rowCompCount = 0;
		int cumulativeWidth = 0; //누적 너비
		int maxHeigt = 0;
		
		for(int i = start; i < compList.size(); i++) {
			MyComponent c = compList.get(i);
			Dimension dim = c.getPreferredSize();
			
			if(cumulativeWidth + dim.getWidth() + hGap > width + (hGap*2)) {
				break; //이번줄 끝
			} else {
				if(dim.getHeight() > maxHeigt) {
					maxHeigt = (int) dim.getHeight();
				}
				cumulativeWidth += dim.getWidth() + hGap;
			}
			rowCompCount++;
		}
		
		return new RowInfo(rowCompCount, cumulativeWidth, maxHeigt);
	}

	

}
