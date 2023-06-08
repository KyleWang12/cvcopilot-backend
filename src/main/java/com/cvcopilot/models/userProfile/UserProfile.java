package com.cvcopilot.models.userProfile;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user_profiles")
public class UserProfile {
    @Id
    private Long id;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private List<Link> links;
    private List<Skill> skills;
    private List<Project> projects;
    private List<WorkExperience> workExperiences;
    private List<Education> educations;

    public UserProfile() {
        this.links = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.workExperiences = new ArrayList<>();
        this.educations = new ArrayList<>();
    }

    public UserProfile(Long id, String firstname, String lastname, String phone, String address, List<Link> links, List<Skill> skills, List<Project> projects, List<WorkExperience> workExperiences, List<Education> educations) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.links = links;
        this.skills = skills;
        this.projects = projects;
        this.workExperiences = workExperiences;
        this.educations = educations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + id +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            ", links=" + links +
            ", skills=" + skills +
            ", projects=" + projects +
            ", workExperiences=" + workExperiences +
            ", educations=" + educations +
            '}';
    }
}

class Link {
    private String name;
    private String url;

    public Link() {
    }

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

class Education {
    private String school;
    private String degree;
    private String start;
    private String end;

    public Education() {
    }

    public Education(String school, String degree, String start, String end) {
        this.school = school;
        this.degree = degree;
        this.start = start;
        this.end = end;
    }

    public String getSchool() {
        return school;
    }

    public String getDegree() {
        return degree;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}

class Skill {
    private String category;
    private List<String> items;

    public Skill() {
        this.items = new ArrayList<>();
    }

    public Skill(String category, List<String> items) {
        this.category = category;
        this.items = items;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getItems() {
        return items;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}

class WorkExperience {
    private String company;
    private String title;
    private String start;
    private String end;
    private String description;

    public WorkExperience() {
    }

    public WorkExperience(String company, String title, String start, String end, String description) {
        this.company = company;
        this.title = title;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

class Project {
    private String title;
    private List<String> techStack;
    private String startDate;
    private String endDate;
    private String description;
    private String link;

    public Project() {
        this.techStack = new ArrayList<>();
    }

    public Project(String title, List<String> techStack, String startDate, String endDate, String description, String link) {
        this.title = title;
        this.techStack = techStack;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getTechStack() {
        return techStack;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTechStack(List<String> techStack) {
        this.techStack = techStack;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLink(String link) {
        this.link = link;
    }
}