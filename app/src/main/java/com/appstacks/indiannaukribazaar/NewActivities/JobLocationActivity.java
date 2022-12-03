package com.appstacks.indiannaukribazaar.NewActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityJobLocationBinding;

import java.util.ArrayList;

public class JobLocationActivity extends AppCompatActivity {
    ActivityJobLocationBinding binding;

    ArrayList<String>list;
    ArrayAdapter<String>adapter;
    SharedPrefe sharedPrefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPrefe = new SharedPrefe(JobLocationActivity.this);

        list = new ArrayList<>();
        list.add("Andhra Pradesh");
        list.add("Arunachal Pradesh");
        list.add("Assam");
        list.add("Bihar");
        list.add("Chhattisgarh");
        list.add("Goa");
        list.add("Gujarat");
        list.add("Haryana");
        list.add("Himachal Pradesh");
        list.add("Jharkhand");
        list.add("Karnataka");
        list.add("Kerala");
        list.add("Maharashtra");
        list.add("Madhya Pradesh");
        list.add("Manipur");
        list.add("Meghalaya");
        list.add("Mizoram");
        list.add("Nagaland");
        list.add("Odisha");
        list.add("Punjab");
        list.add("Rajasthan");
        list.add("Sikkim");
        list.add("Tamil Nadu");
        list.add("Telangana");
        list.add("Tripura");
        list.add("Uttar Pradesh");
        list.add("Uttarakhand");
        list.add("West Bengal");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        binding.listViewLc.setAdapter(adapter);

        binding.searchViewLc.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        binding.listViewLc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Intent intent = new Intent(getApplicationContext(),AddJobsActivity.class);

                String tv = list.get(index).toString();
                sharedPrefe.saveJobBLocation(tv);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AddJobsActivity.class);
        startActivity(intent);
        finish();

    }
}