package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Stack;

import javax.imageio.ImageIO;

public class Player	implements Serializable
{

    private String name;
    private int score,xTop,yTop,xMiddle,xBottom,yBottom,panelWidth,handWidth;
    private transient Image image;
    private Stack<Shape> leftStack;
    private Stack<Shape> rightStack;
    private ShapePool shapePool;
    
    public Player(String name,int xTop, int yTop,ShapePool shapePool, int panelWidth) throws InstantiationException, IllegalAccessException
    {
    	this.panelWidth = panelWidth;
        this.xTop = xTop;
        this.yTop = yTop;
        this.shapePool = shapePool;
        this.name = name;
        this.score = 0 ;
        leftStack = new Stack<Shape>();
        rightStack = new Stack<Shape>();
        handWidth = 20;
        
        URL resource = Player.class.getResource("/pics/"+name+".png");
        try 
        {
        	image = ImageIO.read(resource); 
        	image = image.getScaledInstance(panelWidth/7, panelWidth/7, Image.SCALE_SMOOTH);
        	xBottom = xTop + image.getWidth(null);
        	yBottom = yTop + image.getHeight(null);
        	xMiddle = (xBottom + xTop)/2;
        }
        catch (IOException e) {   e.printStackTrace();    }
    }
    
	public void setImagesAfterLoadingGame() 
	{
		URL resource = Player.class.getResource("/pics/"+name+".png");
        try 
        {
        	image = ImageIO.read(resource); 
        	image = image.getScaledInstance(panelWidth/7, panelWidth/7, Image.SCALE_SMOOTH);
        }
        catch (IOException e) {   e.printStackTrace();    }
	}
    public int getStartDetectionRegion(boolean leftOrRight)
    {
        // right > true , left > false
        if(leftOrRight) return xBottom-handWidth;
        else return xTop+8;
    }
    public int getEndDetectionRegion(boolean leftOrRight)
    {
        // right > true , left > false
        if(leftOrRight) return xBottom-8;
        else return xTop+handWidth;
    }
    
    public int getxMiddle()
    {
    	return xMiddle;
    }
    public int getxTop()
    {
    	return xTop;
    }
    
    public int getHandYTop()
    {
    	return yTop+20;
    }
    
    public void updatePosition(boolean direction) 
    {
    	if(direction && (xBottom+5 <= panelWidth))
    	{
    		xTop+=5;   // right
    		xBottom+=5;
    		moveStacks(5);
    	}
    	else if(!direction &&(xTop-5 >= 0))
    	{
    		xTop-=5;     // left
    		xBottom-=5;
    		moveStacks(-5);
    	}
    	xMiddle = (xBottom + xTop)/2;
    }
    
    public void moveStacks(int step)
    {
        if(rightStack.size()!=0)	stackMotion(rightStack,step);
    	if(leftStack.size()!=0)		stackMotion(leftStack,step);
    }
    
    private void stackMotion(Stack<Shape> stack,int step)
    {
    	StackIterator stackIterator = new StackIterator(stack);
    	Shape shape;
    	while(!stackIterator.isDone())
		{
		    shape = stackIterator.currentItem();
    		shape.setxTop(shape.getxTop()+step);
    		shape.setxBottom(shape.getxBottom()+step);
    		shape.countXMiddle();
    		stackIterator.next();
		}
    }
    
    public  void  paintComponent(Graphics2D g2d) 
	{
    	g2d.drawImage(image,xTop,yTop, null);
    	//g2d.drawString(xTop + " "+xMiddle+" "+xBottom  , xTop, yTop);
	}
    
    public int getScore()
    {
        return score ;
    }
    
    public Stack<Shape> getLeftStack()
    {
        return leftStack;
    }
    
    public Stack<Shape> getRightStack()
    {
        return rightStack;
    }
	public void setScore(int score) {
		this.score= score;
	}

	public String getName() {
		return this.name;
	}
    

    
}
