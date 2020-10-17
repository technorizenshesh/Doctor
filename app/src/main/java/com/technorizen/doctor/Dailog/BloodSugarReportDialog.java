package com.technorizen.doctor.Dailog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.technorizen.doctor.R;
import com.utils.Utils.Tools;


public class BloodSugarReportDialog extends Dialog {
    private TextView tv_blood_sugar;
    private int SIS, DIA;
    private CardView card;

    public BloodSugarReportDialog(Context context) {
        super(context);
    }

    public static BloodSugarReportDialog get(Context context) {
        return new BloodSugarReportDialog(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.get().updateResources(getContext());
        setContentView(R.layout.layout_blood_sugar_report_dialog);
        TextView tv_ok = findViewById(R.id.tv_ok);
        tv_blood_sugar = findViewById(R.id.tv_blood_sugar);
        card = findViewById(R.id.card);
        tv_ok.setOnClickListener(v -> {
            dismiss();
        });
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    public BloodSugarReportDialog setData(int sis, int dia) {
        this.SIS = sis;
        this.DIA = dia;
        return this;
    }

    private String CalculateSugar() {
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
        }  
        return pressure;
    }

}
