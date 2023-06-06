package com.example.qr_code_generator.Models;

public class DetailsModels {

    String NameInput;
    String OrganizationInput;
    String EmailInput;
    String PhoneInput;
    String WebsiteInput;

    public DetailsModels() {
    }

    public String getNameInput() {
        return NameInput;
    }

    public void setNameInput(String nameInput) {
        NameInput = nameInput;
    }

    public String getOrganizationInput() {
        return OrganizationInput;
    }

    public void setOrganizationInput(String organizationInput) {
        OrganizationInput = organizationInput;
    }

    public String getEmailInput() {
        return EmailInput;
    }

    public void setEmailInput(String emailInput) {
        EmailInput = emailInput;
    }

    public String getPhoneInput() {
        return PhoneInput;
    }

    public void setPhoneInput(String phoneInput) {
        PhoneInput = phoneInput;
    }

    public String getWebsiteInput() {
        return WebsiteInput;
    }

    public void setWebsiteInput(String websiteInput) {
        WebsiteInput = websiteInput;
    }

    @Override
    public String toString() {
        return "DetailsModels{" +
                "NameInput='" + NameInput + '\'' +
                ", OrganizationInput='" + OrganizationInput + '\'' +
                ", EmailInput='" + EmailInput + '\'' +
                ", PhoneInput='" + PhoneInput + '\'' +
                ", WebsiteInput='" + WebsiteInput + '\'' +
                '}';
    }
}
