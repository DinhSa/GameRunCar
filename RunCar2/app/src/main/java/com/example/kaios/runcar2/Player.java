package com.example.kaios.runcar2;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class Player {
	private Sprite player;
	private Texture playerTexture;
	private int start_width = 165;
	private int end_width = 555;

	// -----------------------------------
	public Player() {
	}

	// -----------------------------------
	// Phương thức khởi tạo
	public void init(E3Scene scene) {
		player = new Sprite(playerTexture, 315, (Control.HEIGHT / 3) * 2);// 2/3 làn đường

		scene.getTopLayer().add(player);
	}

	// -----------------------------------
	// Phương thức load
	public void load(Context context) {
		playerTexture = new AssetTexture("car1.png", context);

	}

	// -----------------------------------
	// Phương thức move
	public void move(int x) {
		// Giới hạn player không cho phép di chuyển khỏi đường đi.
		// Nếu còn trong đường đi thì mới cho player di chuyển

		if (player.getRealX() + x >= start_width
				&& player.getRealX() + x <= end_width - player.getWidth()) {
			// Nếu có thể di chuyển theo chiều x thì di chuyển
			player.moveRelativeX(x);
		}

	}

	// -----------------------------------
	// Phương thức xét va chạm giữa player và car
	public boolean vacham(Car car) {
		for (int i = 0; i < car.so_car; i++) {
			if (car.car[i].collidesWith(player)) {
				Control.isvacham = true;
				return true;// Có va chạm
			}
		}
		return false;// Không có va chạm
	}
	// -----------------------------------
}
