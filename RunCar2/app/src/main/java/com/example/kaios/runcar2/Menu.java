package com.example.kaios.runcar2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Menu extends Activity {
	// ---------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set full màn hình
		requestWindowFeature(Window.FEATURE_NO_TITLE);// Không hiện thị tiêu đề
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// Fullscreen
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//màn hình k tắt

		setContentView(R.layout.activity_menu);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

		Control.init(this);
		Control.nen_menu.loop();

		final ImageView play=(ImageView)findViewById(R.id.Play);
		ImageView setting =(ImageView)findViewById(R.id.Setting);
		ImageView info=(ImageView)findViewById(R.id.info);
		ImageView exit=(ImageView)findViewById(R.id.Exit);
		ImageView Score=(ImageView)findViewById(R.id.hight);
		ImageView huongdan=(ImageView)findViewById(R.id.hd);

		//
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Control.nen_menu.release();
				//Control.btn.play();
				play();
			}
		});

		//click Play
		setting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(Menu.this, Setting.class);
				startActivity(intent);
				finish();
			}
		});

		////click xem điểm
		Score.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(Menu.this, HightScore.class);
				startActivity(intent);
				finish();
			}
		});

		//click xem thông tin
		info.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(Menu.this, Info.class);
				startActivity(intent);
				finish();
			}
		});

		//click xem hướng dẫn
		huongdan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(Menu.this, HuongDan.class);
				startActivity(intent);
				finish();
			}
		});

		//click thoát
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();//show dialog
			}
		});

	}

	// ---------------------------------------------------------------
	//vào maingame
	public void play() {
		Control.init(this);
		Intent i = new Intent(this, MainGame.class);
		this.startActivity(i);
		this.finish();
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

	// ---------------------------------------------------------------
	//phương thức show dialog Yes No
	private void showDialog(){
		YesNo yesno=new YesNo(this);
		yesno.show();
	}

	// ---------------------------------------------------------------
}
