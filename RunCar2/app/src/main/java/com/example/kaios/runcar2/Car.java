package com.example.kaios.runcar2;

import android.content.Context;
import android.util.Log;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Car {
	// Car
	public Sprite[] car;
	public Sprite HieuUngNo;
	public Texture NoTexture;
	public Texture[] carTexture;
	public int so_car = 10;// Có 10 xe
	public int[] landuong;
	public int so_landuong = 8;//8 làn đường
	public int[] RE;
	public int[] landuong_car;//mảng làn đường
	private int speed = 0;

	private final int speed_re = 1;// Tốc độ rẽ của car là 1

	// Time
	public Time time;


	// ---------------------------------------------------------------
	public Car() {
	}

	// ---------------------------------------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		car = new Sprite[so_car];
		RE = new int[so_car];
		landuong_car = new int[so_car];
		for (int i = 0; i < so_car; i++) {
			car[i] = new Sprite(carTexture[i], -100, -100);// Ban đầu thì ta đặt toàn bộ car bên ngoài
			scene.getTopLayer().add(car[i]);

			// Ban đầu thì không có xe nào được quyền rẽ
			RE[i] = -1;// Nếu RE=0=> rẽ trái, RE=1 rẽ phải

			// Khởi tạo làn đường ban đầu là -1
			landuong_car[i] = -1;
		}

		//load hiệu ứng nổ
		HieuUngNo = new Sprite(NoTexture,-100,-100); //tạm để ngoài màn hình
		scene.getTopLayer().add(HieuUngNo);
		HieuUngNo.hide();//ẩn đi

		// Khởi tạo các giá trị của làn đường. Có 8 làn đường.
		landuong = new int[so_landuong];
		landuong[0] = 165;
		landuong[1] = 215;
		landuong[2] = 265;
		landuong[3] = 315;
		landuong[4] = 365;
		landuong[5] = 415;
		landuong[6] = 465;
		landuong[7] = 515;

		// Sét vị trí ban đầu cho xe
		setVitriBandau();
		//
		time = new Time();
		time.setTime(Control.timeRE);// Sau 4s sẽ có 1 xe chuyển hướng
		time.start();
	}

	// ---------------------------------------------------------------
	// Phương thức load
	public void load(Context context) {
		carTexture = new Texture[so_car];
		for (int i = 0; i < so_car; i++)
			carTexture[i] = new AssetTexture("car" + String.valueOf(i + 2)
					+ ".png", context);
		//
		NoTexture = new AssetTexture("no.png", context);



	}

	// ---------------------------------------------------------------
	// Phương thức kiểm tra xem car đã đi ra khỏi màn hình chưa
	public boolean endHeight(Sprite sprite) {
		if (sprite.getRealY() >= Control.HEIGHT)
			return true;// Ra khỏi màn hình
		return false;// Vẫn còn trong màn hình
	}

	// ---------------------------------------------------------------
	// Phương thức khỏi tạo ví trí ban đầu của các xe
	public void setVitriBandau() {
		int vitri_y = Control.HEIGHT/2; //đặt xe ở vị trí giữa trở lên
		int l = 0;//số làn đường cần đặt
		int y = 0;//tọa độ y

		// Ta chỉ đặt 5 xe lên màn hình
		for (int i = 0; i < 5; i++) {
			while (true) {
				l = Tools.getRandomIndex(0, so_landuong - 1);//random ngẫu nhiên làn đường
				if (l == 3)//trùng vị trí xe Player
					continue;
				y = Tools.getRandomIndex(853, vitri_y);//đặt xe từ vị trí Player trở lên
				car[i].move(landuong[l], y);

				boolean tmp = true;//biến kiểm tra 2 xe có đèlên nhau
				for (int j = 0; j < so_car; j++) {
					if (i != j) {
						if (car[i].collidesWith(car[j]))
							tmp = false;// Đè lên xe khác
					}
				}

				if (!tmp)//nếu có đè thì set lại vị trí
					continue;
				else {//ngược lại thì show car
					car[i].show();
					landuong_car[i] = l;
					break;
				}
			}
		}

		// Số xe còn lại sẽ nằm ở ví trí ngoài màn hình
		for (int i = 5; i < so_car; i++) {
			while (true) {
				l = Tools.getRandomIndex(0, so_landuong - 1);
				y = Tools.getRandomIndex(-1, -Control.HEIGHT);
				car[i].move(landuong[l], y);

				boolean tmp = true;
				for (int j = 0; j < so_car; j++) {
					if (i != j) {
						if (car[i].collidesWith(car[j]))
							tmp = false;// Đè lên xe khác
					}
				}

				if (!tmp)
					continue;
				else {
					car[i].hide();
					landuong_car[i] = l;
					break;
				}
			}
		}
	}

	// ---------------------------------------------------------------
	// Phương thức xét lại vị trí khi car ra khỏi màn hình
	public void setVitri(Sprite sprite, int a) {
		if (endHeight(sprite)) {//ra khỏi màn hình
			int l = 0, y = 0;
			while (true) {
				l = Tools.getRandomIndex(0, so_landuong - 1);
				y = Tools.getRandomIndex(-1, -Control.HEIGHT);
				sprite.move(landuong[l], y);

				boolean tmp = true;
				for (int i = 0; i < so_car; i++) {
					if (!sprite.equals(car[i])) {
						if (sprite.collidesWith(car[i]))
							tmp = false;// Đè lên xe khác
					}
				}

				if (!tmp)
					continue;
				else {
					landuong_car[a] = l;
					sprite.hide();
					break;
				}
			}
		}
	}

	// ---------------------------------------------------------------
	// Phương thức di chuyển car
	public void move() {

		speed = Control.SPEED + Control.touch_up_speed;

		//nếu có hiệu ứng nổ thì cho nó chạy vs tốc độ đường đua
		if(HieuUngNo.isVisible()){
			HieuUngNo.moveRelativeY(Control.SPEED + 5 + Control.touch_up_speed);
		}
		//
		for (int i = 0; i < so_car; i++) {
			car[i].moveRelativeY(speed);
			// Nếu car nào mà di chuyển đến màn hình thì ta hiện thị
			if (car[i].getRealY() + car[i].getHeight() >= 0)
				car[i].show();

			// Nếu xe nào được phép re thì ta cho rẽ
			if (RE[i] != -1) {
				//kt = true;
				if (RE[i] == 0) {// Rẽ trái
						car[i].moveRelativeX(-speed_re);
						if (car[i].getRealX() == (landuong[landuong_car[i] - 1])) {
							landuong_car[i] = landuong_car[i] - 1;
							car[i].moveX(landuong[landuong_car[i]]);
							vachamre(car[i],i);
							RE[i] = -1;
						}

				} else {// Rẽ phải
						car[i].moveRelativeX(speed_re);
						if (car[i].getRealX() == (landuong[landuong_car[i] + 1])) {
							landuong_car[i] = landuong_car[i] + 1;
							car[i].moveX(landuong[landuong_car[i]]);
							vachamre(car[i],i);
							RE[i] = -1;
						}

				}
			}

			if (endHeight(car[i])) {
				setVitri(car[i], i);
				// khi 1 car ra khỏi màn hình ta cộng điểm cho ng chơi. cộng 10d
				Control.diem += 10;
				Log.d("DIEM",String.valueOf(Control.diem));
			}

			//có nổ xe thì khi ra khỏi màn hình sẽ ẩn đi
			if(HieuUngNo.isVisible()){
				if(endHeight(HieuUngNo)){
					HieuUngNo.hide();
				}
			}
		}

		// Nếu time = 0 thì ta bắt đầu cho car rẽ.
		if (time.getTime() == 0) {
			// Xác định xe nào được rẽ
			chonxe();
			time.setTime(Control.timeRE);//sau 4s sẽ có 1 car đc rẽ
		}
	}

	// ---------------------------------------------------------------
	// Phương thức chọn 1 xe để cho xe đó rẽ trái, hoặc phải.
	public void chonxe() {
		int[] temp = new int[so_car];//mảng tạm những xe đc rẽ
		int k = 0;
		for (int i = 0; i < so_car; i++) {//
			if (car[i].isVisible()
					&& car[i].getRealY() < (Control.HEIGHT / 3) * 2 //chỉ những xe trước Player mới rẽ
					&& RE[i] == -1) {
				temp[k] = i;
				k++;
			}
		}

		// Trường hợp không có xe nào đang hiện thị phù hợp với điều kiện
		if (k == 0)
			return;

		// Chọn ngẫu nhiên 1 xe trong các xe được rẽ
		int xeduocre = Tools.getRandomIndex(0, k - 1);

		// Kiểm tra xem car này xem đang ở làn đường nào
		// Nếu là làn đường đầu tiên
		if (landuong_car[temp[xeduocre]] == 0) {
			// Chỉ có thể rẽ phải
			RE[temp[xeduocre]] = 1;
		}

		// Nếu là làn đường cuối cùng
		else if (landuong_car[temp[xeduocre]] == so_landuong - 1) {
			// Chỉ có thể rẽ trái
			RE[temp[xeduocre]] = 0;
		}

		// Ở trong giữa làn đường
		else {
			// Chọn ngẫu nhiên hướng rẽ
			int huongre = Tools.getRandomIndex(0, 1);
			RE[temp[xeduocre]] = huongre;
		}
	}

	// ---------------------------------------------------------------
	//phương thức xử lý khi 2 car rẽ đụng nhau
	public void vachamre(Sprite sprite, int l){
		for(int i=0;i<10;i++){
			if(sprite.collidesWith(car[i]) && !sprite.equals(car[i])){
				Control.no.play();
				HieuUngNo.move(sprite.getRealX()+sprite.getWidth()/2, sprite.getRealY()+sprite.getHeight()/2);
				HieuUngNo.show();
				sprite.moveY(-Control.HEIGHT);//tạm di chuyển ra màn hình
				car[i].moveY(-Control.HEIGHT);//tạm di chuyển ra màn hình
				setVitri(sprite, l);//đặtlại vị trí
				setVitri(car[i],i);//đạt lại vị trí
			}
		}
	}
}