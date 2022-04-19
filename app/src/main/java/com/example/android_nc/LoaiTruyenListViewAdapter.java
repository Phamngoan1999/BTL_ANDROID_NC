package com.example.android_nc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;


public class LoaiTruyenListViewAdapter extends BaseAdapter {

    DatabaseReference reference;
    ArrayList<LoaiTruyen> list;
    final ArrayList<LoaiTruyen> listLoaiTruyen;

    private IClickitermloai iClickitermloai;
    public interface IClickitermloai{
        void onClickItermUser(LoaiTruyen loaiTruyen);
    }

    LoaiTruyenListViewAdapter(ArrayList<LoaiTruyen> listLoaiTruyen,IClickitermloai iClickitermloai) {
        this.listLoaiTruyen = listLoaiTruyen;
        this.iClickitermloai = iClickitermloai;
    }

    @Override
    public int getCount() {
        return listLoaiTruyen.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaiTruyen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listLoaiTruyen.get(position).getMa();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LoaiTruyen loaitruyen = (LoaiTruyen) getItem(position);

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.iterm_loaitruyen, null);
        } else viewProduct = convertView;
        Picasso.with(parent.getContext()).load(loaitruyen.getDuongdan()).into((ImageView) viewProduct.findViewById(R.id.image));
        ((TextView) viewProduct.findViewById(R.id.textTenLoaiTruyen)).setText(loaitruyen.name);
        TextView click =  viewProduct.findViewById(R.id.textTenLoaiTruyen);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermloai.onClickItermUser(loaitruyen);
            }
        });
        return viewProduct;
    }
}
