import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//swing을 이용했을때, 버튼은 기본적으로 클릭 가능한 상태이다. 아무 기능도 안하지만..
//API를 직접 구현할 때, MyFrame에 최소한 버튼 클릭은 할 수 있는 마우스리스너를
//기본적으로 부착할 것이다. 물론, 클릭 시 어떤 기능을 할 지는 응용에서 정한다.
class DefaultMouseListener implements MouseListener {
	MyFrame mf;
	MouseEvent start;
	
	public DefaultMouseListener(MyFrame mf) {
		this.mf = mf;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		start = e;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(mf.getMyMenuBar() != null) {
			if(mf.getMyMenuBar().isClicked(start, e)) {
				mf.repaint();
				return;
			}
			mf.repaint();
		}
		
		if(mf.getToolBar().isClicked(start, e)) {
			return;
		}
		
		for(MyComponent c : mf.getMyComponents()) {
			if(c.getClass() == MyButton.class) {
				MyButton btn = (MyButton)c;
				if(btn.isClicked(start, e) == true) {
					mf.repaint();
					return;
				}
				
			}
		}
	}
	@Override public void mouseClicked(MouseEvent e) {;} //not use
	@Override public void mouseEntered(MouseEvent e) {;} //not use
	@Override public void mouseExited(MouseEvent e) {;}
}
