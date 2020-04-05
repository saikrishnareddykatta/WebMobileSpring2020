package com.pizzaapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int BASE_PRICE = 10;
    final int Jalapinoes = 1;
    final int Onions = 1;
    final int Olives = 2;
    final int Chicken = 1;
    final int Corn = 3;
    final int Lettuce = 1;
    final int Bellpeppers = 1;
    String name = "";
    String order = "";
    String price1 = "";
    String quant = "";
    String totalmessage ="";

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn = (Button) findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });


    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        CheckBox jalapenoes = (CheckBox) findViewById(R.id.jalapenoes);
        boolean hasjalapenoes = jalapenoes.isChecked();

        CheckBox onion = (CheckBox) findViewById(R.id.onion);
        boolean hasonion = onion.isChecked();

        CheckBox chicken = (CheckBox) findViewById(R.id.chicken);
        boolean haschicken = chicken.isChecked();

        CheckBox bellpeppers = (CheckBox) findViewById(R.id.bellpeppers);
        boolean hasbellpeppers = bellpeppers.isChecked();

        CheckBox corn = (CheckBox) findViewById(R.id.corn);
        boolean hascorn = corn.isChecked();

        CheckBox olives = (CheckBox) findViewById(R.id.olives);
        boolean hasolives = olives.isChecked();

        CheckBox lettuce = (CheckBox) findViewById(R.id.lettuce);
        boolean haslettuce = lettuce.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasbellpeppers,hasjalapenoes, haschicken, hasonion, hascorn, haslettuce);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasbellpeppers,hasjalapenoes, haschicken, hasonion, hascorn, haslettuce, totalPrice);

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
        Intent redirect = new Intent(MainActivity.this, Summary.class);
        redirect.putExtra("OrderSummaryMessage", orderSummaryMessage);
        redirect.putExtra("order", order);
        redirect.putExtra("price", price1);
        redirect.putExtra("quant", quant);
        redirect.putExtra("name", name);
        startActivity(redirect);


    }

    protected void sendEmail() {
        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        CheckBox jalapenoes = (CheckBox) findViewById(R.id.jalapenoes);
        boolean hasjalapenoes = jalapenoes.isChecked();

        CheckBox onion = (CheckBox) findViewById(R.id.onion);
        boolean hasonion = onion.isChecked();

        CheckBox chicken = (CheckBox) findViewById(R.id.chicken);
        boolean haschicken = chicken.isChecked();

        CheckBox bellpeppers = (CheckBox) findViewById(R.id.bellpeppers);
        boolean hasbellpeppers = bellpeppers.isChecked();

        CheckBox corn = (CheckBox) findViewById(R.id.corn);
        boolean hascorn = corn.isChecked();

        CheckBox olives = (CheckBox) findViewById(R.id.olives);
        boolean hasolives = olives.isChecked();

        CheckBox lettuce = (CheckBox) findViewById(R.id.lettuce);
        boolean haslettuce = lettuce.isChecked();

        float totalPrice = calculatePrice(hasbellpeppers,hasjalapenoes, haschicken, hasonion, hascorn, haslettuce);

        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_Bellpeppers, boolToString(hasbellpeppers)) + "\n" +
                getString(R.string.order_summary_Jalapenoes, boolToString(hasjalapenoes)) + "\n" +
                getString(R.string.order_summary_Lettuce, boolToString(haslettuce)) + "\n" +
                getString(R.string.order_summary_Onions, boolToString(hasonion)) + "\n" +
                getString(R.string.order_summary_Corn, boolToString(hascorn)) + "\n" +
                getString(R.string.order_summary_Chicken, boolToString(haschicken)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, totalPrice) + "\n" +
                getString(R.string.thank_you);

        TextView email = (TextView) findViewById(R.id.email);

        Log.i("Send email", "");
        String[] TO = {email.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is your pizza Order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName,boolean hasbellpeppers, boolean hasjalapenoes, boolean haschicken, boolean hasonion,boolean hascorn,boolean haslettuce, float price) {
        name = userInputName;
        order= getString(R.string.order_summary_Bellpeppers, boolToString(hasbellpeppers)) + "\n" +
                getString(R.string.order_summary_Jalapenoes, boolToString(hasjalapenoes)) + "\n" +
                getString(R.string.order_summary_Lettuce, boolToString(haslettuce)) + "\n" +
                getString(R.string.order_summary_Onions, boolToString(hasonion)) + "\n" +
                getString(R.string.order_summary_Corn, boolToString(hascorn)) + "\n" +
                getString(R.string.order_summary_Chicken, boolToString(haschicken)) + "\n" ;
        quant= getString(R.string.order_summary_quantity, quantity);
        price1 = getString(R.string.order_summary_total_price, price);

        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_Bellpeppers, boolToString(hasbellpeppers)) + "\n" +
                getString(R.string.order_summary_Jalapenoes, boolToString(hasjalapenoes)) + "\n" +
                getString(R.string.order_summary_Lettuce, boolToString(haslettuce)) + "\n" +
                getString(R.string.order_summary_Onions, boolToString(hasonion)) + "\n" +
                getString(R.string.order_summary_Corn, boolToString(hascorn)) + "\n" +
                getString(R.string.order_summary_Chicken, boolToString(haschicken)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);

        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasbellpeppers, boolean hasjalapenoes,boolean haschicken, boolean hasonion,boolean hascorn,boolean haslettuce) {
        int basePrice = BASE_PRICE;
        TextView text_quan = (TextView) findViewById(R.id.quantity_text_view);
        quantity = Integer.parseInt(text_quan.getText().toString());
        if (hasbellpeppers) {
            basePrice += Bellpeppers;
        }
        if (hasjalapenoes) {
            basePrice += Jalapinoes;
        }
        if (haschicken) {
            basePrice += Chicken;
        }
        if (hasonion) {
            basePrice += Onions;
        }
        if (hascorn) {
            basePrice += Corn;
        }
        if (haslettuce) {
            basePrice += Lettuce;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of coffee cups by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 10) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than 10 pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of coffee cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}