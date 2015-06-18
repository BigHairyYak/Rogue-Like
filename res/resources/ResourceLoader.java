package resources;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

public class ResourceLoader 
{
	static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage(String fileName)
	{
		System.out.println(fileName);
		System.out.println(Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource("res/images/" + fileName)));
		return Toolkit.getDefaultToolkit().getImage("res/images/" + fileName);
	}
	public static BufferedImage getBufferedImage(String fileName)
	{
		System.out.println(fileName);
		try 
		{
			return ImageIO.read(new FileInputStream("res/images/" + fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static AudioClip getAudioClip(String fileName)
	{
		try {
			return Applet.newAudioClip(new File("res/audio/" + fileName).toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
