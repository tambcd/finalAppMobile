package com.example.btlappgiaitri.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.btlappgiaitri.Adapter.AdapterMedia;
import com.example.btlappgiaitri.Models.DataVideo;
import com.example.btlappgiaitri.Models.MediaObject;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerView recyclerViewMain;
    private List<MediaObject> mediaObjectList ;
    private AdapterMedia adapterMedia ;

    public HomeActivity() {
        // Required empty public constructor
    }

    public static HomeActivity newInstance(String param1, String param2,RecyclerView recyclerView) {
        HomeActivity fragment = new HomeActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerViewMain = (RecyclerView)getView().findViewById(R.id.recyclerMain);
        adapterMedia = new AdapterMedia(this.getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewMain.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewMain);


        adapterMedia.setdata(getlistMedia());
        recyclerViewMain.setAdapter(adapterMedia);

    }

    private List<MediaObject> getlistMedia() {
        mediaObjectList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Videos");
        Query query = reference.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    DataVideo videodata = ds.getValue(DataVideo.class);
                    assert videodata != null;
                    mediaObjectList.add(0,new MediaObject(videodata.getID(),videodata.getID_TK(), videodata.getTitle(), videodata.getTimestamo(), videodata.getVideourl(), false, true));
                    adapterMedia.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return  mediaObjectList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home, container, false);
    }


}