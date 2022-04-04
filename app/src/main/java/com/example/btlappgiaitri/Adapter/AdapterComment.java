package com.example.btlappgiaitri.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Models.Comment;
import com.example.btlappgiaitri.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterComment  extends RecyclerView.Adapter<AdapterComment.CommentViewMain>{
    List<Comment> CommentObjectList;
    private Context context;
    public AdapterComment(Context context){
        this.context = context;
    }

    public  void  setdata(List<Comment> list){
        this.CommentObjectList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommentViewMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment,parent,false);
        return new CommentViewMain(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewMain holder, int position) {
        Comment comment = CommentObjectList.get(position);
        if(comment ==null){
            return;
        }
        holder.txtName.setText(comment.getName());
        holder.txtContent.setText(comment.getNoiDung());
        Picasso.get().load(comment.getAvatar()).into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        if(this.CommentObjectList !=null){
            return CommentObjectList.size();
        }
        return 0;
    }

    public class CommentViewMain extends RecyclerView.ViewHolder {
        TextView txtName,txtContent;
        ImageView avatar;
        public CommentViewMain(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtContent = itemView.findViewById(R.id.txtContent);
            avatar = itemView.findViewById(R.id.avatar_MessageItem);
        }
    }



}
