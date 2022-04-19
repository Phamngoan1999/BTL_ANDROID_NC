package com.example.android_nc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFlagment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFlagment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ArrayList<LoaiTruyen> list;
    List<LoaiTruyen> listloai;
    DatabaseReference reference;
    public HomeFlagment() {
    }

    public static HomeFlagment newInstance(String param1, String param2) {
        HomeFlagment fragment = new HomeFlagment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_flagment, container, false);
        GridView gr = view.findViewById(R.id.gridView);
        MainActivity main = (MainActivity)getActivity();
        reference = FirebaseDatabase.getInstance().getReference().child("tbl_loaitruyen");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<LoaiTruyen>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    LoaiTruyen p = dataSnapshot1.getValue(LoaiTruyen.class);
                    list.add(p);
                }
                LoaiTruyenListViewAdapter loaiTruyenListViewAdapter = new LoaiTruyenListViewAdapter(list, new LoaiTruyenListViewAdapter.IClickitermloai() {
                    @Override
                    public void onClickItermUser(LoaiTruyen loaiTruyen) {
                        main.danhSachTruyenTheoloai(loaiTruyen);
                    }
                });
                gr.setAdapter(loaiTruyenListViewAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}