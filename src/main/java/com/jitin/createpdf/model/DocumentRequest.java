package com.jitin.createpdf.model;

public class DocumentRequest<T> {
	private String templateDirectory;
	private String templateName;
	private String context;
	private T data;
	private String cssFileName;
	private String watermark;

	private DocumentRequest(String templateDirectory, String templateName, String context, T data, String cssFileName,
			String watermark) {
		this.templateDirectory = templateDirectory;
		this.templateName = templateName;
		this.context = context;
		this.data = data;
		this.cssFileName = cssFileName;
		this.watermark = watermark;
	}

	public String getTemplateDirectory() {
		return templateDirectory;
	}

	public String getTemplateName() {
		return templateName;
	}

	public String getContext() {
		return context;
	}

	public T getData() {
		return data;
	}

	public String getCssFileName() {
		return cssFileName;
	}

	public String getWatermark() {
		return watermark;
	}

	public static class DocumentRequestBuilder<T> {
		private String templateDirectory;
		private String templateName;
		private String context;
		private T data;
		private String cssFileName;
		private String watermark;

		public DocumentRequestBuilder(String templateDirectory, String templateName, String context, T data) {
			this.templateDirectory = templateDirectory;
			this.templateName = templateName;
			this.context = context;
			this.data = data;
		}

		public DocumentRequestBuilder cssFileName(String cssFileName) {
			this.cssFileName = cssFileName;
			return this;
		}

		public DocumentRequestBuilder watermark(String watermark) {
			this.watermark = watermark;
			return this;
		}

		public DocumentRequest build() {
			return new DocumentRequest(templateDirectory, templateName, context, data, cssFileName, watermark);
		}

	}
}
