package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import view.MainPanel;

import model.Game;
import model.Plate;
import model.Shape;
import model.SnapShot;
import model.Square;


public class MainMenu extends JFrame {

	private JPanel contentPane;
	private JButton startGame;
    private static HashMap<Integer,Class<Shape>> classes;
    private JButton btnLoadGame;
    private JButton addShape;
    private JButton btnExit;
    private JPanel panel_2;
    private static MainPanel picPanel;
    private JLabel lblPlayer;
    private JComboBox player1Chooser;
    private JLabel lblPlayer_1;
    private JComboBox player2Chooser;
    private String player1Name,player2Name;
    
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
	    classes = new HashMap<Integer,Class<Shape> >();
	    Shape s = new Square();
	    classes.put(2,(Class<Shape>) s.getClass());
	    Shape p = new Plate();
	    classes.put(1,(Class<Shape>) p.getClass());
//	    Shape ss = null;
//	    try {
//			 ss = (Shape) Class.forName("Circle").newInstance();
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		}
//	    classes.put(3,(Class<Shape>) ss.getClass());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
					frame.setResizable(false);
					picPanel.intialize(picPanel.getWidth(),picPanel.getHeight());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	
	public void initializeFrame(String[] playersNames)
	{
		player1Name = "matrix";
		player2Name = "mentalist";
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 794, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
        
		picPanel = new MainPanel(player1Name,player2Name);
        contentPane.add(picPanel, BorderLayout.CENTER);
        
        panel_2 = new JPanel();
        panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(220, 20, 60), new Color(255, 69, 0)));
        panel_2.setBackground(new Color(139, 0, 0));
        contentPane.add(panel_2, BorderLayout.SOUTH);
        
        lblPlayer = new JLabel("Player 1:");
        lblPlayer.setFont(new Font("Bauhaus 93", Font.PLAIN, 15));
        lblPlayer.setForeground(Color.YELLOW);
        panel_2.add(lblPlayer);
        
        player1Chooser = new JComboBox(playersNames);
        panel_2.add(player1Chooser);
        startGame = new JButton("Start Game");
        startGame.setFont(new Font("Bauhaus 93", Font.PLAIN, 15));
        startGame.setMaximumSize(new Dimension(95, 23));
        startGame.setMinimumSize(new Dimension(95, 23));
        player1Chooser.setSelectedIndex(0);
        panel_2.add(startGame);
        
        btnLoadGame = new JButton("Load Game");
        btnLoadGame.setFont(new Font("Bauhaus 93", Font.PLAIN, 15));
        btnLoadGame.setMinimumSize(new Dimension(95, 23));
        btnLoadGame.setMaximumSize(new Dimension(95, 23));
        panel_2.add(btnLoadGame);
        
        addShape = new JButton("Add Shape");
        addShape.setFont(new Font("Bauhaus 93", Font.PLAIN, 15));
        panel_2.add(addShape);
        
        btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) 
        	{
        		System.exit(0);
        	}
        });
        btnExit.setFont(new Font("Bauhaus 93", Font.PLAIN, 15));
        btnExit.setMinimumSize(new Dimension(95, 23));
        btnExit.setMaximumSize(new Dimension(96, 23));
        panel_2.add(btnExit);
        
        lblPlayer_1 = new JLabel("Player 2:");
        lblPlayer_1.setFont(new Font("Bauhaus 93", Font.PLAIN, 15));
        lblPlayer_1.setForeground(Color.ORANGE);
        panel_2.add(lblPlayer_1);
        
        player2Chooser = new JComboBox(playersNames);
        player2Chooser.setSelectedIndex(1);
        panel_2.add(player2Chooser);
        
     
	}
	
	public MainMenu() {
		setTitle("Circus Tent");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenu.class.getResource("/pics/logo.png")));
		String[] playersNames = {"Matrix","Mentalist","Taher","Amr","Leopard"};
		initializeFrame(playersNames);
		
		
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(false);
				try 
				{	
					Controller controller = new Controller(MainMenu.this);	
					controller.startGame(classes,player1Name.toLowerCase(),player2Name.toLowerCase(),null);
				}
				catch (InstantiationException | IllegalAccessException e1) {e1.printStackTrace();}
			}
		});
		
		player1Chooser.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				player1Name = (String) player1Chooser.getSelectedItem();
				picPanel.setPlayer1(player1Name.toLowerCase());
			}
		});
		
		player2Chooser.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				player2Name = (String) player2Chooser.getSelectedItem();
				picPanel.setPlayer2(player2Name.toLowerCase());
			}
		});
		
		btnLoadGame.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fc = new JFileChooser();
				fc.setAcceptAllFileFilterUsed(false); // remove all types of files
				fc.addChoosableFileFilter(new FileFilter() { // add only 1 type
					@Override
					public String getDescription() {
						return ".ct Files";
					}
 
					@Override
					public boolean accept(File f) 
					{
						return f.getName().endsWith(".ct") || f.isDirectory();
					}
				});
				
				
				int returnVal = fc.showOpenDialog(MainMenu.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) // user choose a file
				{
					//get file
					File file = fc.getSelectedFile();
				
					SnapShot snapShoot = new SnapShot();
					if(snapShoot.loadGame(file.getAbsoluteFile()+""))
					{
						try 
						{
							setVisible(false);
							Controller controller = new Controller(MainMenu.this);
							controller.startGame(classes,player1Name.toLowerCase(),player2Name.toLowerCase(),Game.getGame());
						}
						catch (InstantiationException | IllegalAccessException e1) {e1.printStackTrace();}
					}
					else JOptionPane.showMessageDialog(null,"Unable to load game.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		addShape.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser fc = new JFileChooser();
				
				fc.setAcceptAllFileFilterUsed(false); // remove all types of files
				fc.addChoosableFileFilter(new FileFilter() { // add only 1 type
					@Override
					public String getDescription() {
						return ".class Files";
					}
 
					@Override
					public boolean accept(File f) 
					{
						return f.getName().endsWith(".class") || f.isDirectory();
					}
				});
				
				
				int returnVal = fc.showOpenDialog(MainMenu.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) // user choose a file
				{
					//get file
					File file = fc.getSelectedFile();
 
					//get file name
					final String theClassName = file.getName().substring(0,file.getName().indexOf('.'));
					addClass(file,theClassName);
				}
			}
		});
	}
	
	public void addClass(File file,final String theClassName)
	{

			//get file directory
			String fileDirectory = file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-6-theClassName.length());
			
			File directory = new File(fileDirectory);
			URL url;
			try 
			{
				url = directory.toURI().toURL();
				URL[] urls = new URL[]{url};
				ClassLoader cl = new URLClassLoader(urls);
				Class cls = (Class) cl.loadClass(theClassName); 
				if(cls.newInstance() instanceof Shape)
				{
				    Shape typeIdentifier = (Shape) cls.newInstance();
				    classes.put(typeIdentifier.getType(),cls);
				    JOptionPane.showMessageDialog(null,"Shape "+theClassName+" Loaded","Done",JOptionPane.INFORMATION_MESSAGE);
				}
				else  JOptionPane.showMessageDialog(null,"Not a Shape Class!","Error",JOptionPane.ERROR_MESSAGE );
			}
			catch (InstantiationException | IllegalAccessException e) {e.printStackTrace();}
			catch (MalformedURLException e) {e.printStackTrace();}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			

	}
}
