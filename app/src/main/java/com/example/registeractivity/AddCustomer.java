package com.example.registeractivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AddCustomer extends AppCompatActivity {

    EditText e1, e2;
    TextView t1;
    int num1, num2;
    String s1, s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PocketBook");
    }
    public boolean getNumbers()
    {
        // defining the edit text 1 to e1
        e1 = (EditText)findViewById(R.id.num1);

        // defining the edit text 2 to e2
        e2 = (EditText)findViewById(R.id.num2);

        // defining the text view to t1
        t1 = (TextView)findViewById(R.id.result);

        // taking input from text box 1
        s1 = e1.getText().toString();

        // taking input from text box 2
        s2 = e2.getText().toString();

        // condition to check if box is not empty
        if ((s1.equals(null) && s2.equals(null)) || (s1.equals("") && s2.equals("")))
        {

            String result = "Please enter a value";
            t1.setText(result);

            return false;
        }
        else
            {
            // converting string to int.
            num1 = Integer.parseInt(s1);

            // converting string to int.
            num2 = Integer.parseInt(s2);
        }

        return true;
    }

    // a public method to perform addition
    public void doSum(View v)
    {

        // get the input numbers
        if (getNumbers()) {
            int sum = num1 + num2;
            t1.setText(Integer.toString(sum));
        }
    }

    // a public method to perform power function
    public void doPow(View v)
    {

        // get the input numbers
        if (getNumbers()) {
            double sum = Math.pow(num1, num2);
            t1.setText(Double.toString(sum));
        }
    }

    // a public method to perform subtraction
    public void doSub(View v)
    {

        // get the input numbers
        if (getNumbers()) {
            int sum = num1 - num2;
            t1.setText(Integer.toString(sum));
        }
    }

    // a public method to perform multiplication
    public void doMul(View v)
    {

        // get the input numbers
        if (getNumbers()) {
            int sum = num1 * num2;
            t1.setText(Integer.toString(sum));
        }
    }
    // a public method to perform Division
    public void doDiv(View v)
    {

        // get the input numbers
        if (getNumbers()) {

            // displaying the text in text view assigned as t1
            double sum = num1 / (num2 * 1.0);
            t1.setText(Double.toString(sum));
        }
    }
    // a public method to perform modulus function
    public void doMod(View v)
    {

        // get the input numbers
        if (getNumbers()) {
            double sum = num1 % num2;
            t1.setText(Double.toString(sum));
        }
    }


    public void go(View v) {
        Intent intent = new Intent(AddCustomer.this, CustomerActivity.class);
        startActivity(intent);
    }
}
