package com.emmanuel.sqlito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    AdapterButtonsListener listener;

    public interface AdapterButtonsListener {
        void onButtonEditClickListener(int position, Product product);
        void onButtonDeleteClickListener(int position, Product product);
    }

    public void setListener(AdapterButtonsListener listener) {
        this.listener = listener;
    }

    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        super(context, R.layout.listview_layout, products);
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.labelProducto);
            viewHolder.textViewPrice = convertView.findViewById(R.id.labelPrecio);
            viewHolder.btnEdit = convertView.findViewById(R.id.btnEdit);
            viewHolder.btnDelete = convertView.findViewById(R.id.btnDelete);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Product product = getItem(position);

        viewHolder.textViewName.setText(product.getName());
        viewHolder.textViewPrice.setText(product.getPrice());

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onButtonEditClickListener(position, product);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onButtonDeleteClickListener(position, product);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView textViewName;
        TextView textViewPrice;
        Button btnEdit;
        Button btnDelete;
    }
}
