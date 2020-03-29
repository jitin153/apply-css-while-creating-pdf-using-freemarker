package com.jitin.createpdf.documentcreator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.jitin.createpdf.exception.DocumentCreatorException;
import com.jitin.createpdf.model.DocumentRequest;
import com.jitin.createpdf.templateprocessor.FreemarkerTemplateProcessor;
import com.jitin.createpdf.util.FileUtil;

public class PdfCreator {

	public static byte[] getPdfDocument(DocumentRequest documentRequest) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			String processedText = "";
			if (Objects.nonNull(documentRequest)) {
				processedText = FreemarkerTemplateProcessor.getProcessedText(documentRequest);
			} else {
				throw new DocumentCreatorException("Document request cannot be null.");
			}
			if (StringUtils.isNotBlank(processedText)) {
				Document document = new Document();
				PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
				document.open();
				XMLWorkerHelper xMLWorkerHelper = XMLWorkerHelper.getInstance();
				InputStream inputStream = new ByteArrayInputStream(processedText.getBytes(StandardCharsets.UTF_8));
				if (StringUtils.isNotBlank(documentRequest.getCssFileName())) {
					byte[] cssContent = FileUtil.fileInByteArray(documentRequest.getCssFileName());
					InputStream css = new ByteArrayInputStream(cssContent);
					xMLWorkerHelper.parseXHtml(writer, document, inputStream, css);
				} else {
					xMLWorkerHelper.parseXHtml(writer, document, inputStream,
							Charset.forName(StandardCharsets.UTF_8.name()));
				}
				document.close();
			} else {
				throw new DocumentCreatorException("Processed text cannot be blank.");
			}
			if (StringUtils.isNotBlank(documentRequest.getWatermark())) {
				return applyWatermark(byteArrayOutputStream.toByteArray(), documentRequest.getWatermark());
			} else {
				return byteArrayOutputStream.toByteArray();
			}
		} catch (Exception e) {
			System.out.println("Error occurred : " + e);
			throw new DocumentCreatorException("Error while creating Pdf!");
		}
	}

	public static void writePdfDocumentToDisk(DocumentRequest documentRequest, String fileName) {
		if (Objects.nonNull(documentRequest)) {
			byte[] pdfFileContent = getPdfDocument(documentRequest);
			if (StringUtils.isNotBlank(fileName)) {
				FileUtil.writeDocumentToDisk(pdfFileContent, fileName);
			} else {
				throw new DocumentCreatorException("File name cannot be blank.");
			}
		} else {
			throw new DocumentCreatorException("Document request cannot be null.");
		}
	}

	private static byte[] applyWatermark(byte[] source, String watermarkValue) throws IOException, DocumentException {
		PdfReader reader = new PdfReader(source);
		int totalPages = reader.getNumberOfPages();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PdfStamper stamper = new PdfStamper(reader, byteArrayOutputStream);
		Font font = new Font(FontFamily.HELVETICA, 80);
		Phrase phrase = new Phrase(watermarkValue, font);
		for (int i = 1; i <= totalPages; i++) {
			PdfContentByte over = stamper.getOverContent(i);
			over.saveState();
			PdfGState pdfGStae = new PdfGState();
			pdfGStae.setFillOpacity(0.1F);
			over.setGState(pdfGStae);
			ColumnText.showTextAligned(over, Element.ALIGN_CENTER, phrase, 297, 450, 45);
			over.restoreState();
		}
		stamper.close();
		reader.close();
		return byteArrayOutputStream.toByteArray();
	}
}
