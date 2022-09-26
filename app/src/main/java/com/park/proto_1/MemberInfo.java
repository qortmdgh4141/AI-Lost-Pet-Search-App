package com.park.proto_1;

public class MemberInfo {

    private String name;
    private String phone;
    private String birthday;
    private String address;
    private String photoUrl;

    public MemberInfo(String name, String phone, String birthday, String address, String photoUrl){
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public MemberInfo(String name, String phone, String birthday, String address){
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


}

