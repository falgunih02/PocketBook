package com.example.registeractivity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomerList extends ArrayAdapter<Customer> {

    private Activity context;
    private List<Customer> customerList;

    public CustomerList(Activity context, List<Customer> customerList) {
        super(context, R.layout.layout_list, customerList);
        this.context = context;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewPhone = (TextView) listViewItem.findViewById(R.id.textViewPhone);
        TextView textViewAmt = (TextView) listViewItem.findViewById(R.id.textViewAmt);

        Customer cust = customerList.get(position);
        textViewName.setText(cust.getName());
        textViewPhone.setText(cust.getPhone());
        textViewAmt.setText(cust.getAmt());

        return listViewItem;
    }
}

