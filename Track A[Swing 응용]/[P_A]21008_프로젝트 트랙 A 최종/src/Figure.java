import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Figure extends MyComponent {
	transient protected Graphics2D g2;
	protected int thick;
	
	protected Color fillColor;
	protected Color outColor;
	
	DrawPanel p;
	
	public Figure(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
	}
	
	@Override public abstract void draw(Graphics g);
	@Override public abstract void relativeDraw(int targetX, int targetY, Graphics g);
	
	public void setOption(int thick, Color fillColor, Color outColor) {
		this.thick = thick;
		this.fillColor = fillColor;
		this.outColor = outColor;
	}
	
	public void setThick(int s) {
		this.thick = s;
	}
}
