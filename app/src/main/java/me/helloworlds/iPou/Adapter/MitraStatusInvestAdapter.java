package me.helloworlds.iPou.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.helloworlds.iPou.Model.m_mitra_status_invest;
import me.helloworlds.iPou.R;

public class MitraStatusInvestAdapter extends BaseAdapter {
    private Context context;
    private List<m_mitra_status_invest> list;

    public MitraStatusInvestAdapter(Context context, List<m_mitra_status_invest> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.list_mitra_status_invest, null);
        m_mitra_status_invest m = list.get(position);

        TextView txtKandang = (TextView) view.findViewById(R.id.txtKandang);
        TextView txtUang = (TextView) view.findViewById(R.id.txtUang);
        TextView txtVerif = (TextView) view.findViewById(R.id.txtVerif);
        TextView txtImg = (TextView) view.findViewById(R.id.txtImg);

        txtKandang.setText("Kandang " + m.getKandang());
        txtUang.setText("Jumlah : " + m.getHarga());
        txtVerif.setText("Status : " + m.getVerif());
        txtImg.setText("Upload Bukti : " + m.getImg());
        view.setTag(m.getId());
        return view;
    }
}
