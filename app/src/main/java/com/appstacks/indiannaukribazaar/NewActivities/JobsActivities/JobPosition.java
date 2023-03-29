package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.SharedPrefe;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.data.JobPositionData;
import com.appstacks.indiannaukribazaar.databinding.ActivityJobPositionBinding;

import java.util.ArrayList;


public class JobPosition extends AppCompatActivity implements View.OnClickListener {
    private ActivityJobPositionBinding binding;
    private static final String TAG = "JobPosition";
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private SharedPrefe sharedPrefe;

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

        JobPositionData.listJobPosition.addAll(list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, JobPositionData.listJobPosition);
        adapter.setNotifyOnChange(true);
        binding.listView.setAdapter(adapter);
        binding.btnAddPosition.setOnClickListener(this::onClick);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                for (String data : list) {
                    if (data.equals(s)) {
                        binding.btnAddPosition.setVisibility(View.INVISIBLE);
                        adapter.getFilter().filter(s);
                        Log.d(TAG, s);
                    } else if (s.equals(""))
                        binding.btnAddPosition.setVisibility(View.INVISIBLE);
                    else
                        binding.btnAddPosition.setVisibility(View.VISIBLE);
                    Toast.makeText(JobPosition.this, "No such itmes in the list", Toast.LENGTH_SHORT).show();
                }

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddPosition:
                addContentToList();
                break;
        }

    }

    private void addContentToList() {

       // binding.addItemLayout.setVisibility(View.VISIBLE);
        binding.btnSubmitItem.setOnClickListener(view -> {
            if (binding.etAddItme.getText().toString().isEmpty()) {
                Toast.makeText(this, "Add any item", Toast.LENGTH_SHORT).show();
            } else {
                String item = binding.etAddItme.getText().toString();
                //((list.add(item);
                JobPositionData.listJobPosition.add(item);

                adapter.setNotifyOnChange(true);

                binding.etAddItme.setText("");

            }
        });

    }
}