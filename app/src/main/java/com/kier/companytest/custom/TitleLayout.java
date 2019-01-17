package com.kier.companytest.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kier.companytest.R;


/**
 * Date： 2016/9/22
 * Description:
 * 标题头
 */
public class TitleLayout extends RelativeLayout {
    //左边view默认属性
    private static final int LEFT_DRAWABLE_PADDING = 10;
    private static final int LEFT_PADDING_LEFT = 40;
    private static final int LEFT_PADDING_TOP = 0;
    private static final int LEFT_PADDING_RIGHT = 36;
    private static final int LEFT_PADDING_BOTTOM = 0;
    private static final int LEFT_PADDING = -1;
    private static final int LEFT_TEXT_SIZE = 16;//默认左边字体大小
    private static final int LEFT_TEXT_COLOR = 0xff343535;//左边默认字体颜色
    //右边view默认属性
    private static final int RIGHT_DRAWABLE_PADDING = 10;
    private static final int RIGHT_PADDING_LEFT = 24;
    private static final int RIGHT_PADDING_TOP = 0;
    private static final int RIGHT_PADDING_RIGHT = 40;
    private static final int RIGHT_PADDING_BOTTOM = 0;
    private static final int RIGHT_PADDING = -1;
    private static final int RIGHT_TEXT_SIZE = 16;//默认右边字体大小
    private static final int RIGHT_TEXT_COLOR = 0xff343535;//右边默认字体颜色
    //中间view默认属性
    private static final int MID_TEXT_SIZE = 19;//默认中间字体大小
    private static final int MID_PADDING_LEFT = 5;
    private static final int MID_PADDING_TOP = 35;
    private static final int MID_PADDING_RIGHT = 5;
    private static final int MID_PADDING_BOTTOM = 35;
    private static final int MID_PADDING = -1;
    private static final int MID_TEXT_COLOR = 0xff343535;//中间默认字体颜色

    private TextView left;
    private TextView mid;
    private TextView right;
    private TextView right2;

