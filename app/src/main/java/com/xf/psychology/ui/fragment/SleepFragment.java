package com.xf.psychology.ui.fragment;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.xf.psychology.R;
import com.xf.psychology.ui.NbButton;
import com.xf.psychology.ui.NbButton1;
import com.xf.psychology.ui.activity.MainActivity;
import com.xf.psychology.ui.activity.ReportActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class SleepFragment extends Fragment {
    private NbButton button;
    private NbButton1 Button_Start;
    private RelativeLayout rlContent;
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
    private static final int LOUDNESS_THRESHOLD = 5000;

    private Handler handler1;
    private Runnable sampleRunnable;

    private Button buttonStart;
    private Button buttonStop;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
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

        button = root.findViewById(R.id.button_test);
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
                            textView3.setText("当前分贝值：" + 20*Math.log10(amplitude));
                            handler1.postDelayed(this, SAMPLE_DELAY_MS);
                        }
                    }
                };
                handler1.postDelayed(sampleRunnable, SAMPLE_DELAY_MS);
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtra("awaketime",awakeTime/1000);
                intent.putExtra("lightsleeptime",lightSleepTime/1000);
                intent.putExtra("deepsleeptime",deepSleepTime/1000);
                startActivity(intent);
                button.startAnim();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //跳转
                        gotoNew();
                    }
                },3000);
                awakeTime=0;
                lightSleepTime=0;
                deepSleepTime=0;
            }
        });

        return root;
    }

    private void gotoNew() {
        button.gotoNew();

        final Intent intent = new Intent(getActivity(), MainActivity.class); //进行设定
        int xc = (button.getLeft() + button.getRight()) / 2;
        int yc = (button.getTop() + button.getBottom()) / 2;
        animator = ViewAnimationUtils.createCircularReveal(rlContent,xc,yc,0,1111);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
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

    @Override
    public void onStop() {
        super.onStop();
        if (animator != null) {
            animator.cancel();
        }
        rlContent.getBackground().setAlpha(0);
        button.regainBackground();
    }
}