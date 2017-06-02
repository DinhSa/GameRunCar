package com.example.kaios.runcar2;

import android.content.Context;

import com.e3roid.E3Scene;
import com.e3roid.drawable.Sprite;
import com.e3roid.drawable.texture.AssetTexture;
import com.e3roid.drawable.texture.Texture;

public class So {

	private Sprite[] so;
	private Texture[] soTexture;

	//-----------------------------------------------------------------------
	public void init(E3Scene scene) {
		so = new Sprite[10];
		for (int i = 0; i < 10; i++) {
			so[i] = new Sprite(soTexture[i], -1, -1);//tạm thời đặt ngoài màn hình
			if(i != 0)
				so[i].hide();//Mặc định ban đầu là hiện thị số 0
			scene.getTopLayer().add(so[i]);
		}

	}
	//-----------------------------------------------------------------------
	public void load(Context context) {
		soTexture = new Texture[10];
		for (int i = 0; i < 10; i++) {
			soTexture[i] = new AssetTexture(String.valueOf(i) + ".png", context);
		}

	}

	//-----------------------------------------------------------------------
	//Phương thức move tới vị trí cần đặt
	public void move(int x, int y){
		for (int i = 0; i < 10; i++)
			so[i].move(x, y);
	}

	//-----------------------------------------------------------------------
	//Phương thức đặt số cần hiện thị
	public void setSo(int so){
		for(int i=0;i<10;i++){
			if(i == so)
				this.so[i].show();
			else this.so[i].hide();
		}
	}
}
