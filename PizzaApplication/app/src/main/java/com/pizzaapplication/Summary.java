package com.pizzaapplication;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Summary extends AppCompatActivity implements OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
         Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Feedback, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        String OrderSummaryMessage = getIntent().getStringExtra("OrderSummaryMessage");
        String order = getIntent().getStringExtra("order");
        String price = getIntent().getStringExtra("price");
        String name = getIntent().getStringExtra("name");
        String quant = getIntent().getStringExtra("quant");


        TextView ingredients = (TextView) findViewById(R.id.ingredients);
        ingredients.setText(order);

        TextView nameid = (TextView) findViewById(R.id.name);
        nameid.setText(name);

        TextView priceid = (TextView) findViewById(R.id.price);
        priceid.setText(price);

        TextView quantityid = (TextView) findViewById(R.id.quantity);
        quantityid.setText(quant);

        Log.d("order", order);
        Log.d("name", name);
        Log.d("price", price);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        if (item == getString(R.string.yes) )
        {
            Toast.makeText(parent.getContext(), "Thanks For your positive feedback " , Toast.LENGTH_LONG).show();
        }
        if(item  == getString(R.string.no))
        {
            Toast.makeText(parent.getContext(), "Apologize for your inconvenience " , Toast.LENGTH_LONG).show();
        }

        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}