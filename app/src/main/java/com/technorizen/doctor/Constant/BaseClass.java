package com.technorizen.doctor.Constant;

public class BaseClass {
    public static BaseClass get() {
        return new BaseClass();
    }

    public String BaseUrl = "http://doctor-cars.com/health/webservice/";

    public String signUp() {
        return BaseUrl.concat("signup_register_update");
    }

    public String Login() {
        return BaseUrl.concat("login");
    }

    public String getProfile() {
        return BaseUrl.concat("get_profile");
    }

    public String clinicListByDoctor() {
        return BaseUrl.concat("clinicList_by_doctor");
    }

    public String getShop() {
        return BaseUrl.concat("get_shop");
    }

    public String getProduct() {
        return BaseUrl.concat("get_product");
    }

    public String addReview() {
        return BaseUrl.concat("add_review");
    }

    public String getBanner() {
        return BaseUrl.concat("get_banner");
    }

    public String getCategory() {
        return BaseUrl.concat("get_category");
    }

    public String getServices() {
        return BaseUrl.concat("get_services");
    }

    public String addToCart() {
        return BaseUrl.concat("add_to_cart");
    }

    public String deleteCartItem() {
        return BaseUrl.concat("delete_cart_item?cart_id=1");
    }

    public String productSearch() {
        return BaseUrl.concat("product_search?product_name=co");
    }

    public String cartList() {
        return BaseUrl.concat("cart_list");
    }

    public String addPlaceorder() {
        return BaseUrl.concat("add_placeorder");
    }

    public String bookAppointment() {
        return BaseUrl.concat("book_appointment");
    }

    public String AboutUs() {
        return "http://doctor-cars.com/health/webpage/about_us.html";
    }

    public String PrivacyPolicy() {
        return "http://doctor-cars.com/health/webpage/privacy_policy.html";
    }

    public String TermsCondition() {
        return "http://doctor-cars.com/health/webpage/terms_condition.html";
    }

    public String getChat() {
        return BaseUrl.concat("get_chat");
    }

    public String insertChat() {
        return BaseUrl.concat("insert_chat");
    }

    public String getConversion() {
        return BaseUrl.concat("get_conversation");
    }

    public String getSendOTP() {
        return BaseUrl.concat("send_otp");
    }

    public String checkOtp() {
        return BaseUrl.concat("check_otp");
    }

    public String resendOtp() {
        return BaseUrl.concat("resend_otp");
    }
    public String getAppointment() {
        return BaseUrl.concat("get_appointment");
    }
    public String updateAppointmentStatus() {
        return BaseUrl.concat("update_appointment_status");
    }
}
