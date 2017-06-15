package com.huaying.floatmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by huaying on 08/06/2017.
 */

public class FloatMenu extends ViewGroup implements MenuController.ControllerListener {

    public interface OnItemClickListener {
        void onItemClick(View menuButton);
    }

    public interface OnStateUpdateListener {
        void onMenuOpened();
        void onMenuClosed();
    }

    private OnItemClickListener mOnItemClickListener;
    private OnStateUpdateListener mOnStateUpdateListener;
    private MenuController mController = new MenuController(this);

    public FloatMenu(Context context) {
        super(context);
    }

    public FloatMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /** Used for layouts that don't need scroll. */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        for (int i = 0; i<getChildCount(); i++) {
            mController.addButton(getChildAt(i));
        }
    }

    /**
     * Ask all children to measure themselves and compute the measurement of this
     * layout based on the children.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();

        // Measurement will ultimately be computing these values.
        int maxHeight = 0;
        int maxWidth = 0;

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;

            // Measure the child.
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            // Total height is the maximum height of all inner children plus the gutters.
            maxHeight += child.getMeasuredHeight() + child.getPaddingTop() + child.getPaddingBottom();
            maxWidth = Math.max(maxWidth, child.getMeasuredWidth() + child.getPaddingLeft() + child.getPaddingRight());

        }
        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec),
                resolveSize(maxHeight, heightMeasureSpec));
    }

    /**
     * Position all children within this layout.
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        Log.d("mia", "children = " + count);

        // Get the available size of child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        Log.d("mia", "view size~~ " + childLeft + " " + childTop + " " + childRight + " " + childBottom);
        int curWidth, curHeight, curLeft, curBottom;

        curLeft = childLeft;
        curBottom = childBottom;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) continue;

            //Get the maximum size of the child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();

            child.layout(curLeft, curBottom-curHeight, curLeft+curWidth, curBottom);
            Log.d("mia", i + "cur~~ " + curLeft + " " + curBottom + " " + curWidth + " " + curHeight);
            curBottom = curBottom - curHeight - child.getPaddingBottom() - child.getPaddingTop();
        }
    }

    @Override
    public void onItemClick(View menuButton) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(menuButton);
        }
    }

    @Override
    public void onStartOpening() {

    }

    @Override
    public void onOpened() {
        if (mOnStateUpdateListener != null) {
            mOnStateUpdateListener.onMenuOpened();
        }
    }

    @Override
    public void onStartClosing() {
        if (mOnStateUpdateListener != null) {
            mOnStateUpdateListener.onMenuClosed();
        }
    }

    @Override
    public void onClosed() {

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setStateUpdateListener(OnStateUpdateListener onStateUpdateListener) {
        this.mOnStateUpdateListener = onStateUpdateListener;
    }

    public void toggle() {
        mController.toggle();
    }

    public boolean isOpened() {
        return mController.isOpened();
    }

    public void addButton(View menuButton) {
        addView(menuButton, getChildCount()-1);
        mController.addButton(menuButton);
    }
}
