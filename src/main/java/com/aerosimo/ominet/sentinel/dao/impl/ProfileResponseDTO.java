/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ProfileResponseDTO.java                                                 *
 * Created:   26/09/2025, 12:38                                               *
 * Modified:  26/09/2025, 12:38                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.sentinel.dao.impl;

public class ProfileResponseDTO {

    private String username;
    private String email;
    private String maritalStatus;
    private String height;
    private String weight;
    private String ethnicity;
    private String religion;
    private String eyeColour;
    private String phenotype;
    private String genotype;
    private String disability;
    private String modifiedBy;
    private String modifiedDate;

    public ProfileResponseDTO() {
    }

    public ProfileResponseDTO(String username, String email, String maritalStatus, String height, String weight, String ethnicity, String religion, String eyeColour, String phenotype, String genotype, String disability, String modifiedBy, String modifiedDate) {
        this.username = username;
        this.email = email;
        this.maritalStatus = maritalStatus;
        this.height = height;
        this.weight = weight;
        this.ethnicity = ethnicity;
        this.religion = religion;
        this.eyeColour = eyeColour;
        this.phenotype = phenotype;
        this.genotype = genotype;
        this.disability = disability;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getEyeColour() {
        return eyeColour;
    }

    public void setEyeColour(String eyeColour) {
        this.eyeColour = eyeColour;
    }

    public String getPhenotype() {
        return phenotype;
    }

    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "ProfileResponseDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", religion='" + religion + '\'' +
                ", eyeColour='" + eyeColour + '\'' +
                ", phenotype='" + phenotype + '\'' +
                ", genotype='" + genotype + '\'' +
                ", disability='" + disability + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedDate='" + modifiedDate + '\'' +
                '}';
    }
}