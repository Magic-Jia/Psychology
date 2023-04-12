package com.xf.psychology.ui.fragment;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xf.psychology.R;
import com.xf.psychology.ui.NbButton;
import com.xf.psychology.ui.NbButton1;
import com.xf.psychology.ui.activity.MainActivity;

public class SleepFragment extends Fragment {
    private NbButton button;
    private NbButton1 Button_Start;
    private RelativeLayout rlContent;
    private Handler handler;
    private Animator animator;

    public static SleepFragment newInstance() {
        Bundle args = new Bundle();
        SleepFragment fragment = new SleepFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_sleep, container, false);

        button = root.findViewById(R.id.button_test);
        rlContent = root.findViewById(R.id.rl_content);
        Button_Start = root.findViewById(R.id.btn_start_sleep);
        rlContent.getBackground().setAlpha(0);
        handler = new Handler();

        Button_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Do something
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
        animator.cancel();
        rlContent.getBackground().setAlpha(0);
        button.regainBackground();
    }
}