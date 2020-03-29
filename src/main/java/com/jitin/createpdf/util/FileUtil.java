package com.jitin.createpdf.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.jitin.createpdf.exception.DocumentCreatorException;

public class FileUtil {
	private FileUtil() {

	}

	public static byte[] fileInByteArray(String filePath) {
		byte[] fileContent = null;
		try {
			fileContent = Files.readAllBytes(Paths.get(filePath));
		} catch (IOException e) {
			System.out.println(e);
		}
		return fileContent;
	}

	public static String generateUniqueFileName(String filename) {
		if (StringUtils.isNotBlank(filename)) {
			String[] parts = filename.split("\\.");
			String fileExtension = parts[parts.length - 1];
			return new StringBuilder(parts[0]).append("_")
					.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())).append(".")
					.append(fileExtension).toString();
		} else {
			throw new DocumentCreatorException("Either file prefix of extension was blank.");
		}
	}

	public static void writeDocumentToDisk(byte[] fileContent, String fileName) {
		if (StringUtils.isNotBlank(fileName)) {
			String uniqueFileName = generateUniqueFileName(getFileNameOnly(fileName));
			String fileDirectory = getFileDirectory(fileName);
			String file = new StringBuilder(fileDirectory).append("\\").append(uniqueFileName).toString();
			try {
				Files.write(new File(file).toPath(), fileContent);
			} catch (IOException e) {
				System.out.println("Error occurred : " + e);
			}
		} else {
			throw new DocumentCreatorException("Filename cannot be blank.");
		}
	}

	private static String getFileNameOnly(String filePath) {
		if (StringUtils.isNotBlank(filePath)) {
			return Paths.get(filePath).getFileName().toString();
		}
		throw new DocumentCreatorException("File path cannot be blank.");
	}

	private static String getFileDirectory(String filePath) {
		if (StringUtils.isNotBlank(filePath)) {
			return Paths.get(filePath).getParent().toString();
		}
		throw new DocumentCreatorException("File path cannot be blank.");
	}
}
