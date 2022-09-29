package com.park.proto_1;

public class MemberInfo {

    private String name;
    private String phone;
    private String birthday;
    private String address;
    private String photoUrl;
    private Integer point;
    public MemberInfo(String name, String phone, String birthday, String address, String photoUrl, Integer point){
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.address = address;
        this.photoUrl = photoUrl;
        this.point = point;
    }

    public MemberInfo(String name, String phone, String birthday, String address, int point){
        this.name = this.name;
        this.phone = this.phone;
        this.birthday = this.birthday;
        this.address = this.address;
        this.point = this.point;
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

    public Integer getPoint() {
        return this.point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

}

