package com.jitin.createpdf;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.jitin.createpdf.documentcreator.PdfCreator;
import com.jitin.createpdf.model.DocumentRequest;
import com.jitin.createpdf.model.DocumentRequest.DocumentRequestBuilder;
import com.jitin.createpdf.util.Constants;

public class Driver {

	public static void main(String[] args) {
		Student student1 = new Student(1, "Kaushal Singh", new Date("01-Jul-1997"), 89.7, Boolean.TRUE);
		Student student2 = new Student(2, "Prakash Sinde", new Date("22-Sep-1996"), 31.4, Boolean.FALSE);
		Student student3 = new Student(3, "Gagan Sen", new Date("27-Mar-1998"), 76.4, Boolean.TRUE);
		Student student4 = new Student(4, "Vaishali Das", new Date("17-Jun-1997"), 81.6, Boolean.TRUE);
		Student student5 = new Student(5, "Navneet Singh", new Date("09-Nov-1996"), 68.4, Boolean.TRUE);

		List<Student> students = Arrays.asList(student1, student2, student3, student4, student5);

		DocumentRequest documentRequest = new DocumentRequestBuilder(Constants.FREEMARKER_TEMPLATE_DIRECTORY,
				"students-report.ftl", Constants.GENERIC_DATA_MAP_KEY, students)
						.cssFileName(Constants.CSS_FILES_DIRECTORY + "students-report-template.css").watermark("SAMPLE")
						.build();
		PdfCreator.writePdfDocumentToDisk(documentRequest, Constants.FILE_STORAGE_DIRECTORY+"Student-Report.pdf");
		System.out.println("File created!");
	}

}
