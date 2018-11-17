package me.helloworlds.iPou.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.helloworlds.iPou.Model.m_peternak_transaction;
import me.helloworlds.iPou.R;

public class PeternakTransactionAdapter extends BaseAdapter {
    private Activity activity;
    private List<m_peternak_transaction> list;

    public PeternakTransactionAdapter(Activity activity, List<m_peternak_transaction> list) {
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
        View view = View.inflate(activity, R.layout.list_peternak_transaction,null);
        m_peternak_transaction m = list.get(position);

        TextView txtPembeli = (TextView) view.findViewById(R.id.txtPembeli);
        TextView txtJumlah = (TextView) view.findViewById(R.id.txtJumlah);
        TextView txtVerif = (TextView) view.findViewById(R.id.txtVerif);

        txtPembeli.setText("Pembeli : "+m.getPembeli());
        txtJumlah.setText("Jumlah : "+m.getJumlah());
        txtVerif.setText(m.getVerif());
        view.setTag(m.getId());
        return view;
    }
}
