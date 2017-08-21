package com.james.medInfoSearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.james.textocr.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 101716 on 2017/8/11.
 */

public class propertyAdapter extends ArrayAdapter<Property> {
    private String TAG = propertyAdapter.class.getSimpleName();
    private Context context;
    private List<Property> medicianProperties;
    private ImageView imgViewCamera;
    private Property medProperty;
    private TextView medcian, description, tvAdapter, tvEffect, tvFrequency, tvIndiaction, avoidText, tvDosage, tvBathroom, HealthDpt, healthDpLimitText, accrossActionText, adjustText, maxText, ATCClassText;
    private Button btn_back;
    private View view;
    float scaleWidth;
    float scaleHeight;
    double img_Width;
    double img_Height;
    int w;
    int h;
    boolean fullScrean = true;
    private Bitmap bitmap;

    public propertyAdapter(Context context, int resource, ArrayList<Property> medicianProperties) {
        super(context, resource, medicianProperties);
        this.context = context;
        this.medicianProperties = medicianProperties;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        medProperty = medicianProperties.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.property_layout, null);
        initView(view);
        //Log.e(TAG, "" + "http://web-reg-server.803.org.tw:8090/image_med/" + medProperty.getImageNumber() + ".jpg");
        new getPicURL().execute(("http://web-reg-server.803.org.tw:8090/image_med/" + medProperty.getImageNumber() + ".jpg"));
        btn_back = (Button) view.findViewById(R.id.btnReturn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });
        return view;

    }

    private void myimageviewsize(ImageView imgid, int evenWidth, int evenHight) {
        ViewGroup.LayoutParams params = imgid.getLayoutParams();
        params.width = evenWidth;
        params.height = evenHight;
        imgid.setLayoutParams(params);
    }

    public void initView(View view) {

        imgViewCamera = (ImageView) view.findViewById(R.id.img_med);
        imgViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int screen_h;
                int screen_w;

                if (fullScrean){
                    Animation am = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
                    am.setDuration(500);
                    imgViewCamera.startAnimation(am);
                    changeSize(w,h);
                    fullScrean = false;
                }else{
                    screen_h = (int)img_Height;
                    screen_w = (int)img_Width;
                    changeSize(screen_w ,screen_h);
                    fullScrean = true;
                }
            }
        });
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
        accrossActionText = (TextView) view.findViewById(R.id.accrossActionText);
        accrossActionText.setText(medProperty.getInteraction());
        adjustText = (TextView) view.findViewById(R.id.adjustText);
        adjustText.setText(medProperty.getDosageAdjust());
        maxText = (TextView) view.findViewById(R.id.maxText);
        maxText.setText(medProperty.getMaxDosage());
        ATCClassText = (TextView) view.findViewById(R.id.ATCClassText);
        ATCClassText.setText(medProperty.getATC());
    }
    public void changeSize(int w, int h){
        Log.e(TAG, bitmap.getWidth() + " : " + bitmap.getHeight());
        Log.e(TAG,"getDensity + " + getDensity(context));
        myimageviewsize(imgViewCamera, w, h);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        imgViewCamera.setImageBitmap(newBitmap);
    }
    public static float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    private class getPicURL extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            return getBitmapFromURL(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imgViewCamera.setImageBitmap(result);
            int width = result.getWidth();
            int height = result.getHeight();
            w = getDisplay()[0];
            h = getDisplay()[1];
            scaleWidth = ((float) w) / width;
            scaleHeight = ((float) h) / height;
            img_Height = imgViewCamera.getHeight();
            img_Width = imgViewCamera.getWidth();
            Log.e(TAG, " getDisplay: " + w + " X " + h);
        }

    }

    public int[] getDisplay() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        return new int[]{width, height};
    }

    private Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }
}
