package com.example.justjava; /**
 * package com.example.justjava;
 * <p>
 * import androidx.appcompat.app.AppCompatActivity;
 * <p>
 * import android.os.Bundle;
 * <p>
 * public class MainActivity extends AppCompatActivity {
 *
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * setContentView(R.layout.activity_main);
 * }
 * }
 **/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;
    String name = null;

    //@override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        quantity++;
        display(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if (quantity - 1 >= 0) {
            quantity--;
        }
        display(quantity);

    }

    /**
     * Calculates the price of the order based on the current quantity. Modifies the price per cup
     * based on whether cream or chocolate is included or not.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, boolean cream, boolean chocolate) {
        int perCup = 5;
        if (cream) {
            perCup += 1;
        }
        if (chocolate) {
            perCup += 2;
        }
        return quantity * perCup;
    }

    /**
     * Creates an order summary of the coffee items ordered and any customizations.
     *
     * @param hasWhippedCream
     * @param hasChocolate
     * @return
     */
    private String createOrderSummary(boolean hasWhippedCream, boolean hasChocolate) {
        return "Name: " + name
                + "\nAdd whipped cream? " + hasWhippedCream
                + "\nAdd chocolate? " + hasChocolate
                + "\nQuantity: " + quantity
                + "\nTotal: $" + calculatePrice(quantity, hasWhippedCream, hasChocolate)
                + "\nThank you!";
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editText = (EditText) findViewById(R.id.name_view);
        name = editText.getText().toString();

        CheckBox checkBoxCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox checkBoxChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = checkBoxCream.isChecked();
        boolean hasChocolate = checkBoxChocolate.isChecked();

        String priceMessage = createOrderSummary(hasWhippedCream, hasChocolate);

        //sends an intent to the email app to send an email containing the order.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}