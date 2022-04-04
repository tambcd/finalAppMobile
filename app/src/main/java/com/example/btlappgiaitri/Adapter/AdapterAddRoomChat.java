package com.example.btlappgiaitri.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btlappgiaitri.Models.MessageData;
import com.example.btlappgiaitri.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterAddRoomChat extends BaseAdapter implements Filterable {
    private Activity activity;

    ArrayList<MessageData> data;

    ArrayList<MessageData> dataBackup;

    private LayoutInflater inflater;

    public AdapterAddRoomChat(Activity activity, ArrayList<MessageData> data) {
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
            v=inflater.inflate(R.layout.add_roomchat_item, null);

            TextView txtFullName = v.findViewById(R.id.txtName_AddRoomchatItem);
            txtFullName.setText(data.get(i).getFullName());

            ImageView avatar = v.findViewById(R.id.avatar_AddRoomchatItem);
            Picasso.get().load(data.get(i).getAvartar()).into(avatar);
        }
        return v;
    }

    //Filter theo tên
    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if(dataBackup==null){
                    dataBackup = new ArrayList<>(data);
                }

                //Nếu chuỗi filter là rộng thì khôi phục dữ liệu
                if(charSequence ==null|| charSequence.length()==0){
                    fr.count = dataBackup.size();
                    fr.values = dataBackup;
                }
                //còn nếu không rỗng thì thực hiện filter
                else {
                    ArrayList<MessageData> newData = new ArrayList<>();
                    for (MessageData u :dataBackup ) {
                        if(u.getFullName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                            newData.add(u);
                        }
                    }
                    fr.count = newData.size();
                    fr.values = newData;
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = new ArrayList<MessageData>();
                ArrayList<MessageData> tmp = (ArrayList<MessageData>) filterResults.values;
                for (MessageData u : tmp){
                    data.add(u);
                }
                notifyDataSetChanged();
            }
        };
        return f;
    }



}
