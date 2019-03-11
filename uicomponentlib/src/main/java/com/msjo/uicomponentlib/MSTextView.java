package com.msjo.uicomponentlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.TypedValue;


public class MSTextView extends AppCompatTextView {

    private static final int INPUT_TYPE_DECIMAL = 1;
    private static final int INPUT_TYPE_CAPS_ENG = 2;


    private float defaultTextSize;
    private float afterTextSize;

    private int defaultTextColor;
    private int defaultTextStyle;

    private int afterTextColor;
    private int afterTextStyle;

    private int inputType;

    private boolean isRemoveSpace;


    public MSTextView(Context context) {
        super(context);
    }

    public MSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getAttrs(attrs);

        init();
    }

    public MSTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttrs(attrs, defStyleAttr);

        init();
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MSTextView);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MSTextView, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {

        defaultTextSize = getTextSize();
        defaultTextColor = getCurrentTextColor();
        if(getTypeface() != null) {
            defaultTextStyle = getTypeface().getStyle();
        }


        afterTextSize = typedArray.getFloat(R.styleable.MSTextView_afterTextSize, defaultTextSize);

        afterTextColor = typedArray.getInt(R.styleable.MSTextView_afterTextColor, defaultTextColor);

        afterTextStyle = typedArray.getInt(R.styleable.MSTextView_afterTextStyle, defaultTextStyle);

        inputType = typedArray.getInt(R.styleable.MSTextView_InputType, 0);

        isRemoveSpace = typedArray.getBoolean(R.styleable.MSTextView_removeSpace, false);


        typedArray.recycle();
    }

    private void init(){

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String str = getText().toString().replace(" ", "");
                if(str.length() <= 0){
                    setTextSize(TypedValue.COMPLEX_UNIT_PX,defaultTextSize);
                    setTextColor(defaultTextColor);
                    setTypeface(getTypeface(), defaultTextStyle);
                }else{
                    setTextSize(TypedValue.COMPLEX_UNIT_DIP,afterTextSize);
                    setTextColor(afterTextColor);
                    setTypeface(getTypeface(), afterTextStyle);
                }
            }
        });
    }


    /**
     *
     * <pre>
     * 설명 : 텍스트뷰 스타일을 기본 스타일로 변경
     * </pre>
     * <p>
     * msjo
     *
     * 2019. 01. 08.
     */
    public void setDefaultState(){
        setText("");
        setTextSize(defaultTextSize);
        setTextColor(defaultTextColor);
        setTypeface(getTypeface(), defaultTextStyle);
    }

    public void setText(final String str){
        setText(str);
    }

    public void setText(final String str, final int dpSize){
        setText(str);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, dpSize);
    }

    public void setText(final String str, final int dpSize, final int style){
        setText(str);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, dpSize);
        setTypeface(getTypeface(), style);
    }

    public void setText(final String str, final int dpSize, final int color, final int style){
        setText(str);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, dpSize);
        setTextColor(color);

        if(style > 0) {
            setTypeface(getTypeface(), style);
        }
    }

    public void setText(final CharSequence str, final int dpSize, final int color, final int style){
        setText(str);
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, dpSize);
        setTextColor(color);

        if(style > 0) {
            setTypeface(getTypeface(), style);
        }
    }

}
