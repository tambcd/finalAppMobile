package com.example.btlappgiaitri.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.AddFriend;
import com.example.btlappgiaitri.R;
import com.example.btlappgiaitri.entertain.MovieActivity;
import com.example.btlappgiaitri.navigation_bar.navigationBarActivity;
import com.example.btlappgiaitri.personal.PersonalActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterAddFriend extends BaseAdapter implements Filterable {
    private MovieActivity activity;
    private Context context;
    
    ArrayList<AddFriend> data;

    ArrayList<AddFriend> dataBackup;

    private LayoutInflater inflater;

    Fragment fragment=null;

    public AdapterAddFriend(Context context) {
        this.context = context;
    }

    public AdapterAddFriend(MovieActivity activity, ArrayList<AddFriend> data) {
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
    public AddFriend getItem(int i) {
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
            v=inflater.inflate(R.layout.add_friend_item, null);

            TextView txtFullName = v.findViewById(R.id.txtName_AF);
            txtFullName.setText(data.get(i).getName());

            Button btnXacNhan = v.findViewById(R.id.btnXacNhan_AF);
            if(!data.get(i).isCheck()){
                btnXacNhan.setText("Kết bạn");
            }
            btnXacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(btnXacNhan.getText().equals("Xác nhận")){ //button xác nhận
                        addFirebase("IDTK1",LoginActivity.ID_Tk,"IDTK2",getItem(i).getIDtk(),"BanBe");
                        addFirebase("IDTaiKhoan1",LoginActivity.ID_Tk,"IDTaiKhoan2",getItem(i).getIDtk(),"RoomChat");
                        XoaLoiMoi(i);
                        navigationBarActivity.chipNavigationBar.showBadge(R.id.Person);
                    }
                    else{ //button kết bạn
                        addFirebase("IDTKGui",LoginActivity.ID_Tk,"IDTKNhan",getItem(i).getIDtk(),"LoiMoi");
                        XoaDeXuat(i);
                    }
                }
            });

            Button btnXoa = v.findViewById(R.id.btnXoa);
            btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getItem(i).isCheck()){//Button xóa trong listLoiMoi
                        XoaLoiMoi(i);
                    }
                    else {//Button xóa trong listDeXuat
                        XoaDeXuat(i);
                    }

                }
            });

            ImageView avatar = v.findViewById(R.id.avatar_MessageItem);
            Picasso.get().load(data.get(i).getAvatar()).into(avatar);
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    navigationBarActivity.id_user = getItem(i).getIDtk();
                    navigationBarActivity.chipNavigationBar.dismissBadge(R.id.Person);
                    fragment = new PersonalActivity();
                    activity.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            });
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
                    ArrayList<AddFriend> newData = new ArrayList<>();
                    for (AddFriend u :dataBackup ) {
                        if(u.getName().toLowerCase().contains(charSequence.toString().toLowerCase())){
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
                data = new ArrayList<AddFriend>();
                ArrayList<AddFriend> tmp = (ArrayList<AddFriend>) filterResults.values;
                for (AddFriend u : tmp){
                    data.add(u);
                }
                notifyDataSetChanged();
                if(data.size()==0){
                    MovieActivity.txtNotFound.setVisibility(View.VISIBLE);
                    MovieActivity.txtNotFound1.setVisibility(View.VISIBLE);
                }
                else{
                    MovieActivity.txtNotFound.setVisibility(View.GONE);
                    MovieActivity.txtNotFound1.setVisibility(View.GONE);
                }

            }
        };
        return f;
    }

    //Thêm dữ liệu vào firebase
    private void addFirebase(String id1,String s1,String id2, String s2,String path ){
        String timestamp = "" + System.currentTimeMillis();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("ID",timestamp);
        hashMap.put(id1,s1);
        hashMap.put(id2,s2);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(path);
        reference.child(timestamp).setValue(hashMap);
    }

    //xóa lời mời i khỏi listLoiMoi
    private  void XoaLoiMoi(int i){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("LoiMoi").child(getItem(i).getID()).removeValue();
        ArrayList<AddFriend> list = new ArrayList<>();
        for (AddFriend add: MovieActivity.listLoiMoi) {
            if(!add.getIDtk().equals(getItem(i).getIDtk())){
                list.add(add);
            }
        }
        MovieActivity.listLoiMoi=list;
        MovieActivity.lstLoiMoiAdapter = new AdapterAddFriend(activity,MovieActivity.listLoiMoi);
        MovieActivity.lvLoiMoi.setAdapter(MovieActivity.lstLoiMoiAdapter);
    }

    //Xóa đề xuất i khỏi listDeXuat
    private  void XoaDeXuat(int i){
        ArrayList<AddFriend> list = new ArrayList<>();
        for (AddFriend add: MovieActivity.listDeXuat) {
            if(!add.getIDtk().equals(getItem(i).getIDtk())){
                list.add(add);
            }
        }
        MovieActivity.listDeXuat=list;
        MovieActivity.lstDeXuatAdapter = new AdapterAddFriend(activity,MovieActivity.listDeXuat);
        MovieActivity.lvDeXuat.setAdapter(MovieActivity.lstDeXuatAdapter);
    }

}
