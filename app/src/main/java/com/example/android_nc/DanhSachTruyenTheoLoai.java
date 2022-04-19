package com.example.android_nc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachTruyenTheoLoai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachTruyenTheoLoai extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ArrayList<Truyen> list;
    ArrayList<Truyen> listtruyen;
    DatabaseReference reference;
    LoaiTruyen loaiTruyen;

    public DanhSachTruyenTheoLoai() {
        // Required empty public constructor
    }
    public static DanhSachTruyenTheoLoai newInstance(String param1, String param2) {
        DanhSachTruyenTheoLoai fragment = new DanhSachTruyenTheoLoai();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_danh_sach_truyen_theo_loai, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            loaiTruyen = (LoaiTruyen) bundle.getSerializable("loaitruyen");
        }
        ListView gr = view.findViewById(R.id.listtruyen);
        MainActivity main = (MainActivity) getActivity();
        list = new ArrayList<Truyen>();
        listtruyen = new ArrayList<Truyen>();
        reference = FirebaseDatabase.getInstance().getReference().child("tbl_truyen");
        reference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Truyen truyen = dataSnapshot1.getValue(Truyen.class);
                    list.add(truyen);
                }

                for(int i = 0;i<list.size();i++){
                    if (list.get(i).getMaloai() == loaiTruyen.getMa()) {
                        listtruyen.add(list.get(i));
                    }
                }

                Adaptertruyen adaptertruyen = new Adaptertruyen(listtruyen, new Adaptertruyen.IClickitermTruyen() {
                    @Override
                    public void onClickItermTruyen(Truyen truyen, String trangthai) {
                        if(trangthai == "yeuthich")
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(main);
                            builder.setTitle("Bạn có muốn thêm vào danh sach yêu thích?");
                            builder.setMessage("danh sách yêu thích");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    main.gotologin(truyen);
//                                    Toast.makeText(main, "ok", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.show();
                        }
                        if(trangthai == "chitiet")
                        {
                            main.goChitiet(truyen);
                        }
                    }
                });
                gr.setAdapter(adaptertruyen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}