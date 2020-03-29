package com.jitin.createpdf.templateprocessor;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.jitin.createpdf.exception.DocumentCreatorException;
import com.jitin.createpdf.model.DocumentRequest;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerTemplateProcessor {

	public static String getProcessedText(DocumentRequest documentRequest) {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_26);
		configuration.setDefaultEncoding(StandardCharsets.UTF_8.name());
		configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		configuration.setLogTemplateExceptions(false);
		if (Objects.nonNull(documentRequest)) {
			if (StringUtils.isNotBlank(documentRequest.getTemplateDirectory())) {
				processTemplateFromFile(configuration, documentRequest.getTemplateDirectory());
			} else {
				throw new DocumentCreatorException("Template directory cannot be blank.");
			}
		} else {
			throw new DocumentCreatorException("Document request cannot be null.");
		}
		String processedText = "";
		Template template = null;
		try {
			if (StringUtils.isNotBlank(documentRequest.getTemplateName())) {
				template = configuration.getTemplate(documentRequest.getTemplateName());
			} else {
				throw new DocumentCreatorException("Template name cannot be blank.");
			}
			if (Objects.nonNull(template) && StringUtils.isNotBlank(documentRequest.getContext())
					&& Objects.nonNull(documentRequest.getData())) {
				processedText = processTemplate(template, documentRequest.getContext(), documentRequest.getData());
			} else {
				throw new DocumentCreatorException("Object template or context or data was null.");
			}
		} catch (TemplateNotFoundException e) {
			System.out.println("Error occurred : " + e);
		} catch (MalformedTemplateNameException e) {
			System.out.println("Error occurred : " + e);
		} catch (ParseException e) {
			System.out.println("Error occurred : " + e);
		} catch (IOException e) {
			System.out.println("Error occurred : " + e);
		}
		return processedText;
	}

	private static void processTemplateFromFile(Configuration configuration, String templateDirectory) {
		try {
			configuration.setDirectoryForTemplateLoading(new File(templateDirectory + "/"));
		} catch (IOException e) {
			System.out.println("Error occurred while processing template from file : " + e);
		}
	}

	private static String processTemplate(Template template, String contextName, Object data) {
		String processedText = "";
		try {
			Map<String, Object> root = new HashMap<String, Object>();
			root.put(contextName, data);
			Writer writer = new StringWriter();
			try {
				template.process(root, writer);
				processedText = processTemplateIntoString(template, root);
			} catch (TemplateException e) {
				System.out.println("Error occurred : " + e);
			}
		} catch (IOException e) {
			System.out.println("Error occurred : " + e);
		}
		return processedText;
	}

	private static String processTemplateIntoString(Template template, Object model)
			throws IOException, TemplateException {
		StringWriter result = new StringWriter();
		template.process(model, result);
		return result.toString();
	}
}
