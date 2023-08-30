package com.work.info.data;

public class WorkInformation {
	
	private String nameWork;
	private String experience;
	private String education;
	
	
	public WorkInformation(String nameWork, String experience, String education) {
		this.nameWork = nameWork;
		this.experience = experience;
		this.education = education;
		
	}

    public String getNameWork() {
		return nameWork;
	}
	public void setNameWork(String nameWork) {
		this.nameWork = nameWork;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}


}
