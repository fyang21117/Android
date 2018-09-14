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

import com.example.administrator.WeChats.R;

public class ViewIndicator extends LinearLayout implements View.OnClickListener {

        private int mDefaultIndicator = 0;                          // 默认的选定View
        private static int mCurIndicator;                           // 当前选定View
        private static View[] mIndicators;                          // View集合
        private OnIndicateListener mOnIndicateListener;             // 对应的监听器

        private static final String TAG_ICON_0 = "icon_tag_0";      //// 对应的图标Tag
        private static final String TAG_ICON_1 = "icon_tag_1";
        private static final String TAG_ICON_2 = "icon_tag_2";
        private static final String TAG_ICON_3 = "icon_tag_3";
        private static final String TAG_TEXT_0 = "text_tag_0";      //// 对应的文字Tag
        private static final String TAG_TEXT_1 = "text_tag_1";
        private static final String TAG_TEXT_2 = "text_tag_2";
        private static final String TAG_TEXT_3 = "text_tag_3";

        private static final int COLOR_UNSELECT = Color.BLACK;
        private static final int COLOR_SELECT = Color.BLACK;

        public ViewIndicator(Context context) {
            super(context);
        }

        public ViewIndicator(Context context, AttributeSet attrs) {
            super(context, attrs);
            mCurIndicator = mDefaultIndicator;
            setOrientation(LinearLayout.HORIZONTAL);// 水平布局
            init();
        }
/**
iconResID图片资源;
 IDstringResID文字资源ID;
 stringColor颜色资源ID
iconTag图片标签
textTag文字标签
*/
        private View createIndicator(int iconResID, int stringResID,
                                     int stringColor, String iconTag, String textTag) {
           LinearLayout view = new LinearLayout(getContext());
            view.setOrientation(LinearLayout.VERTICAL);
            view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            view.setGravity(Gravity.CENTER_HORIZONTAL);
            view.setBackgroundResource(R.drawable.indicator_bg_normal);

           ImageView iconView = new ImageView(getContext());
            iconView.setTag(iconTag);
            iconView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            iconView.setImageResource(iconResID);


           TextView textView = new TextView(getContext());
            textView.setTag(textTag);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
            textView.setTextColor(stringColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            textView.setText(stringResID);

            view.addView(iconView);
            view.addView(textView);
            return view;
        }

        private void init() {
            mIndicators = new View[4];
            mIndicators[0] = createIndicator(R.mipmap.chats_focus, R.string.tab_item_home, COLOR_SELECT, TAG_ICON_0, TAG_TEXT_0);
            mIndicators[0].setBackgroundResource(R.drawable.indicator_bg);
            mIndicators[0].setTag(Integer.valueOf(0));
            mIndicators[0].setOnClickListener(this);
            addView(mIndicators[0]);

            mIndicators[1] = createIndicator(R.mipmap.contacts_normal, R.string.tab_item_category, COLOR_UNSELECT, TAG_ICON_1, TAG_TEXT_1);
            mIndicators[1].setBackgroundResource(R.drawable.indicator_bg);
            mIndicators[1].setTag(Integer.valueOf(1));
            mIndicators[1].setOnClickListener(this);
            addView(mIndicators[1]);

            mIndicators[2] = createIndicator(R.mipmap.discover_focus, R.string.tab_item_down, COLOR_UNSELECT, TAG_ICON_2, TAG_TEXT_2);
            mIndicators[2].setBackgroundResource(R.drawable.indicator_bg);
            mIndicators[2].setTag(Integer.valueOf(2));
            mIndicators[2].setOnClickListener(this);
            addView(mIndicators[2]);

            mIndicators[3] = createIndicator(R.mipmap.me_normal, R.string.tab_item_user, COLOR_UNSELECT, TAG_ICON_3, TAG_TEXT_3);
            mIndicators[3].setBackgroundResource(R.drawable.indicator_bg);
            mIndicators[3].setTag(Integer.valueOf(3));
            mIndicators[3].setOnClickListener(this);
            addView(mIndicators[3]);
        }

        public static void setIndicator(int which) {
            //清除之前状态// mIndicators[mCurIndicator].setBackgroundResource(R.drawable.main_tab_item_bg_normal);
            ImageView prevIcon;
            TextView prevText;
            switch (mCurIndicator) {
                case 0:
                    prevIcon = mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_0);
                    prevIcon.setImageResource(R.drawable.indicator_wechat);
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_0);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
                case 1:
                    prevIcon =  mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_1);
                    prevIcon.setImageResource(R.drawable.indicator_contacts);
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_1);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
                case 2:
                    prevIcon =  mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_2);
                    prevIcon.setImageResource(R.drawable.indicator_discover);
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_2);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
                case 3:
                    prevIcon =  mIndicators[mCurIndicator].findViewWithTag(TAG_ICON_3);
                    prevIcon.setImageResource(R.drawable.indicator_me);
                    prevText =  mIndicators[mCurIndicator].findViewWithTag(TAG_TEXT_3);
                    prevText.setTextColor(COLOR_UNSELECT);
                    break;
            }
            // /////////////////更新前状态/////////////////////////////////
            // mIndicators[which].setBackgroundResource(R.drawable.main_tab_item_bg_focus);
            ImageView currIcon;
            TextView currText;
            switch (which) {
                case 0:
                    currIcon =  mIndicators[which].findViewWithTag(TAG_ICON_0);
                    currIcon.setImageResource(R.mipmap.chats_focus);
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_0);
                    currText.setTextColor(COLOR_SELECT);
                    break;
                case 1:
                    currIcon =  mIndicators[which].findViewWithTag(TAG_ICON_1);
                    currIcon.setImageResource(R.mipmap.contacts_focus);
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_1);
                    currText.setTextColor(COLOR_SELECT);
                    break;
                case 2:
                    currIcon =  mIndicators[which].findViewWithTag(TAG_ICON_2);
                    currIcon.setImageResource(R.mipmap.discover_normal);
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_2);
                    currText.setTextColor(COLOR_SELECT);
                    break;
                case 3:
                    currIcon =  mIndicators[which].findViewWithTag(TAG_ICON_3);
                    currIcon.setImageResource(R.mipmap.me_focus);
                    currText =  mIndicators[which].findViewWithTag(TAG_TEXT_3);
                    currText.setTextColor(COLOR_SELECT);
                    break;
            }
            mCurIndicator = which;
        }

        public interface OnIndicateListener {
            public void onIndicate(View v, int which);
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
                        }
                        break;
                    case 1:
                        if (mCurIndicator != 1) {
                            mOnIndicateListener.onIndicate(v, 1);
                            setIndicator(1);
                        }
                        break;
                    case 2:
                        if (mCurIndicator != 2) {
                            mOnIndicateListener.onIndicate(v, 2);
                            setIndicator(2);
                        }
                        break;
                    case 3:
                        if (mCurIndicator != 3) {
                            mOnIndicateListener.onIndicate(v, 3);
                            setIndicator(3);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }
