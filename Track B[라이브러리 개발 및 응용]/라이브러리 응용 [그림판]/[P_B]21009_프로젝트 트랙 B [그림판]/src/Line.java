import java.awt.Graphics;

public class Line extends Figure {
	public Line(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override public void draw(Graphics g) {
		g.drawLine(x, y, width, height);
	}
}
