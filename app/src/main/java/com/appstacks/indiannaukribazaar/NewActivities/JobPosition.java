package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityJobPositionBinding;

import java.util.ArrayList;


public class JobPosition extends AppCompatActivity {
    ActivityJobPositionBinding binding;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    SharedPrefe sharedPrefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobPositionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefe = new SharedPrefe(JobPosition.this);
        list = new ArrayList<String>();

        list.add("Assistant");
        list.add("Associate");
        list.add("Administrative Assistant");
        list.add("Account Manager");
        list.add("Assistant Manager");
        list.add("Commission Sales Associate");
        list.add("Sales Attendant");
        list.add("Accountant");
        list.add("Sales Advocate");
        list.add("Analyst");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        binding.listView.setAdapter(adapter);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                adapter.getFilter().filter(s);
                return false;
            }
        });

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int index = i;
                Intent intent = new Intent(getApplicationContext(), AddJobsActivity.class);
                String tv = list.get(index).toString();
                sharedPrefe.saveJoPosition(tv);
                startActivity(intent);


            }
        });


    }
}