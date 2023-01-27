package com.appstacks.indiannaukribazaar.profile.Education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.DetailsActivity;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityLevelOfEducationBinding;

import java.util.ArrayList;

public class LevelOfEducationActivity extends AppCompatActivity {
ActivityLevelOfEducationBinding  binding;
  private static   ArrayList<String> list = new ArrayList<>();
private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLevelOfEducationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        searchView(list);

        binding.btnBackLevelOfEducation.setOnClickListener(view -> {
            finish();
        });


        binding.listViewSearchData.setOnItemClickListener((adapterView, view15, i, l) -> {
         String   levelOfEducation = list.get(i).toString();


            Toast.makeText(LevelOfEducationActivity.this, "" + levelOfEducation, Toast.LENGTH_SHORT).show();

        });


    }
    private void searchView(ArrayList<String> list) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        binding.listViewSearchData.setAdapter(adapter);
        binding.searchViewSearchData.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }
}