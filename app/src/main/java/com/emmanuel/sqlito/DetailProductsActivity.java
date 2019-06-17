package com.emmanuel.sqlito;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailProductsActivity extends AppCompatActivity implements ProductAdapter.AdapterButtonsListener {
    private ListView listView;
    private ProductAdapter productAdapter;
    private ArrayList<Product> products;
    private DataBaseHelper db;

    private EditText name;
    private EditText price;
    private Button btnSaveChanges;

    private int productId;
    private int arrayIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);

        name = findViewById(R.id.editName);
        price = findViewById(R.id.editPrice);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        name.setEnabled(false);
        price.setEnabled(false);
        btnSaveChanges.setEnabled(false);

        listView = findViewById(R.id.listView);
        products = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(), products);
        productAdapter.setListener(this);
        listView.setAdapter(productAdapter);

        db = new DataBaseHelper(this);
        Cursor cursor = db.getProducts();

        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(0);
                String productName = cursor.getString(1);
                String productPrice = cursor.getString(2);
                products.add(new Product(productId, productName, productPrice));
            }
            while (cursor.moveToNext());
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_ = name.getText().toString();
                String price_ = price.getText().toString();

                products.get(arrayIndex).setName(name_);
                products.get(arrayIndex).setPrice(price_);
                productAdapter.notifyDataSetChanged();

                name.setText("");
                price.setText("");
                name.setEnabled(false);
                price.setEnabled(false);
                btnSaveChanges.setEnabled(false);

                db.updateProductById(productId, name_, price_);
            }
        });
    }

    @Override
    public void onButtonEditClickListener(int position, Product product) {
        arrayIndex = position;
        productId = product.getId();
        name.setText(product.getName());
        price.setText(product.getPrice());
        name.setEnabled(true);
        price.setEnabled(true);
        btnSaveChanges.setEnabled(true);
    }

    @Override
    public void onButtonDeleteClickListener(int position, Product product) {
        db.deleteProductById(product.getId());
        products.remove(position);
        productAdapter.notifyDataSetChanged();
    }
}
