package com.appstacks.indiannaukribazaar.profile.Education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.databinding.ActivityFieldOfStudyBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;

import java.util.ArrayList;

public class FieldOfStudyActivity extends AppCompatActivity {
private ActivityFieldOfStudyBinding binding;
private ProfileUtils profileUtils;
    private static ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFieldOfStudyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        profileUtils = new ProfileUtils(this);

        list.add("Bs Computer science");
        list.add("Bs Information Technology");
        list.add("Bs Software Engineering");
        list.add("Bs GIS");
        list.add("Bs in Medical Science");
        list.add("Bs in Social Sciences");
        list.add("Bs Zoology");
        list.add("BS Mathematics");
        list.add("BS Sociology");
        list.add("BS Honors");
        searchView(list);

        binding.btnBackField.setOnClickListener(view -> {
            finish();
        });


        binding.listViewSearchData.setOnItemClickListener((adapterView, view15, i, l) -> {
            String fieldOfStudy = list.get(i).toString();

            profileUtils.saveFieldStudy(fieldOfStudy);
            finish();

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