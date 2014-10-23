package model;

import java.awt.Graphics2D;

public class Square extends Shape
{
	public Square()
	{
		super();
		type = 2 ;
	}
	@Override
	public void paintComponent(Graphics2D g2d) 
	{
		g2d.setColor(color);
		g2d.fillRect(xTop, yTop, width, height);
	}
}
