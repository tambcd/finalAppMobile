package com.example.btlappgiaitri.entertain;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.btlappgiaitri.Adapter.AdapterAddFriend;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.AddFriend;
import com.example.btlappgiaitri.Models.BanBe;
import com.example.btlappgiaitri.Models.LoiMoi;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MovieActivity extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static ListView lvLoiMoi,lvDeXuat;
    LinearLayout linearLoiMoi,linearDeXuat;
    EditText etSearch;
    public static TextView txtNotFound , txtNotFound1;

    public static AdapterAddFriend lstLoiMoiAdapter,lstDeXuatAdapter;
    public static ArrayList<AddFriend> listLoiMoi,listDeXuat;

    public MovieActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieActivity newInstance(String param1, String param2) {
        MovieActivity fragment = new MovieActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtNotFound = (TextView) getView().findViewById(R.id.txtNotFound);
        txtNotFound1 =(TextView) getView().findViewById(R.id.txtNotFound1);
        linearLoiMoi = (LinearLayout) getView().findViewById(R.id.linearLoiMoi);
        linearDeXuat = (LinearLayout) getView().findViewById(R.id.linearDeXuat);
        etSearch =(EditText) getView().findViewById(R.id.etSearch_AddRoomChat);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Filter theo tên
                lstLoiMoiAdapter.getFilter().filter(charSequence.toString());
                lstLoiMoiAdapter.notifyDataSetChanged();

                lstDeXuatAdapter.getFilter().filter(charSequence.toString());
                lstDeXuatAdapter.notifyDataSetChanged();
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        lvLoiMoi = (ListView) getView().findViewById(R.id.listLoiMoi);
        listLoiMoi = new ArrayList<>();
        lstLoiMoiAdapter = new AdapterAddFriend(MovieActivity.this,listLoiMoi);
        lvLoiMoi.setAdapter(lstLoiMoiAdapter);

        lvDeXuat = (ListView) getView().findViewById(R.id.listDeXuat);
        listDeXuat=new ArrayList<>();
        lstDeXuatAdapter = new AdapterAddFriend(MovieActivity.this,listDeXuat);
        lvDeXuat.setAdapter(lstDeXuatAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user= snapshot.getValue(User.class);
                if(!user.getIdtk().equals(LoginActivity.ID_Tk)){
                    //load list lời mời
                    reference.child("LoiMoi").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            ArrayList<LoiMoi> arrayListLM = new ArrayList<LoiMoi>();
                            for (DataSnapshot postSnapshot: snapshot.getChildren()){
                                LoiMoi loiMoi = postSnapshot.getValue(LoiMoi.class);
                                arrayListLM.add(loiMoi);
                                if(user.getIdtk().equals(loiMoi.getIDTKGui())&&LoginActivity.ID_Tk.equals(loiMoi.getIDTKNhan())){
                                    listLoiMoi.add(new AddFriend(loiMoi.getID(),user.getIdtk(),user.getAvatar(),user.getName(),true));
                                    lstLoiMoiAdapter.notifyDataSetChanged();
                                    if(listLoiMoi.size()>0){
                                        linearLoiMoi.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        linearLoiMoi.setVisibility(View.GONE);
                                    }
                                }
                            }

                            //Load danh sách đề xuất
                            reference.child("BanBe").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean checkBB = true;
                                    for (DataSnapshot postSnapshot: snapshot.getChildren()){
                                        BanBe banBe = postSnapshot.getValue(BanBe.class);
                                        //kiểm tra đã user và ID_tk(tài khoản đăng nhập) là bạn bè hay chưa
                                        if((banBe.getIDTK1().equals(LoginActivity.ID_Tk)&&banBe.getIDTK2().equals(user.getIdtk()))
                                                ||(banBe.getIDTK2().equals(LoginActivity.ID_Tk)&&banBe.getIDTK1().equals(user.getIdtk()))){
                                            checkBB=false;
                                            break;
                                        }
                                    }
                                    for (AddFriend addf:listDeXuat ) {
                                        if(addf.getIDtk().equals(user.getIdtk())){
                                            checkBB=false;
                                            break;
                                        }
                                    }

                                    for(LoiMoi item : arrayListLM){
                                        if((item.getIDTKNhan().equals(LoginActivity.ID_Tk)&&item.getIDTKGui().equals(user.getIdtk()))
                                         ||(item.getIDTKGui().equals(LoginActivity.ID_Tk)&&item.getIDTKNhan().equals(user.getIdtk()))){
                                            checkBB=false;
                                            break;
                                        }
                                    }

                                    if(checkBB){
                                        listDeXuat.add(new AddFriend("**",user.getIdtk(),user.getAvatar(),user.getName(),false));
                                        lstDeXuatAdapter.notifyDataSetChanged();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) { }
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_movie, container, false);
    }

}