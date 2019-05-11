package de.fh_zwickau.oose.zuul.containers;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A class that works with files. That is, 
 * reading the text of the file and searching 
 * for the desired file
 * @author salavat
 */
public class FileContainer{

	/**
	 * Returns the contents of the file by 
	 * the specified path if such a file exists
	 * @param filePath
	 * @return String
	 */
     public static String getTextFromFile( String filePath) {
    	 BufferedReader reader = null ;
      	  String outputText = "";
            try(InputStream fis = FileContainer.getFileInputStreamByFileName(filePath)) {
          	  reader = new BufferedReader( new InputStreamReader(fis));
                String line ;

                while ((line = reader.readLine()) != null) {

              	  outputText+=(line+'\n');
                }
            } catch (IOException e) {
                // log error
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        // log warning
                    }
                }
            }
            return outputText;
     }
     
    /**
     * Returns the FileInputStream of file whose
     * name was specified as an incoming parameter 
     * filename. Can only work with those 
     * files that are in the resource folder of project
     * @param filename
     * @return FileInputStream
     */
 	public static InputStream getFileInputStreamByFileName(String filename) {
 		return ClassLoader.getSystemResourceAsStream(filename);
	}
}