package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import model.Player;

public class MainPanel extends JPanel {

	private String player1Name,player2Name;
	private Image bg,player1Image,player2Image,logo;
	private int panelWidth,panelHeight;
	public MainPanel(String player1Name,String player2Name) 
	{
		this.player1Name = player1Name;
		this.player2Name = player2Name;
	}
	
	public void intialize(int panelWidth, int panelHeight)
	{
		this.panelWidth = panelWidth; 
		this.panelHeight = panelHeight;
		URL resource = Player.class.getResource("/pics/"+"main_bg.jpg");
        try 
        {
        	bg = ImageIO.read(resource); 
        	bg = bg.getScaledInstance(panelWidth+10, panelHeight+10, Image.SCALE_SMOOTH);
        	
        	resource = Player.class.getResource("/pics/matrix.png");
        	player1Image = ImageIO.read(resource); 
        	player1Image = player1Image.getScaledInstance(panelWidth/3, panelWidth/3, Image.SCALE_SMOOTH);
        	
        	resource = Player.class.getResource("/pics/mentalist.png");
        	player2Image = ImageIO.read(resource); 
        	player2Image = player2Image.getScaledInstance(panelWidth/3, panelWidth/3, Image.SCALE_SMOOTH);
        	
        	resource = Player.class.getResource("/pics/logo.png");
        	logo = ImageIO.read(resource); 
        	
        	
        }
        catch (IOException e) {   e.printStackTrace();    }	
        
		render();
	}
	
	public void render()
    {
       repaint();
    }
	
	public void setPlayer1(String newPlayer)
	{
		URL resource = Player.class.getResource("/pics/"+newPlayer+".png");
        try 
        {
        	player1Image = ImageIO.read(resource); 
        	player1Image = player1Image.getScaledInstance(panelWidth/3, panelWidth/3, Image.SCALE_SMOOTH);
        }
        catch (IOException e) {   e.printStackTrace();    }	
		render();
	}
	public void setPlayer2(String newPlayer)
	{
		URL resource = Player.class.getResource("/pics/"+newPlayer+".png");
        try 
        {
        	player2Image = ImageIO.read(resource); 
        	player2Image = player2Image.getScaledInstance(panelWidth/3, panelWidth/3, Image.SCALE_SMOOTH);
        }
        catch (IOException e) {   e.printStackTrace();    }	
		render();
	}
	
	@Override
	public void paintComponent(Graphics g) 
	{	super.paintComponent(g);
	  	Graphics2D g2d = (Graphics2D) g;
	  	g2d.drawImage(bg,0,0,null);
	  	g2d.drawImage(player1Image,40,panelHeight+10-player1Image.getHeight(null),null);
	  	g2d.drawImage(player2Image,panelWidth+10 -player2Image.getWidth(null) - 40,panelHeight+10-player2Image.getHeight(null),null);
	  	g2d.drawImage(logo,panelWidth/2 - logo.getWidth(null)/2 + 5,panelHeight/3+5+logo.getHeight(null)/2,null);
	  	
	}

}

