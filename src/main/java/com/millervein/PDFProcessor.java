package com.millervein;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFProcessor {
	private static final String MASTER_FILE_NAME = "Scorecard-Master.pdf";
	private static final Integer SUMMARY_PAGE_NUMBER = 0;
	private static final Integer CALL_DATA_PAGE_NUMBER = 6;

	private PDDocument master;
	private List<PDDocument> sites = new ArrayList<PDDocument>();

	public void process(String scorecardPDFPath) throws IOException {
		File scorecardPDF = new File(scorecardPDFPath);
		this.master = PDDocument.load(scorecardPDF);
		this.createSiteScorecard("Novi", 1);
		this.createSiteScorecard("Dearborn", 3, 4);
		this.createSiteScorecard("Troy", 2, 5);
	}
	
//	public void process(String scorecardPDFPath, String callDataPDFPath) throws IOException {
//		File scorecardPDF = new File(scorecardPDFPath);
//		File callDataPDF = new File(callDataPDFPath);
//		this.createMaster(scorecardPDF, callDataPDF);
//		this.createSiteScorecard("Novi", 1);
//		this.createSiteScorecard("Dearborn", 3, 4);
//		this.createSiteScorecard("Troy", 2, 5);
//		scorecardPDF.deleteOnExit();
//		callDataPDF.deleteOnExit();
//	}

	private void createMaster(File scorecardPDF, File callDataPDF) throws IOException {
		PDFMergerUtility merger = new PDFMergerUtility();
		merger.setDestinationFileName(MASTER_FILE_NAME);
		merger.addSource(scorecardPDF);
		merger.addSource(callDataPDF);
		merger.mergeDocuments(null);
		this.master = PDDocument.load(new File(MASTER_FILE_NAME));
	}

	private void createSiteScorecard(String name, Integer... sitePageNumbers) throws IOException {
		ArrayList<Integer> pageNumbers = new ArrayList<Integer>(Arrays.asList(sitePageNumbers));
		pageNumbers.add(SUMMARY_PAGE_NUMBER);
		pageNumbers.add(CALL_DATA_PAGE_NUMBER);
		Collections.sort(pageNumbers);
		PDDocument siteScorecard = new PDDocument();
		for (Integer pageNum : pageNumbers) {
			PDPage page = this.master.getPage(pageNum);
			siteScorecard.addPage(page);
		}
		siteScorecard.save("Scorecard-" + name + ".pdf");
		siteScorecard.close();
		this.sites.add(siteScorecard);
	}

}
