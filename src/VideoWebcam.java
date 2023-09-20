import java.awt.Dimension;
import java.awt.Image;
import java.util.concurrent.TimeUnit;

import com.github.sarxos.webcam.Webcam;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

public class VideoWebcam implements Runnable {
	private ImageView Webcamera;
	private Webcam eweb;
	
	public void setImageView(ImageView Webcamer)
	{
		 Webcamera = Webcamer;
			 eweb =Webcam.getDefault();
			eweb.setViewSize(new Dimension(640,480));
			
			eweb.open();
	} 
	
	@Override
	public void run() {
	
		while(true)
		{
			Webcamera.setImage( SwingFXUtils.toFXImage(eweb.getImage(), null));
		
		}
		
	}

}
