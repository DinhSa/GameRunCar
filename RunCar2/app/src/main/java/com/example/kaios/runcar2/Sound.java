package com.example.kaios.runcar2;

import android.content.Context;
import android.media.MediaPlayer;

public class Sound {
	private MediaPlayer mPlayer;

	private boolean mPlaying = false;
	private boolean mLoop = false;
	public boolean complet = false;

	public Sound(Context ctx, int resID) {
		mPlayer = MediaPlayer.create(ctx, resID);

		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer mp) {
				mPlaying = false;
				complet = true;
				if (mLoop) {
					mp.start();
				}
			}

		});
	}

	public synchronized void play() {
		if (mPlaying)
			return;

		if (mPlayer != null) {
			mPlaying = true;
			mPlayer.start();
		}
	}

	//dừng
	public synchronized void stop() {
		try {
			mLoop = false;
			if (mPlaying) {
				mPlaying = false;
				mPlayer.pause();
			}

		} catch (Exception e) {
		}
	}

	//lặp lại
	public synchronized void loop() {
		mLoop = true;
		mPlaying = true;
		mPlayer.start();
	}

	//thoát
	public void release() {
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
}
