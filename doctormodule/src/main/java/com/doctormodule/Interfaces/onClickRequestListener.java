package com.doctormodule.Interfaces;

import com.doctormodule.Models.ModelRequest;

public interface onClickRequestListener {
    void onAccept(ModelRequest request);
    void onCancel(ModelRequest request);
    void onCall(ModelRequest request);
    void onChat(ModelRequest request);
    void onComplete(ModelRequest request);
}
