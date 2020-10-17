package com.technorizen.doctor.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ModelClinic implements Serializable {

    private String message;
    private String status;
    private ArrayList<Result> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result
    {
        private String id;

        private String name;

        private String doctor_id;

        private String address;

        private String open_time;

        private String close_time;

        private String lat;

        private String lon;

        private String date_time;

        private String doctor_name;

        private String image;

        public void setId(String id){
            this.id = id;
        }
        public String getId(){
            return this.id;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return this.name;
        }
        public void setDoctor_id(String doctor_id){
            this.doctor_id = doctor_id;
        }
        public String getDoctor_id(){
            return this.doctor_id;
        }
        public void setAddress(String address){
            this.address = address;
        }
        public String getAddress(){
            return this.address;
        }
        public void setOpen_time(String open_time){
            this.open_time = open_time;
        }
        public String getOpen_time(){
            return this.open_time;
        }
        public void setClose_time(String close_time){
            this.close_time = close_time;
        }
        public String getClose_time(){
            return this.close_time;
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
        public void setDate_time(String date_time){
            this.date_time = date_time;
        }
        public String getDate_time(){
            return this.date_time;
        }
        public void setDoctor_name(String doctor_name){
            this.doctor_name = doctor_name;
        }
        public String getDoctor_name(){
            return this.doctor_name;
        }
        public void setImage(String image){
            this.image = image;
        }
        public String getImage(){
            return this.image;
        }
    }
}
