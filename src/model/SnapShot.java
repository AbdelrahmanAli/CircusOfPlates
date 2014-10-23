package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SnapShot implements Serializable
{
    
	public SnapShot() 
	{}
	
	public void saveGame(Game game,String path)
	{
	    FileOutputStream fos;
		try 
		{
			fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game);
			fos.close();
			JOptionPane.showMessageDialog(null,"Game Saved","Done",JOptionPane.INFORMATION_MESSAGE);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		
	}
	
	public boolean loadGame(String path)
	{
	    FileInputStream fis;
		try 
		{
			fis = new FileInputStream(path);
			ObjectInputStream oin = new ObjectInputStream(fis);
			Object obj = oin.readObject();
			Game.setGame((Game) obj);
			Game.getGame().setImagesAfterLoadingGame();
			Game.getGame().setTimer();
			fis.close();
			return true;
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		catch (ClassNotFoundException e) {e.printStackTrace();}
		return false;
	}

}
