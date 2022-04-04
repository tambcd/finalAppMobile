package com.example.btlappgiaitri.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Models.BanBe;
import com.example.btlappgiaitri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFriend  extends RecyclerView.Adapter<AdapterFriend.FriendList>{

    private List<BanBe> banBeList;
    private  Context context;


    public AdapterFriend(Context context){
        this.context = context;
    }

    public  void  setdata(List<BanBe> list){
        this.banBeList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FriendList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfrieands,parent,false);
        return new FriendList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendList holder, int position) {
        BanBe banBe = banBeList.get(position);
        if(banBe==null){
            return;
        }
        Picasso.get().load(banBe.getAvatar()).into(holder.imgAvatar);
        holder.name.setText(banBe.getName());
    }

    @Override
    public int getItemCount() {
        if(banBeList !=null){
            return banBeList.size();
        }
        return 0;
    }

    public class FriendList extends RecyclerView.ViewHolder{

        ImageView imgAvatar;
        TextView name;
        public FriendList(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.avatarFriend);
            name = itemView.findViewById(R.id.txtfriend);
        }
    }
}
