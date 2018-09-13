package me.helloworlds.iPou.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import me.helloworlds.iPou.AppController;
import me.helloworlds.iPou.Model.m_pembeli_home;
import me.helloworlds.iPou.R;

public class PembeliHomeAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity activity;
    private List<m_pembeli_home> list;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public PembeliHomeAdapter(Activity activity, List<m_pembeli_home> list) {
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
        View view = View.inflate(activity, R.layout.grid_home_pembeli, null);
        Locale locale = new Locale("in", "ID");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        m_pembeli_home m = list.get(position);
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.grid_home_pembeli, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView imgProduct = (NetworkImageView) view.findViewById(R.id.imgProduct);
        TextView txtHarga = (TextView) view.findViewById(R.id.txtHarga);
        TextView txtStok = (TextView) view.findViewById(R.id.txtStok);

        txtHarga.setText(format.format(Double.parseDouble(m.getHarga())));
        txtStok.setText("Stok : " + m.getStok());
        imgProduct.setImageUrl(m.getImg(), imageLoader);
        view.setTag(m.getId());
        return view;
    }
}
