package com.example.android_nc;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiTruyenListViewAdminAdapter extends BaseAdapter {
    DatabaseReference reference;
    ArrayList<LoaiTruyen> list;
    final ArrayList<LoaiTruyen> listLoaiTruyen;

    private IClickitermloai iClickitermloai;
    public interface IClickitermloai{
        void onClickItermUser(LoaiTruyen loaiTruyen,String trangthai);
    }

    LoaiTruyenListViewAdminAdapter(ArrayList<LoaiTruyen> listLoaiTruyen,IClickitermloai iClickitermloai) {
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
            viewProduct = View.inflate(parent.getContext(), R.layout.iterm_loaitruyenadmin, null);
        } else viewProduct = convertView;
        Picasso.with(parent.getContext()).load(loaitruyen.getDuongdan()).into((ImageView) viewProduct.findViewById(R.id.image));
        ((TextView) viewProduct.findViewById(R.id.textTenLoaiTruyen)).setText(String.format(loaitruyen.name));
        TextView dstruyen = viewProduct.findViewById(R.id.textTenLoaiTruyen);
        dstruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermloai.onClickItermUser(loaitruyen,"danhsach");
            }
        });
        Button click =  viewProduct.findViewById(R.id.button_them_truyen);
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermloai.onClickItermUser(loaitruyen,"themtruyen");
            }
        });
        return viewProduct;
    }
}
