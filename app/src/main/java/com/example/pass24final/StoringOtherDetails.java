package com.example.pass24final;

public class StoringOtherDetails {

    String gender,address,age,occupation,education,catagory;

    public StoringOtherDetails(){

    }

    public StoringOtherDetails(String gender, String address, String age, String occupation, String education, String catagory) {
        this.gender = gender;
        this.address = address;
        this.age = age;
        this.occupation = occupation;
        this.education = education;
        this.catagory = catagory;
    }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getAge() { return age; }

    public void setAge(String age) { this.age = age; }

    public String getOccupation() { return occupation; }

    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getEducation() { return education; }

    public void setEducation(String education) { this.education = education; }

    public String getCatagory() { return catagory; }

    public void setCatagory(String catagory) { this.catagory = catagory; }
}
