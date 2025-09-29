/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      SilhouetteResponseDTO.java                                      *
 * Created:   26/09/2025, 12:18                                               *
 * Modified:  26/09/2025, 12:18                                               *
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

import java.util.List;

public class SilhouetteResponseDTO {

    private PersonResponseDTO person;
    private ImageResponseDTO image;
    private AddressResponseDTO address;
    private List<ContactResponseDTO> contacts;
    private ProfileResponseDTO profile;
    private HoroscopeResponseDTO horoscope;

    public SilhouetteResponseDTO() {
    }

    public SilhouetteResponseDTO(PersonResponseDTO person, ImageResponseDTO image, AddressResponseDTO address, List<ContactResponseDTO> contacts, ProfileResponseDTO profile, HoroscopeResponseDTO horoscope) {
        this.person = person;
        this.image = image;
        this.address = address;
        this.contacts = contacts;
        this.profile = profile;
        this.horoscope = horoscope;
    }

    public PersonResponseDTO getPerson() {
        return person;
    }

    public void setPerson(PersonResponseDTO person) {
        this.person = person;
    }

    public ImageResponseDTO getImage() {
        return image;
    }

    public void setImage(ImageResponseDTO image) {
        this.image = image;
    }

    public AddressResponseDTO getAddress() {
        return address;
    }

    public void setAddress(AddressResponseDTO address) {
        this.address = address;
    }

    public List<ContactResponseDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactResponseDTO> contacts) {
        this.contacts = contacts;
    }

    public ProfileResponseDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileResponseDTO profile) {
        this.profile = profile;
    }

    public HoroscopeResponseDTO getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(HoroscopeResponseDTO horoscope) {
        this.horoscope = horoscope;
    }

    @Override
    public String toString() {
        return "SilhouetteResponseDTO{" +
                "person=" + person +
                ", image=" + image +
                ", address=" + address +
                ", contacts=" + contacts +
                ", profile=" + profile +
                ", horoscope=" + horoscope +
                '}';
    }
}