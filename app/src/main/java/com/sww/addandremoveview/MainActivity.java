package com.sww.addandremoveview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LinearLayout linearLayout;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = findViewById(R.id.linear_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addView(null);
        setupTransition(linearLayout);
    }

    private void setupTransition(final ViewGroup viewGroup) {
        final LayoutTransition transition = new LayoutTransition();
        viewGroup.setLayoutTransition(transition);
        transition.setAnimator(LayoutTransition.APPEARING, transition.getAnimator(LayoutTransition.APPEARING));
        transition.setAnimator(LayoutTransition.DISAPPEARING, transition.getAnimator(LayoutTransition.DISAPPEARING));
//        transition.setAnimator(LayoutTransition.CHANGE_APPEARING, transition.getAnimator(LayoutTransition.CHANGE_APPEARING));
//        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, transition.getAnimator(LayoutTransition.CHANGE_DISAPPEARING));
        transition.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {

            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                viewGroup.post(new Runnable() {
                    @Override
                    public void run() {
                        if (viewGroup.getChildCount() > 1) viewGroup.removeViewAt(0);
                    }
                });
            }
        });
    }

    private Animator appearingAnim() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(null, "translationY", 100, 0));
        return set;
    }

    private Animator disappearingAnim() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(ObjectAnimator.ofFloat(null, "translationY", 0, -200));
        return set;
    }

    public void addView(View view) {
        TextView textView = new TextView(MainActivity.this);
        textView.setText("哈哈"+(count++));
        linearLayout.addView(textView);
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
//        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
//        layoutParams.height = linearLayout.getHeight()/2;
//        textView.setLayoutParams(layoutParams);
    }

    public void removeView(View view) {
        Log.e(TAG, "removeView: " + linearLayout.getChildCount());
        if (linearLayout.getChildCount()>1){
            linearLayout.removeView(linearLayout.getChildAt(0));
        }
    }

}
