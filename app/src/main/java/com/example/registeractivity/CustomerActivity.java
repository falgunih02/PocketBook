package com.example.registeractivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    EditText txtname,txtAmt, editTextPhone;
    Button btnsave;
    DatabaseReference reff;
    ListView listViewCustomer;
    List<Customer> customerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        txtname = (EditText)findViewById(R.id.txtname);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        txtAmt = (EditText)findViewById(R.id.txtamt);
        btnsave = (Button)findViewById(R.id.btnsave);
        listViewCustomer = (ListView) findViewById(R.id.listViewCustomer);
        customerList = new ArrayList<>();

        reff = FirebaseDatabase.getInstance().getReference().child("Members");

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("PocketBook");

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });
        listViewCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = customerList.get(i);
                showUpdateDialog(customer.getId(), customer.getName(),customer.getPhone(), customer.getAmt());
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                customerList.clear();

                for(DataSnapshot customerSnapshot : dataSnapshot.getChildren()) {
                    Customer customer = customerSnapshot.getValue(Customer.class);

                    customerList.add(customer);
                }

                CustomerList adapter = new CustomerList(CustomerActivity.this, customerList);
                listViewCustomer.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String id, final String name, final String phone, String amt) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextAmt = (EditText) dialogView.findViewById(R.id.editTextAmt);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);

        dialogBuilder.setTitle("updating customer " + name);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String amt = editTextAmt.getText().toString();

                updateCustomer(id, name, phone, amt);
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteCustomer(id);
                alertDialog.dismiss();
            }
        });
    }

    private void deleteCustomer(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Members").child(id);

        //removing customer
        dR.removeValue();
        Toast.makeText(this, "Customer Deleted", Toast.LENGTH_LONG).show();

    }


    private boolean updateCustomer(String id, String name, String phone, String amt) {

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Members").child(id);

        //updating customer
        Customer customer = new Customer(id, name, phone, amt);
        dR.setValue(customer);
        Toast.makeText(this, "customer Updated", Toast.LENGTH_LONG).show();
        return true;

    }

    public void addCustomer() {
        String name = txtname.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String amt = txtAmt.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(amt) ) {
            if (phone.length()==10) {
                String id = reff.push().getKey();
                Customer cust = new Customer(id, name, phone, amt);
                reff.child(id).setValue(cust);
                Toast.makeText(this, "Customer added", Toast.LENGTH_LONG).show();
                txtname.setText("");
                txtAmt.setText("");
                editTextPhone.setText("");
            } else {
                    Toast.makeText(CustomerActivity.this, "Invalid phone number", Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(this,"Enter details",Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.sendsms){
            Intent intent = new Intent(CustomerActivity.this, Sendsms.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.logout){
            Intent lo = new Intent(CustomerActivity.this, MainActivity.class);
            startActivity(lo);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

