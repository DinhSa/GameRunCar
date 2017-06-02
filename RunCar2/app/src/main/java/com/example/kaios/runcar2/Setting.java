package com.example.kaios.runcar2;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class Setting extends Activity implements View.OnClickListener {
    private ImageButton but_v_giam, but_v_tang, but_ok;
    private TextView text_volume;

    // ---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//màn hình k tắt

        setContentView(R.layout.activity_setting);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        //Control.init(this);

        but_v_giam = (ImageButton) findViewById(R.id.v_giam);
        but_v_tang = (ImageButton) findViewById(R.id.v_tang);
        but_ok = (ImageButton) findViewById(R.id.ok_setting);

        text_volume = (TextView)findViewById(R.id.text_volume);

        but_v_tang.setOnClickListener(this);
        but_v_giam.setOnClickListener(this);
        but_ok.setOnClickListener(this);

        //set volume
        text_volume.setText(String.valueOf(Control.audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)));
    }

    // ---------------------------------------------------------------
    @Override
    public void onClick(View v) {

        //V_tăng
        if(v.getId() == R.id.v_tang){
            int t = Integer.parseInt(text_volume.getText().toString());
            if(t+1 < 16){
                text_volume.setText(String.valueOf(t+1));
                Control.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,	(t+1), 1);
            }
        }
        //V_giảm
        else if(v.getId() == R.id.v_giam){
            int t = Integer.parseInt(text_volume.getText().toString());
            if(t-1 > -1){
                text_volume.setText(String.valueOf(t-1));
                Control.audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,	(t-1), 1);
            }
        }
        //Click OK
        else if (v.getId() == R.id.ok_setting) {
            if(Control.isPause){
//                Control.nhacnen.loop();
//                Control.isPause = false;
                this.finish();
            }
            else {
                Control.nen_menu.release();
                Intent intent = new Intent(Setting.this, Menu.class);
                startActivity(intent);
                finish();
            }

        }
    }

    // ---------------------------------------------------------------
    //bấm nút back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(Control.isPlay&&Control.isPause){
                //
            }
            else {
                YesNo yesno=new YesNo(this);
                yesno.show();
            }
        }
        return false;
    }
}
