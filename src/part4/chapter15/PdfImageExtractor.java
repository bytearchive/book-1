/*
 * $Id: PdfTextExtractor.java 4321 2010-02-10 05:26:05Z trumpetinc $
 *
 * This file is part of the iText project.
 * Copyright (c) 1998-2009 1T3XT BVBA
 * Authors: Kevin Day, Bruno Lowagie, Paulo Soares, et al.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation with the addition of the
 * following permission added to Section 15 as permitted in Section 7(a):
 * FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY 1T3XT,
 * 1T3XT DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see http://www.gnu.org/licenses or write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA, 02110-1301 USA, or download the license from the following URL:
 * http://itextpdf.com/terms-of-use/
 *
 * The interactive user interfaces in modified source and object code versions
 * of this program must display Appropriate Legal Notices, as required under
 * Section 5 of the GNU Affero General Public License.
 *
 * In accordance with Section 7(b) of the GNU Affero General Public License,
 * you must retain the producer line in every PDF that is created or manipulated
 * using iText.
 *
 * You can be released from the requirements of the license by purchasing
 * a commercial license. Buying such a license is mandatory as soon as you
 * develop commercial activities involving the iText software without
 * disclosing the source code of your own applications.
 * These activities include: offering paid services to customers as an ASP,
 * serving PDFs on the fly in a web application, shipping iText with a closed
 * source product.
 *
 * For more information, please contact iText Software Corp. at this
 * address: sales@itextpdf.com
 */
package part4.chapter15;

import java.io.IOException;

import part3.chapter10.ImageTypes;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ContentByteUtils;
import com.itextpdf.text.pdf.parser.PdfContentStreamProcessor;
import com.itextpdf.text.pdf.parser.RenderListener;

/**
 * Extracts images from a PDF file.
 */
public class PdfImageExtractor {

	/** The PdfReader that holds the PDF file. */
    private PdfReader reader;
    
    /** The RenderListener that will receive render notifications. */
    private RenderListener renderListener = new ImageRenderListener();
    
    /**
     * Creates a new Text Extractor object, using the most current algorithm for text
     * extraction (currently {@link LocationTextExtractionStrategy}) as the render listener
  
     * @param reader	the reader with the PDF
     */
    public PdfImageExtractor(PdfReader reader) {
        this.reader = reader;
    }
    
    /**
     * Gets the images from a page.
     * @param page	the page number of the page
     * @return	a String with the content as plain text (without PDF syntax)
     * @throws IOException
     */
    public String getImagesFromPage(int page) throws IOException {
        PdfDictionary pageDic = reader.getPageN(page);
        PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
        
        renderListener.reset();
        PdfContentStreamProcessor processor = new PdfContentStreamProcessor(renderListener);
        processor.processContent(ContentByteUtils.getContentBytesForPage(reader, page), resourcesDic);        
        return "";
    }
    
    public static void main(String[] args) throws IOException, DocumentException {
    	//new ImageTypes().createPdf(ImageTypes.RESULT);
    	PdfReader reader = new PdfReader(ImageTypes.RESULT);
    	PdfImageExtractor extractor = new PdfImageExtractor(reader);
    	for (int i = 1; i <= reader.getNumberOfPages(); i++) {
    		extractor.getImagesFromPage(i);
    	}
    }
}