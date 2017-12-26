package com.mangoer.indexlietview.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.mangoer.indexlietview.People;
import com.mangoer.indexlietview.adapter.PeopleAdapter;

/**
 * @ClassName SortListView
 * @Description TODO(listview需要与顶部edittext和侧边字母表进行联动)
 * @author Mangoer
 * @Date 2017/12/19 16:35
 */
public class SortListView extends ListView implements AbsListView.OnScrollListener {

    private final String TAG = "SortListView";

    private TextView tv_hoverTitle;
    private EditTextWithImg editText;
    private SideLetterBar side_letterbar;

    private String lastPy;

    private boolean isScroll;//是否滑动listview
    private boolean isChackLetterBar;//是否手动选中侧边字母表

    public SortListView(Context context) {
        super(context);
        initListener();
    }

    public SortListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initListener();
    }

    public SortListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initListener();
    }

    private void initListener(){
        setOnScrollListener(this);
    }

    public void setTv_hoverTitle(TextView tv_hoverTitle) {
        this.tv_hoverTitle = tv_hoverTitle;
    }

    public void setEditText(EditTextWithImg editText) {
        this.editText = editText;
    }

    public void setSide_letterbar(final SideLetterBar side_letterbar) {
        this.side_letterbar = side_letterbar;
        side_letterbar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                isChackLetterBar = true;
                lastPy = letter;
                //设置顶部悬停view
                tv_hoverTitle.setVisibility(View.VISIBLE);
                tv_hoverTitle.setText(letter);
                //设置listview位置
                PeopleAdapter peopleAdapter = (PeopleAdapter) getAdapter();
                int position = peopleAdapter.getLetterPosition(letter);
                setSelection(position);
                //然后绘制侧边字母表选中状态
                side_letterbar.drawA_ZCircle(letter);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN :
                //当手开始触摸屏幕时候才进行联动 避免刚初始化就绘制报错
                isScroll = true;
                break;
            case MotionEvent.ACTION_MOVE :
                //当手开始滑动屏幕时解除手动选中字母表行为
                isChackLetterBar = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //当滑动时，如果edittext没有输入内容 就清空焦点，恢复初始状态
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
                && editText != null && TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setInput();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (!isScroll) return;
        if (isChackLetterBar) return;
        if (side_letterbar == null) return;
        if (tv_hoverTitle == null) return;
        if (getChildAt(0) == null) return;

        People p = (People) getAdapter().getItem(firstVisibleItem);
        if (p == null) return;
        String py = p.getPinyin();

        int top = Math.abs(getChildAt(0).getTop());//选取当前屏幕可见的第一个item距离顶部距离
        if (top == 0) {//一开始就设置显示，防止闪烁情况
            tv_hoverTitle.setText(py.toUpperCase());
            tv_hoverTitle.setVisibility(View.VISIBLE);
        } else {
            //页面第一次进来后再次滑动，后面的itemview gettop值总不为0
            if (!py.equals(lastPy)) {
                tv_hoverTitle.setText(py.toUpperCase());
                tv_hoverTitle.setVisibility(View.VISIBLE);
            }
        }

        //滑动时判断当前屏幕第一个item是什么字母开头的，然后绘制侧边字母表选中状态
        if (!py.equals(lastPy) ) {//避免重复绘制
            if (TextUtils.equals("#", py)) {
                side_letterbar.drawA_ZCircle(py);
            } else {
                side_letterbar.drawA_ZCircle(py.toUpperCase());
            }
        }

        lastPy = py;

    }
}
