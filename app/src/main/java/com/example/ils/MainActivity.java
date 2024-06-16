package com.example.ils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    ImageButton addList;
    SQLiteDatabase OrderDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OrderDB = openOrCreateDatabase("OrderDB", Context.MODE_PRIVATE, null);
        OrderDB.execSQL("CREATE TABLE IF NOT EXISTS orders(name TEXT, address TEXT, number TEXT, flavor TEXT, size TEXT);");
        addList = findViewById(R.id.addList);
        linearLayout = findViewById(R.id.l_layout);

        createElements();

        addList.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, AddActitvity.class);
            startActivity(i);
        });
    }

    private void createElements() {
        Toast.makeText(this, "createElement() work", Toast.LENGTH_SHORT).show();
        try {
            Cursor c = OrderDB.rawQuery("SELECT * FROM orders", null);
            if (c.moveToFirst()) {
                Toast.makeText(this, "moveToFirst() working", Toast.LENGTH_SHORT).show();
                do {
                    String name = c.getString(c.getColumnIndex("name"));
                    String address = c.getString(c.getColumnIndex("address"));
                    String number = c.getString(c.getColumnIndex("number"));
                    String flavor = c.getString(c.getColumnIndex("flavor"));
                    String size = c.getString(c.getColumnIndex("size"));

                    // Create ConstraintLayout
                    ConstraintLayout constraintLayout = new ConstraintLayout(this);
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.MATCH_PARENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    );
                    constraintLayout.setLayoutParams(layoutParams);
                    constraintLayout.setPadding(16, 16, 16, 16);

                    // Create TextView for order ID
                    TextView orderIdTxt = new TextView(this);
                    orderIdTxt.setId(View.generateViewId());
                    orderIdTxt.setText("#69420");
                    orderIdTxt.setTextSize(24);
                    orderIdTxt.setTypeface(null, Typeface.BOLD);
                    constraintLayout.addView(orderIdTxt);

                    // Create ImageButton for delivered
                    ImageButton deliveredBtn = new ImageButton(this);
                    deliveredBtn.setId(View.generateViewId());
                    deliveredBtn.setLayoutParams(new ConstraintLayout.LayoutParams(50, 50));
                    deliveredBtn.setBackgroundResource(R.drawable.tansparent_image_button);
                    deliveredBtn.setImageResource(R.drawable.baseline_check_24);
                    constraintLayout.addView(deliveredBtn);

                    // Create ImageButton for delete
                    ImageButton deleteBtn = new ImageButton(this);
                    deleteBtn.setId(View.generateViewId());
                    deleteBtn.setLayoutParams(new ConstraintLayout.LayoutParams(50, 50));
                    deleteBtn.setBackgroundResource(R.drawable.tansparent_image_button);
                    deleteBtn.setImageResource(R.drawable.baseline_delete_outline_24);
                    constraintLayout.addView(deleteBtn);

                    // Create TextView for order name
                    TextView orderNameTxt = new TextView(this);
                    orderNameTxt.setId(View.generateViewId());
                    orderNameTxt.setText(name);
                    orderNameTxt.setTextSize(16);
                    constraintLayout.addView(orderNameTxt);

                    // Create TextView for order number
                    TextView orderNumberTxt = new TextView(this);
                    orderNumberTxt.setId(View.generateViewId());
                    orderNumberTxt.setText(number);
                    constraintLayout.addView(orderNumberTxt);

                    // Create TextView for order address
                    TextView orderAddressTxt = new TextView(this);
                    orderAddressTxt.setId(View.generateViewId());
                    orderAddressTxt.setText(address);
                    constraintLayout.addView(orderAddressTxt);

                    // Create TextView for order size
                    TextView orderSizeTxt = new TextView(this);
                    orderSizeTxt.setId(View.generateViewId());
                    orderSizeTxt.setText(size);
                    constraintLayout.addView(orderSizeTxt);

                    // Create TextView for order flavor
                    TextView orderFlavorTxt = new TextView(this);
                    orderFlavorTxt.setId(View.generateViewId());
                    orderFlavorTxt.setText(flavor);
                    constraintLayout.addView(orderFlavorTxt);

                    // Apply constraints using ConstraintSet
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    // Constraints for orderIdTxt
                    constraintSet.connect(orderIdTxt.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                    constraintSet.connect(orderIdTxt.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);

                    // Constraints for deliveredBtn
                    constraintSet.connect(deliveredBtn.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
                    constraintSet.connect(deliveredBtn.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSet.connect(deliveredBtn.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

                    // Constraints for deleteBtn
                    constraintSet.connect(deleteBtn.getId(), ConstraintSet.END, deliveredBtn.getId(), ConstraintSet.START, 16);
                    constraintSet.connect(deleteBtn.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSet.connect(deleteBtn.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);

                    // Constraints for orderNameTxt
                    constraintSet.connect(orderNameTxt.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                    constraintSet.connect(orderNameTxt.getId(), ConstraintSet.TOP, orderIdTxt.getId(), ConstraintSet.BOTTOM);

                    // Constraints for orderNumberTxt
                    constraintSet.connect(orderNumberTxt.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                    constraintSet.connect(orderNumberTxt.getId(), ConstraintSet.TOP, orderNameTxt.getId(), ConstraintSet.BOTTOM);

                    // Constraints for orderAddressTxt
                    constraintSet.connect(orderAddressTxt.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                    constraintSet.connect(orderAddressTxt.getId(), ConstraintSet.TOP, orderNumberTxt.getId(), ConstraintSet.BOTTOM);

                    // Constraints for orderSizeTxt
                    constraintSet.connect(orderSizeTxt.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                    constraintSet.connect(orderSizeTxt.getId(), ConstraintSet.TOP, orderAddressTxt.getId(), ConstraintSet.BOTTOM, 16);

                    // Constraints for orderFlavorTxt
                    constraintSet.connect(orderFlavorTxt.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
                    constraintSet.connect(orderFlavorTxt.getId(), ConstraintSet.TOP, orderSizeTxt.getId(), ConstraintSet.BOTTOM);

                    constraintSet.applyTo(constraintLayout);
                    linearLayout.addView(constraintLayout);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error on catch", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        linearLayout.removeAllViews();
        createElements();
    }

}