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
import android.view.inputmethod.InputMethodManager;

import com.mangoer.indexlietview.R;

/**
 * @ClassName EditTextWithImg
 * @Description TODO(通过输入框的不同状态去绘制)
 * @author Mangoer
 * @Date 2017/12/19 14:23
 */
public class EditTextWithImg extends AppCompatEditText {

    private Bitmap searchBitmap;
    private Bitmap undoBitmap;

    private float txtSize;
    private int  txtColor;
    private String txt;

    private int height;
    private int width;
    private float searchLeft;
    private float searchTop ;
    private float undoLeft ;
    private float undoTop  ;

    private Paint txtPaint;

    private boolean isInput;

    UnDoListener unDoListener;

    /**
     * 撤销接口
     * @param unDoListener
     */
    public void setUnDoListener(UnDoListener unDoListener) {
        this.unDoListener = unDoListener;
    }

    /**
     * 当listview进行滑动时，要判断下输入框有无内容，没有就恢复初始状态
     */
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
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (searchBitmap == null)
            searchBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.img_search);

        if (undoBitmap == null)
            undoBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.img_undo);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();

        searchLeft = width / 2 - searchBitmap.getWidth() - dpToPx(1);
        searchTop = height / 2 - searchBitmap.getHeight() / 2 + dpToPx(1);
        undoLeft = width - undoBitmap.getWidth() - dpToPx(10);
        undoTop = height / 2 - undoBitmap.getHeight() / 2 ;
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
        } else {
            //当开始点击输入框进行搜索时 绘制删除图标
            canvas.drawBitmap(undoBitmap, undoLeft, undoTop, null);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (searchBitmap != null && !searchBitmap.isRecycled())
            searchBitmap.recycle();
        if (undoBitmap != null && !undoBitmap.isRecycled())
            undoBitmap.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //当手开始点击时，获取焦点，清空初始状态
                setInputMode(true);
                break;
            case MotionEvent.ACTION_UP :
                float x = event.getX();
                //如果手离开屏幕的时候在撤销图标范围内，就回调
                if (x >= undoLeft + 10 && unDoListener != null) {
                    setText("");
                    setInputMode(false);
                    //隐藏软键盘
                    InputMethodManager manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (manager.isActive()) {
                        manager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    unDoListener.unDoClick();
                    return true;
                }
        }
        return super.onTouchEvent(event);
    }

    private void setInputMode(Boolean isInput){
        this.isInput = isInput;
        setFocusable(isInput);
        setFocusableInTouchMode(isInput);
        invalidate();
    }

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public interface UnDoListener{
        void unDoClick();
    }
}
