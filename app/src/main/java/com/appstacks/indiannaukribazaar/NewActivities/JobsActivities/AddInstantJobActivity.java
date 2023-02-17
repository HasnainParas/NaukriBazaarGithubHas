package com.appstacks.indiannaukribazaar.NewActivities.JobsActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.appstacks.indiannaukribazaar.NewActivities.Adapters.CompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.GridAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.Adapters.InstantCompanyAdapter;
import com.appstacks.indiannaukribazaar.NewActivities.AddJobsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.AddPostsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.CompanyActivity;
import com.appstacks.indiannaukribazaar.NewActivities.FindJobsActivity;
import com.appstacks.indiannaukribazaar.NewActivities.InstantJobActivity;
import com.appstacks.indiannaukribazaar.NewActivities.Models.CompanyModel;
import com.appstacks.indiannaukribazaar.NewActivities.SharedPrefe;
import com.appstacks.indiannaukribazaar.R;
import com.appstacks.indiannaukribazaar.data.JobPositionData;
import com.appstacks.indiannaukribazaar.databinding.ActivityAddInstantJobBinding;
import com.appstacks.indiannaukribazaar.databinding.ActivityInstantJobBinding;
import com.appstacks.indiannaukribazaar.databinding.BudgetinstantlayoutBinding;
import com.appstacks.indiannaukribazaar.databinding.InstantpositionlayoutBinding;
import com.appstacks.indiannaukribazaar.databinding.InstanttimeperiodBinding;
import com.appstacks.indiannaukribazaar.databinding.SkillsrequiredbottomlayoutBinding;
import com.appstacks.indiannaukribazaar.profile.ProfileUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class AddInstantJobActivity extends AppCompatActivity {

    ActivityAddInstantJobBinding binding;
    ArrayList<CompanyModel> list;
    InstantCompanyAdapter adapter;
    SharedPrefe sharedPrefe;

    private ArrayList<String> positionList;
    private ArrayAdapter<String> positionAdapter;
    private ArrayAdapter<String> gridAdapter;
    private ArrayAdapter<String> skillresultAdapter;


    private ArrayList<String> skillRequiredList = new ArrayList<String>();
    private ArrayAdapter<String> skillRequiredAdapter;
    ArrayList<String> listToAdd = new ArrayList<>();

    private ProfileUtils profileUtils;
    ArrayList<String> selectedSkills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddInstantJobBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPrefe = new SharedPrefe(AddInstantJobActivity.this);
        profileUtils = new ProfileUtils(AddInstantJobActivity.this);


        binding.textCompanyInstant.setText(sharedPrefe.fetchInstantCom());
        binding.txtPositonInstant.setText(sharedPrefe.fetchInstantPos());
        binding.txtBudgetInstant.setText(sharedPrefe.fetchInstantBudget());
        binding.txtTimePeriodInstant.setText(sharedPrefe.fetchInstantTimePeriod());
        selectedSkills = profileUtils.fetchSelectedSkills();


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < selectedSkills.size(); i++) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(lparams);
            tv.setText(selectedSkills.get(i));
            binding.textlayout.addView(tv);
        }

        iconChange();

        binding.cancelBtnintant.setOnClickListener(view -> {

            sharedPrefe.deleteCom();
            profileUtils.deleteSelectedSkills();
            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

        });


        binding.btnCompanyintant.setOnClickListener(view -> companyBottomDialog());

        binding.jobPositionintant.setOnClickListener(view -> positionBottomDialog());

        binding.budgetBtnintant.setOnClickListener(view -> budgetBottomDialog());

        binding.timePeriodBtnintant.setOnClickListener(view -> timePeriodBottomDialog());

        binding.skillRequriedIntant.setOnClickListener(view -> skillsRequiredBottomDialog());


    }

    private void companyBottomDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(AddInstantJobActivity.this, R.style.AppBottomSheetDialogTheme);

        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.activity_company, (CardView) findViewById(R.id.UndoChanges));
        dialog.setContentView(bottomsheetView);
        dialog.show();
        dialog.setCancelable(true);
        RecyclerView recyclerViewBottomSheet = bottomsheetView.findViewById(R.id.recyclerViewCom);
        SearchView searchView = bottomsheetView.findViewById(R.id.searchViewCom);

        list = new ArrayList<>();
        list.add(new CompanyModel(R.drawable.googleic, "Google", "Internet"));
        list.add(new CompanyModel(R.drawable.ic_apple, "Apple", "Electronic goods"));
        list.add(new CompanyModel(R.drawable.ic_amazon, "Amazon", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Dribble", "Design"));
        list.add(new CompanyModel(R.drawable.googleic, "Twitter", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Facebook", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Microsoft", "Internet"));
        list.add(new CompanyModel(R.drawable.googleic, "Allianz", "Financial Service"));
        list.add(new CompanyModel(R.drawable.googleic, "Adobe", "Computer software"));
        list.add(new CompanyModel(R.drawable.googleic, "AXA", "Insurance"));
        adapter = new InstantCompanyAdapter(list, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(AddInstantJobActivity.this);
        recyclerViewBottomSheet.setLayoutManager(layoutManager);
        recyclerViewBottomSheet.setAdapter(adapter);

        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
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

    private void positionBottomDialog() {
        BottomSheetDialog positondialog = new BottomSheetDialog(AddInstantJobActivity.this, R.style.AppBottomSheetDialogTheme);

        InstantpositionlayoutBinding positionBinding = InstantpositionlayoutBinding.inflate(getLayoutInflater());
        positondialog.setContentView(positionBinding.getRoot());
        positondialog.show();
        positondialog.setCancelable(true);

        positionList = new ArrayList<String>();

        positionList.add("Assistant");
        positionList.add("Associate");
        positionList.add("Administrative Assistant");
        positionList.add("Account Manager");
        positionList.add("Assistant Manager");
        positionList.add("Commission Sales Associate");
        positionList.add("Sales Attendant");
        positionList.add("Accountant");
        positionList.add("Sales Advocate");
        positionList.add("Analyst");

        JobPositionData.listJobPosition.addAll(positionList);

        positionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, JobPositionData.listJobPosition);
        positionAdapter.setNotifyOnChange(true);
        positionBinding.instantListView.setAdapter(positionAdapter);

        positionBinding.instantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int index = i;
//                Intent intent = new Intent(getApplicationContext(), AddInstantJobActivity.class);
                String tv = positionList.get(i).toString();
                sharedPrefe.saveInstantPos(tv);
                startActivity(new Intent(getApplicationContext(), AddInstantJobActivity.class));
                finish();
            }
        });

    }

    private void budgetBottomDialog() {
        BudgetinstantlayoutBinding budgetBinding = BudgetinstantlayoutBinding.inflate(getLayoutInflater());

        BottomSheetDialog dialog = new BottomSheetDialog(AddInstantJobActivity.this, R.style.AppBottomSheetDialogTheme);

        dialog.setContentView(budgetBinding.getRoot());
        dialog.show();
        dialog.setCancelable(true);

        budgetBinding.budgetclosebtn.setOnClickListener(view -> dialog.dismiss());

        budgetBinding.noticePeriodEditBox.setOnEditorActionListener((textView, i, keyEvent) -> {
            String hourlyRate = budgetBinding.hourlRateEditBox.getText().toString();
            String dailywork = budgetBinding.dailyworkEditBox.getText().toString();
            String noticePeriod = budgetBinding.noticePeriodEditBox.getText().toString();

            if (i == EditorInfo.IME_ACTION_DONE) {
                if (hourlyRate.isEmpty()) {
                    Toast.makeText(AddInstantJobActivity.this, "Please set hourly rate", Toast.LENGTH_SHORT).show();
                } else {
                    String allEditBoxTxt = hourlyRate + " + " + dailywork + " + " + noticePeriod;
                    sharedPrefe.saveInstantBudget("$" + hourlyRate);
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), AddInstantJobActivity.class));
                    finish();
                    Toast.makeText(AddInstantJobActivity.this, allEditBoxTxt, Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });

        budgetBinding.maxbudgetEditBox.setOnEditorActionListener((textView, i, keyEvent) -> {
            String maxProjectBudget = budgetBinding.maxbudgetEditBox.getText().toString();

            if (i == EditorInfo.IME_ACTION_DONE) {
                if (maxProjectBudget.isEmpty()) {
//                        budgetBinding.hourlRateEditBox.setError("Can't be empty");
                    Toast.makeText(AddInstantJobActivity.this, "Please set hourly rate", Toast.LENGTH_SHORT).show();
                } else {
                    sharedPrefe.saveInstantBudget("$" + maxProjectBudget);
                    dialog.dismiss();
                    finish();
                    startActivity(new Intent(getApplicationContext(), AddInstantJobActivity.class));
                    Toast.makeText(AddInstantJobActivity.this, maxProjectBudget, Toast.LENGTH_SHORT).show();
                }
            }

            return false;
        });

        budgetBinding.hourlyRatedClick.setOnClickListener(view -> {
            budgetBinding.hourlyRatedClick.setBackgroundResource(R.drawable.greenradiobtnshape);
            budgetBinding.projectBudgetClick.setBackgroundResource(R.drawable.radiobtnshape);
            budgetBinding.hourlView.setVisibility(View.VISIBLE);
            budgetBinding.budgetview.setVisibility(View.GONE);

            budgetBinding.hourlyratedLayout.setVisibility(View.VISIBLE);
            budgetBinding.budgetperiodlayout1.setVisibility(View.GONE);

        });

        budgetBinding.projectBudgetClick.setOnClickListener(view -> {
            budgetBinding.projectBudgetClick.setBackgroundResource(R.drawable.greenradiobtnshape);
            budgetBinding.hourlyRatedClick.setBackgroundResource(R.drawable.radiobtnshape);
            budgetBinding.budgetview.setVisibility(View.VISIBLE);
            budgetBinding.hourlView.setVisibility(View.GONE);

            budgetBinding.budgetperiodlayout1.setVisibility(View.VISIBLE);
            budgetBinding.hourlyratedLayout.setVisibility(View.GONE);
        });

    }

    private void timePeriodBottomDialog() {
        BottomSheetDialog timeperioddialog = new BottomSheetDialog(AddInstantJobActivity.this, R.style.AppBottomSheetDialogTheme);

        InstanttimeperiodBinding instanttimeBinding = InstanttimeperiodBinding.inflate(getLayoutInflater());
        timeperioddialog.setContentView(instanttimeBinding.getRoot());
        timeperioddialog.show();
        timeperioddialog.setCancelable(true);


        instanttimeBinding.radioBtn1to3month.setOnClickListener(view -> {
            String working1to3Months = instanttimeBinding.radioBtn1to3month.getText().toString();
            sharedPrefe.saveInstantTimePeriod(working1to3Months);
            timeperioddialog.dismiss();
            binding.txtTimePeriodInstant.setText(working1to3Months);
            binding.txtTimePeriodInstant.setVisibility(View.VISIBLE);
            Toast.makeText(AddInstantJobActivity.this, working1to3Months, Toast.LENGTH_SHORT).show();

        });

        instanttimeBinding.radioBtn3to6month.setOnClickListener(view -> {
            String working3to6month = instanttimeBinding.radioBtn3to6month.getText().toString();

            sharedPrefe.saveInstantTimePeriod(working3to6month);
            timeperioddialog.dismiss();
            binding.txtTimePeriodInstant.setText(working3to6month);
            binding.txtTimePeriodInstant.setVisibility(View.VISIBLE);
            Toast.makeText(AddInstantJobActivity.this, working3to6month, Toast.LENGTH_SHORT).show();
        });

        instanttimeBinding.radiobtnMorethnMonths.setOnClickListener(view -> {
            String moreThanMonths = instanttimeBinding.radiobtnMorethnMonths.getText().toString();

            instanttimeBinding.radiobtnMorethnMonths.isChecked();

            sharedPrefe.saveInstantTimePeriod(moreThanMonths);
            timeperioddialog.dismiss();
            binding.txtTimePeriodInstant.setText(moreThanMonths);
            binding.txtTimePeriodInstant.setVisibility(View.VISIBLE);
            Toast.makeText(AddInstantJobActivity.this, moreThanMonths, Toast.LENGTH_SHORT).show();

        });


    }

    private void skillsRequiredBottomDialog() {

        BottomSheetDialog skillsRequireddialog = new BottomSheetDialog(AddInstantJobActivity.this, R.style.AppBottomSheetDialogTheme);

        SkillsrequiredbottomlayoutBinding skillsrequiredBinding = SkillsrequiredbottomlayoutBinding.inflate(getLayoutInflater());
        skillsRequireddialog.setContentView(skillsrequiredBinding.getRoot());
        skillsRequireddialog.show();
        skillsRequireddialog.setCancelable(false);
        skillsrequiredBinding.instantskillreqbackbtn.setOnClickListener(view -> skillsRequireddialog.dismiss());


        skillRequiredList.add("UI/UX Designing");
        skillRequiredList.add("Graphics Designing");
        skillRequiredList.add("Voice Over");
        skillRequiredList.add("Game Developer");
        skillRequiredList.add("Excel");
        skillRequiredList.add("Software Development");
        skillRequiredList.add("Powerpoint");
        skillRequiredList.add("Game Designer");
        skillRequiredList.add("DevOps");
        skillRequiredList.add("Machine learning");
        skillRequiredList.add("Data analysis");
        skillRequiredList.add("Data 1");
        skillRequiredList.add("Data 2");


        skillRequiredAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, skillRequiredList);
        skillRequiredAdapter.setNotifyOnChange(true);

        skillsrequiredBinding.instantSkillReqListView.setAdapter(skillRequiredAdapter);
