package model;

import java.awt.Graphics2D;
import java.awt.Polygon;

public class Plate extends Shape
{
	private int xPoly[], yPoly[];
	private Polygon poly;
	
	public Plate() 
	{
		super();
		type = 1;
	}
	
	@Override
	public void setAll(int xTop, int yTop, int xBottom, int yBottom) 
	{
		super.setAll(xTop, yTop, xBottom, yBottom);
		xPoly = new int[4];
		yPoly = new int[4];
		width-=5;
	}

	@Override
	public void paintComponent(Graphics2D g2d) 
	{
		xPoly[0] = xTop;
		yPoly[0] = yTop;
		
		xPoly[1] = xTop + width + 10;
		yPoly[1] = yTop;
		
		xPoly[2] = xBottom - 5;
		yPoly[2] = yBottom;
		
		xPoly[3] = xBottom - width +5 ;
		yPoly[3] = yBottom;
		
	    poly = new Polygon(xPoly, yPoly, xPoly.length);
		g2d.setColor(color);

	    g2d.fillPolygon(poly);
	}
	


}
