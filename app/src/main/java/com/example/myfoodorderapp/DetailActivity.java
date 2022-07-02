package com.example.myfoodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    Button insert;
    EditText c_name, phone;
    TextView total, quantity, foodname;
    ImageView food_img,Minus,Plus;
    int count=1;
   // String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final DBHelper helper = new DBHelper(this);
        c_name = findViewById(R.id.c_Name);
        foodname = findViewById(R.id.tvFoodname);
        food_img = findViewById(R.id.imageView);
        quantity = findViewById(R.id.tvQuantity);
        phone = findViewById(R.id.c_Phone);
        total = findViewById(R.id.tvTotal);
        Minus = findViewById(R.id.Minus_btn);
        Plus = findViewById(R.id.Plus_btn);
        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int price = Integer.parseInt(getIntent().getStringExtra("price"));
                if (count >= 2) {
                    count = count - 1;
                    quantity.setText(String.valueOf(count));
                    // int i = Integer.parseInt(total.getText().toString());
                    String s = String.valueOf(price * count);
                    total.setText(s);
                } else {
                    Toast.makeText(DetailActivity.this, "You have selected one Item", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int price = Integer.parseInt(getIntent().getStringExtra("price"));
                // int i = Integer.parseInt(total.getText().toString());
                count = count + 1;
                quantity.setText(String.valueOf(count));
                String s = String.valueOf(price * count);
                total.setText(s);
            }
        });
        insert = findViewById(R.id.btnOrderNow);
        if (getIntent().getIntExtra("type", 0) == 1) {
            final String image = getIntent().getStringExtra("image");
            final int price = Integer.parseInt(getIntent().getStringExtra("price"));
            final String name = getIntent().getStringExtra("name");
            total.setText(String.format("%d", price));
            Picasso
                    .with(food_img.getContext())
                    .load(image)
                    // .placeholder(R.drawable.vegitarian) // can also be a drawable
                    .fit() // will explain later
                    .noFade()
                    .into(food_img);
            foodname.setText(name);

            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = helper.insertOrder(c_name.getText().toString(),
                            phone.getText().toString(),
                            Integer.parseInt(total.getText().toString()),
                            image,
                            Integer.parseInt(quantity.getText().toString()),
                            name);
                    if (isInserted && isEmpty()) {
                        Toast.makeText(DetailActivity.this, "Your Order is Placed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            int id=getIntent().getIntExtra("id",0);
            Cursor cursor=helper.getOrderById(id);
            final String image = getIntent().getStringExtra("image") ;
           // final int price = Integer.parseInt(getIntent().getStringExtra("price"));
           // final String name = getIntent().getStringExtra("name");
            total.setText(String.format("/d",cursor.getString(3)));
            foodname.setText(cursor.getString(6));
            c_name.setText(cursor.getString(1));
            phone.setText(cursor.getString(2));
            food_img.setImageResource(cursor.getInt(3));
            Picasso
                    .with(food_img.getContext())
                    .load(image)
                    // .placeholder(R.drawable.vegitarian) // can also be a drawable
                    .fit() // will explain later
                    .noFade()
                    .into(food_img);
            insert.setText("Update Now");
            insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 boolean isUpdated=   helper.updateOrder(c_name.getText().toString(),
                            phone.getText().toString(),
                             Integer.parseInt(total.getText().toString()),
                            image,
                            1,
                            foodname.getText().toString(),
                            id);
                 if (isUpdated)
                     Toast.makeText(DetailActivity.this, "Your Order is Updated", Toast.LENGTH_SHORT).show();
                   else
                     Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        }
    public boolean isEmpty(){
        String regex = "(0|91)?[7-9][0-9]{9}";
        String number = phone.getText().toString();
        if (c_name.length() == 0) {
            c_name.setError("This field is required");
            return false;
        }else if (c_name.length() < 3){
            c_name.setError("at least 3 characters");
        }

        if (phone.length() == 0) {
            phone.setError("This field is required");
            return false;
        } else if (phone.length() < 10 || (number.matches(regex) == false)) {
            phone.setError("Please Enter Valid Mobile No");
            return false;
        }
      return true;
    }

}