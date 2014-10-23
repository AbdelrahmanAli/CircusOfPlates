package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;

import controller.Controller;


public class Game implements Serializable
{
    private HashMap<Integer,Class<Shape>> classes;
    private ShapePool shapePool;
    private ArrayList<Shape> droppedShapes;
    private Player player1,player2;
    private HangedShapes hangedShapes;
    private static Game game ;
    private int panelWidth,panelHeight;
    private Collision collisionDetector;
    private boolean player1Win,player2Win,gameFinished;
    private long startTime , finishTime ,timePassed;
    private transient Image player1Image,player2Image,draw,win;
    
    private Game(long startTime,HashMap<Integer,Class<Shape>> classes,String player1Name , String player2Name, int panelWidth, int panelHeight) throws InstantiationException, IllegalAccessException
    {
        
        player1Win = player2Win = gameFinished = false;
        collisionDetector = new Collision();
        this.classes = classes;
        shapePool = ShapePool.getInstance(classes);
        droppedShapes = new ArrayList<Shape>();
        player1 = new Player(player1Name,panelWidth/4,panelHeight/2 + panelHeight/6,shapePool,panelWidth);
        player2 = new Player(player2Name,panelWidth-panelWidth/4 - panelWidth/7,panelHeight/2 + panelHeight/6,shapePool,panelWidth);
        hangedShapes = new HangedShapes(shapePool,panelWidth,10,70,this);
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.startTime = startTime;
        System.out.println(startTime);
        finishTime = 120000;
        player1Image = getImage(player1Name);
        player2Image = getImage(player2Name);
        draw = getImage("draw");
        draw = draw.getScaledInstance(panelWidth/7, panelWidth/7, Image.SCALE_SMOOTH);
        win = getImage("win");
        win = win.getScaledInstance(panelWidth/7, panelWidth/7, Image.SCALE_SMOOTH);
    }
    
	public void setTimer() 
	{
	    startTime = System.currentTimeMillis() - timePassed;
	}
    
    public void setImagesAfterLoadingGame()
    {
        player1.setImagesAfterLoadingGame();
        player2.setImagesAfterLoadingGame();
        
        player1Image = getImage(player1.getName());
        player2Image = getImage(player2.getName());
        
        draw = getImage("draw");
        draw = draw.getScaledInstance(panelWidth/7, panelWidth/7, Image.SCALE_SMOOTH);
        win = getImage("win");
        win = win.getScaledInstance(panelWidth/7, panelWidth/7, Image.SCALE_SMOOTH);
    }
    
    public Image getImage(String name)
    {
    	Image image = null;
        URL resource = Game.class.getResource("/pics/"+name+".png");
        try 
        {
        	image = ImageIO.read(resource);
        }
        catch (IOException e) {   e.printStackTrace();    }
		return image;
    }
    
    public String getPlayerOneScore()
    {
        return player1.getScore()+"";
    }
    
    public String getPlayerTwoScore()
    {
        return player2.getScore()+"";
    }
     
     
    public void drawPlayer(Player player, Graphics2D g2d)
    {
        player.paintComponent(g2d);
    }
    
    public void drawPlayerStack(Player player, Graphics2D g2d) 
    {
    	if(player.getRightStack().size()!=0)	iterateThroughStack(player.getRightStack(),g2d);
    	if(player.getLeftStack().size()!=0)		iterateThroughStack(player.getLeftStack(),g2d);
	}
    
    public void iterateThroughStack(Stack<Shape> stack,Graphics2D g2d)
    {
    	StackIterator stackIterator = new StackIterator(stack);
    	while(!stackIterator.isDone())
		{
    		stackIterator.currentItem().paintComponent(g2d);
    		stackIterator.next();
		}
    }
    
    public void drawHangedShapes(Graphics2D g2d) 
	{
	    Color wood = new Color(199,97,20);
    	
	    // draw topLeft
	    hangedShapes.drawLeft(g2d,hangedShapes.getTopLeft());
	    //draw topRight
	    hangedShapes.drawRight(g2d,hangedShapes.getTopRight());

	    // draw bottomLeft
	    hangedShapes.drawLeft(g2d,hangedShapes.getBottomLeft());

	    //draw bottomRight
	    hangedShapes.drawRight(g2d,hangedShapes.getBottomRight());
	    
	    g2d.setColor(wood);
        g2d.fillRect(0, 50, 480, 8);
	    g2d.fillRect(panelWidth-490, 50, 490, 8);
	    g2d.fillRect(0, 110, 360, 8);
	    g2d.fillRect(panelWidth-370, 110, 370,8);

	}
	
	    
	public void drawDroppedShapes(Graphics2D g2d) 
	{
	    for(int i = 0 ; i < droppedShapes.size() ; i++)	droppedShapes.get(i).paintComponent(g2d);
	}

    
	public Player getPlayerOne() 
	{
		return player1;
	}
	

