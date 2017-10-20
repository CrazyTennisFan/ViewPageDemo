package lucas.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class FragmentWithPageThree extends Fragment {


    private LinearLayout ll;
    private LinearLayout.LayoutParams llLP;
    private LinearLayout.LayoutParams lp;

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

    private void addSectionFive()
    {
        TextView s1 = new TextView(getContext());
        s1.setLayoutParams(lp);

        MainActivity activity = (MainActivity) getActivity();
        HashMap<Integer, String> myData = activity.getDesc();

        s1.setText(myData.get(5));
        ll.addView(s1);

        // Initialize a new RadioGroup
        RadioGroup rg = new RadioGroup(getContext());
        rg.setOrientation(RadioGroup.VERTICAL);
        rg.setLayoutParams(lp);

        HashMap<Integer, List<String>> myValues = activity.getValues();
        List<String> values = myValues.get(5);
        for(int i = 0; i < values.size(); i++)
        {
            // Create a Radio Button for RadioGroup
            RadioButton rb_coldfusion = new RadioButton(getContext());
            rb_coldfusion.setText(values.get(i));
            rb_coldfusion.setTextColor(Color.BLACK);
            rg.addView(rb_coldfusion);
        }


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) radioGroup.getChildAt(index);
                String content = (String) btn.getText();
                ((MainActivity) getActivity()).setAnswerFive(content);
            }
        });


        ll.addView(rg);
    }


    public static FragmentWithPageThree newInstance() {
        FragmentWithPageThree fragment = new FragmentWithPageThree();
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
        View view = inflater.inflate(R.layout.page_one, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setLayout();
        addSectionFive();


        ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.addView(ll);

    }
}