    public TitleLayout(Context context) {
        this(context, null);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        left = new TextView(context);
        mid = new TextView(context);
        mid.setMaxLines(1);
        mid.setMaxEms(12);
        mid.setEllipsize(TextUtils.TruncateAt.END);
        right = new TextView(context);
        right2 = new TextView(context);
        //左边view属性
        int leftPadding = LEFT_PADDING;
        int leftPaddingLeft = LEFT_PADDING_LEFT;
        int leftPaddingTop = LEFT_PADDING_TOP;
        int leftPaddingRight = LEFT_PADDING_RIGHT;
        int leftPaddingBottom = LEFT_PADDING_BOTTOM;
        int leftDrawablePadding = LEFT_DRAWABLE_PADDING;
        ColorStateList leftTextColor = ColorStateList.valueOf(LEFT_TEXT_COLOR);//左边字体颜色
        float leftTextSize = LEFT_TEXT_SIZE;//左边字体大小
        left.setBackgroundColor(Color.TRANSPARENT);
        //右边view属性
        int rightPadding = RIGHT_PADDING;
        int rightPaddingLeft = RIGHT_PADDING_LEFT;
        int rightPaddingTop = RIGHT_PADDING_TOP;
        int rightPaddingRight = RIGHT_PADDING_RIGHT;
        int rightPaddingBottom = RIGHT_PADDING_BOTTOM;
        int rightDrawablePadding = RIGHT_DRAWABLE_PADDING;
        ColorStateList rightTextColor = ColorStateList.valueOf(RIGHT_TEXT_COLOR);//右边字体颜色
        float rightTextSize = RIGHT_TEXT_SIZE;//右边字体大小
        right.setBackgroundColor(Color.TRANSPARENT);
        //右边数第二个view属性
        int right2Padding = RIGHT_PADDING;
        int right2PaddingLeft = RIGHT_PADDING_LEFT;
        int right2PaddingTop = RIGHT_PADDING_TOP;
        int right2PaddingRight = RIGHT_PADDING_RIGHT;
        int right2PaddingBottom = RIGHT_PADDING_BOTTOM;
        int right2DrawablePadding = RIGHT_DRAWABLE_PADDING;
        ColorStateList right2TextColor = ColorStateList.valueOf(RIGHT_TEXT_COLOR);//右边字体颜色
        float right2TextSize = RIGHT_TEXT_SIZE;//右边字体大小
        right2.setBackgroundColor(Color.TRANSPARENT);
        //中边view属性
        int midPadding = MID_PADDING;
        int midPaddingLeft = MID_PADDING_LEFT;
        int midPaddingTop = MID_PADDING_TOP;
        int midPaddingRight = MID_PADDING_RIGHT;
        int midPaddingBottom = MID_PADDING_BOTTOM;
        ColorStateList midTextColor = ColorStateList.valueOf(MID_TEXT_COLOR);
        float midTextSize = MID_TEXT_SIZE;
        mid.setBackgroundColor(Color.TRANSPARENT);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleLayout, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                //左边view属性
                case R.styleable.TitleLayout_leftDrawable:
                    left.setCompoundDrawablesWithIntrinsicBounds(a.getDrawable(attr), null, null, null);
                    break;
                case R.styleable.TitleLayout_leftDrawablePadding:
                    leftDrawablePadding = a.getDimensionPixelSize(attr, LEFT_DRAWABLE_PADDING);
                    break;
                case R.styleable.TitleLayout_leftPadding:
                    leftPadding = a.getDimensionPixelOffset(attr, LEFT_PADDING);
                    break;
                case R.styleable.TitleLayout_leftPaddingLeft:
                    leftPaddingLeft = a.getDimensionPixelOffset(attr, LEFT_PADDING_LEFT);
                    break;
                case R.styleable.TitleLayout_leftPaddingTop:
                    leftPaddingTop = a.getDimensionPixelOffset(attr, LEFT_PADDING_TOP);
                    break;
                case R.styleable.TitleLayout_leftPaddingRight:
                    leftPaddingRight = a.getDimensionPixelOffset(attr, LEFT_PADDING_RIGHT);
                    break;
                case R.styleable.TitleLayout_leftPaddingBottom:
                    leftPaddingBottom = a.getDimensionPixelOffset(attr, LEFT_PADDING_BOTTOM);
                    break;
                case R.styleable.TitleLayout_leftText:
                    left.setText(a.getString(attr));
                    break;
                case R.styleable.TitleLayout_leftTextColor:
                    leftTextColor = a.getColorStateList(attr);
                    break;
                case R.styleable.TitleLayout_leftTextSize:
                    leftTextSize = a.getDimensionPixelSize(attr, LEFT_TEXT_SIZE);
                    break;
                case R.styleable.TitleLayout_leftClick://左边点击事件
                    if (context.isRestricted()) {
                        throw new IllegalStateException("The android:onClick attribute cannot "
                                + "be used within a restricted context");
                    }

                    final String handlerLeft = a.getString(attr);
                    if (handlerLeft != null) {
                        left.setOnClickListener(new DeclaredOnClickListener(this, handlerLeft));
                    }
                    break;

                //右边view属性
                case R.styleable.TitleLayout_rightClick://右边点击事件
                    if (context.isRestricted()) {
                        throw new IllegalStateException("The android:onClick attribute cannot "
                                + "be used within a restricted context");
                    }

                    final String handlerRight = a.getString(attr);
                    if (handlerRight != null) {
                        right.setOnClickListener(new DeclaredOnClickListener(this, handlerRight));
                    }
                    break;
                case R.styleable.TitleLayout_rightDrawable:
                    right.setCompoundDrawablesWithIntrinsicBounds(null, null, a.getDrawable(attr), null);
                    break;
                case R.styleable.TitleLayout_rightDrawablePadding:
                    rightDrawablePadding = a.getDimensionPixelSize(attr, RIGHT_DRAWABLE_PADDING);
                    break;
                case R.styleable.TitleLayout_rightPadding:
                    rightPadding = a.getDimensionPixelOffset(attr, RIGHT_PADDING);
                    break;
                case R.styleable.TitleLayout_rightPaddingLeft:
                    rightPaddingLeft = a.getDimensionPixelOffset(attr, RIGHT_PADDING_LEFT);
                    break;
                case R.styleable.TitleLayout_rightPaddingTop:
                    rightPaddingTop = a.getDimensionPixelOffset(attr, RIGHT_PADDING_TOP);
                    break;
                case R.styleable.TitleLayout_rightPaddingRight:
                    rightPaddingRight = a.getDimensionPixelOffset(attr, RIGHT_PADDING_RIGHT);
                    break;
                case R.styleable.TitleLayout_rightPaddingBottom:
                    rightPaddingBottom = a.getDimensionPixelOffset(attr, RIGHT_PADDING_BOTTOM);
                    break;
                case R.styleable.TitleLayout_rightText:
                    right.setText(a.getString(attr));
                    break;
                case R.styleable.TitleLayout_rightTextColor:
                    rightTextColor = a.getColorStateList(attr);
                    break;
                case R.styleable.TitleLayout_rightTextSize:
                    rightTextSize = a.getDimensionPixelSize(attr, RIGHT_TEXT_SIZE);
                    break;
                //右边数第二个view属性
                case R.styleable.TitleLayout_right2Click://右边点击事件
                    if (context.isRestricted()) {
                        throw new IllegalStateException("The android:onClick attribute cannot "
                                + "be used within a restricted context");
                    }

                    final String handlerRight2 = a.getString(attr);
                    if (handlerRight2 != null) {
                        right2.setOnClickListener(new DeclaredOnClickListener(this, handlerRight2));
                    }
                    break;
                case R.styleable.TitleLayout_right2Drawable:
                    right2.setCompoundDrawablesWithIntrinsicBounds(null, null, a.getDrawable(attr), null);
                    break;
                case R.styleable.TitleLayout_right2DrawablePadding:
                    right2DrawablePadding = a.getDimensionPixelSize(attr, RIGHT_DRAWABLE_PADDING);
                    break;
                case R.styleable.TitleLayout_right2Padding:
                    right2Padding = a.getDimensionPixelOffset(attr, RIGHT_PADDING);
                    break;
                case R.styleable.TitleLayout_right2PaddingLeft:
                    right2PaddingLeft = a.getDimensionPixelOffset(attr, RIGHT_PADDING_LEFT);
                    break;
                case R.styleable.TitleLayout_right2PaddingTop:
                    right2PaddingTop = a.getDimensionPixelOffset(attr, RIGHT_PADDING_TOP);
                    break;
                case R.styleable.TitleLayout_right2PaddingRight:
                    right2PaddingRight = a.getDimensionPixelOffset(attr, RIGHT_PADDING_RIGHT);
                    break;
                case R.styleable.TitleLayout_right2PaddingBottom:
                    right2PaddingBottom = a.getDimensionPixelOffset(attr, RIGHT_PADDING_BOTTOM);
                    break;
                case R.styleable.TitleLayout_right2Text:
                    right2.setText(a.getString(attr));
                    break;
                case R.styleable.TitleLayout_right2TextColor:
                    right2TextColor = a.getColorStateList(attr);
                    break;
                case R.styleable.TitleLayout_right2TextSize:
                    right2TextSize = a.getDimensionPixelSize(attr, RIGHT_TEXT_SIZE);
                    break;
                //中间view属性
                case R.styleable.TitleLayout_midText:
                    mid.setText(a.getString(attr));
                    break;
                case R.styleable.TitleLayout_midTextSize:
                    midTextSize = a.getDimensionPixelSize(attr, MID_TEXT_SIZE);
                    break;
                case R.styleable.TitleLayout_midTextColor:
                    midTextColor = a.getColorStateList(attr);
                    break;
                case R.styleable.TitleLayout_midPadding:
                    midPadding = a.getDimensionPixelOffset(attr, MID_PADDING);
                    break;
                case R.styleable.TitleLayout_midPaddingLeft:
                    midPaddingLeft = a.getDimensionPixelOffset(attr, MID_PADDING_LEFT);
                    break;
                case R.styleable.TitleLayout_midPaddingTop:
                    midPaddingTop = a.getDimensionPixelOffset(attr, MID_PADDING_TOP);
                    break;
                case R.styleable.TitleLayout_midPaddingRight:
                    midPaddingRight = a.getDimensionPixelOffset(attr, MID_PADDING_RIGHT);
                    break;
                case R.styleable.TitleLayout_midPaddingBottom:
                    midPaddingBottom = a.getDimensionPixelOffset(attr, MID_PADDING_BOTTOM);
                    break;
            }
        }
        a.recycle();
        //左边view  padding
        if (leftPadding == -1) {
            left.setPadding(leftPaddingLeft, leftPaddingTop, leftPaddingRight, leftPaddingBottom);
        } else {
            left.setPadding(leftPadding, leftPadding, leftPadding, leftPadding);
        }
        left.setTextColor(leftTextColor);//设置左边字体颜色
        left.setTextSize(leftTextSize);//设置左边字体大小
        left.setCompoundDrawablePadding(leftDrawablePadding);

        //右边view padding
        if (rightPadding == -1) {
            right.setPadding(rightPaddingLeft, rightPaddingTop, rightPaddingRight, rightPaddingBottom);
        } else {
            right.setPadding(rightPadding, rightPadding, rightPadding, rightPadding);
        }
        right.setTextColor(rightTextColor);//设置右边字体颜色
        right.setTextSize(rightTextSize);//设置右边字体大小
        right.setCompoundDrawablePadding(rightDrawablePadding);

        //右边第二个view padding
        if (right2Padding == -1) {
            right2.setPadding(right2PaddingLeft, right2PaddingTop, 0, right2PaddingBottom);
        } else {
            right2.setPadding(right2Padding, right2Padding, 0, right2Padding);
        }
        right2.setTextColor(right2TextColor);//设置右边字体颜色
        right2.setTextSize(right2TextSize);//设置右边字体大小
        right2.setCompoundDrawablePadding(right2DrawablePadding);

        //中间view padding
        if (midPadding == -1) {
            mid.setPadding(midPaddingLeft, midPaddingTop, midPaddingRight, midPaddingBottom);
        } else {
            mid.setPadding(midPadding, midPadding, midPadding, midPadding);
        }
        mid.setTextColor(midTextColor);//设置中间字体颜色
        mid.setTextSize(midTextSize);//设置中间字体大小

        //加入父布局
        addView(left);
        addView(mid);
        addView(right2);
        addView(right);

        //左边view布局
        left.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams leftParams = (LayoutParams) left.getLayoutParams();
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
        left.setLayoutParams(leftParams);

        //中间view布局
        LayoutParams params = (LayoutParams) mid.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mid.setLayoutParams(params);


        //右边view布局
        right.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams rightParams = (LayoutParams) right.getLayoutParams();
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
        right.setLayoutParams(rightParams);
        right.measure(0, 0);
        //右边第二个view布局
        right2.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams right2Params = (LayoutParams) right2.getLayoutParams();
        right2Params.setMargins(0, 0, right.getMeasuredWidth() + 12, 0);
        right2Params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        right2Params.addRule(RelativeLayout.CENTER_VERTICAL);
        right2.setLayoutParams(right2Params);
    }

    /**
     * 左边view点击事件
     */
    public void setLeftClickListener(OnClickListener listener) {
        left.setOnClickListener(listener);
    }

    /**
     * 设置左边view是否显示
     */
    public void setLeftVisibility(int visibility) {
        left.setVisibility(visibility);
    }

    /**
     * 设置中间view文字
     */
    public void setMidText(String text) {
        mid.setText(text);
    }

    public void setRightText(String text){
        right.setText(text);
    }

    public void setMidBackgroundResource(@DrawableRes int resid){
        mid.setBackgroundResource(resid);
    }

    /**
     * 设置右边view点击事件
     */
    public void setRightClickListener(OnClickListener listener) {
        right.setOnClickListener(listener);
    }

    /**
     * 设置右边view是否显示
     */
    public void setRightVisibility(int visibility) {
        right.setVisibility(visibility);
    }

    /**
     * 设置右边view点击事件
     */
    public void setRight2ClickListener(OnClickListener listener) {
        right2.setOnClickListener(listener);
    }

    /**
     * 设置右边view是否显示
     */
    public void setRight2Visibility(int visibility) {
        right2.setVisibility(visibility);
    }

    public TextView getMidView(){
        return mid;
    }
}
