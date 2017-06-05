package com.example.kaios.runcar2;

import android.content.Context;
import android.widget.Toast;

public class Tools {

	// ---------------------------------------------------------------------------------------
	// Phương thức cho bạn 1 số ngẫu nhiên từ min->max.
	public static int getRandomIndex(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	// ----------------------------------------------------------------------------------------
	// Hiện thị 1 tin nhắn
	public static void senMessenger(Context context, String messenger) {
		Toast.makeText(context, messenger, Toast.LENGTH_LONG).show();
	}
}
