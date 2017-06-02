package com.example.kaios.runcar2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Info extends Activity {

    // ---------------------------------------------------------------
    //bấm nút back
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//màn hình k tắt

        setContentView(R.layout.activity_info);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ImageView btn_ok =(ImageView)findViewById(R.id.ok_infor);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control.nen_menu.release();
                Intent intent = new Intent(Info.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // ---------------------------------------------------------------
    //bấm nút back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            YesNo yesno=new YesNo(this);
            yesno.show();
        }
        return false;
    }
}
