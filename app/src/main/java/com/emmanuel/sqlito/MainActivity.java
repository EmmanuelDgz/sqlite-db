package com.emmanuel.sqlito;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = findViewById(R.id.btnName);
        final EditText price = findViewById(R.id.btnPrice);
        final Button register = findViewById(R.id.btnRegister);

        final DataBaseHelper dbHandler = new DataBaseHelper(MainActivity.this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_i = name.getText().toString();
                String price_i = price.getText().toString();

                dbHandler.insertProduct(name_i, price_i);

                name.setText("");
                price.setText("");

                Toast.makeText(getApplicationContext(), "Producto Agregado", Toast.LENGTH_SHORT).show();

                Intent detailsActivity = new Intent(MainActivity.this, DetailProductsActivity.class);
                startActivity(detailsActivity);
            }
        });
    }
}
