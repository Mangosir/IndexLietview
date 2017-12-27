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
import android.util.TypedValue;
import android.view.MotionEvent;

import com.mangoer.indexlietview.R;

/**
 * @ClassName EditTextWithImg
 * @Description TODO(通过输入框的不同状态去绘制)
 * @author Mangoer
 * @Date 2017/12/19 14:23
 */
public class EditTextWithImg extends AppCompatEditText {

    private Bitmap searchBitmap;

    private float txtSize;
    private int  txtColor;
    private String txt;

    private int height;//控件本身的高度
    private int width;//控件本身的宽度
    private float searchLeft;//搜索图片 距离控件左边的距离
    private float searchTop ;//搜索图片距离控件顶部的距离

    private Paint txtPaint;

    private boolean isInput;//是否是手动开始输入
    private boolean isNeedDraw ;//避免重复绘制

    private InputListener inputListener;

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    /**
     * 当listview进行滑动时，要判断下输入框有无内容，没有就恢复初始状态
     */
    public void setInput() {
        if (TextUtils.isEmpty(getText().toString().trim()) && isNeedDraw) {
            setInputMode(false);
            if (inputListener != null) {
                inputListener.inputOver();
            }
        }
    }

    /**
     * 设置edittext是否获取焦点
     * @param isInput
     */
    public void setInputMode(Boolean isInput){
        this.isInput = isInput;
        isNeedDraw = isInput;
        setFocusable(isInput);
        setFocusableInTouchMode(isInput);
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
        //自定义的一些属性 提示文字大小，颜色，内容
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
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (searchBitmap == null)
            searchBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.img_search);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();

        searchLeft = width / 2 - searchBitmap.getWidth() - dpToPx(1);
        searchTop = height / 2 - searchBitmap.getHeight() / 2 + dpToPx(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //当没有点击输入框且输入框无内容时，绘制初始状态
        if (!isInput && TextUtils.isEmpty(getText().toString().trim())) {
            canvas.drawText(txt, width / 2, height / 2 + txtSize / 2, txtPaint);
            if (searchBitmap != null) {
                canvas.drawBitmap(searchBitmap, searchLeft, searchTop, null);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (searchBitmap != null && !searchBitmap.isRecycled())
            searchBitmap.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //当手开始点击时，获取焦点，清空初始状态
                setInputMode(true);
                if (inputListener != null) {
                    inputListener.inputBegin();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public interface InputListener{
       void inputBegin();
        void inputOver();
    }
}
