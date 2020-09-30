package com.example.administrator.WeChats;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static java.lang.Integer.valueOf;

public class ViewIndicator extends LinearLayout implements View.OnClickListener {
        private int mDefaultIndicator = 0;                          // 默认的选定View
        private static int mCurIndicator;                           // 当前选定View
        private static View[] mIndicators;                          // View集合
        private OnIndicateListener mOnIndicateListener;             // 对应的监听器

        private static final String TAG_TEXT_0 = "text_tag_0";      //// 对应的文字Tag
        private static final String TAG_TEXT_1 = "text_tag_1";
        private static final String TAG_TEXT_2 = "text_tag_2";
        private static final String TAG_TEXT_3 = "text_tag_3";

        private static final int COLOR_UNSELECT = Color.GRAY;
        private static final int COLOR_SELECT = Color.BLACK;

        public ViewIndicator(Context context) {
            super(context);
        }

        public ViewIndicator(Context context, AttributeSet attrs) {
            super(context, attrs);
            mCurIndicator = mDefaultIndicator;
            setOrientation(LinearLayout.VERTICAL);// 水平布局
            init();
        }

        private View createIndicator( int stringResID, int stringColor,  String textTag)
        {
           LinearLayout view = new LinearLayout(getContext());
            view.setOrientation(LinearLayout.HORIZONTAL);
            view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            view.setGravity(Gravity.CENTER_HORIZONTAL);

           TextView textView = new TextView(getContext());
            textView.setTag(textTag);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            textView.setTextColor(stringColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textView.setText(stringResID);

            view.addView(textView);
            return view;
        }

        private void init() {
            mIndicators = new View[4];
            mIndicators[0] = createIndicator( R.string.tab_item_home, COLOR_SELECT,  TAG_TEXT_0);
            mIndicators[0].setTag(0);
            mIndicators[0].setOnClickListener(this);
            addView(mIndicators[0]);

            mIndicators[1] = createIndicator( R.string.tab_item_category, COLOR_UNSELECT, TAG_TEXT_1);
            mIndicators[1].setTag(1);
            mIndicators[1].setOnClickListener(this);
            addView(mIndicators[1]);

            mIndicators[2] = createIndicator( R.string.tab_item_down, COLOR_UNSELECT,  TAG_TEXT_2);
            mIndicators[2].setTag(2);
            mIndicators[2].setOnClickListener(this);
            addView(mIndicators[2]);

            mIndicators[3] = createIndicator(R.string.tab_item_user, COLOR_UNSELECT, TAG_TEXT_3);
            mIndicators[3].setTag(3);
            mIndicators[3].setOnClickListener(this);
            addView(mIndicators[3]);
        }

        public static void setIndicator(int which) {
            //清除之前状态
            mIndicators[mCurIndicator].setBackgroundResource(R.drawable.indicator_bg_normal);
            TextView prevText;
            switch (mCurIndicator) {
                case 0:
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_0);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
                case 1:
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_1);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
                case 2:
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_2);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
                case 3:
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_3);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
            }
            // /////////////////更新前状态/////////////////////////////////
            TextView currText;
            switch (which) {
                case 0:
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_0);
                    currText.setTextColor(COLOR_SELECT);
                    break;
                case 1:
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_1);
                    currText.setTextColor(COLOR_SELECT);
                    break;
                case 2:
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_2);
                    currText.setTextColor(COLOR_SELECT);
                    break;
                case 3:
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_3);
                    currText.setTextColor(COLOR_SELECT);
                    break;
            }
            mCurIndicator = which;
        }

        public interface OnIndicateListener {
             void onIndicate(View v, int which);
        }

        public void setOnIndicateListener(OnIndicateListener listener) {
            mOnIndicateListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mOnIndicateListener != null) {
                int tag = (Integer) v.getTag();
                switch (tag) {
                    case 0:
                        if (mCurIndicator != 0) {
                            mOnIndicateListener.onIndicate(v, 0);
                            setIndicator(0);
                        }break;
                    case 1:
                        if (mCurIndicator != 1) {
                            mOnIndicateListener.onIndicate(v, 1);
                            setIndicator(1);
                        }break;
                    case 2:
                        if (mCurIndicator != 2) {
                            mOnIndicateListener.onIndicate(v, 2);
                            setIndicator(2);
                        }break;
                    case 3:
                        if (mCurIndicator != 3) {
                            mOnIndicateListener.onIndicate(v, 3);
                            setIndicator(3);
                        }break;
                    default:
                        break;
                }
            }
        }
    }
