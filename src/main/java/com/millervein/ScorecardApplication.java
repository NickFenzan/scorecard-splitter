package com.millervein;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScorecardApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScorecardApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String scorecardPDFPath = args[0];
//		String callDataPDFPath = args[1];
		PDFProcessor pdfProcessor = new PDFProcessor();
		pdfProcessor.process(scorecardPDFPath);
	}
}