	public Player getPlayerTwo() 
	{
		return player2;
	}
    public void updateGame(boolean isAPressed, boolean isDPressed, boolean isLeftPressed, boolean isRightPressed)
    {
        if(!gameFinished)
        {
        	 //update player 1 
            if(isDPressed && !isAPressed)   player1.updatePosition(true);
            else if (!isDPressed && isAPressed)  player1.updatePosition(false);
            
            //update player 2 
            if(isRightPressed && !isLeftPressed)    player2.updatePosition(true);
            else if (!isRightPressed && isLeftPressed)  player2.updatePosition(false);
            
            //hanged shapes update and dropped shapes adding
            hangedShapes.update();
            
            //dropped shapes update
            updateDroppedShapes();
            
            // update players stack
            collisionDetector.detectCollision(player1,droppedShapes);
            collisionDetector.detectCollision(player2,droppedShapes);
            
            // check win game
            checkGameWinner();
            checkTime();
        }

    }
    
    public void drawEndGame(Graphics2D g2d)
    {
        if(gameFinished)
        {
            if(!player1Win && !player2Win) // draw
            {
                showDraw(g2d);
            }
            else if(player1Win) // player1 win
            {
                showWin(true , g2d);
            }
            else //player2 win 
            {
                showWin(false , g2d);
            }
        }
        
    }
    
    public void showWin(boolean player ,Graphics2D g2d)
    {
    	if(win.getWidth(null)>= 400)    Controller.setIsRunning(false);
    	
        if(player) g2d.drawImage(player1Image,30,100,null);
        else g2d.drawImage(player2Image,panelWidth-20-player2Image.getWidth(null),100,null);
        
        int prevWidth = win.getWidth(null);
        int prevHeight = win.getHeight(null);
        win = getImage("win");
        win = win.getScaledInstance(prevWidth+10,prevHeight+10, Image.SCALE_SMOOTH);
        g2d.drawImage(win,panelWidth/2 -win.getWidth(null)/2 - 20,panelHeight/2 - win.getHeight(null)/2,null);
    }

    public void showDraw(Graphics2D g2d)
    {
    	if(draw.getWidth(null)>= 500)    Controller.setIsRunning(false);
        g2d.drawImage(player1Image,30,100,null);
        g2d.drawImage(player2Image,panelWidth-20-player2Image.getWidth(null),100,null);
        int prevWidth = draw.getWidth(null);
        int prevHeight = draw.getHeight(null);
        draw = getImage("draw");
        draw = draw.getScaledInstance(prevWidth+20,prevHeight+10, Image.SCALE_SMOOTH);
        g2d.drawImage(draw,panelWidth/2 -draw.getWidth(null)/2 - 20,panelHeight/2 - draw.getHeight(null)/2,null);
    }
    
    public void checkTime()
    {
        if(System.currentTimeMillis() - startTime >= finishTime)
        {
            gameFinished= true;
            if(player1.getScore() > player2.getScore()) 
            {
                player1Win = true;
                player2Win = false;
                
            }
            else if(player1.getScore() < player2.getScore()) 
            {
                player1Win = false;
                player2Win = true;
            }
            else
            {
                player1Win = false;
                player2Win = false;
            }
        }
    }
    
    public void checkGameWinner()
    {
        if(checkStackCondition(player1) && checkStackCondition(player2))
         {
             player1Win = false; 
             player2Win = false;
             gameFinished = true;
         }
        else if(checkStackCondition(player1)) 
        {   
            player1Win = false;
            player2Win=true;
            gameFinished = true;
        }
        else if(checkStackCondition(player2)) 
        {
            player1Win = true;
            player2Win = false;
            gameFinished = true;
        }
    }
    
    public boolean checkStackCondition(Player player)
    {
        return(player.getLeftStack().size() == 9 || player.getRightStack().size() == 9);
    }
    
    public void updateDroppedShapes()
    {
        for(int i=droppedShapes.size()-1 ; i>=0 ; i-- )
        {
        	droppedShapes.get(i).changeRandom(panelWidth);
        	if(droppedShapes.get(i).getyTop() >= panelHeight)
        	{
        	    shapePool.releaseShape(droppedShapes.get(i));
        	    droppedShapes.remove(i);
        	}
        }
    }
    
    public void addDroppedShape(Shape shape)
    {
    	droppedShapes.add(shape);
    }



	public static synchronized Game getInstance(long startTime,HashMap<Integer,Class<Shape>> classes,String player1Name , String player2Name, int panelWidth, int panelHeight) throws InstantiationException, IllegalAccessException
	{
		if (game == null)   game = new Game(startTime,classes,player1Name,player2Name,panelWidth,panelHeight);
		return game;
	}
	
	public static void setNull()
	{
		game = null;
	}

	public String getTimeLeft() 
	{
		long minutesLeft =(finishTime - (System.currentTimeMillis()-startTime))/(1000*60);
		long secondsLeft = (finishTime - (System.currentTimeMillis()-startTime))/(1000) - minutesLeft*60;
		return minutesLeft+" : "+secondsLeft;
	}
	
	public boolean isGameFinished()
	{
	    return gameFinished;
	}

	public void handlePausedTime(long pausePeriod) 
	{
	    startTime+=	pausePeriod;
	    timePassed = System.currentTimeMillis() - startTime;
	}
	public static void setGame(Game loadedGame)
	{
		game= loadedGame; 
	}

    
	public static Game getGame() {
			
			return game ;
	}


	
}
