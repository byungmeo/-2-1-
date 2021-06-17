import java.awt.event.MouseEvent;

public class MyEvent {
	private MouseEvent me;
	private MyButton btn;
	private MyMenu menu;
	private MyMenuItem item;
	
	public MyEvent(MouseEvent me, MyButton btn) {
		this.me = me;
		this.btn = btn;
	}
	
	public MyEvent(MouseEvent me, MyMenu myMenu) {
		this.me = me;
		this.menu = myMenu;
	}
	
	public MyButton getSource() {	
		return btn;
	}
	
	public MyMenu getMenuSource() {
		return menu;
	}
	
	public int getX() {
		return me.getX();
	}
	public int getY() {
		return me.getY();
	}
}
