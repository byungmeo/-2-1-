import java.awt.Dimension;
import java.awt.Graphics;

public class Oval extends Figure {
	public Oval(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override public void draw(Graphics g) {
		g.drawOval(x, y, width, height);
	}
}