import java.awt.Dimension;
import java.util.ArrayList;

//MenuBar, Menu, ToolBar
public abstract class MyContainer implements MyComponent {
	private ArrayList<MyComponent> compList;
	private MyLayout layout;
	
	public abstract MyComponent add(MyComponent c);
	
	@Override public void setPreferredSize(Dimension size) {
		throw(new UnsupportedOperationException());
	}
	
	//레이아웃 설정
	public void setLayout(MyLayout ml) { 
		ml.setContainer(this);
		this.layout = ml;
	} 
}
