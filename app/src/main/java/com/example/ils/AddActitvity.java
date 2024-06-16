package com.example.ils;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AddActitvity extends AppCompatActivity {
    EditText customerName, customerAddress, customerNumber;
    CheckBox buko, cheese, lanka, kasoy, cookies, fruit_salad, ube, macapuno;
    RadioGroup sizes;
    SQLiteDatabase OrderDB;

    @Override
    protected void onCreate(Bundle savedInstanceCreate) {
        super.onCreate(savedInstanceCreate);
        setContentView(R.layout.addactivity);

        customerName = findViewById(R.id.customer_name);
        customerAddress = findViewById(R.id.customer_address);
        customerNumber = findViewById(R.id.customer_number);

        buko = findViewById(R.id.c_buko);
        cheese = findViewById(R.id.c_cheese);
        lanka = findViewById(R.id.c_lanka);
        kasoy = findViewById(R.id.c_kasoy);
        cookies = findViewById(R.id.c_cookies);
        fruit_salad = findViewById(R.id.c_fruitSalad);
        ube = findViewById(R.id.c_ube);
        macapuno = findViewById(R.id.c_macapuno);

        sizes = findViewById(R.id.r_sizes);
        Button finishButton = findViewById(R.id.finish);

        finishButton.setOnClickListener(v -> {
            String name = customerName.getText().toString().trim();
            String address = customerAddress.getText().toString().trim();
            String number = customerNumber.getText().toString().trim();
            String flavor = getSelectedFlavors();
            String size = getSelectedSize();
            String id = generateRandomId();

            if (flavor.isEmpty()) {
                msg("No", "Select a flavor");
            } else if (size.isEmpty()) {
                msg("No", "Select a Size");
            } else if (name.isEmpty()) {
                msg("No", "Enter a name");
            } else if (address.isEmpty()) {
                msg("No", "Enter an address");
            } else if (number.isEmpty()) {
                msg("No", "Enter a Number");
            } else {
                OrderDB = openOrCreateDatabase("OrderDB", Context.MODE_PRIVATE, null);
                OrderDB.execSQL("CREATE TABLE IF NOT EXISTS orders(id TEXT, name TEXT, address TEXT, number TEXT, flavor TEXT, size TEXT);");
                ContentValues cv = new ContentValues();
                cv.put("id", id);
                cv.put("name", name);
                cv.put("address", address);
                cv.put("number", number);
                cv.put("flavor", flavor);
                cv.put("size", size);
                OrderDB.insert("orders", null, cv);
                msg("done");
            }
        });
    }

    private String generateRandomId() {
        Random random = new Random();
        int randomNumber = random.nextInt(100000000);
        return String.format("%08d", randomNumber);
    }

    String getSelectedFlavors() {
        StringBuilder selectedFlavors = new StringBuilder();

        if (buko.isChecked()) {
            selectedFlavors.append("Buko, ");
        }
        if (cheese.isChecked()) {
            selectedFlavors.append("Cheese, ");
        }
        if (lanka.isChecked()) {
            selectedFlavors.append("Lanka, ");
        }
        if (kasoy.isChecked()) {
            selectedFlavors.append("Kasoy, ");
        }
        if (cookies.isChecked()) {
            selectedFlavors.append("Cookies and Cream, ");
        }
        if (fruit_salad.isChecked()) {
            selectedFlavors.append("Fruit Salad, ");
        }
        if (ube.isChecked()) {
            selectedFlavors.append("Ube, ");
        }
        if (macapuno.isChecked()) {
            selectedFlavors.append("Macapuno, ");
        }
        if (selectedFlavors.length() > 0) {
            selectedFlavors.setLength(selectedFlavors.length() - 2);
        }
        return selectedFlavors.toString();
    }

    String getSelectedSize() {
        String selectedSize = "";

        int selectedRadioButtonId = sizes.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            selectedSize = selectedRadioButton.getTag().toString();
        }
        return selectedSize;
    }
    void msg(String title, String context) {
        new AlertDialog.Builder(this ).setTitle(title).setMessage(context).setPositiveButton("OK", null).show();
    }
    void msg(String title) {
        new AlertDialog.Builder(this ).setTitle(title).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }
}