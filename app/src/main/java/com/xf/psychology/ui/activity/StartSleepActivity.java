package com.xf.psychology.ui.activity;

import static com.xf.psychology.App.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.xf.psychology.R;
import com.xf.psychology.ui.NbButton;

import java.io.File;
import java.io.IOException;


public class StartSleepActivity extends AppCompatActivity {

    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = 1024;
    private static final int RECORD_AUDIO_REQUEST_CODE = 1;
    private NbButton btn_end_sleep;
    private Handler handler;
    private AudioRecord mAudioRecord;
    private int mBufferSize;
    private short[] mAudioBuffer;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private ImageView mWaveformView;
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private String fileName;
    private int awakeTime = 0;
    private int lightSleepTime = 0;
    private int deepSleepTime = 0;
    private Animator animator;
    private static final int SAMPLE_DELAY_MS = 1000;
    private static final int LOUDNESS_THRESHOLD = 5000;
    private Handler handler1;
    private androidx.constraintlayout.widget.ConstraintLayout rlContent;
    private Runnable sampleRunnable;
    private Button buttonStart;
    private Button buttonStop;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_sleep);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mWaveformView = findViewById(R.id.waveform_view);
        btn_end_sleep = findViewById(R.id.button_test);
        mBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        mAudioBuffer = new short[mBufferSize];
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
        } else {
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, mBufferSize);
            mAudioRecord.startRecording();
        }
        mBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
        } else {
            startRecording();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }


    private void startRecording() {
        mAudioRecord.startRecording();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int bytesRead = mAudioRecord.read(mAudioBuffer, 0, mBufferSize);
                    drawWaveform(bytesRead);
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            }
        }
    }

    private void drawWaveform(int bytesRead) {
        int width = mWaveformView.getWidth();
        int height = mWaveformView.getHeight();
        mCanvas.drawColor(Color.WHITE);
        for (int i = 0; i < bytesRead; i++) {
            float y = height / 2 + mAudioBuffer[i] * height / 2 / Short.MAX_VALUE;
            mCanvas.drawLine(i * width / bytesRead, height / 2, i * width / bytesRead, y, mPaint);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWaveformView.setImageBitmap(mBitmap);
            }
        });
    }
}