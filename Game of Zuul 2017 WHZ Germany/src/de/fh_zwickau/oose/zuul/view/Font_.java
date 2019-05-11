package de.fh_zwickau.oose.zuul.view;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.fh_zwickau.oose.zuul.containers.FileContainer;
import javafx.scene.text.Font;
/**
 * Ñlass containing static style fields for the font
 * @author salavat
 *
 */
public class Font_ {
	
	public static Font first_font;
	
	public Font_(){
		try (InputStream fis = FileContainer.getFileInputStreamByFileName("SFPixelate.ttf")){
			first_font = Font.loadFont(fis, 14.5);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
