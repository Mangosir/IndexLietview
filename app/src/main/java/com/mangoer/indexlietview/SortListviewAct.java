package com.mangoer.indexlietview;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mangoer.indexlietview.adapter.PeopleAdapter;
import com.mangoer.indexlietview.util.PinyinUtils;
import com.mangoer.indexlietview.view.EditTextWithImg;
import com.mangoer.indexlietview.view.SideLetterBar;
import com.mangoer.indexlietview.view.SortListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Intent.ACTION_DIAL;

public class SortListviewAct extends AppCompatActivity implements PeopleAdapter.OnImageClickListener {

    @Bind(R.id.et_search)
    EditTextWithImg editText;
    @Bind(R.id.listview)
    SortListView listview;
    @Bind(R.id.tv_hoverTitle)
    TextView tv_hoverTitle;
    @Bind(R.id.tv_letter_overlay)
    TextView tv_overlay;
    @Bind(R.id.side_letter_bar)
    SideLetterBar side_letterbar;

    List<People> peopleList = new ArrayList<>();
    List<People> peopleSearchList = new ArrayList<>();
    PeopleAdapter peopleAdapter;
    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //侧边字母表与悬浮view联动
        side_letterbar.setOverlay(tv_overlay);
        //让listview与edittext，侧边字母列表，顶部悬停view互动
        listview.setTv_hoverTitle(tv_hoverTitle);
        listview.setEditText(editText);
        listview.setSide_letterbar(side_letterbar);

        initdata();

