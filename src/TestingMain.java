import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDevice;

public class TestingMain {

	public static void main(String[] args){
		
			UserClass myClass = new UserClass("Davis","12345","Ralinala","mualalo022@gmail.com","mulalo.jpg");
			
			FileManagement.WriteData(myClass);
			
	
		}

}
