package com.xf.psychology.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MusicService extends Service {
    private MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setLooping(false);
    }

    public interface OnPreparedListener {
        public void onPrepared();
    }

    private Executor executor = Executors.newSingleThreadExecutor();

    public class MusicBinder extends Binder {

        private boolean prepared = false;

        private OnPreparedListener preparedListener;

        public void setPreparedListener(OnPreparedListener preparedListener) {
            this.preparedListener = preparedListener;
        }

        public void setDataSource(String path) {
            prepared = false;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        player.reset();
                        player.setDataSource(path);
                        player.prepare();
                        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                prepared = true;
                                if (preparedListener != null) {
                                    preparedListener.onPrepared();
                                }
                                mp.start();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        public void seekToProgress(int progress) {
            if (!prepared) return;
            if (!player.isPlaying()) {
                player.start();
            }
            player.seekTo(progress);

        }

        public int getDuration() {
            return player.getDuration();
        }

        public int getCurrentProgress() {
            return player.getCurrentPosition();
        }

        public void play() {
            if (!prepared) return;
            Log.d("TAG", "play: ");
            if (!player.isPlaying()) {
                Log.d("TAG", "play: ");
                player.start();
            } else {
                player.pause();
            }
        }

        public void pause() {
            if (!prepared) return;
            if (player.isPlaying()) {
                player.pause();
            }
        }

    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        if (player.isPlaying()) {
            player.pause();
            player.release();
            player = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }
}
