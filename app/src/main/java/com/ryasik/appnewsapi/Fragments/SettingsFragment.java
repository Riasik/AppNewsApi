package com.ryasik.appnewsapi.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.ryasik.appnewsapi.Filters.ArticleFilter;
import com.ryasik.appnewsapi.R;

import java.util.Calendar;

public class SettingsFragment extends DialogFragment{

    TextView etFromDateText;
    TextView etToDateText;
    Spinner sprSortOrder;
    Button btnSave;
    ArrayAdapter<CharSequence> spinnerAdapter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public SettingsFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_settings, container, false);
        etFromDateText = (TextView) view.findViewById(R.id.etDate);
        etToDateText = (TextView) view.findViewById(R.id.etToDate);
        btnSave = (Button)view.findViewById(R.id.btnSave);
        loadSortOrder(view);
        return view;
    }

    private void loadSortOrder(View view){
        sprSortOrder = (Spinner) view.findViewById(R.id.sort_spinner);
        spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sortorder_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprSortOrder.setAdapter(spinnerAdapter);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        setListeners();
        setData();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    private void setData(){
        ArticleFilter settings = ArticleFilter.getArticleFilterInstance();
        etFromDateText.setText(settings.getFromDate());
        etToDateText.setText(settings.getToDate());
        int sprPosition = spinnerAdapter.getPosition(settings.getSortOrder());
        sprSortOrder.setSelection(sprPosition);
    }

    private void setListeners(){
        etFromDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v.getId());
            }
        });
        etToDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v.getId());
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClick(v);
            }
        });
    }

    private void onSaveClick(View v){
        ArticleFilter.getArticleFilterInstance().setFromDate((String.valueOf(etFromDateText.getText())));
        ArticleFilter.getArticleFilterInstance().setToDate((String.valueOf(etToDateText.getText())));
        ArticleFilter.getArticleFilterInstance().setSortOrder(String.valueOf(sprSortOrder.getSelectedItem()));
        dismiss();
    }

    private void showDatePicker(final int idDate){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        final Calendar cal = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year",cal.get(Calendar.YEAR));
        args.putInt("month",cal.get(Calendar.MONTH));
        args.putInt("day",cal.get(Calendar.DAY_OF_MONTH));
        datePickerFragment.setArguments(args);

        final DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                if (idDate == R.id.etDate) {
                    etFromDateText.setText(String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth)
                            + "/" + String.valueOf(year));
                }else {
                    etToDateText.setText(String.valueOf(monthOfYear + 1) + "/" + String.valueOf(dayOfMonth)
                            + "/" + String.valueOf(year));
                }
            }
        };

        datePickerFragment.setCallBack(ondate);
        datePickerFragment.show(getFragmentManager(),"Date Picker");
    }

}
