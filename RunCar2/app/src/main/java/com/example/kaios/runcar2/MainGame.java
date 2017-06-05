package com.example.kaios.runcar2;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.e3roid.E3Activity;
import com.e3roid.E3Engine;
import com.e3roid.E3Scene;
import com.e3roid.drawable.Shape;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.event.SceneUpdateListener;
import com.e3roid.event.ShapeEventListener;

public class MainGame extends E3Activity implements SceneUpdateListener,
		ShapeEventListener, SensorEventListener {

	// Khai báo các biến để xứ lý cảm biến gia tốc
	private SensorManager sensorManager;
	private int getX; //Vì Player chỉ di chuyển sang trái hoặc phải nên ta chỉ cần nhận giá trị theo trục X
	private boolean[] checkUp=new boolean[9]; //kiểm tra đã đạt móc up speed.
	
	// Dialog hiện thị thông báo hỏi muốn thoát khỏi game
	private YesNo yesno;

	//
	private TimeSpeedUp timeSpeedUp;

	// DuongDua. Lớp này để tạo ra đường đua
	private DuongDua duongdua = new DuongDua();

	// Car. tất cả các oto cùng tham gia đua cùng Player
	private Car car = new Car();

	// Player. Nhân vật chính
	private Player player = new Player();

	// Pause
	private Sprite pause;
	private AssetTexture pauseTexture;

	// Continue. Ảnh hiện thị button COntinue khi bạn pause game
	private Sprite continue_;
	private AssetTexture continueTexture;

	// Setting. hiện thị button setting.
	private Sprite setting;
	private AssetTexture settingTexture;

	//hiển thị thông báo khi đc up speed
	private Sprite speedup;
	private AssetTexture speedupTexture;

	// DemNguoc .Khi bắt đầu game thì ta đếm ngược kiểu 3,2,1,go. Khi vẽ xong GO thì ta cho Player di chuyển
	private DemNguoc demnguoc = new DemNguoc();

	// OverGame. vẽ lên khi gameover
	private Sprite overgame;
	private AssetTexture overgameTexture;

	//Diểm. Lớp này dùng để hiện thị ảnh
	private Diem dd = new Diem();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Với tham số FLAG_KEEP_SCREEN_ON giúp chúng ta khi chơi game màn hình không bị tắt
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	}

	// ---------------------------------------------------------

	@Override
	public E3Engine onLoadEngine() {
		E3Engine engine = new E3Engine(this, Control.WIDTH, Control.HEIGHT);
		engine.requestFullScreen();
		engine.requestPortrait();

		return engine;
	}

	// ---------------------------------------------------------
	
	//Thực hiện load toàn bộ dữ liệu cho các biến và đối tượng
	@Override
	public void onLoadResources() {
		// Load Background
		duongdua.load(this);

		// Load Car
		car.load(this);

		// Load Player
		player.load(this);

		// Load Pause
		pauseTexture = new AssetTexture("pause.png", this);

		// Load Continue
		continueTexture = new AssetTexture("continue.png", this);

		// Load Setting
		settingTexture = new AssetTexture("setting.png", this);

		//load speedup
		speedupTexture = new AssetTexture("speedup.png",this);

		// Load DemNguoc
		demnguoc.load(this);

		// Load OverGame
		overgameTexture = new AssetTexture("overgame.jpg", this);

		// Load Diem
		dd.load(this);

	}

	// ---------------------------------------------------------
	//Khởi tạo và add vào scene
	@Override
	public E3Scene onLoadScene() {
		E3Scene scene = new E3Scene();
		scene.registerUpdateListener(Control.time_delay, this);
		scene.addEventListener(this);

		// Đăng kí để lắng nghe sự kiện thay đổi sensor
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager
						.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 1000); // delay

		// Khởi tạo Background
		duongdua.init(scene);// Khởi tạo và add background vào scene

		//load YesNo
		yesno =new YesNo(this);

		// Khởi tạo Car
		car.init(scene);

		// Khởi tạo Player
		player.init(scene);

		// Khởi tạo điểm
		dd.init(scene);

		//load speedup lên scene
		speedup = new Sprite(speedupTexture,-100,-100); //tạm để ngoài màn hình
		scene.getTopLayer().add(speedup);
		speedup.hide();

		//khởi tạo TimeSpeedUp
		timeSpeedUp = new TimeSpeedUp();


		//ban đầu cho checkUp = false
		for(int i=0;i<9;i++)
			checkUp[i]=false;

		// Add phần Pause tại vị trí góc dưới bên trái màn hình
		pause = new Sprite(pauseTexture, 5, Control.HEIGHT
				- pauseTexture.getHeight() - 5);
		pause.addListener(this);
		scene.addEventListener(pause);
		scene.getTopLayer().add(pause);

		// Add phần Setting tại vị trí góc dưới bên phải màn hình
		setting = new Sprite(settingTexture, Control.WIDTH
				- settingTexture.getWidth() - 5, Control.HEIGHT
				- pauseTexture.getHeight() - 5);
		setting.addListener(this);
		scene.addEventListener(setting);
		scene.getTopLayer().add(setting);

		// Add phần Continue tại vị trí giữa màn hình
		int cx = Control.WIDTH / 2 - continueTexture.getWidth() / 2;
		int cy = Control.HEIGHT / 2 - continueTexture.getHeight() / 2;
		continue_ = new Sprite(continueTexture, cx, cy);
		continue_.hide();//ẩn đi
		continue_.addListener(this);
		scene.addEventListener(continue_);
		scene.getTopLayer().add(continue_);

		// Khởi tạo đếm ngược
		demnguoc.init(scene);
		demnguoc.start();// Bắt đầu đếm ngược

		cx = Control.WIDTH / 2 - overgameTexture.getWidth() / 2;
		cy = Control.HEIGHT / 2 - overgameTexture.getHeight() / 2;
		overgame = new Sprite(overgameTexture, cx, cy);
		overgame.hide();
		scene.getTopLayer().add(overgame);

		return scene;
	}

	// ---------------------------------------------------------
	//Khi chạm vào màn hình thì ta tăng tốc độ game lên tạm thời. Khi bạn rời tay khỏi màn hình
	//thì trở lại trạng thái ban đầu
	@Override
	public boolean onSceneTouchEvent(E3Scene scene, MotionEvent motionEvent) {
		if (!Control.isPause && Control.isPlay) {
			if (motionEvent.getAction() == MotionEvent.ACTION_MOVE)
				Control.touch_up_speed = 10;//Tăng tốc độ thêm 10
			else
				Control.touch_up_speed = 0;
		}
		return false;
	}

	// ---------------------------------------------------------
	//Bắt các sự kiện khi chạm vào các button
	public boolean onTouchEvent(E3Scene arg0, Shape shape,
			MotionEvent motionEvent, int arg3, int arg4) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			if (!Control.isPause && Control.isPlay) {//Khi người chơi chọn vào nút pause
				if (shape.equals(pause) && pause.isVisible()) {
					Control.nhacnen.stop();//tạm dùng nhạc nền
					Control.isPause = true;
					continue_.show();//Hiện thị 1 button continue ở giữa màn hình
					pause.hide();//ẩn button pause và setting
					setting.hide();
				} else if (shape.equals(setting) && setting.isVisible()) {//Khi chọn vào button setting
					Control.nhacnen.stop();//tạm dừng nhạc nền
					Control.isPause = true;
					continue_.show();//Hiện thị 1 button continue ở giữa màn hình
					pause.hide();//ẩn button pause và setting
					setting.hide();
					startActivity(new Intent(MainGame.this,Setting.class));//start class setting
				}
			} else {
				if (shape.equals(continue_) && continue_.isVisible()) {//Khi chọn buttong play
					Control.isPause = false;
					//Control.nhacnen.loop();//cho chạy nhạc nền
					continue_.hide();//ẩn buttong continue
					pause.show();
					setting.show();//hiện thị button pause và setting
				}
			}
		}
		return false;
	}

	// ---------------------------------------------------------
	//Khi đang chơi mà bạn nhấn nút back trên thiết bị thì ta cho hiện thị thông báo hỏi
	//xem muốn thoát hay không
	@Override
	public boolean onKeyDown(E3Scene scene, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Control.nhacnen.stop();//tạm dùng nhạc nền
			Control.isPause = true;
			continue_.show();//Hiện thị button continue ở giữa màn hình
			pause.hide();//ẩn button pause và setting
			setting.hide();
			yesno.show();
		}
		return false;
	};

	// ---------------------------------------------------------

	public void onUpdateScene(E3Scene scene, long elapsedMsec) {
		runOnUiThread(new Runnable() {
			public void run() {
				if (!Control.overGame) {
					if (!Control.isPause && Control.isPlay) {
						// Di chuyển background
						duongdua.move();

						// Di chuyển xe
						car.move();

						//kiểm tra có va chạm xe khác hay không
						player.vacham(car);

						dd.move();

						speedUp();//tăng speed nếu có

						//xử lý ẩn thông báo speedup nếu có
						if(timeSpeedUp.getTime()==0){
							speedup.hide();
						}

						//xứ lý va chạm
						if (Control.isvacham == true) {
							Control.overGame = true;
							Control.nhacGameOver.play();


							Control.isPause = true;
							Control.isPlay = false;
							// Tat nhac
							Control.carstart.release();
							Control.nhacnen.release();
							Control.nhacnen2.release();

							try {
								Thread.sleep(800);
								overgame.show();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

							try {
								Thread.sleep(2000);
								bangdiem();//
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						}
					}
				}

				//nếu bấm thoát thì thoát game
				if (Control.thoat){
					Control.isPlay=false;
					Control.nhacnen.release();//tắt nhạc
					Control.nhacnen2.release();//tắt nhạc
					Intent intent = new Intent(MainGame.this, Menu.class);
					startActivity(intent);
					MainGame.this.finish();//thoát khỏi màn hình chơi game
				}

			}

		});

	}

	// ---------------------------------------------------------
	//Phuong thức này sẽ chuyển sang giao diện hiện thị danh sách điểm
	public void bangdiem() {
		//customdialogsetting.dismiss();
		Intent i = new Intent(this, Ratings.class);
		this.startActivity(i);
		this.finish();//Khi chuyển sang thì ta tắt MainGame này đi
	}

	// ---------------------------------------------------------
	// Phần xử lý cảm biến gia tốc
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	// ---------------------------------------------------------
	// Khi cảm biến gia tốc thay đổi
	public void onSensorChanged(SensorEvent event) {
		if (!Control.isPause && Control.isPlay) {
			float[] values = event.values;
			// lấy giá trị tọa độ X thông qua giá trị thay đổi sensor
			getX = Math.round(values[0]);

			getX = -getX;
			// Di chuyển player
			player.move(getX);
		}

	}

	// ---------------------------------------------------------
	// Phương thức tăng tốc độ
	public void speedUp() {
		switch (Control.diem) {
		case 500:
			if(checkUp[0]==false){
				Control.SPEED ++;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[0]=true;
			}
				break;
		case 1000:
			if(checkUp[1]==false){
				Control.SPEED ++;
				Control.timeRE--;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[1]=true;
			}
				break;
		case 1500:
			if(checkUp[2]==false){
				Control.SPEED ++;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[2]=true;
			}
				break;
		case 3000:
			if(checkUp[3]==false){
				Control.SPEED ++;
				Control.timeRE--;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[3]=true;
			}
				break;
		case 4000:
			if(checkUp[4]==false){
				Control.SPEED ++;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[4]=true;
			}
				break;
		case 5000:
			if(checkUp[5]==false){
				Control.SPEED ++;
				Control.timeRE--;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[5]=true;
			}
				break;
		case 6000:
			if(checkUp[6]==false){
				Control.SPEED ++;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[6]=true;
			}
				break;
		case 7000:
			if(checkUp[7]==false){
				Control.SPEED ++;
				Control.timeRE--;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[7]=true;
			}
				break;
		case 8000:
			if(checkUp[8]==false){
				Control.SPEED ++;
				speedup.move(Control.WIDTH/2-speedup.getWidth()/2,Control.HEIGHT/2-speedup.getHeight()/2);//đặt giữa màn hình
				timeSpeedUp.setTime(2);//set 2s sẽ ẩn thông báo speedup
				timeSpeedUp.start();
				speedup.show();
				checkUp[8]=true;
			}
				break;
		}
	}
}
