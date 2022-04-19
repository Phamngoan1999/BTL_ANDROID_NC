package com.example.android_nc;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Adaptertruyenyeuthich  extends BaseAdapter {

    private FirebaseAuth mAuth;
    final ArrayList<Truyenyeuthich> listtruyen;
    Context context;
    private IClickitermTruyen iClickitermtruyen;

    public interface IClickitermTruyen {
        void onClickItermUser(Truyenyeuthich user,String trangthai);
    }

    Adaptertruyenyeuthich(ArrayList<Truyenyeuthich> listProduct, IClickitermTruyen iClickitermloai) {
        this.listtruyen = listProduct;
        this.iClickitermtruyen = iClickitermloai;
    }

    @Override
    public int getCount() {
        return listtruyen.size();
    }

    @Override
    public Object getItem(int position) {
        return listtruyen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listtruyen.get(position).getMa();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.itermtruyeninit, null);
        } else viewProduct = convertView;
        Truyenyeuthich product = (Truyenyeuthich) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.tentruyen)).setText(String.format(product.tentruyen));
        ((TextView) viewProduct.findViewById(R.id.tenloaitruyen)).setText(String.format(product.tenloaitruyen));
        TextView truyenchitiet = viewProduct.findViewById(R.id.tenloaitruyen);
        ImageView yeuthich = viewProduct.findViewById(R.id.yeuthich);
        yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermtruyen.onClickItermUser(product,"yeuthich");
            }
        });
        truyenchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermtruyen.onClickItermUser(product,"chitiet");
            }
        });
        return viewProduct;
    }
}
