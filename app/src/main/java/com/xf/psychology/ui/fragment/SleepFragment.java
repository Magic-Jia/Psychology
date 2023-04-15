package com.xf.psychology.ui.fragment;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseFragment;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.ui.NbButton;
import com.xf.psychology.ui.NbButton1;
import com.xf.psychology.ui.activity.MainActivity;
import com.xf.psychology.ui.activity.ReportActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

public class SleepFragment extends BaseFragment {
    private NbButton button_stop;
    private NbButton1 Button_Start;


    //new wave
    private AudioRecord mAudioRecord;
    private int mBufferSize;
    private short[] mAudioBuffer;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private ImageView mWaveformView;



    private androidx.constraintlayout.widget.ConstraintLayout rlContent;
    private Handler handler;
    private Animator animator;
    private MediaRecorder recorder;
    private boolean isRecording = false;
    private String fileName;
    private int awakeTime = 0;
    private int lightSleepTime = 0;
    private int deepSleepTime = 0;

    private static final int SAMPLE_RATE = 44100;
    private static final int SAMPLE_DELAY_MS = 1000;
    private static final int BUFFER_SIZE = 1024;
    private static final int RECORD_AUDIO_REQUEST_CODE = 1;
    private static final int LOUDNESS_THRESHOLD = 6000;

    private Handler handler1;
    private Runnable sampleRunnable;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private Thread thread;

    public static SleepFragment newInstance() {
        Bundle args = new Bundle();
        SleepFragment fragment = new SleepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_sleep, container, false);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        //wave
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mWaveformView = root.findViewById(R.id.waveform_view);

        mBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        mAudioBuffer = new short[mBufferSize];
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
        } else {
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, mBufferSize);
            mAudioRecord.startRecording();
        }
        mBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        //end


        button_stop = root.findViewById(R.id.button_test);
        rlContent = root.findViewById(R.id.rl_content);
        Button_Start = root.findViewById(R.id.btn_start_sleep);
        rlContent.getBackground().setAlpha(0);
        handler = new Handler();
        textView = root.findViewById(R.id.tv_sleep_time);
        textView2 = root.findViewById(R.id.tv_sleep_status);
        textView3 = root.findViewById(R.id.tv_decibel);

        Button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                File dir = getContext().getExternalFilesDir(null);

                fileName = dir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".3gp";
                recorder.setOutputFile(fileName);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    recorder.prepare();
                } catch (IOException e) {
                    Log.e("MainActivity", "prepare() failed");
                }
                recorder.start();
                isRecording = true;

                handler1 = new Handler(Looper.getMainLooper());
                sampleRunnable = new Runnable() {
                    @Override
                    public void run() {
                        if (isRecording) {
                            int amplitude = recorder.getMaxAmplitude();
                            if (amplitude < LOUDNESS_THRESHOLD) {
                                if (amplitude < 3000) {
                                    lightSleepTime += SAMPLE_DELAY_MS;
                                    textView2.setText("浅睡眠");
                                } else {
                                    deepSleepTime += SAMPLE_DELAY_MS;
                                    textView2.setText("深睡眠");
                                }
                            } else {
                                awakeTime += SAMPLE_DELAY_MS;
                                textView2.setText("清醒");
                            }
                            textView.setText("清醒时间：" + awakeTime / 1000 + "秒\n浅睡眠时间：" + lightSleepTime / 1000 + "秒\n深睡眠时间：" + deepSleepTime / 1000 + "秒");
                            double voice = (20*Math.log10(amplitude));
                            String result = String .format("%.0f",voice);
                            textView3.setText("当前分贝值：" + result);
                            handler1.postDelayed(this, SAMPLE_DELAY_MS);
                        }
                    }
                };
                handler1.postDelayed(sampleRunnable, SAMPLE_DELAY_MS);
            }

        });

        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRecording == false) return;
                isRecording = false;
                recorder.stop();
                recorder.release();
                recorder = null;
                handler1.removeCallbacks(sampleRunnable);
                textView.setText("清醒时间：" + awakeTime / 1000 + "秒\n浅睡眠时间：" + lightSleepTime / 1000 + "秒\n深睡眠时间：" + deepSleepTime / 1000 + "秒");
                // To start a new activity, we need to create an Intent object
                // The first parameter is the current context (in this case, MainActivity.this)
                // The second parameter is the class of the activity we want to start (in this case, Report.class)
                // We then call startActivity() with the intent as the parameter to start the new activity
                button_stop.startAnim();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        thread.interrupt();
                        //跳转
                        gotoNew();
                    }
                },3000);

            }
        });
        return root;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        if (App.isLogin()) {

        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void findViewsById(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    private void gotoNew() {
        button_stop.gotoNew();
        Intent intent = new Intent(getActivity(), ReportActivity.class);
        intent.putExtra("awaketime",awakeTime/1000);
        intent.putExtra("lightsleeptime",lightSleepTime/1000);
        intent.putExtra("deepsleeptime",deepSleepTime/1000);
        startActivity(intent);
        awakeTime=0;
        lightSleepTime=0;
        deepSleepTime=0;
        int xc = (button_stop.getLeft() + button_stop.getRight()) / 2;
        int yc = (button_stop.getTop() + button_stop.getBottom()) / 2;
        animator = ViewAnimationUtils.createCircularReveal(rlContent,xc,yc,0,1111);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getActivity().overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                    }
                },200);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
        rlContent.getBackground().setAlpha(255);
    }

    public void onPause() {
        super.onPause();
        if (mAudioRecord != null) {
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }

//wave
    @Override
    public void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
        } else {
            startRecording();
        }
    }

    private void startRecording() {
        if (mAudioRecord != null) {
            mAudioRecord.startRecording();

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if(mAudioRecord != null) {
                            int bytesRead = mAudioRecord.read(mAudioBuffer, 0, mBufferSize);
                            drawWaveform(bytesRead);
                        }
                        else break;
                    }
                }
            });
            thread.start();
        } else {
            // mAudioRecord 对象为 null，进行相应处理
        }
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

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWaveformView.setImageBitmap(mBitmap);
            }
        });
    }

    //end
    @Override
    public void onStop() {
        super.onStop();
        if (animator != null) {
            animator.cancel();
        }
        rlContent.getBackground().setAlpha(0);
        button_stop.regainBackground();
    }
}