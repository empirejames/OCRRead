package com.james.medInfoSearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.james.textocr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 101716 on 2017/8/11.
 */

public class propertyAdapter extends ArrayAdapter<Property> {
    private String TAG = propertyAdapter.class.getSimpleName();
    private Context context;
    private List<Property> medicianProperties;
    private Property medProperty;
    private TextView medcian, description, tvAdapter, tvEffect, tvFrequency, tvIndiaction,avoidText, tvDosage, tvBathroom,HealthDpt, healthDpLimitText, accrossActionText,adjustText,maxText,ATCClassText;
    private Button btn_back;
    public propertyAdapter(Context context, int resource, ArrayList<Property> medicianProperties) {
        super(context, resource, medicianProperties);
        this.context = context;
        this.medicianProperties = medicianProperties;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        medProperty = medicianProperties.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.property_layout, null);
        initView(view);
        btn_back = (Button) view.findViewById(R.id.btnReturn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
            }
        });
        return view;

    }

    public void initView(View view) {
        medcian = (TextView) view.findViewById(R.id.medcian);
        medcian.setText(medProperty.getMedChiName());
        description = (TextView) view.findViewById(R.id.description);
        description.setText(medProperty.getMedEngName());
        tvAdapter = (TextView) view.findViewById(R.id.tvAdapter);
        tvAdapter.setText(medProperty.getIndications());
        tvEffect = (TextView) view.findViewById(R.id.tvEffect);
        tvEffect.setText(medProperty.getSideEffect());
        tvFrequency = (TextView) view.findViewById(R.id.tvFrequency);
        tvFrequency.setText(medProperty.getFrequences());
        avoidText = (TextView) view.findViewById(R.id.avoidText);
        avoidText.setText(medProperty.getAttention());
        tvIndiaction = (TextView) view.findViewById(R.id.tvIndiaction);
        tvIndiaction.setText(medProperty.getIngredient());
        tvDosage = (TextView) view.findViewById(R.id.tvDosage);
        tvDosage.setText(medProperty.getDosageForm());
        tvBathroom = (TextView) view.findViewById(R.id.tvBathroom);
        tvBathroom.setText(medProperty.getGenBaoNum());
        HealthDpt = (TextView) view.findViewById(R.id.healthDpText);
        HealthDpt.setText(medProperty.getHealthDpt());
        healthDpLimitText = (TextView) view.findViewById(R.id.healthDpLimitText);
        healthDpLimitText.setText(medProperty.getInjectionLimit());
        accrossActionText= (TextView) view.findViewById(R.id.accrossActionText);
        accrossActionText.setText(medProperty.getInteraction());
        adjustText = (TextView) view.findViewById(R.id.adjustText);
        adjustText.setText(medProperty.getDosageAdjust());
        maxText = (TextView) view.findViewById(R.id.maxText);
        maxText.setText(medProperty.getMaxDosage());
        ATCClassText = (TextView) view.findViewById(R.id.ATCClassText);
        ATCClassText.setText(medProperty.getATC());
    }
}
