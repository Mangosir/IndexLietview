package com.mangoer.indexlietview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mangoer.indexlietview.People;
import com.mangoer.indexlietview.R;

import java.util.HashMap;
import java.util.List;

/**
 * @ClassName PeopleAdapter
 * @Description TODO()
 * @author Mangoer
 * @Date 2017/12/18 15:58
 */
public class PeopleAdapter extends BaseAdapter {

    public final String PEOPLE_CLICK_TYPE_CALL = "call";
    public final String PEOPLE_CLICK_TYPE_COPY = "copy";
    public final String PEOPLE_CLICK_TYPE_DETAIL = "detail";

    private Context mContext;
    private List<People> peopleList;

    private OnImageClickListener imageClickListener ;
    private HashMap<String, Integer> letterIndexes = new HashMap<>();;


    public PeopleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setImageClickListener(OnImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
        int size = peopleList.size();
        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = peopleList.get(index).getPinyin().toUpperCase();
            if (!letterIndexes.containsKey(currentLetter)) {
                letterIndexes.put(currentLetter, index);
            }
        }
    }

    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getCount() {
        if (peopleList == null)
            return 0;
        return peopleList.size();
    }

    @Override
    public Object getItem(int position) {
        if (peopleList == null)
            return  null;
        return peopleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewholder holder;
        if (convertView == null) {
            holder = new Viewholder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_people, parent, false);
            holder.ll_people = (LinearLayout) convertView.findViewById(R.id.ll_people);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_letter = (TextView) convertView.findViewById(R.id.tv_letter);
            holder.iv_callcompany = (ImageView) convertView.findViewById(R.id.iv_callcompany);
            holder.iv_copycompany = (ImageView) convertView.findViewById(R.id.iv_copycompany);
            holder.iv_callphone= (ImageView) convertView.findViewById(R.id.iv_callphone);
            holder.iv_copyphone = (ImageView) convertView.findViewById(R.id.iv_copyphone);
            holder.iv_calloffice= (ImageView) convertView.findViewById(R.id.iv_calloffice);
            holder.iv_copyoffice = (ImageView) convertView.findViewById(R.id.iv_copyoffice);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }

        final String city = peopleList.get(position).getName();
        holder.tv_name.setText(city);

        String currentLetter = peopleList.get(position).getPinyin();
        if (position == 0) {
            holder.tv_letter.setVisibility(View.VISIBLE);
            holder.tv_letter.setText(currentLetter.toUpperCase());
        } else {
            String previousLetter = peopleList.get(position-1).getPinyin();
            if (!TextUtils.equals(currentLetter, previousLetter)){
                holder.tv_letter.setVisibility(View.VISIBLE);
                holder.tv_letter.setText(currentLetter.toUpperCase());
            }else{
                holder.tv_letter.setVisibility(View.GONE);
            }
        }

        initListener(holder,position);

        return convertView;
    }

    private void initListener(Viewholder holder,int position) {
        holder.ll_people.setOnClickListener(new ClickListener(position));
        holder.iv_callcompany.setOnClickListener(new ClickListener(position));
        holder.iv_copycompany.setOnClickListener(new ClickListener(position));
        holder.iv_callphone.setOnClickListener(new ClickListener(position));
        holder.iv_copyphone.setOnClickListener(new ClickListener(position));
        holder.iv_calloffice.setOnClickListener(new ClickListener(position));
        holder.iv_copyoffice.setOnClickListener(new ClickListener(position));
    }

    class ClickListener implements View.OnClickListener{

        int position;

        public ClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            int ID = v.getId();
            People p = peopleList.get(position);
            switch (ID) {
                case R.id.ll_people :
                    imageClickListener.onPeopleClick(p, PEOPLE_CLICK_TYPE_DETAIL);
                    break;
                case R.id.iv_callcompany :
                    imageClickListener.onPeopleClick(p, PEOPLE_CLICK_TYPE_CALL);
                    break;
                case R.id.iv_copycompany :
                    imageClickListener.onPeopleClick(p, PEOPLE_CLICK_TYPE_COPY);
                    break;
                case R.id.iv_callphone :
                    imageClickListener.onPeopleClick(p, PEOPLE_CLICK_TYPE_CALL);
                    break;
                case R.id.iv_copyphone :
                    imageClickListener.onPeopleClick(p, PEOPLE_CLICK_TYPE_COPY);
                    break;
                case R.id.iv_calloffice :
                    imageClickListener.onPeopleClick(p, PEOPLE_CLICK_TYPE_CALL);
                    break;
                case R.id.iv_copyoffice :
                    imageClickListener.onPeopleClick(p, PEOPLE_CLICK_TYPE_COPY);
                    break;
            }

        }
    }


    class Viewholder{
        LinearLayout ll_people;
        TextView tv_name;
        TextView tv_letter;
        ImageView iv_callcompany;
        ImageView iv_copycompany;
        ImageView iv_callphone;
        ImageView iv_copyphone;
        ImageView iv_calloffice;
        ImageView iv_copyoffice;
    }

    public interface OnImageClickListener{
        void onPeopleClick(People p, String type);
    }

}
