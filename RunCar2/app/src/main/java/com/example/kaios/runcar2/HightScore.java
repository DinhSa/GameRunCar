package com.example.kaios.runcar2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class HightScore extends Activity {
    Model_Diem diem ;

    // ---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sét full màn hình
        requestWindowFeature(Window.FEATURE_NO_TITLE);// Không hiện thị tiêu đề
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// Fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//màn hình k tắt

        setContentView(R.layout.activity_hight_score);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        ImageView btn_ok =(ImageView)findViewById(R.id.ok);
        TextView tv_name=(TextView)findViewById(R.id.tv_Name);
        TextView tv_diem =(TextView)findViewById(R.id.tv_Diem);

        getData();//get data
        if(diem ==null){//k có dữ liệu
            tv_name.setText(""); //set text
            tv_diem.setText("");//set text
        }
        else {//có dữ liệu
            tv_name.setText(diem.getTen()); //set text
            tv_diem.setText(String.valueOf(diem.getDiem()));//set text
        }


        //click ok
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control.nen_menu.release();
                Intent intent = new Intent(HightScore.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // ---------------------------------------------------------------
    //phương thức lấy data
    private void getData(){
        DataBaseHandling db= new DataBaseHandling(this);
        db.openDataBase();
         diem=db.GetMax();
    }

    // ---------------------------------------------------------------
    //xử lya bấm Back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            YesNo yesno=new YesNo(this);
            yesno.show();
        }
        return false;
    }
}
