package com.huaying.floatmenu;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huaying on 08/06/2017.
 */

class MenuController {
    private static final int STATE_CLOSED = 1;
    private static final int STATE_OPENED = 2;
    private static final int STATE_CLOSING = 3;
    private static final int STATE_OPENING = 4;

    private static final int ANIM_DURATION = 300;
    private static final float ANIM_ALPHA_0 = 0.0f;
    private static final float ANIM_ALPHA_100 = 1.0f;

    interface ControllerListener {
        void onItemClick(View menuButton);
        void onStartOpening();
        void onOpened();
        void onStartClosing();
        void onClosed();
    }

    private final ControllerListener mListener;
    private final List<View> mButtons = new ArrayList<>();
    private int mState;
    private View mActivatedButton;

    MenuController(ControllerListener listener) {
        this.mListener = listener;
        this.mState = STATE_CLOSED;
    }

    void toggle() {
        if (isOpened()) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        if (isOpened()) return;

        setState(STATE_OPENING);
        setOpenAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(STATE_OPENED);
            }
        }, ANIM_DURATION);
    }

    private void close() {
        if (!isOpened()) return;

        setState(STATE_CLOSING);
        setCloseAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(STATE_CLOSED);
            }
        }, ANIM_DURATION);
    }

    boolean isOpened() {
        return mState == STATE_OPENED;
    }

    private void setOpenAnimation() {
        float fromY = 1.0f;
        for (int i=1; i<getButtonsNumber(); i++) {
            View button = mButtons.get(i);
            if (button.getVisibility() != View.GONE) {
                AnimationSet openAnim = new AnimationSet(true);
                openAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                openAnim.setDuration(ANIM_DURATION);
                Animation translate = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, fromY,
                        Animation.RELATIVE_TO_SELF, 0);
                Animation alpha = new AlphaAnimation(ANIM_ALPHA_0, ANIM_ALPHA_100);
                openAnim.addAnimation(translate);
                openAnim.addAnimation(alpha);
                button.startAnimation(openAnim);
                fromY++;
            }
        }

    }

    private void setCloseAnimation() {
        float toY = 1.0f;
        for (int i=1; i<getButtonsNumber(); i++) {
            View button = mButtons.get(i);
            if (button.getVisibility() != View.GONE) {
                AnimationSet openAnim = new AnimationSet(true);
                openAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                openAnim.setDuration(ANIM_DURATION);
                Animation translate = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, toY);
                Animation alpha = new AlphaAnimation(ANIM_ALPHA_100, ANIM_ALPHA_0);
                openAnim.addAnimation(translate);
                openAnim.addAnimation(alpha);
                button.startAnimation(openAnim);
                toY++;
            }
        }
    }

    private void setState(int state) {
        this.mState = state;
        switch (state) {
            case STATE_CLOSED:
                setButtonsEnable(true);
                setButtonsVisibility(View.GONE);
                break;
            case STATE_CLOSING:
                setButtonsEnable(false);
                mListener.onStartClosing();
                break;
            case STATE_OPENED:
                setButtonsEnable(true);
                break;
            case STATE_OPENING:
                setButtonsEnable(false);
                setButtonsVisibility(View.VISIBLE);
                mListener.onStartOpening();
                break;
        }
    }

    void addButton(final View menuButton) {
        mButtons.add(menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v);
                    if (v != getMenuButton()) {
                        mActivatedButton = v;
                    }
                }
            }
        });
    }

    private int getButtonsNumber() {
        return mButtons.size();
    }

    private View getMenuButton() {
        return mButtons.get(0);
    }

    void initActivatedButton(View menuButton) {
        mActivatedButton = menuButton;
    }

    void setButtonsVisibility(int visibility) {
        for (int i=1; i<getButtonsNumber(); i++) {
            View button = mButtons.get(i);
            if (mActivatedButton == button && mState == STATE_OPENING) {
                button.setVisibility(View.GONE);
            } else {
                button.setVisibility(visibility);
            }
        }
    }

    void setMenuButtonBackground(Drawable drawable) {
        getMenuButton().setBackground(drawable);
    }

    private void setButtonsEnable(boolean isEnabled) {
        for (int i=0; i<getButtonsNumber(); i++) {
            mButtons.get(i).setEnabled(isEnabled);
        }
    }

}