//        skillRequiredList.clear();


        skillsrequiredBinding.instantSkillReqListView.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = skillRequiredList.get(i);


            //TODO thewr uihifhsdgfyuasidgsdgaysfv j vwue ridagydeorfusfgd ihas igdjsdadgas yugfastydi
            if (listToAdd.isEmpty()) {
                listToAdd.add(item);
                gridAdapter.notifyDataSetChanged();

            } else if (!listToAdd.contains(item)) {
                listToAdd.add(item);
                gridAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(this, "Already", Toast.LENGTH_SHORT).show();
            }

            if (listToAdd.size() == 10) {
//                Toast.makeText(this, "Juuu 10 thega", Toast.LENGTH_SHORT).show();
                skillresultDialog();

            }

        });


        gridAdapter = new ArrayAdapter<>(this, R.layout.grid_item, R.id.tvidd, listToAdd);
        //  JobPositionData.listJobPosition.addAll(skillRequiredList);


        skillsrequiredBinding.gridSkillReq.setAdapter(gridAdapter);

        skillsrequiredBinding.gridSkillReq.setOnItemClickListener((adapterView, view, i, l) -> {
            listToAdd.remove(i);
            gridAdapter.notifyDataSetChanged();
        });


    }


    private void iconChange() {


        if (binding.textCompanyInstant.length() != 0) {
            binding.textCompanyInstant.setVisibility(View.VISIBLE);
            binding.btnEdit1intant.setVisibility(View.VISIBLE);
            binding.btnAdd1intant.setVisibility(View.GONE);
        } else {
            binding.textCompanyInstant.setVisibility(View.GONE);
            binding.btnEdit1intant.setVisibility(View.GONE);
            binding.btnAdd1intant.setVisibility(View.VISIBLE);
        }

        if (binding.txtPositonInstant.length() != 0) {
            binding.txtPositonInstant.setVisibility(View.VISIBLE);
            binding.btnEdit2intant.setVisibility(View.VISIBLE);
            binding.btnAdd2intant.setVisibility(View.GONE);
        } else {
            binding.txtPositonInstant.setVisibility(View.GONE);
            binding.btnEdit2intant.setVisibility(View.GONE);
            binding.btnAdd2intant.setVisibility(View.VISIBLE);
        }

        //bud
        if (binding.txtBudgetInstant.length() != 0) {
            binding.txtBudgetInstant.setVisibility(View.VISIBLE);
            binding.btnEdit3intant.setVisibility(View.VISIBLE);
            binding.btnAdd3intant.setVisibility(View.GONE);
        } else {
            binding.txtBudgetInstant.setVisibility(View.GONE);
            binding.btnEdit3intant.setVisibility(View.GONE);
            binding.btnAdd3intant.setVisibility(View.VISIBLE);
        }
        //time
        if (binding.txtTimePeriodInstant.length() != 0) {
            binding.txtTimePeriodInstant.setVisibility(View.VISIBLE);
            binding.btnEdit4intant.setVisibility(View.VISIBLE);
            binding.btnAdd4intant.setVisibility(View.GONE);
        } else {
            binding.txtTimePeriodInstant.setVisibility(View.GONE);
            binding.btnEdit4intant.setVisibility(View.GONE);
            binding.btnAdd4intant.setVisibility(View.VISIBLE);
        }
        //skill
        if (!selectedSkills.isEmpty() && selectedSkills == null) {
            binding.textlayout.setVisibility(View.VISIBLE);
            binding.btnEdit5intant.setVisibility(View.VISIBLE);
            binding.btnAdd5intant.setVisibility(View.GONE);
        } else {
            binding.textlayout.setVisibility(View.GONE);
            binding.btnEdit5intant.setVisibility(View.GONE);
            binding.btnAdd5intant.setVisibility(View.VISIBLE);
        }

    }

    public void skillresultDialog() {
        View view = LayoutInflater.from(AddInstantJobActivity.this).inflate(R.layout.skillsresultdialog, null);

        AlertDialog alertDialog = new AlertDialog.Builder(AddInstantJobActivity.this).setView(view).create();

        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        alertDialog.setCancelable(false);

        alertDialog.show();

        ListView resultlist;
        Button okBtn, cancelBtn;

        skillresultAdapter = new ArrayAdapter<>(this, R.layout.resultlayout4skills, R.id.resulttvidd, listToAdd);
        //  JobPositionData.listJobPosition.addAll(skillRequiredList);

        resultlist = view.findViewById(R.id.skillresultslist);
        cancelBtn = view.findViewById(R.id.cancel_buttonResult);
        okBtn = view.findViewById(R.id.ok_button);

        cancelBtn.setOnClickListener(view1 -> alertDialog.dismiss());

        okBtn.setOnClickListener(view1 -> {
            if (!listToAdd.isEmpty()) {
                profileUtils.saveSelectedSkills(listToAdd);
                startActivity(new Intent(AddInstantJobActivity.this, AddInstantJobActivity.class));
                finish();
            } else {
                alertDialog.dismiss();
                Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show();
            }
        });


        resultlist.setAdapter(skillresultAdapter);

    }


}