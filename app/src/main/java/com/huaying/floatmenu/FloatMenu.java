package com.huaying.floatmenu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by huaying on 08/06/2017.
 */

public class FloatMenu extends ViewGroup implements MenuController.ControllerListener {
    private List<View> mMenuBtnList;
    private View mFirstMenuBtn;

    public FloatMenu(Context context) {
        super(context);
    }

//    public FloatMenu(View firstMenuBtn, View... otherMenuBtns) {
//        mFirstMenuBtn = firstMenuBtn;
//        Collections.addAll(mMenuBtnList, otherMenuBtns);
//    }

    public void openMenu() {

    }

    public void closeMenu(View selectedBtn) {
        if (mFirstMenuBtn != selectedBtn) {
            mMenuBtnList.add(mFirstMenuBtn);
            mMenuBtnList.remove(selectedBtn);
            mFirstMenuBtn = selectedBtn;
        }
    }

    public View getFirstMenuBtn() {
        return mFirstMenuBtn;
    }

    public List<View> getMenuBtnList() {
        return mMenuBtnList;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public void onItemClick(FloatMenuButton menuButton) {

    }

    @Override
    public void onStartOpening() {

    }

    @Override
    public void onStartClosing() {

    }
}
