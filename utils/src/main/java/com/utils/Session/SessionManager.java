package com.utils.Session;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionManager {
    private final SharedPreferences prf;
    private final SharedPreferences.Editor edit;
    Context context;

    public static SessionManager get(Context context) {
        return new SessionManager(context);
    }
    public SessionManager(Context context) {
        this.context = context;
        prf=context.getSharedPreferences(SessionKey.DB_Health.name(), Context.MODE_PRIVATE);
        edit=prf.edit();
    }
    public void setUserType(String type){
        edit.putString(SessionKey.user_type.name(),type);
        edit.commit();
    }
    public String getUserType(){
        return prf.getString(SessionKey.user_type.name(),"");
    }
    public void CreateSession(String result){
        edit.putBoolean(SessionKey.IsUserLogedIn.name(),true);
        edit.putString(SessionKey.UserProfile.name(),result);
        edit.commit();
    }
    public void CreateSession(String result,boolean b){
        edit.putBoolean(SessionKey.IsUserLogedIn.name(),b);
        edit.putString(SessionKey.UserProfile.name(),result);
        edit.commit();
    }
    public String getAllDetails(){
        return prf.getString(SessionKey.UserProfile.name(),"No Data");
    }
    public String getValue(SessionKey key){
        JSONObject jsonObject = null;
        String result="";
        try {
            jsonObject=new JSONObject(prf.getString(SessionKey.UserProfile.name(),""));
            result=jsonObject.getString(key.name());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String getUserID(){
        JSONObject jsonObject = null;
        String result="";
        try {
            jsonObject=new JSONObject(prf.getString(SessionKey.UserProfile.name(),""));
            result=jsonObject.getString(SessionKey.id.name());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    public String CheckSession(){
        if (prf.getBoolean(SessionKey.IsUserLogedIn.name(),false)){
           return getUserType();
        }else {
            return "Not Login";
        }
    }
    public void Logout(){
        edit.clear();
        edit.commit();
    }
    public void setLanguage(String lng){
        SharedPreferences lng_prf = context.getSharedPreferences(SessionKey.language.name(), Context.MODE_PRIVATE);
        SharedPreferences.Editor lng_edit = lng_prf.edit();
        lng_edit.putString(SessionKey.language.name(),lng);
        lng_edit.apply();
    }

    public String getSelectedLanguage(){
        SharedPreferences lng_prf = context.getSharedPreferences(SessionKey.language.name(), Context.MODE_PRIVATE);
        return lng_prf.getString(SessionKey.language.name(),"en");
    }
    public void setIsLogin(boolean b){
        edit.putBoolean(SessionKey.IsUserLogedIn.name(),b);
        edit.apply();
    }
    public boolean IsUserLogedIn(){
        return prf.getBoolean(SessionKey.IsUserLogedIn.name(),false);
    }
}

