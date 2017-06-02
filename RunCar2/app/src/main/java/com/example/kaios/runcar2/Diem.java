package com.example.kaios.runcar2;

import android.content.Context;

import com.e3roid.E3Scene;

public class Diem {

	public int len = 5;//độ dài 5 chữ số
	public So[] p_so = new So[len];

	public void init(E3Scene scene) {
		int x = 9;
		for(int i=0;i<len;i++){			
			p_so[i].init(scene);
			p_so[i].setSo(0);//khởi tạo 5 số 0
			p_so[i].move(x, 70);//y=70px
			x+=30;
		}
		
	}

	public void load(Context context) {
		
		for(int i=0;i<len;i++){
			p_so[i]= new So();
			p_so[i].load(context);
			}
		
	}
	
	int du = 0, diem = 0, vitri_so = 0;
	public void move() {
		diem = Control.diem;
		if(diem >= 99999 && diem == 0)//đạt max hoặc k có điểm thì k làm gì
			return;
		du = 0;//số dư
		vitri_so = len - 1;//4
		while(true){
			du = diem % 10;
			diem = diem / 10;
			p_so[vitri_so].setSo(du);
			vitri_so--;
			if(diem < 10){
				p_so[vitri_so].setSo(diem);
				break;
			}
		}
		
	}

}
