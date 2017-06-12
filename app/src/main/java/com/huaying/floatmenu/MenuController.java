package com.huaying.floatmenu;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huaying on 08/06/2017.
 */

public class MenuController {
    public static final int STATE_CLOSED = 1;
    public static final int STATE_OPENED = 2;
    public static final int STATE_CLOSING = 3;
    public static final int STATE_OPENING = 4;
    private static final int ANIMATION_DURATION = 100;

    public interface ControllerListener {
        void onItemClick(FloatMenuButton menuButton);
        void onStartOpening();
        void onStartClosing();
    }

    private List<FloatMenuButton> mButtons = new ArrayList<>();
    private Map<FloatMenuButton, ButtonPoint> mButtonPositions = new HashMap<>();
    private int mState;
    private final ControllerListener mListener;

    public MenuController(ControllerListener listener) {
        this.mListener = listener;
        this.mState = STATE_CLOSED;
    }

    public void toggle() {
        if (isOpened()) {
            close();
        } else {
            open();
        }
    }

    private void open() {
        if (isOpened()) return;

        setState(STATE_OPENING);
        
        setState(STATE_OPENED);
    }

    private void close() {
        if (!isOpened()) return;

        setState(STATE_CLOSING);
        setState(STATE_CLOSED);
    }

    public boolean isOpened() {
        return mState == STATE_OPENED;
    }

    public void setState(int state) {
        this.mState = state;
        switch (state) {
            case STATE_CLOSED:
                setButtonsVisibility(View.INVISIBLE);
                break;
            case STATE_CLOSING:
                setButtonsEnable(false);
                mListener.onStartClosing();
                break;
            case STATE_OPENED:
                setButtonsVisibility(View.VISIBLE);
                setButtonsEnable(true);
                break;
            case STATE_OPENING:
                setButtonsEnable(false);
                mListener.onStartOpening();
                break;
        }
    }

    public void addButton(final FloatMenuButton menuButton) {
        mButtons.add(menuButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(menuButton);
            }
        });
    }

    private void onItemClick(FloatMenuButton menuButton) {
        if (mListener != null) {
            mListener.onItemClick(menuButton);
        }
    }

    public int getButtonsNumber() {
        return mButtons.size();
    }

    public void setButtonsVisibility(int visibility) {
        for (int i=0; i<getButtonsNumber(); i++) {
            mButtons.get(i).setVisibility(visibility);
        }
    }

    public void setButtonsEnable(boolean isEnabled) {
        for (int i=0; i<getButtonsNumber(); i++) {
            mButtons.get(i).setEnabled(isEnabled);
        }
    }

    public ButtonPoint getButtonsPoint(FloatMenuButton menuButton) {
        return mButtonPositions.get(menuButton);
    }

}
