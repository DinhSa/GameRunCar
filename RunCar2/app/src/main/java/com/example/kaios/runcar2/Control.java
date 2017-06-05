package com.example.kaios.runcar2;

import android.content.Context;
import android.media.AudioManager;

public class Control {
	// 2 biến xác định chiều cao và rộng của màn hình
	public static int HEIGHT = 1280;
	public static int WIDTH = 720;

	public static int time_delay = 10;// Thời gian cập nhật lại màn hình (ms)

	public static int SPEED = 1;// Điều khiển tốc độ của background và car

	public static int timeRE=4; //mặc định ban đầu sau 4s sẽ có 1 car rẽ

	public static boolean isvacham = false;// đánh dấu va chạm

	// Ngược lại di chuyển từ dưới lên.

	public static boolean isPause = false;// Điều khiển tạm dừng và chơi tiếp
											// trong game
	// nếu isPause = false: Game đang chạy
	// nếu isPause = true: Game tạm dừng

	public static boolean overGame = false;// Biến xác định xem game kết thúc
											// hay chưa
	// Nếu overGame = false: Game chưa kết thúc
	// Nếu overGame = true: Game kết thúc

	public static AudioManager audioManager = null;// Quản lý volume.

	public static boolean thoat = false;// Khi người dùng chọn thoát game chính

	public static boolean isPlay = false;// Biến dùng để xác định xem game đã
											// bắt đầu hay chưa.
	// Nếu isPlay = false: hiện thị thời gian đếm ngược.
	// Nếu isPlay = true: Bắt đầu game

	public static int diem = 0;// Biến để lưu lại điểm của người chơi.

	public static Sound carstart = null, nhacGameOver=null, nen_menu=null, ratings=null,
	nhacnen2=null, no=null;

	
	
	//Khi người dùng chạm vào màn hình thì ta tăng tốc lên 3 
	public static int touch_up_speed = 0;

	// ------------------------------------------------------------
	// Phương thức khởi tạo toàn bộ giá trị biến tĩnh
	public static void init(Context context) {
		HEIGHT = 1280;
		WIDTH = 720;
		time_delay = 10;
		SPEED = 1;
		isvacham = false;
		isPause = false;
		overGame = false;
		thoat = false;
		isPlay = false;
		diem = 0;
		touch_up_speed = 0;
		timeRE=4;

		Control.no = new Sound(context, R.raw.no);
		Control.nhacnen2 = new Sound(context, R.raw.nhacnen2);
		Control.ratings = new Sound(context, R.raw.nhacnen2);
		Control.nen_menu = new Sound(context, R.raw.nen_menu);
		Control.carstart = new Sound(context, R.raw.carstart);
		Control.nhacGameOver = new Sound(context, R.raw.gameover);
		Control.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	}

}
