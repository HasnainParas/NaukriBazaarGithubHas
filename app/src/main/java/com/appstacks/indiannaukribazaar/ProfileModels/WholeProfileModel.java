package com.appstacks.indiannaukribazaar.ProfileModels;

import com.appstacks.indiannaukribazaar.profile.Education.Education;
import com.appstacks.indiannaukribazaar.profile.Lanuages.SelectedLanguages;
import com.appstacks.indiannaukribazaar.profile.appreciation.Appreciation;
import com.appstacks.indiannaukribazaar.profile.resume.Resume;
import com.appstacks.indiannaukribazaar.profile.skills.Skills;

public class WholeProfileModel {
    private Appreciation appreciation;
    private Education education;
    private SelectedLanguages selectedLanguages;
    private Resume resume;
    private Skills skills;
    private AboutMeDescription aboutMeDescription;
    private AddWorkExperience addWorkExperience;
    private String HourlyCharges;
    private String UserImage;

    public WholeProfileModel(Appreciation appreciation, Education education, SelectedLanguages selectedLanguages, Resume resume, Skills skills, AboutMeDescription aboutMeDescription, AddWorkExperience addWorkExperience, String hourlyCharges, String userImage) {
        this.appreciation = appreciation;
        this.education = education;
        this.selectedLanguages = selectedLanguages;
        this.resume = resume;
        this.skills = skills;
        this.aboutMeDescription = aboutMeDescription;
        this.addWorkExperience = addWorkExperience;
        HourlyCharges = hourlyCharges;
        UserImage = userImage;
    }

    public WholeProfileModel() {
    }


    public Appreciation getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(Appreciation appreciation) {
        this.appreciation = appreciation;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public SelectedLanguages getSelectedLanguages() {
        return selectedLanguages;
    }

    public void setSelectedLanguages(SelectedLanguages selectedLanguages) {
        this.selectedLanguages = selectedLanguages;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public AboutMeDescription getAboutMeDescription() {
        return aboutMeDescription;
    }

    public void setAboutMeDescription(AboutMeDescription aboutMeDescription) {
        this.aboutMeDescription = aboutMeDescription;
    }

    public AddWorkExperience getAddWorkExperience() {
        return addWorkExperience;
    }

    public void setAddWorkExperience(AddWorkExperience addWorkExperience) {
        this.addWorkExperience = addWorkExperience;
    }

    public String getHourlyCharges() {
        return HourlyCharges;
    }

    public void setHourlyCharges(String hourlyCharges) {
        HourlyCharges = hourlyCharges;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }
}