        setListener();
    }

    public void setListener(){

        editText.setUnDoListener(new EditTextWithImg.UnDoListener() {
            @Override
            public void unDoClick() {
                if (peopleList.size() != 0) {
                    peopleAdapter.setPeopleList(peopleList);
                    peopleAdapter.notifyDataSetChanged();
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String msg = s.toString().trim();
                //在这里进行模糊搜索，我这只是举例，实际上可能是去数据库或者服务器去查
                /*Iterator<People> iterator = peopleList.iterator();
                while (iterator.hasNext()){
                    p = iterator.next();
                }*/
                peopleSearchList.clear();
                for (int i=0; i<peopleList.size(); i++) {
                    People p = peopleList.get(i);
                    if (p.getName().contains(msg)) {
                        peopleSearchList.add(p);
                    }
                }

                if (peopleSearchList.size() != 0) {
                    peopleAdapter.setPeopleList(peopleSearchList);
                    peopleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initdata() {

        People p1 = new People("啊包",PinyinUtils.getFirstLetter("啊包".toCharArray()[0]));
        People p2 = new People("巴包", PinyinUtils.getFirstLetter("巴包".toCharArray()[0]));
        People p29 = new People("比包", PinyinUtils.getFirstLetter("比包".toCharArray()[0]));
        People p3 = new People("草包",PinyinUtils.getFirstLetter("草包".toCharArray()[0]));
        People p30 = new People("擦包",PinyinUtils.getFirstLetter("擦包".toCharArray()[0]));
        People p4 = new People("德包",PinyinUtils.getFirstLetter("德包".toCharArray()[0]));
        People p5 = new People("恶包",PinyinUtils.getFirstLetter("恶包".toCharArray()[0]));
        People p6 = new People("发包",PinyinUtils.getFirstLetter("发包".toCharArray()[0]));
        People p7 = new People("给包",PinyinUtils.getFirstLetter("给包".toCharArray()[0]));
        People p8 = new People("好包",PinyinUtils.getFirstLetter("好包".toCharArray()[0]));
        People p9 = new People("i包",PinyinUtils.getFirstLetter("i包".toCharArray()[0]));
        People p10 = new People("加包",PinyinUtils.getFirstLetter("加包".toCharArray()[0]));
        People p11 = new People("开包",PinyinUtils.getFirstLetter("开包".toCharArray()[0]));
        People p12 = new People("老包",PinyinUtils.getFirstLetter("老包".toCharArray()[0]));
        People p13 = new People("没包",PinyinUtils.getFirstLetter("没包".toCharArray()[0]));
        People p14 = new People("拿包",PinyinUtils.getFirstLetter("拿包".toCharArray()[0]));
        People p15 = new People("你包",PinyinUtils.getFirstLetter("你包".toCharArray()[0]));

        People p20 = new People("哦包",PinyinUtils.getFirstLetter("哦包".toCharArray()[0]));
        People p21 = new People("平包",PinyinUtils.getFirstLetter("平包".toCharArray()[0]));
        People p22 = new People("请包",PinyinUtils.getFirstLetter("请包".toCharArray()[0]));

        People p23 = new People("日包",PinyinUtils.getFirstLetter("日包".toCharArray()[0]));
        People p24 = new People("啥包",PinyinUtils.getFirstLetter("啥包".toCharArray()[0]));
        People p25 = new People("听包",PinyinUtils.getFirstLetter("听包".toCharArray()[0]));

        People p26 = new People("我包",PinyinUtils.getFirstLetter("我包".toCharArray()[0]));
        People p27 = new People("想包",PinyinUtils.getFirstLetter("想包".toCharArray()[0]));
        People p28 = new People("有包",PinyinUtils.getFirstLetter("有包".toCharArray()[0]));

        People p16 = new People("找包",PinyinUtils.getFirstLetter("找包".toCharArray()[0]));
        People p17 = new People("@包",PinyinUtils.getFirstLetter("@包".toCharArray()[0]));
        People p19 = new People("啊啊",PinyinUtils.getFirstLetter("啊啊".toCharArray()[0]));
        People p18 = new People("啊撒",PinyinUtils.getFirstLetter("啊sa".toCharArray()[0]));


        peopleList.add(p1);
        peopleList.add(p18);
        peopleList.add(p19);
        peopleList.add(p2);
        peopleList.add(p3);
        peopleList.add(p4);
        peopleList.add(p5);
        peopleList.add(p6);
        peopleList.add(p7);
        peopleList.add(p8);
        peopleList.add(p9);
        peopleList.add(p10);
        peopleList.add(p11);
        peopleList.add(p12);
        peopleList.add(p13);
        peopleList.add(p14);
        peopleList.add(p15);
        peopleList.add(p16);
        peopleList.add(p17);

        peopleList.add(p20);
        peopleList.add(p21);
        peopleList.add(p22);
        peopleList.add(p23);
        peopleList.add(p24);
        peopleList.add(p25);
        peopleList.add(p26);
        peopleList.add(p27);
        peopleList.add(p28);
        peopleList.add(p29);
        peopleList.add(p30);

        Collections.sort(peopleList, new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {

                String py1 = o1.getPinyin();
                String py2 = o2.getPinyin();

                if (py1.equals(py2)) {
                    return PinyinUtils.getChineseSpell(o1.getName()).compareTo(PinyinUtils.getChineseSpell(o2.getName()));
                } else {
                    if ("#".equals(py1)) {
                        return 1;
                    } else if ("#".equals(py2)) {
                        return -1;
                    }
                    return py1.compareTo(py2);
                }
            }
        });

        peopleAdapter = new PeopleAdapter(this);
        peopleAdapter.setPeopleList(peopleList);
        peopleAdapter.setImageClickListener(this);
        listview.setAdapter(peopleAdapter);
    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onPeopleClick(People p, String type) {

        if (peopleAdapter.PEOPLE_CLICK_TYPE_CALL.equals(type)) {
            //ACTION_CALL：直接拨号；
            //ACTION_DIAL：调用拨号程序，手工拨出。
            Uri uri = Uri.parse("tel:"+"18010928373");
            Intent intent = new Intent();
            intent.setAction(ACTION_DIAL);
            intent.setData(uri);
            startActivity(intent);

        } else if (peopleAdapter.PEOPLE_CLICK_TYPE_COPY.equals(type)) {

            if (clipboardManager == null) {
                clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            }
            ClipData clipData = ClipData.newPlainText("phoneNum", "18010928373");
            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(this, "拷贝成功：18010928373", Toast.LENGTH_LONG).show();

            // 检查剪贴板是否有内容
            if (clipboardManager.hasPrimaryClip()) {
                String content = new String();
                ClipData data = clipboardManager.getPrimaryClip();
                int count = data.getItemCount();
                Log.e("复制", "count=" + count);
                for (int i = 0; i < count; i++) {
                    ClipData.Item item = data.getItemAt(i);
                    CharSequence cs = item.coerceToText(this);
                    content += cs;
                }
                Log.e("复制", "content=" + content);
            }
        } else {
            startActivity(new Intent(this,PeopleDetailAct.class));
        }
    }
}
