package com.example.kaios.runcar2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Background;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.sprite.TextSprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;
import com.e3roid.drawable.texture.TiledTexture;
import com.e3roid.event.SceneUpdateListener;
import com.e3roid.event.ShapeEventListener;

import java.util.ArrayList;

public class Ratings extends E3Activity implements SceneUpdateListener,ShapeEventListener{
	
	private Sprite playagain_p;
	private Sprite menu_p;
	private Sprite reset_p;
	
	private Texture playagain_pTexture;
	private Texture menu_pTexture;
	private Texture reset_pTexture;
	
	private TextSprite[] d;
	private ArrayList<Model_Diem> List =new ArrayList<>(); //list top5
	
	//public CustomDialogYesNo customdialogyesno;
	//-------------------------------------------------------------------
	@Override
	public E3Engine onLoadEngine() {
		E3Engine engine = new E3Engine(this, Control.WIDTH, Control.HEIGHT);
		engine.requestFullScreen();
		engine.requestPortrait();

		return engine;
	}
	//-------------------------------------------------------------------
	@Override
	public void onLoadResources() {
		playagain_pTexture = new AssetTexture("playagain.png", this);
		menu_pTexture = new AssetTexture("menu.png", this);
		reset_pTexture = new AssetTexture("reset.png", this);
		
	}
	//-------------------------------------------------------------------
	@Override
	public E3Scene onLoadScene() {
		E3Scene scene = new E3Scene();
		scene.registerUpdateListener(Control.time_delay, this);
		scene.addEventListener(this);

		//thêm điểm mới vào csdl
		this.addDiem(Control.diem);

		//lấy top 5 điểm cao nhất
		this.getTop5();

		Control.ratings.loop();//phát nhạc
		// Đặt hình nền
		Background nen = new Background(new TiledTexture("nen_ratings.png",
				Control.WIDTH, Control.HEIGHT, this));
		scene.getTopLayer().setBackground(nen);

		playagain_p = new Sprite(playagain_pTexture, 112, 1060);
		menu_p = new Sprite(menu_pTexture, 404, 1060);
		reset_p = new Sprite(reset_pTexture, 266, 946);
		
		playagain_p.addListener(this);
		menu_p.addListener(this);
		reset_p.addListener(this);
		
		scene.addEventListener(playagain_p);
		scene.addEventListener(menu_p);
		scene.addEventListener(reset_p);
		
		scene.getTopLayer().add(playagain_p);
		scene.getTopLayer().add(menu_p);
		scene.getTopLayer().add(reset_p);


		
		Typeface font = Typeface.createFromAsset(getAssets(), "SHOWG.TTF");
		d = new TextSprite[5];
		for(int i=0;i<5;i++){
			d[i] = new TextSprite("0", 34, this);
			d[i].setTypeface(font);
			d[i].setColor(Color.WHITE);
			scene.getTopLayer().add(d[i]);
		}
		
		d[0].move(378, 454);
		d[1].move(296, 546);
		d[2].move(210, 650);
		d[3].move(170, 756);
		d[4].move(100, 858);

		boolean trung=false;//trường hợp 2 điểm giống nhau
		for(int i=0; i<List.size(); i++) {
			if (List.get(i).getDiem() == Control.diem && trung==false) {
				d[i].setText("New=>" + String.valueOf(List.get(i).getDiem()));
				trung=true;
			} else
				{
					d[i].setText(String.valueOf(List.get(i).getDiem()));
				}
		}

		return scene;
	}
	//-------------------------------------------------------------------
	public boolean onTouchEvent(E3Scene arg0, Shape shape, MotionEvent motionEvent,
			int arg3, int arg4) {
		if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
			if(shape.equals(playagain_p)){
				Control.ratings.release();
				Control.init(this);
				Intent i = new Intent(this, MainGame.class);
				this.startActivity(i);
				this.finish();
			}else if(shape.equals(menu_p)){
				Control.ratings.release();
				Intent i = new Intent(this, Menu.class);
				this.startActivity(i);
				this.finish();
			}
			else if(shape.equals(reset_p)){
				this.reset();//xóa dữ liệu
				for(int i=0;i<5;i++){
					d[i].reload(true);
					d[i].setText("0");
				}
			}
		}
		return false;
	}
	//-------------------------------------------------------------------

	public void onUpdateScene(E3Scene arg0, long arg1) {
		
	}
	//-------------------------------------------------------------------
	@Override
	public boolean onKeyDown(E3Scene scene, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			YesNo yesno=new YesNo(this);
			yesno.show();
		}
		return false;
	}


	// ------------------------------------------------------
	// Phương thức thêm dữ liệu
	public void addDiem(int Diem) {
		try {
			DataBaseHandling db= new DataBaseHandling(this);
			db.openDataBase();
			db.insert(Diem);
			//return true;// Thành công
		} catch (Throwable t) {
			//return false;// Không thành công
		}
	}

	// ------------------------------------------------------
	// Phương thức reset điểm
	public void reset(){
		DataBaseHandling db= new DataBaseHandling(this);
		db.openDataBase();
		db.DeleteData();

	}

	// ------------------------------------------------------
	// Phương thức lấy Top5 điểm
	public void getTop5(){
		DataBaseHandling db= new DataBaseHandling(this);
		db.openDataBase();
		List=db.Top5();
	}
}
