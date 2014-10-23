import java.awt.Graphics2D;


public class Circle extends model.Shape
{
	public Circle() 
	{
		super();
		type = 3;
	}
	
	@Override
	public void paintComponent(Graphics2D g2d) 
	{
		g2d.setColor(color);
		g2d.fillOval(xTop, yTop, width, height);
	}

}
