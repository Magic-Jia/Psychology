package com.xf.psychology.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.xf.psychology.R;



public class LimitEditText extends androidx.appcompat.widget.AppCompatEditText {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int maxInputCount;
    private int currentCount;
    private boolean isMax;

    public boolean isMax() {
        return currentCount > maxInputCount;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    private int offset = 30;

    public LimitEditText(Context context) {
        this(context, null);
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LimitEditText);
        maxInputCount = array.getInt(R.styleable.LimitEditText_maxInputCount, 200);
        array.recycle();
        initTextWatcher();
        initPaint();
    }


    private void initPaint() {
        paint.setTextSize(getTextSize() / 4 * 3);
    }


    private void initTextWatcher() {

        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals("\n") || source.equals(" ")) return "";
                return null;
            }
        };
        //限制空格回车
        setFilters(new InputFilter[]{inputFilter});


        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentCount = s.length();
                if (onMaxListener != null) {
                    if (isMax != currentCount > maxInputCount) {
                        onMaxListener.isMax(currentCount > maxInputCount);
                    }

                }
                if (currentCount > maxInputCount) {
                    isMax = true;
                    paint.setColor(Color.RED);
                } else {
                    isMax = false;
                    paint.setColor(Color.BLACK);
                }
                invalidate();
            }
        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        String s = currentCount + "/" + maxInputCount;
        float showTextWidth = paint.measureText(s);
        int width = getWidth();
        int height = getHeight();
        float x = width + getScrollX() - showTextWidth - offset;
        float y = height + getScrollY() - offset;
        canvas.drawText(s, x, y, paint);
        super.onDraw(canvas);
    }

    private float dp2px(int value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }

    public void setOnMaxListener(OnMaxListener onMaxListener) {
        this.onMaxListener = onMaxListener;
    }

    OnMaxListener onMaxListener;

    public interface OnMaxListener {
        void isMax(boolean max);
    }

    public boolean isEmpty() {
        return getText().toString().isEmpty();
    }

    public void clean() {
        setText("");
    }

}
