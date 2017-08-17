package com.james.medInfoSearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.james.textocr.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;

    private ImageView imgViewCamera;
    private EditText edTextMedInfo;
    private FirebaseAnalytics mFirebaseAnalytics;
    private DatabaseReference ref;
    private ArrayList<String> medData = new ArrayList<String>();
    private Bundle bundle = new Bundle();

    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";
    private String medNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        statusMessage = (TextView) findViewById(R.id.status_message);
        imgViewCamera = (ImageView) findViewById(R.id.iv_camera);
        edTextMedInfo = (EditText) findViewById(R.id.edTextMedInfo);
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        findViewById(R.id.read_text).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        medData.clear();
        if (v.getId() == R.id.read_text) {
            //new GetMedInfo().execute("Alprazolam");
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());
            startActivityForResult(intent, RC_OCR_CAPTURE);
        } else if (v.getId() == R.id.btn_read) {
            String parserMed;
            medNames = edTextMedInfo.getText().toString();
            parserMed = medNames.substring(0,1).toUpperCase() +medNames.substring(1);
            new GetMedInfo().execute(parserMed);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    statusMessage.setText(R.string.ocr_success);
                    edTextMedInfo.setText(text);
                    //new GetMedInfo().execute(text);
                } else {
                    statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class GetMedInfo extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(final String... params) {
            Log.e(TAG, "getParams : "  + params[0]);
            ref = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference usersRef = ref.child("medicine");
            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e(TAG, "onDataChange : " );
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot medNum : dataSnapshot.getChildren()) {
                            for (DataSnapshot medName : medNum.getChildren()) {
                                if (medName.getKey().contains((params[0]))) {
                                    for (DataSnapshot medNames : medName.child("arrayListName").getChildren()) {
                                        Log.e(TAG, "medNames: " + medNames.getValue());
                                        medData.add(medNames.getValue().toString());
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    goToDisplay();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

    public void goToDisplay() {
        bundle.putSerializable("arrayList", medData);
        if (medData.size()!=0) {
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "找不到藥品資訊，請盡量輸入完整名稱", Toast.LENGTH_SHORT).show();
        }


    }
}
