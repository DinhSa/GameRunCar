package com.example.kaios.runcar2;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

public class GioiThieu extends Activity implements MediaPlayer.OnCompletionListener,
        View.OnTouchListener {

    private VideoView v;
    private Intent i;

    // ---------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {


        // Sét full màn hình
        requestWindowFeature(Window.FEATURE_NO_TITLE);// Không hiện thị tiêu đề
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// Fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//màn hình k tắt

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gioi_thieu);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    // Hiện thị dòng chữ để thông báo cho người dùng biết khi chạm vào màn
    // hình thì bỏ qua phần giới thiệu này.
        Tools.senMessenger(this,"Touch Skip");

        v = (VideoView) findViewById(R.id.videoView);
        Uri u = Uri.parse("android.resource://" + getPackageName() + "/"
        + R.raw.video);// Lấy video từ thư mục raw
        v.setVideoURI(u);
        v.setOnCompletionListener(this);// Khi video đã chạy xong
        v.setOnTouchListener(this);// Khi chạm vào màn hình
        v.setKeepScreenOn(true);// Khi hiện thị video thì không cho tự động tắt
    // màn hình
        v.start();

    // Khởi tạo intent mà ta muốn chuyển sang sau khi video chơi xong
        i = new Intent(this, Menu.class);
    }

    // ---------------------------------------------------------------
    public void onCompletion(MediaPlayer arg0) {
    // Khi video chạy xong thì ta dựng lại và chuyển sang phần game
        Intent intent = new Intent(this, Menu.class);
        this.startActivity(i);
        finish();// Kết thúc Intent
    }

    // ---------------------------------------------------------------
    public boolean onTouch(View arg0, MotionEvent arg1) {
    // Khi người chạm vào màn hình.
        this.startActivity(i);
        finish();// Kết thúc Intent
        return false;
    }
}