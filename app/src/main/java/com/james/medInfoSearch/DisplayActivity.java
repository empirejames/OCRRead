package com.james.medInfoSearch;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.james.textocr.R;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {
    private ArrayList <String> getMedData = new ArrayList<String>();
    private ArrayList<Property> medcianProperties = new ArrayList<>();
    private static final String TAG = DisplayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        Bundle buldle = intent.getExtras();
        getMedData = buldle.getStringArrayList("arrayList");
        medcianProperties.add(new Property(getMedData.get(0),getMedData.get(1),getMedData.get(2),getMedData.get(3),getMedData.get(4),getMedData.get(5),getMedData.get(6),getMedData.get(7),getMedData.get(8),getMedData.get(9),getMedData.get(10),getMedData.get(11),getMedData.get(12),getMedData.get(13),getMedData.get(14),getMedData.get(15),getMedData.get(16),getMedData.get(17),getMedData.get(18),getMedData.get(19)));
        ArrayAdapter<Property> adapter = new propertyAdapter(this, 0, medcianProperties);
        ListView listView = (ListView) findViewById(R.id.customListView);
        listView.setAdapter(adapter);
        Log.e(TAG,"getMedData : " + getMedData );
    }

}
