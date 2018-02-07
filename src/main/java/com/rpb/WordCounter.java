package com.rpb;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/*
 * 
 * The Apache PDFBox® library is an open source Java tool for working with PDF documents. 
 * It allows creation of new PDF documents, manipulation of existing documents and the 
 * 	ability to extract content from documents.
 * 
 * Apache PDFBox is published under the Apache License v2.0.
 * 
 * Apache PDFBox® is available here :
 * https://pdfbox.apache.org/
 * 
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * 
 * @author rpb
 *
 */
public class WordCounter {
	
	public static int countWords(String filePath) {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(null == document) {
			System.err.println("The specified file could not be opened");
			return 0;
		}
		if(document.isEncrypted()) {
			System.err.println("Encrypted files are not supported");
			return 0;
		}
		
		try {
			return countWords(document);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * @param document
	 * @return the number of words in document
	 * @throws IOException
	 */
	public static int countWords(PDDocument document) throws IOException {
		PDFTextStripper stripper = new PDFTextStripper();
		String s = stripper.getText(document);
		return countWordsInString(s);
	}
	
	/**
	 * 
	 * @param text
	 * @return the number of words in text
	 */
	public static int countWordsInString(String text) {
		String trimmedText = text.trim();
		/*
		 * Suppress non-ASCII characters
		 */
		String asciiText = trimmedText.replaceAll("[^\\x00-\\x7F]", "");
		if ( asciiText.isEmpty() ) {
		    return 0;
		}
		return asciiText.split("\\s+").length;
	}
	
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Documents", "pdf");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	String fileName = chooser.getSelectedFile().getPath();
        	String message = fileName + " contains " + countWords(fileName) + " words.";
        	JOptionPane.showMessageDialog(null, message);
        }
	}
}
