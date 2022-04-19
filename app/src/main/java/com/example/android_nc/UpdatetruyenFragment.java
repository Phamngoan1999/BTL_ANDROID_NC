package com.example.android_nc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatetruyenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatetruyenFragment extends Fragment {

    Truyen truyen;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    public UpdatetruyenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatetruyenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatetruyenFragment newInstance(String param1, String param2) {
        UpdatetruyenFragment fragment = new UpdatetruyenFragment();
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
        View view = inflater.inflate(R.layout.fragment_updatetruyen, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            truyen = (Truyen) bundle.getSerializable("object");
        }

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tbl_truyen");
        MainAdmin main = (MainAdmin) getActivity();
        EditText tentruyen = view.findViewById(R.id.tentruyen);
        EditText noidungtruyen = view.findViewById(R.id.noidungtruyen);
        Button btn_update = view.findViewById(R.id.btn_update);

        tentruyen.setText(truyen.getTentruyen());
        noidungtruyen.setText(truyen.getNoidung());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(String.valueOf(truyen.getMa())).child("tentruyen").setValue(tentruyen.getText().toString());
                mDatabase.child(String.valueOf(truyen.getMa())).child("noidung").setValue(noidungtruyen.getText().toString());
                Toast.makeText(main, "Update thành công", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}