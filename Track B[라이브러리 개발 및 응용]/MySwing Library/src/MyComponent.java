import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

interface MyComponent {
	public void draw(Graphics g);
	public void setPreferredSize(Dimension size);
	public void layoutDraw(int d, int y, Dimension size, Graphics g);
	public Dimension getSize();
	public Dimension getPreferredSize();
	public void drawAlignString(String str, int x, int y, int width, int height, Graphics g);
	public boolean isClicked(MouseEvent start, MouseEvent end);
	public boolean contains(int click_x, int click_y);
	public void setLocation(int x, int y);
}
