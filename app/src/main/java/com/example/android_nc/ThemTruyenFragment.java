package com.example.android_nc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemTruyenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemTruyenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    LoaiTruyen loaiTruyen;
    Timestamp timestamp;

    public ThemTruyenFragment() {
        // Required empty public constructor
    }
    public static ThemTruyenFragment newInstance(String param1, String param2) {
        ThemTruyenFragment fragment = new ThemTruyenFragment();
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
        View view = inflater.inflate(R.layout.fragment_them_truyen, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            loaiTruyen = (LoaiTruyen) bundle.getSerializable("loaitruyen");
        }

        Button btn = view.findViewById(R.id.btn_save);
        EditText inputNoidung = view.findViewById(R.id.noidungtruyen);
        EditText inputTentruyen = view.findViewById(R.id.tentruyen);
        MainAdmin main = (MainAdmin) getActivity();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noidung = inputNoidung.getText().toString();
                String tentruyen = inputTentruyen.getText().toString();
                timestamp = new Timestamp(System.currentTimeMillis());
                if(noidung.equals(""))
                {
                    Toast.makeText(main, "Vui lòng nhập nội dung", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tentruyen.equals(""))
                {
                    Toast.makeText(main, "Vui lòng nhập tên truyện", Toast.LENGTH_SHORT).show();
                    return;
                }
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tbl_truyen");
                Truyen truyen = new Truyen(timestamp.getTime(),tentruyen,noidung,loaiTruyen.getMa(),loaiTruyen.getName());
                mDatabase.child(String.valueOf(timestamp.getTime())).setValue(truyen);
                Toast.makeText(main, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}