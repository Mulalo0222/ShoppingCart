import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.StringReader;

public class QRJsonClientHandler {
	
	private static String GetResponce(String Xml) throws SAXException, IOException, ParserConfigurationException
	{
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(Xml)));
       	NodeList errNodes = doc.getElementsByTagName("barcode");
       	String returnAnswer = "";
              
                 Element err = (Element)errNodes.item(0);
                 returnAnswer =err.getElementsByTagName("data").item(0).getTextContent();
              if(returnAnswer.equals(""))
            	   returnAnswer = "Not Found";
            
               
      return returnAnswer;
       	
	}
	public static String ReadTofile(String FileUrl) throws IOException
	{
		// Connect to the web server endpoint
		URL serverUrl =
		    new URL("http://api.qrserver.com/v1/read-qr-code/?outputformat=xml");
		HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
		 
		String boundaryString = "----38GVHEFUBTRHU";
		String fileUrl = FileUrl;
		File logFileToUpload = new File(fileUrl);
		 
		// Indicate that we want to write to the HTTP request body
		urlConnection.setDoOutput(true);
		urlConnection.setRequestMethod("POST");
		urlConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
		 
		OutputStream outputStreamToRequestBody = urlConnection.getOutputStream();
		BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));
		 
		// Include value from the myFileDescription text area in the post data
		httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
		httpRequestBodyWriter.write("Content-Disposition: form-data; name=\"MAX_FILE_SIZE\"  value=\"1048576\"");
		httpRequestBodyWriter.write("\n\n");
		 
		// Include the section to describe the file
		httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
		httpRequestBodyWriter.write("Content-Disposition: form-data;"
		        + "name=\"file\";"
		        + "filename=\""+ logFileToUpload.getName() +"\""
		        + "\nContent-Type: text/plain\n\n");
		httpRequestBodyWriter.flush();
		 
		// Write the actual file contents
		FileInputStream inputStreamToLogFile = new FileInputStream(logFileToUpload);
		 
		int bytesRead;
		byte[] dataBuffer = new byte[1024];
		while((bytesRead = inputStreamToLogFile.read(dataBuffer)) != -1) {
		    outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
		}
		 
		outputStreamToRequestBody.flush();
		 
		// Mark the end of the multipart http request
		httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
		httpRequestBodyWriter.flush();
		 
		// Close the streams
		outputStreamToRequestBody.close();
		httpRequestBodyWriter.close();
		
		BufferedReader httpResponseReader =
			    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String lineRead;
			String xml = "";
			while((lineRead = httpResponseReader.readLine()) != null) {
			 //  System.out.println(lineRead);
			   xml+=lineRead;
			}
			try {
		return		QRJsonClientHandler.GetResponce(xml);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Not Found";
	}

}
