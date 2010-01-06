package part3.chapter11;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class FontTypes {

    /** The resulting PDF file. */
    public static String RESULT
        = "results/part3/chapter11/font_types.pdf";
    /** The resulting PDF file. */
    public static String TEXT
        = "quick brown fox jumps over the lazy dog\nQUICK BROWN FOX JUMPS OVER THE LAZY DOG";
    
    public static String[][] FONTS = {
    	{BaseFont.HELVETICA, BaseFont.WINANSI},
    	{"resources/fonts/cmr10.afm", BaseFont.WINANSI},
    	{"resources/fonts/cmr10.pfm", BaseFont.WINANSI},
    	{"c:/windows/fonts/ARBLI__.TTF", BaseFont.WINANSI},
    	{"c:/windows/fonts/arial.ttf", BaseFont.WINANSI},
    	{"c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H},
    	{"resources/fonts/Puritan2.otf", BaseFont.WINANSI},
    	{"c:/windows/fonts/msgothic.ttc,0", BaseFont.IDENTITY_H},
    	{"KozMinPro-Regular", "UniJIS-UCS2-H"}
    };
    
	public static void main(String[] args) throws IOException, DocumentException {
		new FontTypes().createPdf(RESULT);
	}
	
	public void createPdf(String filename) throws IOException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(filename));
		document.open();
		BaseFont bf;
		Font font;
		for (int i = 0; i < FONTS.length; i++) {
			bf = BaseFont.createFont(FONTS[i][0], FONTS[i][1], BaseFont.EMBEDDED);
			document.add(new Paragraph(
					String.format("Font file: %s with encoding %s", FONTS[i][0], FONTS[i][1])));
			document.add(new Paragraph(
					String.format("iText class: %s", bf.getClass().getName())));
			font = new Font(bf, 12);
			document.add(new Paragraph(TEXT, font));
			document.add(new LineSeparator(0.5f, 100, null, 0, -5));
		}
		document.close();
	}
}
