package com.xf.psychology.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import com.xf.psychology.R;
import com.xf.psychology.ui.NbButton;
import com.xf.psychology.ui.NbButton1;

public class SleepActivity extends AppCompatActivity {
    private NbButton button;
    private NbButton1 Button_Start;
    private RelativeLayout rlContent;
    private Handler handler;
    private Animator animator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        button=findViewById(R.id.button_test);
        rlContent=findViewById(R.id.rl_content);
        Button_Start = findViewById(R.id.btn_start_sleep);
        rlContent.getBackground().setAlpha(0);
        handler=new Handler();
        Button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.startAnim();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        //跳转
                        gotoNew();
                    }
                },3000);

            }
        });
    }

    private void gotoNew() {
        button.gotoNew();

        final Intent intent=new Intent(this,MainActivity.class); //进行设定
        int xc=(button.getLeft()+button.getRight())/2;
        int yc=(button.getTop()+button.getBottom())/2;
        animator= ViewAnimationUtils.createCircularReveal(rlContent,xc,yc,0,1111);
        animator.setDuration(300);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        overridePendingTransition(R.anim.anim_in,R.anim.anim_out);

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
    protected void onStop() {
        super.onStop();
        animator.cancel();
        rlContent.getBackground().setAlpha(0);
        button.regainBackground();
    }

}
