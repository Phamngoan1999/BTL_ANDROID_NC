package com.example.android_nc;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimkiemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimkiemFragment extends Fragment {
    DatabaseReference reference;
    ArrayList<Truyen> list,listtruyen;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public TimkiemFragment() {
        // Required empty public constructor
    }
    public static TimkiemFragment newInstance(String param1, String param2) {
        TimkiemFragment fragment = new TimkiemFragment();
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

        View nView = inflater.inflate(R.layout.fragment_timkiem, container, false);
        Button timkiem = nView.findViewById(R.id.timkiem);
        reference = FirebaseDatabase.getInstance().getReference().child("tbl_truyen");
        ListView ls = nView.findViewById(R.id.listproduct);
        MainActivity main = (MainActivity) getActivity();
        EditText tentruyen = nView.findViewById(R.id.thongtin);
        int  i = 0;
        timkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list = new ArrayList<Truyen>();
                        listtruyen = new ArrayList<Truyen>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {
                            Truyen truyen = dataSnapshot1.getValue(Truyen.class);
                            list.add(truyen);
                        }
                        for(int i = 0;i<list.size();i++){
                            if (list.get(i).getTentruyen().compareTo(tentruyen.getText().toString()) == 0) {
                                listtruyen.add(list.get(i));
                            }
                        }
                        Adaptertruyen adaptertruyen = new Adaptertruyen(listtruyen, new Adaptertruyen.IClickitermTruyen() {
                            @Override
                            public void onClickItermTruyen(Truyen truyen, String trangthai) {
                                main.goChitiet(truyen);
                            }
                        });
                        ls.setAdapter(adaptertruyen);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return nView;
    }
}