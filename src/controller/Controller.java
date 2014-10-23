package controller;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import model.Game;
import model.Shape;
import model.SnapShot;
import view.View;

public class Controller extends JFrame implements KeyListener , Runnable {

	private JPanel contentPane,viewPanel,panel_1,panel_2,panel_3;
	private JLabel player1Label,player1Score,player2Label,player2Score;
	private JButton saveGame,menu;
	private static boolean isRunning;
	private boolean isPaused;
	private boolean isAPressed;
	private boolean isDPressed;
	private boolean isLeftPressed;
	private boolean isRightPressed;
	private Thread thread;
	private Game game;
	private double GAME_HERTZ,TIME_BETWEEN_UPDATES ,TARGET_FPS ,TARGET_TIME_BETWEEN_RENDERS;
	private int MAX_UPDATES_BEFORE_RENDER,frameCount,panelWidth,panelHeight; 
	private JButton closeButton;
	private MainMenu mainMenu;
	private JButton pauseGame;
	private JLabel timer;
	private long startPauseTime,pausePeriod;
	private SnapShot snapShot ;
	
	public Controller(MainMenu mainMenu) throws InstantiationException, IllegalAccessException 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(Controller.class.getResource("/pics/logo.png")));
		setTitle("Circus Tent");
		this.mainMenu = mainMenu;
		initializeFrame();
	    initializeLoopVariables();
	    isPaused = true;
	    isRunning = isPaused = isAPressed = isDPressed = isLeftPressed = isRightPressed = false;
		snapShot = new SnapShot();
	}
	
	public void startGame(HashMap<Integer,Class<Shape>> classes,String player1Name,String player2Name,Game sentGame)
	{
	    player1Label.setText((player1Name.charAt(0)+"").toUpperCase()+player1Name.substring(1)+": ");
		player2Label.setText((player2Name.charAt(0)+"").toUpperCase()+player2Name.substring(1)+": ");
	    try 
	    {   if(sentGame==null) game = Game.getInstance(System.currentTimeMillis(),classes,player1Name,player2Name,panelWidth,panelHeight);
	    	else game = sentGame;
	    }
	    catch (InstantiationException | IllegalAccessException e) {e.printStackTrace();}
	    
	    player1Score.setText(game.getPlayerOneScore());
		player2Score.setText(game.getPlayerTwoScore());
		
		((View) viewPanel).setGame(game);
		setFocusable(true); 
		addKeyListener(this);  
		thread = new Thread(this);
		thread.start();
	}
	
	public void initializeLoopVariables()
	{
	    frameCount = 0;
	    GAME_HERTZ = 30;
	    TARGET_FPS = 60;
	    MAX_UPDATES_BEFORE_RENDER = 5;
	    TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
	    TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
	}
	public static void setIsRunning(boolean set)
	{
		isRunning=set;
	}
	
	public void gameLoop() 
	{
      double lastUpdateTime = System.nanoTime();
      double lastRenderTime = System.nanoTime();
      int lastSecondTime = (int) (lastUpdateTime / 1000000000);
      
		while(isRunning)
		{
		    if(!isPaused)
		    {
		    	
    		    double now = System.nanoTime();
                int updateCount = 0;
                
                while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER )
                {
                   game.updateGame(isAPressed,isDPressed,isLeftPressed,isRightPressed); // update
                   lastUpdateTime += TIME_BETWEEN_UPDATES;
                   updateCount++;
                }
                player1Score.setText(game.getPlayerOneScore());
        		player2Score.setText(game.getPlayerTwoScore());
        		if(!game.isGameFinished()) timer.setText("Time: "+game.getTimeLeft());
                if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)   lastUpdateTime = now - TIME_BETWEEN_UPDATES;
             
                ((View) viewPanel).render(); // render
                lastRenderTime = now;
             
                int thisSecond = (int) (lastUpdateTime / 1000000000);
                if (thisSecond > lastSecondTime)
                {
                   System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                   frameCount = 0;
                   lastSecondTime = thisSecond;
                }
                while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
                {
                   Thread.yield();
                   try {Thread.sleep(1);} catch(Exception e) {} 
                   now = System.nanoTime();
                }
		    }
		}
	}
	
	@Override
	public void run() 
	{
		isRunning = true;
		isPaused = false;
		gameLoop();
	}
	
	private void initializeFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 429);
		setSize(1200, 600);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		viewPanel = new View();
		contentPane.add(viewPanel, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel.add(panel_1, BorderLayout.WEST);
		
		player1Label = new JLabel("Player 1: ");
		player1Label.setForeground(new Color(255, 69, 0));
		player1Label.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(player1Label);
		
		
		player1Score = new JLabel("Score1");
		player1Score.setForeground(new Color(255, 69, 0));
		player1Score.setFont(new Font("Arial", Font.BOLD, 14));
		panel_1.add(player1Score);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 0));
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel.add(panel_2, BorderLayout.EAST);
		
		player2Label = new JLabel("Player 2: ");
		player2Label.setForeground(new Color(255, 127, 80));
		player2Label.setFont(new Font("Arial", Font.BOLD, 14));
		panel_2.add(player2Label);
		
		player2Score = new JLabel("Score2");
		player2Score.setForeground(new Color(255, 127, 80));
		player2Score.setFont(new Font("Arial", Font.BOLD, 14));
		panel_2.add(player2Score);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 0));
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		saveGame = new JButton("Save");
		saveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				isPaused = true;
				startPauseTime = System.currentTimeMillis();
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(Controller.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                String path = file.getAbsolutePath();
	                if(path.contains(".ct"))	path=path.replaceAll(".ct", "");
	                pausePeriod = System.currentTimeMillis() - startPauseTime;
				    game.handlePausedTime(pausePeriod);
					snapShot.saveGame(game,path+".ct");
	            }
	            isPaused = false;
				requestFocus();
			}
		});
		saveGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		saveGame.setForeground(new Color(255, 255, 255));
		saveGame.setBackground(new Color(0, 0, 0));
		panel_3.add(saveGame);
		
		menu = new JButton("Menu");
		menu.setFont(new Font("Tahoma", Font.BOLD, 14));
		menu.setForeground(new Color(255, 255, 255));
		menu.setBackground(new Color(0, 0, 0));
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				isRunning = false;
				thread=null;
				Game.setNull();
				dispose();
				mainMenu.setVisible(true);
			}
		});
		panel_3.add(menu);
		
		closeButton = new JButton("Exit");
		closeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		closeButton.setForeground(new Color(255, 255, 255));
		closeButton.setBackground(new Color(0, 0, 0));
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				System.exit(0);
			}
		});
		
		timer = new JLabel("Time: ");
		timer.setFont(new Font("Tahoma", Font.BOLD, 14));
		timer.setForeground(Color.ORANGE);
		panel_3.add(timer);
		
		pauseGame = new JButton("Pause");
		pauseGame.setFont(new Font("Tahoma", Font.BOLD, 14));
		pauseGame.setForeground(new Color(255, 255, 255));
		pauseGame.setBackground(new Color(0, 0, 0));
		pauseGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				isPaused = !isPaused;
				if(isPaused) 
				{
				    startPauseTime = System.currentTimeMillis();
				    pauseGame.setText("Resume");
				}
				else
				{
				    pausePeriod = System.currentTimeMillis() - startPauseTime;
				    game.handlePausedTime(pausePeriod);
				    pauseGame.setText("Pause");
				}
				requestFocus();
			}
		});
		panel_3.add(pauseGame);
		panel_3.add(closeButton);
		
		setVisible(true);
		panelWidth = viewPanel.getWidth();
		panelHeight= viewPanel.getHeight();

		//http://stackoverflow.com/questions/6403738/java-borderlayout-center-getting-the-width-and-height-of-the-jpanel
	}

	@Override
	public void keyPressed(KeyEvent key) 
	{
		switch(key.getKeyCode())
		{
		 	case KeyEvent.VK_A: isAPressed = true;	break;
		 	case KeyEvent.VK_D: isDPressed = true;	break;
		 	case KeyEvent.VK_LEFT: isLeftPressed = true;	break;
		 	case KeyEvent.VK_RIGHT: isRightPressed = true;	break;
		}
		key.consume();
	}

	@Override
	public void keyReleased(KeyEvent key) 
	{
		switch(key.getKeyCode())
		{
		 	case KeyEvent.VK_A:isAPressed = false;	break;
		 	case KeyEvent.VK_D:isDPressed = false;	break;
		 	case KeyEvent.VK_LEFT:isLeftPressed = false;	break;
		 	case KeyEvent.VK_RIGHT:isRightPressed= false;	break;
		}
		key.consume();
	}

	@Override
	public void keyTyped(KeyEvent key) 
	{
		key.consume();
	}





}
