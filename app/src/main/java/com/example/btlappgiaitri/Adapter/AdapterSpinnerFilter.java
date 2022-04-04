package com.example.btlappgiaitri.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlappgiaitri.R;
import com.example.btlappgiaitri.message.MessageActivity;

public class AdapterSpinnerFilter extends BaseAdapter {


    private MessageActivity activity;
    int iconsNotifyFilter[];
    String[] contextNotifyFilter;
    LayoutInflater inflater;

    public AdapterSpinnerFilter( MessageActivity activity, int[] iconsNotifyFilter, String[] contextNotifyFilter) {
        this.activity = activity;
        this.iconsNotifyFilter = iconsNotifyFilter;
        this.contextNotifyFilter = contextNotifyFilter;
        this.inflater = (LayoutInflater) activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return iconsNotifyFilter.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_spinner_notify, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
        icon.setImageResource(iconsNotifyFilter[i]);
        names.setText(contextNotifyFilter[i]);
        return view;
    }
}
