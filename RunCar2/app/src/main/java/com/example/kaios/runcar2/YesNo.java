package com.example.kaios.runcar2;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class YesNo extends Dialog implements View.OnClickListener {
    ImageButton yes, no;

    public YesNo(Activity activity) {
        super(activity);

    }

    // ---------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// Không hiện thị tiêu đề
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// Fullscreen
        setContentView(R.layout.yes_no);
        yes = (ImageButton) findViewById(R.id.yes);
        no = (ImageButton) findViewById(R.id.no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }

    // ---------------------------------------------------------
    @Override
    public void onClick(View v) {
        if((Control.isPause && Control.isPlay) || (!Control.isPause&&Control.isPlay)){//đang trong Maingame
            switch (v.getId()){
                case R.id.yes:{
                    this.dismiss();
                    Control.thoat=true;//thoát game
                    break;
                }
                case R.id.no:{
                    this.dismiss();//tiếp tục
                    break;
                }
            }
        }
        else {//đang ở Menu

            switch (v.getId()){
                case R.id.yes:{
                    Control.nen_menu.release();
                    System.exit(0);//thoát ứng dụng
                    this.hide();
                    break;
                }
                case R.id.no:{
                    this.hide();
                    break;
                }
            }
        }


    }

    // ---------------------------------------------------------
}
