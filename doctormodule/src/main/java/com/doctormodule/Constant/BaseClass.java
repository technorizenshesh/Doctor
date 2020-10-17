package com.doctormodule.Constant;

public class BaseClass {
    public static BaseClass get() {
        return new BaseClass();
    }

    public String BaseURL = "http://doctor-cars.com/health/webservice/";
    public String AboutUs() {
        return "http://doctor-cars.com/health/webpage/about_us.html";
    }

    public String PrivacyPolicy() {
        return "http://doctor-cars.com/health/webpage/privacy_policy.html";
    }

    public String TermsCondition() {
        return "http://doctor-cars.com/health/webpage/terms_condition.html";
    }
    public String degreeList() {
        return BaseURL.concat("degree_list");
    }

    public String categoryList() {
        return BaseURL.concat("category_list");
    }

    public String sendOTP() {
        return BaseURL.concat("doctor_send_otp");
    }

    public String resendOtp() {
        return BaseURL.concat("resend_otp");
    }

    public String CheckOtp() {
        return BaseURL.concat("doctor_check_otp");
    }
    public String docterSignup() {
        return BaseURL.concat("docter_signup");
    }
    public String getChat() {
        return BaseURL.concat("get_chat");
    }

    public String insertChat() {
        return BaseURL.concat("insert_chat");
    }

    public String getConversion() {
        return BaseURL.concat("get_conversation");
    }
    public String getDoctorProfile() {
        return BaseURL.concat("get_doctor_profile");
    }public String getAppointmentDoctor() {
        return BaseURL.concat("get_appointment_doctor");
    }public String updateAppointmentStatus() {
        return BaseURL.concat("update_appointment_status");
    }
}
