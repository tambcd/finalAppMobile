package com.example.btlappgiaitri.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlappgiaitri.Models.MessageData;
import com.example.btlappgiaitri.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMessage extends BaseAdapter {
    private Activity activity;

    ArrayList<MessageData> data;

    private LayoutInflater inflater;

    public AdapterMessage(Activity activity, ArrayList<MessageData> data) {
        this.activity = activity;
        this.data = data;
        //Tạo đối tượng phân tích Layout
        this.inflater = (LayoutInflater) activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public MessageData getItem(int i) {
        return  data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v =view;
        if(v==null){
            //phân tích layout đã được tạo cho một phần tử trong listview
            v=inflater.inflate(R.layout.list_item_notify, null);
            //tham chiếu đến từng phần tử và trình bày dữ liệu lên view
            TextView txtFullName = v.findViewById(R.id.txtFullName);
            txtFullName.setText(data.get(i).getFullName());

            TextView txtTextContent = v.findViewById(R.id.txtTextContent);
            txtTextContent.setText(data.get(i).getTextContent());

            TextView txtTime = v.findViewById(R.id.txtTime);
            txtTime.setText(data.get(i).getTextTime());

            ImageView avatar = v.findViewById(R.id.avatar);
            Picasso.get().load(data.get(i).getAvartar()).into(avatar);
        }
        return v;
    }

}
