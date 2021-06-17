import java.awt.Graphics;

public abstract class Figure {
	int x, y, width, height;
	
	public Figure(int x, int y, int width, int height) {
		this.x = x; this.y = y; this.width = width; this.height = height;
	}
	
	abstract public void draw(Graphics g);
}