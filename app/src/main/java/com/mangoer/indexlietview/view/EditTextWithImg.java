package com.mangoer.indexlietview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.mangoer.indexlietview.R;

/**
 * @ClassName EditTextWithImg
 * @Description TODO()
 * @author Mangoer
 * @Date 2017/12/19 14:23
 */
public class EditTextWithImg extends AppCompatEditText {


    private Bitmap mBitmap;

    private float txtSize;
    private int  txtColor;
    private String txt;
    int height;
    int width;

    private Paint txtPaint;
    private Paint imgPaing;

    private boolean isInput;

    public void setInput() {
        if (TextUtils.isEmpty(getText().toString().trim())) {
            isInput = false;
        }
        invalidate();
    }

    public EditTextWithImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResource(attrs);
        initPaint();
    }

    public EditTextWithImg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initResource(attrs);
        initPaint();
    }

    private void initResource(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.etimg);
        float density = getResources().getDisplayMetrics().density;//像素密度
        txtSize = typedArray.getDimension(R.styleable.etimg_edit_txtsize,11*density+0.5f);
        txtColor = typedArray.getColor(R.styleable.etimg_edit_txtcolor,0x000000);
        txt = typedArray.getString(R.styleable.etimg_edit_txt);
        typedArray.recycle();
    }

    private void initPaint() {
        txtPaint = new Paint();
        txtPaint.setTextSize(txtSize);
        txtPaint.setColor(txtColor);
        txtPaint.setAntiAlias(true);
        imgPaing = new Paint();
        imgPaing.setAntiAlias(true);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mBitmap == null) {
            mBitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.img_search);
        }
        Log.e("onAttachedToWindow","mBitmap");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInput && TextUtils.isEmpty(getText().toString().trim())) {
            canvas.drawText(txt, width / 2, height / 2 + txtSize / 2, txtPaint);
            if (mBitmap != null) {
                canvas.drawBitmap(mBitmap, width / 2 - mBitmap.getWidth() - 3, height / 2 - mBitmap.getHeight() / 2 + 3, imgPaing);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
        Log.e("onDetachedFromWindow","mBitmap");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //当手开始点击时，获取焦点，清空初始状态
                isInput = true;
                setFocusable(true);
                setFocusableInTouchMode(true);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}
