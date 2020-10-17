package com.technorizen.doctor.models;

import java.io.Serializable;

public class ModelLogin implements Serializable {

    String status;
    String message;
    Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public class Result
    {
        private String id;

        private String first_name;

        private String last_name;

        private String user_name;

        private String mobile;

        private String email;

        private String password;

        private String image;

        private String lat;

        private String lon;

        private String social_id;

        private String ios_register_id;

        private String date_time;

        private String type;

        private String dob;

        private String address;

        private String gender;

        private String register_id;

        private String status;

        private String otp;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setFirst_name(String first_name){
            this.first_name = first_name;
        }
        public String getFirst_name(){
            return this.first_name;
        }
        public void setLast_name(String last_name){
            this.last_name = last_name;
        }
        public String getLast_name(){
            return this.last_name;
        }
        public void setUser_name(String user_name){
            this.user_name = user_name;
        }
        public String getUser_name(){
            return this.user_name;
        }
        public void setMobile(String mobile){
            this.mobile = mobile;
        }
        public String getMobile(){
            return this.mobile;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setPassword(String password){
            this.password = password;
        }
        public String getPassword(){
            return this.password;
        }
        public void setImage(String image){
            this.image = image;
        }
        public String getImage(){
            return this.image;
        }
        public void setLat(String lat){
            this.lat = lat;
        }
        public String getLat(){
            return this.lat;
        }
        public void setLon(String lon){
            this.lon = lon;
        }
        public String getLon(){
            return this.lon;
        }
        public void setSocial_id(String social_id){
            this.social_id = social_id;
        }
        public String getSocial_id(){
            return this.social_id;
        }
        public void setIos_register_id(String ios_register_id){
            this.ios_register_id = ios_register_id;
        }
        public String getIos_register_id(){
            return this.ios_register_id;
        }
        public void setDate_time(String date_time){
            this.date_time = date_time;
        }
        public String getDate_time(){
            return this.date_time;
        }
        public void setType(String type){
            this.type = type;
        }
        public String getType(){
            return this.type;
        }
        public void setDob(String dob){
            this.dob = dob;
        }
        public String getDob(){
            return this.dob;
        }
        public void setAddress(String address){
            this.address = address;
        }
        public String getAddress(){
            return this.address;
        }
        public void setGender(String gender){
            this.gender = gender;
        }
        public String getGender(){
            return this.gender;
        }
        public void setRegister_id(String register_id){
            this.register_id = register_id;
        }
        public String getRegister_id(){
            return this.register_id;
        }
        public void setStatus(String status){
            this.status = status;
        }
        public String getStatus(){
            return this.status;
        }
        public void setOtp(String otp){
            this.otp = otp;
        }
        public String getOtp(){
            return this.otp;
        }
    }

}
