package com.example.pass24final;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
    TimeTableActivity2 tImeTableActivity2;
    String[] title;

    public MainAdapter(TimeTableActivity2 tImeTableActivity2, String[] title) {
        this.tImeTableActivity2 = tImeTableActivity2;
        this.title = title;
    }



    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(tImeTableActivity2).inflate(R.layout.new_item,viewGroup,false);

        TextView textView;
        LinearLayout ll_bg;
        ll_bg = view.findViewById(R.id.ll_bg);
        textView = view.findViewById(R.id.textView);
        textView.setText(title[i]);
        return view;
    }
}
