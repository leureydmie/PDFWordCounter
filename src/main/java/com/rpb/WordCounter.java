package com.rpb;

import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class WordCounter {
	
	public static int countWords(String fileName) {
		PDDocument document = null;
		try {
			document = PDDocument.load(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Check if file is encrypted
		 */
		if(null == document || document.isEncrypted()) {
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
	
	public static int countWords(PDDocument document) throws IOException {
		PDFTextStripper stripper = new PDFTextStripper();
		String s = stripper.getText(document);
		return countStringWords(s);
	}
	
	public static int countStringWords(String originalText) {
		String trim = originalText.trim();
		String asciiText = trim.replaceAll("[^\\x00-\\x7F]", "");
		if ( asciiText.isEmpty() ) {
		    return 0;
		}
		/*
		 * FOR DEBUG PURPOSE ONLY
		 * System.out.println(asciiText);
		 */
		return asciiText.split("\\s+").length;
	}
	
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PDF Documents", "pdf");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
        	String fileName = chooser.getSelectedFile().getPath();
        	String message = fileName + " contains " + countWords(fileName) + " words.";
        	JOptionPane.showMessageDialog(null, message);
        	System.out.println("");
        	System.out.println("-------------------------------------------------------");
        	System.out.println(message);
        	System.out.println("-------------------------------------------------------");
        }
	}
}
