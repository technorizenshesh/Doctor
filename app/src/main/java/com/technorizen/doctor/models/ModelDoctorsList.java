package com.technorizen.doctor.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelDoctorsList implements Serializable {

    private ArrayList<Result> result;

    private String message;

    private String status;

    public void setResult(ArrayList<Result> result){
        this.result = result;
    }
    public ArrayList<Result> getResult(){
        return this.result;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }

    public class Result implements Serializable
    {
        private String id;

        private String first_name;

        private String last_name;

        private String user_name;

        private String mobile;

        private String email;

        private String password;

        private String image;

        private String otp;

        private String age;

        private String height;

        private String weight;

        private String blood_pressure_systonic;

        private String blood_pressure_diastolic;

        private String how_do_you_feel;

        private String blood_sugar_reading;

        private String blood_sugar_meal;

        private String blood_sugar_take_reading;

        private String date;

        private String time;

        private String gender;

        private String status;

        private String lat;

        private String lon;

        private String social_id;

        private String date_time;

        private String ios_register_id;

        private String register_id;

        private String type;

        private String dob;

        private String address;

        private String diabetes;

        private String diabetes_type;

        private String diagonosed_year;

        private String medication;

        private String foot_problem;

        private String numbness;

        private String diabetic_eye;

        private String associated_medication;

        private String hypertension_blood_pressure;

        private String service_id;

        private String category_id;

        private String year_of_experience;

        private String fees;

        private String qualification;

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
        public void setOtp(String otp){
            this.otp = otp;
        }
        public String getOtp(){
            return this.otp;
        }
        public void setAge(String age){
            this.age = age;
        }
        public String getAge(){
            return this.age;
        }
        public void setHeight(String height){
            this.height = height;
        }
        public String getHeight(){
            return this.height;
        }
        public void setWeight(String weight){
            this.weight = weight;
        }
        public String getWeight(){
            return this.weight;
        }
        public void setBlood_pressure_systonic(String blood_pressure_systonic){
            this.blood_pressure_systonic = blood_pressure_systonic;
        }
        public String getBlood_pressure_systonic(){
            return this.blood_pressure_systonic;
        }
        public void setBlood_pressure_diastolic(String blood_pressure_diastolic){
            this.blood_pressure_diastolic = blood_pressure_diastolic;
        }
        public String getBlood_pressure_diastolic(){
            return this.blood_pressure_diastolic;
        }
        public void setHow_do_you_feel(String how_do_you_feel){
            this.how_do_you_feel = how_do_you_feel;
        }
        public String getHow_do_you_feel(){
            return this.how_do_you_feel;
        }
        public void setBlood_sugar_reading(String blood_sugar_reading){
            this.blood_sugar_reading = blood_sugar_reading;
        }
        public String getBlood_sugar_reading(){
            return this.blood_sugar_reading;
        }
        public void setBlood_sugar_meal(String blood_sugar_meal){
            this.blood_sugar_meal = blood_sugar_meal;
        }
        public String getBlood_sugar_meal(){
            return this.blood_sugar_meal;
        }
        public void setBlood_sugar_take_reading(String blood_sugar_take_reading){
            this.blood_sugar_take_reading = blood_sugar_take_reading;
        }
        public String getBlood_sugar_take_reading(){
            return this.blood_sugar_take_reading;
        }
        public void setDate(String date){
            this.date = date;
        }
        public String getDate(){
            return this.date;
        }
        public void setTime(String time){
            this.time = time;
        }
        public String getTime(){
            return this.time;
        }
        public void setGender(String gender){
            this.gender = gender;
        }
        public String getGender(){
            return this.gender;
        }
        public void setStatus(String status){
            this.status = status;
        }
        public String getStatus(){
            return this.status;
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
        public void setDate_time(String date_time){
            this.date_time = date_time;
        }
        public String getDate_time(){
            return this.date_time;
        }
        public void setIos_register_id(String ios_register_id){
            this.ios_register_id = ios_register_id;
        }
        public String getIos_register_id(){
            return this.ios_register_id;
        }
        public void setRegister_id(String register_id){
            this.register_id = register_id;
        }
        public String getRegister_id(){
            return this.register_id;
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
        public void setDiabetes(String diabetes){
            this.diabetes = diabetes;
        }
        public String getDiabetes(){
            return this.diabetes;
        }
        public void setDiabetes_type(String diabetes_type){
            this.diabetes_type = diabetes_type;
        }
        public String getDiabetes_type(){
            return this.diabetes_type;
        }
        public void setDiagonosed_year(String diagonosed_year){
            this.diagonosed_year = diagonosed_year;
        }
        public String getDiagonosed_year(){
            return this.diagonosed_year;
        }
        public void setMedication(String medication){
            this.medication = medication;
        }
        public String getMedication(){
            return this.medication;
        }
        public void setFoot_problem(String foot_problem){
            this.foot_problem = foot_problem;
        }
        public String getFoot_problem(){
            return this.foot_problem;
        }
        public void setNumbness(String numbness){
            this.numbness = numbness;
        }
        public String getNumbness(){
            return this.numbness;
        }
        public void setDiabetic_eye(String diabetic_eye){
            this.diabetic_eye = diabetic_eye;
        }
        public String getDiabetic_eye(){
            return this.diabetic_eye;
        }
        public void setAssociated_medication(String associated_medication){
            this.associated_medication = associated_medication;
        }
        public String getAssociated_medication(){
            return this.associated_medication;
        }
        public void setHypertension_blood_pressure(String hypertension_blood_pressure){
            this.hypertension_blood_pressure = hypertension_blood_pressure;
        }
        public String getHypertension_blood_pressure(){
            return this.hypertension_blood_pressure;
        }
        public void setService_id(String service_id){
            this.service_id = service_id;
        }
        public String getService_id(){
            return this.service_id;
        }
        public void setCategory_id(String category_id){
            this.category_id = category_id;
        }
        public String getCategory_id(){
            return this.category_id;
        }
        public void setYear_of_experience(String year_of_experience){
            this.year_of_experience = year_of_experience;
        }
        public String getYear_of_experience(){
            return this.year_of_experience;
        }
        public void setFees(String fees){
            this.fees = fees;
        }
        public String getFees(){
            return this.fees;
        }
        public void setQualification(String qualification){
            this.qualification = qualification;
        }
        public String getQualification(){
            return this.qualification;
        }
    }

}
