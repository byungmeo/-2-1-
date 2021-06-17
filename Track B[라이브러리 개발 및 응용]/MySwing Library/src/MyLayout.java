import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;


interface MyLayout {
	public void setContainer(MyContainer c);
	public void setContainer(MyFrame f);
	public void draw(List<MyComponent> compList, Graphics g);
	public Dimension getContainerSize();
}
