package com.mangoer.indexlietview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.mangoer.indexlietview.R;

import java.util.LinkedHashMap;


/**
 * @ClassName SideLetterBar
 * @Description TODO(字母索引)
 * @author Mangoer
 * @Date 2017/12/19 9:06
 */
public class SideLetterBar extends View {

    private static final String[] index = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};

    //使用该Map，可以保存字母put进去的顺序
    private static LinkedHashMap<String,Integer> indexMap = new LinkedHashMap<>();
    static {
        indexMap.put("A",0);indexMap.put("B",1);
        indexMap.put("C",2);indexMap.put("D",3);
        indexMap.put("E",4);indexMap.put("F",5);
        indexMap.put("G",6);indexMap.put("H",7);
        indexMap.put("I",8);indexMap.put("J",9);
        indexMap.put("K",10);indexMap.put("L",11);
        indexMap.put("M",12);indexMap.put("N",13);
        indexMap.put("O",14);indexMap.put("P",15);
        indexMap.put("Q",16);indexMap.put("R",17);
        indexMap.put("S",18);indexMap.put("T",19);
        indexMap.put("U",20);indexMap.put("V",21);
        indexMap.put("W",22);indexMap.put("X",23);
        indexMap.put("Y",24);indexMap.put("Z",25);
        indexMap.put("#",26);
    }

    private int choose = -1;//当前选中的字母的位置

    private Paint paint = new Paint();
    private Paint paintCircle = new Paint();

    private OnLetterChangedListener onLetterChangedListener;

    private TextView overlay;

    private float circleradius ;
    private int circleColor ;
    private float textSize;
    private float textSize_Big;
    private int textColor;
    private int textChooseColor;

    private int height;//控件高度
    private int width;//控件宽度
    private int singleHeight;//每个字母所占的高度


    public SideLetterBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideLetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideLetterBar(Context context) {
        super(context);
    }

    /**
     * 设置悬浮的textview
     * @param overlay
     */
    public void setOverlay(TextView overlay){
        this.overlay = overlay;
    }

    /**
     * listview拖动时候 顶部第一个item定位到对应的字母
     * @param letter
     */
    public void drawA_ZCircle(String letter){
        if (TextUtils.isEmpty(letter)) return;
        choose = indexMap.get(letter);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        height = getHeight();
        width = getWidth();
        singleHeight = height / index.length;

        circleradius = getResources().getDimension(R.dimen.side_letter_bar_clrcle_size);
        circleColor = getResources().getColor(R.color.letter_clrclr);

        textSize = getResources().getDimension(R.dimen.side_letter_bar_letter_size);
        textSize_Big = getResources().getDimension(R.dimen.side_letter_bar_letter_bigsize);
        textColor = getResources().getColor(R.color.letter_text);
        textChooseColor = getResources().getColor(R.color.white);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < index.length; i++) {
            paint.setAntiAlias(true);
            if (i == choose) {
                //对选中字母进行单独绘制
                paint.setColor(textChooseColor);
                paint.setTextSize(textSize_Big);
                paintCircle.setColor(circleColor);
                paintCircle.setAntiAlias(true);
                paintCircle.setStyle(Paint.Style.FILL);
                canvas.drawCircle(width / 2, singleHeight * (choose + 1) - singleHeight / 3,
                        circleradius, paintCircle);
            } else {
                paint.setTextSize(textSize);
                paint.setColor(textColor);
            }
            float xPos = width / 2 - paint.measureText(index[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(index[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    /**
     * 因为在Listview上方，所以当点击事件来的时候，直接return ，不进行向下分发
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int c = (int) (y / singleHeight);
        final int oldChoose = choose;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (oldChoose != c && onLetterChangedListener != null) {
                    if (c >= 0 && c < index.length) {
                        changeLetterBar(c);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && onLetterChangedListener != null) {
                    if (c >= 0 && c < index.length) {
                        changeLetterBar(c);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                choose = c;
                invalidate();
                if (overlay != null){
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    private void changeLetterBar(int letterPosition) {
        onLetterChangedListener.onLetterChanged(index[letterPosition]);
        choose = letterPosition;
        invalidate();
        //设置悬浮view
        if (overlay != null){
            overlay.setVisibility(VISIBLE);
            overlay.setText(index[letterPosition]);
        }
    }


    public void setOnLetterChangedListener(OnLetterChangedListener onLetterChangedListener) {
        this.onLetterChangedListener = onLetterChangedListener;
    }

    public interface OnLetterChangedListener {
        void onLetterChanged(String letter);
    }

}
