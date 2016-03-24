package com.huxi.progresshud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.huxi.tools.ProgressHUD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onShowSuccessClick(View view) {
        ProgressHUD.showSuccess(this,"Congratulations!!", Toast.LENGTH_SHORT);
    }

    public void onShowCustomAnimation(View view) {
        ProgressHUD.show(this,"wait!!",R.drawable.progress_hud_animation);
    }

    public void onShowErrorClick(View view) {
        ProgressHUD.showError(this,"I am so sorry.",Toast.LENGTH_LONG);
    }

    public void onShowDefaultProgressClick(View view) {
        ProgressHUD.show(this,"wait a moment");

    }
}
