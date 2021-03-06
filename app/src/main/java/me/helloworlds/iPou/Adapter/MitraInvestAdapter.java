package me.helloworlds.iPou.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.helloworlds.iPou.Model.m_mitra_invest;
import me.helloworlds.iPou.R;

public class MitraInvestAdapter extends BaseAdapter {
    private Activity activity;
    private List<m_mitra_invest> list;

    public MitraInvestAdapter(Activity activity, List<m_mitra_invest> list) {
        this.activity = activity;
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
        View view = View.inflate(activity, R.layout.list_mitra_invest, null);
        m_mitra_invest m = list.get(position);

        TextView txtKandang = (TextView) view.findViewById(R.id.txtKandang);
        TextView txtInvestor = (TextView) view.findViewById(R.id.txtInvestor);

        txtKandang.setText("Kandang " + m.getKandang());
        txtInvestor.setText("Investor : " + m.getInvestor());
        view.setTag(m.getId());
        return view;
    }
}
