package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Game;
import model.Player;


public class View extends JPanel 
{
	private Game game;
	private Image image;
	public View() 
	{
		setBackground(Color.WHITE);
		setImage();
	}
	
	public void setImage()
	{
		URL resource = Player.class.getResource("/pics/bg.jpg");
        try 
        {
        	image = ImageIO.read(resource); 
        	image = image.getScaledInstance(1194, 532, Image.SCALE_SMOOTH);

        }
        catch (IOException e) {   e.printStackTrace();    }	
        
	}
	public void setGame(Game game)
	{
	    this.game = game;
	}
	
	public void render()
    {
       repaint();
    }
	
	@Override
	public void paintComponent(Graphics g) 
	{	super.paintComponent(g);
	  	Graphics2D g2d = (Graphics2D) g;
	  	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	  	g2d.drawImage(image,0,0,null);
        // draw player 1
	  	game.drawPlayer(game.getPlayerOne(),g2d);
	  	// draw player 2
	  	game.drawPlayer(game.getPlayerTwo(),g2d);
	  	
	   // draw hanged shapes
	   game.drawHangedShapes(g2d);
	   
	   // draw droped shapes
	   game.drawDroppedShapes(g2d);
	   
	  	// draw player1 shapes in stack 
	  	game.drawPlayerStack(game.getPlayerOne(),g2d);

	  	// draw player2 shapes in stack 
	  	game.drawPlayerStack(game.getPlayerTwo(),g2d);
	  	
	  	//draw game end
	  	game.drawEndGame(g2d);
        
	}

}
