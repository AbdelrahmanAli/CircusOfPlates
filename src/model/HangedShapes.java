package model;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

public class HangedShapes implements Serializable
{
    private ShapePool shapePool;
    private ArrayList<Shape> topLeft;
    private ArrayList<Shape> topRight;
    private ArrayList<Shape> bottomLeft;
    private ArrayList<Shape> bottomRight;
    private Shape shape;
    private int panelWidth;
    private Game game;
    public HangedShapes(ShapePool shapePool, int panelWidth,int yTopUpShelf,int yTopBottomShelf, Game game) throws InstantiationException, IllegalAccessException
    {
        this.game = game;
        this.panelWidth = panelWidth;
        this.shapePool = shapePool;
        topLeft = new ArrayList<Shape>();
        topRight = new ArrayList<Shape>();
        bottomLeft = new ArrayList<Shape>();
        bottomRight = new ArrayList<Shape>();
        initializeShelves();
        initializeShelvesShapes(yTopUpShelf,yTopBottomShelf);
    }
    
    public void initializeShelves()
    {
        for (int i = 0 ; i<13 ;i++ )
        {
            acquireShape(topLeft);
            acquireShape(topRight);
        }
        for (int i = 0 ; i<10 ;i++ )
        {   
            acquireShape(bottomLeft);
            acquireShape(bottomRight);
        }
    }
    
    
	private void acquireShape(ArrayList<Shape> shelf)
    {
        shape = shapePool.acquireShape();
        if(shape != null)
        {
            shape.setRandomX();
            shelf.add(shape);
        }
    }
    
    public void initializeShelvesShapes(int yTopUpShelf , int yTopBottomShelf)
    {
        initializeLeftShelve(topLeft,yTopUpShelf);
        initializeLeftShelve(bottomLeft,yTopBottomShelf);
        initializeRightShelve(topRight,yTopUpShelf);
        initializeRightShelve(bottomRight,yTopBottomShelf);
    }

    public void initializeLeftShelve(ArrayList<Shape> bench,int yTop)
    {
         // left
        int width = 30 , height = 30;
        int xTop=-70,xBottom,yBottom = yTop + height;
        for(int i = 0; i < bench.size() ; i++)
        {
            xTop = xTop + 40 ;
            xBottom = xTop + width;
            bench.get(i).setAll(xTop,yTop,xBottom,yBottom);
        }        
    }
    
    public void initializeRightShelve(ArrayList<Shape> bench,int yTop)
    {
        int width = 30, height = 30;
        int xTop=panelWidth+30,xBottom,yBottom = yTop + height ;
        for(int i = 0 ; i < bench.size() ; i++)
        {
            xTop = xTop - 40 ;
            xBottom = xTop + width;
            bench.get(i).setAll(xTop,yTop,xBottom,yBottom);
        }
    }
    
    public void drawLeft(Graphics2D g2d,ArrayList<Shape> bench )
    {
        for(int i = 0; i < bench.size() ; i++)  bench.get(i).paintComponent(g2d);
    }
    
    public void drawRight(Graphics2D g2d , ArrayList<Shape> bench)
    {
        for(int i = 0 ; i < bench.size() ; i++) bench.get(i).paintComponent(g2d);
    }
    
    
	public void update() 
	{
		updateLeft(topLeft,510);
		updateLeft(bottomLeft,390);
		updateRight(topRight,panelWidth-490);
		updateRight(bottomRight,panelWidth-370);
	}
	
	public void updateLeft(ArrayList<Shape> bench , int maxLength)
	{
	    for(int i = 0 ; i < bench.size() ; i++)
	    {
	        shape = bench.get(i);
	        shape.setxTop(shape.getxTop() + 1);
	        shape.setxBottom(shape.getxBottom() + 1);
	        shape.countXMiddle();
	    }
	    if(bench.size()!=0)
	    {
		    shape = bench.get(bench.size()-1);
		    if(shape.getxBottom() >= maxLength )	addDroppedShapes(bench,-40,shape.getyTop(),shape.getyBottom(),-10);
		}
	}
	
	public void updateRight(ArrayList<Shape> bench , int maxLength)
	{
	     for(int i = 0 ; i < bench.size() ; i++)
	    {
	        shape = bench.get(i);
	        shape.setxTop(shape.getxTop() -1 );
	        shape.setxBottom(shape.getxBottom() - 1);
	        shape.countXMiddle();
	    }
	     if(bench.size()!=0)
		 {
			 shape = bench.get(bench.size()-1);
			 if(shape.getxBottom() <= maxLength )	addDroppedShapes(bench,panelWidth,shape.getyTop(),shape.getyBottom(),panelWidth+30);
		 }
		 
	}
	
	public void addDroppedShapes(ArrayList<Shape> bench,int xTop,int yTop,int yBottom,int xBottom)
	{
		game.addDroppedShape(shape);
	    bench.remove(bench.size()-1);
	    shape = shapePool.acquireShape();

	    if(shape!=null) 
	    { 
	        shape.setAll(xTop,yTop,xBottom,yBottom);
	        shape.setRandomX();
	        bench.add(0,shape);
	    }
	}
	
	////////////////////////////////// getters and setters
    
    public ArrayList<Shape> getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(ArrayList<Shape> topLeft) {
		this.topLeft = topLeft;
	}

	public ArrayList<Shape> getTopRight() {
		return topRight;
	}

	public void setTopRight(ArrayList<Shape> topRight) {
		this.topRight = topRight;
	}

	public ArrayList<Shape> getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(ArrayList<Shape> bottomLeft) {
		this.bottomLeft = bottomLeft;
	}

	public ArrayList<Shape> getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(ArrayList<Shape> bottomRight) {
		this.bottomRight = bottomRight;
	}

    
    private void relaseShape(ArrayList<Shape> shelf)
    {
    	if(shelf.size()!=0)	shelf.remove(0);
    }

    ////////////////////  adders
    public void addAtTopLeft()
    {
        acquireShape(topLeft);
    }
    
    public void addAtTopRight()
    {
        acquireShape(topRight);   
    }
    
    public void addAtBottomLeft()
    {
        acquireShape(bottomLeft);
    }
    public void addAtBottomRight()
    {
        acquireShape(bottomRight);
    }
    
    ///////////////////// removers
    public void removeAtTopLeft()
    {
        relaseShape(topLeft);
    }
    
    public void removeAtTopRight()
    {
        relaseShape(topRight);   
    }
    
    public void removeAtBottomLeft()
    {
        relaseShape(bottomLeft);
    }
    public void removeAtBottomRight()
    {
        relaseShape(bottomRight);
    }
}

    
