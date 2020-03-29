package com.jitin.createpdf;

import java.util.Date;

public class Student {
	private Integer rollNumber;
	private String name;
	private Date dateOfBirth;
	private Double percentage;
	private Boolean isPass;

	public Student() {

	}

	public Student(Integer rollNumber, String name, Date dateOfBirth, Double percentage, Boolean isPass) {
		this.rollNumber = rollNumber;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.percentage = percentage;
		this.isPass = isPass;
	}

	public Integer getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(Integer rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}
}
