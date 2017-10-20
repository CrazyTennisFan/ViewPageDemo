package lucas.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FragmentWithPageTwo extends Fragment {



    private LinearLayout ll;
    private LinearLayout.LayoutParams llLP;
    private LinearLayout.LayoutParams lp;

    private Button btndate = null;

    private void setLayout()
    {
        // This will create the LinearLayout
        ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.VERTICAL);

        // Configuring the width and height of the linear layout.
        llLP = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(llLP);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void addSectionThree()
    {
        MainActivity activity = (MainActivity) getActivity();
        HashMap<Integer, String> myData = activity.getDesc();

        TextView s3 = new TextView(getContext());
        s3.setLayoutParams(lp);
        s3.setText(myData.get(3));
        ll.addView(s3);

        btndate = new Button(getContext());
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        btndate.setText(formattedDate);
        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDatePicker();
            }
        });

        ll.addView(btndate);
    }

    private  void addSectionFour()
    {
        MainActivity activity = (MainActivity) getActivity();
        HashMap<Integer, String> myData = activity.getDesc();

        TextView s4 = new TextView(getContext());
        s4.setLayoutParams(lp);
        s4.setText(myData.get(4));
        ll.addView(s4);


        HashMap<Integer, List<String>> myvalues = activity.getValues();
        List<String> values = myvalues.get(4);
        for(int i = 0; i < values.size(); i++) {
            CheckBox ch = new CheckBox(getContext());
            ch.setId(i);
            ch.setText(values.get(i));
            ch.setOnCheckedChangeListener(new myCheckBoxChnageClicker(activity));


            ll.addView(ch);
        }
    }

    public static FragmentWithPageTwo newInstance() {
        FragmentWithPageTwo fragment = new FragmentWithPageTwo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_two, container, false);
        TextView text = new TextView(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLayout();

        addSectionThree();
        addSectionFour();

        ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.addView(ll);

    }


    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener
    {
        MainActivity myactivity;

        public myCheckBoxChnageClicker(MainActivity activity)
        {
            myactivity = activity;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked)
            {
                myactivity.addToMultiplyChoiceList(buttonView.getText().toString());
            }
            else
            {
                myactivity.removeFromMultiplyChoiceList(buttonView.getText().toString());
            }


        }
    }


    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth);
            btndate.setText(date);
            ((MainActivity)getActivity()).setDateInfo(date);
        }
    };


}
