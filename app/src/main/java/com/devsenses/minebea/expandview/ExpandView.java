package com.devsenses.minebea.expandview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.devsenses.minebea.R;

/**
 * Created by Horus on 2/2/2015.
 */
public class ExpandView {
    LinearLayout mLinearLayoutMain;
    LinearLayout mLinearLayout;
    Context context;
    public ExpandView(Context context, LinearLayout mLinearLayoutMain , LinearLayout mLinearLayout) {
        this.mLinearLayoutMain = mLinearLayoutMain;
        this.mLinearLayout = mLinearLayout;
        this.context = context;

        setBGMain();
    }
    void setBGMain(){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mLinearLayoutMain.setBackgroundDrawable( context.getResources().getDrawable(R.drawable.spinner_default_holo_light) );
        } else {
            mLinearLayoutMain.setBackground( context.getResources().getDrawable(R.drawable.spinner_default_holo_light));
        }
    }
    void clearBGMain(){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mLinearLayoutMain.setBackgroundDrawable(null);
        } else {
            mLinearLayoutMain.setBackground(null);
        }
    }
    public void expand() {
        //set Visible
        clearBGMain();  //Clear BG.
        mLinearLayout.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
        mAnimator.start();
    }
    public void collapse() {

        setBGMain();                                   //Set BG.
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mAnimator.start();
    }
    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();

                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }





}
