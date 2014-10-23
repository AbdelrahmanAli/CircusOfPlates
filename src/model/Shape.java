package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Random;

public class Shape implements Serializable
{
	protected int xTop,yTop,yBottom,xMiddle,xBottom,width,height,type,changedX;
	protected Color color;
	
	public Shape() 
	{
		getRandomColor();
	}
	
	public void paintComponent(Graphics2D g2d) {}
	
	public void setAll(int xTop,int yTop,int xBottom,int yBottom)
	{
	    this.xTop = xTop; 
		this.yTop = yTop;
		this.yBottom = yBottom;
		this.xBottom = xBottom;
		width = xBottom - xTop;
		height = yBottom - yTop;
		countXMiddle();
	}
	
    public int getStartDetectionRegion()
    {
        switch(type)
		{
		    // plate
			case 1:  return xBottom - width + 5 ;
			// square
			case 2:  return xTop; 
			//circle
			case 3: return xTop+5;
		}
        return 0;
    }
   
   
    public int getEndDetectionRegion()
    {
        switch(type)
		{
		    // plate
			case 1:  return xBottom -5 ;
			// square
			case 2: return xBottom;
			//circle
			case 3: return xBottom-5;
		}
        return 0;
    }
    
	public void countXMiddle() 
	{
		switch(type)
		{
			case 1:  xMiddle = (2*xTop + width + 10)/2; break; // plate
			case 2: xMiddle = (xTop+xBottom)/2; break; // square
			case 3: xMiddle = (xTop+xBottom)/2; break; // circle
		}
	}
	
	public int getxMiddle()
	{
		return xMiddle;
	}

	public void setRandomX()
	{
	   	Random rand = new Random();
	  	changedX = rand.nextInt(9)-4;
	}
	
	public void changeRandom(int panelWidth)
	{
	    if(changedX<=0) // motion left
	    {
	        if(xTop <= 0) changedX*=-1;
	    }
	    else // motion right
	    {
	        if(xBottom >= panelWidth) changedX*=-1;
	    }
	    yTop+=2;
	    yBottom+= 2; 
	    xTop+=changedX;
	    xBottom+=changedX;
	    countXMiddle();
	}
	public void getRandomColor()
	{
	    //http://cloford.com/resources/colours/500col.htm
		Random rand = new Random();
		switch(rand.nextInt(5)+1)
		{
			case 1: // blue
			        color = new Color(30,144,255	);
			        break;
			case 2: // red
			        color = new Color(255,0,0);
			        break;
			case 3: // yellow
			        color = new Color(255,193,3);
			        break;
			case 4: // green
			        color = new Color(0,255,127);
			        break;
			case 5:// pink
			        color = new Color(255,62,150);
			        break;
		}
	}

////////////////////////////////
	public int getxTop() {
		return xTop;
	}

	public void setxTop(int xTop) {
		this.xTop = xTop;
	}

	public int getyTop() {
		return yTop;
	}

	public void setyTop(int yTop) {
		this.yTop = yTop;
	}

	public int getyBottom() {
		return yBottom;
	}

	public void setyBottom(int yBottom) {
		this.yBottom = yBottom;
	}

	public int getxBottom() {
		return xBottom;
	}

	public void setxBottom(int xBottom) {
		this.xBottom = xBottom;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	public int getType()
	{
		return type;
	}

	
    
}
