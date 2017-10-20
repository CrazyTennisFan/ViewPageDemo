package lucas.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

public class FragmentWithPageOne extends Fragment {


    private LinearLayout ll;
    private LinearLayout.LayoutParams llLP;
    private LinearLayout.LayoutParams lp;


    public static FragmentWithPageOne newInstance() {
        FragmentWithPageOne fragment = new FragmentWithPageOne();
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


    private void addElements()
    {
        TextView s1 = new TextView(getContext());
        s1.setLayoutParams(lp);

        HashMap<Integer, String> desc = ((MainActivity) getActivity()).getDesc();

        s1.setText(desc.get(1));
        ll.addView(s1);

        final EditText myEditText=  new EditText(getContext());
        myEditText.setTextColor(Color.RED);
        ll.addView(myEditText);


        TextView s2 = new TextView(getContext());
        s2.setText(desc.get(2));
        ll.addView(s2);

        // Initialize a new RadioGroup
        RadioGroup rg = new RadioGroup(getContext());
        rg.setOrientation(RadioGroup.VERTICAL);
        rg.setLayoutParams(lp);

        HashMap<Integer, List<String>> myValues = ((MainActivity)getActivity()).getValues();
        List<String> values = myValues.get(2);
        for(int i = 0; i < values.size(); i++)
        {
            // Create a Radio Button for RadioGroup
            RadioButton rb = new RadioButton(getContext());
            rb.setText(values.get(i));
            rb.setTextColor(Color.BLACK);
            rg.addView(rb);
        }


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                RadioButton btn = (RadioButton) radioGroup.getChildAt(index);
                String genderInfo = (String) btn.getText();
                ((MainActivity) getActivity()).setSectionOne(myEditText.getText().toString(), genderInfo);
            }
        });

        // Finally, add the RadioGroup to main layout
        ll.addView(rg);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setLayout();
        addElements();

        ViewGroup viewGroup = (ViewGroup) view;
        viewGroup.addView(ll);

    }
}
