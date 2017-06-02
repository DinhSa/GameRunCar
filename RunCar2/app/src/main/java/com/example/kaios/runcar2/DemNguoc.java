package com.example.kaios.runcar2;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class DemNguoc extends Thread {
	private Texture[] demnguocTexture;
	private Sprite[] demnguoc;
	private int i = 3;

	public DemNguoc() {
	}

	// -----------------------------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		demnguoc = new Sprite[4]; //3 2 1 go

		for (int j = 0; j < demnguoc.length; j++) {

			int cx = Control.WIDTH / 2 - demnguocTexture[j].getWidth() / 2;//xác định tọa độ x
			int cy = Control.HEIGHT / 2 - demnguocTexture[j].getHeight() / 2;//xác định tọa độ y
			demnguoc[j] = new Sprite(demnguocTexture[j], cx, cy);//khởi tạo
			demnguoc[j].hide();
			scene.getTopLayer().add(demnguoc[j]);//add vào scene
		}
	}

	// -----------------------------------------------------
	// Phương thức load
	public void load(Context context) {
		demnguocTexture = new Texture[4];

		demnguocTexture[0] = new AssetTexture("go.png", context);
		demnguocTexture[1] = new AssetTexture("mot.png", context);
		demnguocTexture[2] = new AssetTexture("hai.png", context);
		demnguocTexture[3] = new AssetTexture("ba.png", context);
	}

	// -----------------------------------------------------
	// Cài đặt phương thức run
	public void run() {
		try {
			Thread.sleep(1000);//delay 1s
			Control.carstart.play();//nhạc start
			for(int j=3;j>=0;j--){
				if(j==0){//đếm tới 0
					demnguoc[1].hide();//ẩn 1
					demnguoc[0].show();//hiện Go
					try {
						Thread.sleep(1500);//dừng 1.5s
						demnguoc[0].hide();//ẩn Go
						Control.isPlay = true;//palying
						Control.nhacnen2.loop();
						//Control.nhacnen.loop();//bắt đầu nhạc nền
						Control.carstart.release();//tắt nhạc start

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					demnguoc[j].show();
					try {
						Thread.sleep(1000);//dừng 1s rồi đếm tiếp
						demnguoc[j].hide();//ẩn đi
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
