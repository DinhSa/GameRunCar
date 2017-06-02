package com.example.kaios.runcar2;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

/**
 * Created by kaios on 5/28/2017.
 */

public class DuongDua {
    private Sprite bg1;
    private Sprite bg2;
    private Texture bg1Texture;//load ảnh
    public int speedBackGround;

    // ---------------------------------------------------------------
    public DuongDua() {
    }

    // ---------------------------------------------------------------
    // Phương thức khởi tạo
    public void init(E3Scene scene) {
        bg1 = new Sprite(bg1Texture, 0, 0);
        bg2 = new Sprite(bg1Texture, 0, -Control.HEIGHT);
        scene.getTopLayer().add(bg1);
        scene.getTopLayer().add(bg2);
        speedBackGround = 0;
    }

    // ---------------------------------------------------------------
    // Phương thức load
    public void load(Context context) {

        bg1Texture = new AssetTexture("duongdua.png", context);
    }

    // ---------------------------------------------------------------
    // Phương thức vẽ
    public void move() {
        speedBackGround = (Control.SPEED + 5 + Control.touch_up_speed);
        if (speedBackGround <= 0)
            return;// Ngừng lại

        bg1.moveRelativeY(speedBackGround);
        bg2.moveRelativeY(speedBackGround);

        if (bg1.getRealY() >= Control.HEIGHT) {
            bg1.moveY(-Control.HEIGHT);
        }

        if (bg2.getRealY() >= Control.HEIGHT) {
            bg2.moveY(-Control.HEIGHT);
        }
    }
}
