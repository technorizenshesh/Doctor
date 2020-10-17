package com.technorizen.doctor.Dailog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.technorizen.doctor.R;
import com.utils.Utils.Tools;


public class BloodPresureReportDialog extends Dialog {
    private TextView tv_blood_pressure, tv_systolic, tv_diastolic;
    private int SIS, DIA;
    private CardView card;

    public BloodPresureReportDialog(Context context) {
        super(context);
    }

    public static BloodPresureReportDialog get(Context context) {
        return new BloodPresureReportDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.get().updateResources(getContext());
        setContentView(R.layout.layout_blood_presure_report_dialog);
        TextView tv_ok = findViewById(R.id.tv_ok);
        tv_blood_pressure = findViewById(R.id.tv_blood_pressure);
        tv_systolic = findViewById(R.id.tv_systolic);
        tv_diastolic = findViewById(R.id.tv_diastolic);
        card = findViewById(R.id.card);
        tv_systolic.setText("" + SIS);
        tv_diastolic.setText("" + DIA);
        tv_blood_pressure.setText(CalculatePressure());
        tv_ok.setOnClickListener(v -> {
            dismiss();
        });
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    public BloodPresureReportDialog setData(int sis, int dia) {
        this.SIS = sis;
        this.DIA = dia;
        return this;
    }

    private String CalculatePressure() {
        String pressure = "NORMAL";
        if (SIS > 180 || DIA > 120) {
            pressure = "HYPERTENSION CRISIS (consult your doctor immediately.)";
            card.setCardBackgroundColor(getContext().getResources().getColor(R.color.high_stage3));
            return pressure;
        }
        if ((SIS > 140 && SIS < 180) || (DIA > 90 && DIA < 120)) {
            pressure = "HIGH BLOOD PRESSURE (HYPERTENSION) STAGE 2";
            card.setCardBackgroundColor(getContext().getResources().getColor(R.color.high_stage2));
            return pressure;
        }
        if ((SIS > 130 && SIS < 139) && (DIA > 80 && DIA < 89)) {
            pressure = "HIGH BLOOD PRESSURE (HYPERTENSION) STAGE 1";
            card.setCardBackgroundColor(getContext().getResources().getColor(R.color.high_stage1));
            return pressure;
        }
        if ((SIS > 120 && SIS < 129) && DIA < 80) {
            pressure = "ELEVATED";
            card.setCardBackgroundColor(getContext().getResources().getColor(R.color.elevated));
            return pressure;
        }
        if (SIS < 120 && DIA < 80) {
            pressure = "NORMAL";
            card.setCardBackgroundColor(getContext().getResources().getColor(R.color.normal));
            return pressure;
        }if (SIS <= 110) {
            pressure = "LOW";
            card.setCardBackgroundColor(getContext().getResources().getColor(R.color.elevated));
            return pressure;
        }
        return pressure;
    }

}
