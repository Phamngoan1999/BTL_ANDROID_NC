package com.example.android_nc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachTruyenTheoLoaiAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachTruyenTheoLoaiAdmin extends Fragment {

    LoaiTruyen loaiTruyen;
    DatabaseReference reference;
    ArrayList<Truyen> list;
    ArrayList<Truyen> listtruyen;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DanhSachTruyenTheoLoaiAdmin() {
        // Required empty public constructor
    }

    public static DanhSachTruyenTheoLoaiAdmin newInstance(String param1, String param2) {
        DanhSachTruyenTheoLoaiAdmin fragment = new DanhSachTruyenTheoLoaiAdmin();
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
        View view = inflater.inflate(R.layout.fragment_danh_sach_truyen_theo_loai_admin, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            loaiTruyen = (LoaiTruyen) bundle.getSerializable("loaitruyen");
        }
        ListView gr = view.findViewById(R.id.listtruyen);
        MainAdmin main = (MainAdmin) getActivity();
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

                Adaptertruyenadmin adaptertruyen = new Adaptertruyenadmin(listtruyen, new Adaptertruyenadmin.IClickitermTruyen() {
                    @Override
                    public void onClickItermTruyen(Truyen truyen, String trangthai) {
                        if(trangthai == "xoa")
                        {
                            AlertDialog.Builder builder = new AlertDialog.Builder(main);
                            builder.setTitle("Bạn có chắc chẵn xóa truyện không?");
                            builder.setMessage("Sau khi xóa sẽ không thể khôi phục");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    main.goChitiet(truyen,trangthai);
//                                    Toast.makeText(main, "ok", Toast.LENGTH_SHORT).show();
                                }
                            });
                            builder.show();
                        }else
                        {
                            main.goChitiet(truyen,trangthai);
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