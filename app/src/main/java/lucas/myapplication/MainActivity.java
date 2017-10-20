package lucas.myapplication;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Environment;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter adapterViewPager;

    private HashMap<Integer, String>  page_desc;
    private HashMap<Integer, List<String>>  page_values;

    private String name;
    private String genderInfo;
    private List<String> multiplyChoiceList;
    private String date;
    private String answerFive;

    public HashMap<Integer, String> getDesc()
    {
        return page_desc;
    }

    public HashMap<Integer, List<String>> getValues()
    {
        return page_values;
    }


    public void setAnswerFive(String answer)
    {
        this.answerFive = answer;
    }

    public void setDateInfo(String dateinfo)
    {
        this.date = dateinfo;
    }

    public void addToMultiplyChoiceList(String item)
    {
        multiplyChoiceList.add(item);
    }

    public void removeFromMultiplyChoiceList(String item)
    {
        multiplyChoiceList.remove(item);
    }

    public void setSectionOne(String name, String gender)
    {
        this.name = name;
        this.genderInfo = gender;
    }

    public void parseJSONFile()
    {
        page_desc = new HashMap();
        page_values = new HashMap();

        Resources res = this.getResources();
        InputStream is = res.openRawResource(R.raw.view_paper);
        Scanner scan = new Scanner(is);
        StringBuilder sb = new StringBuilder();
        while(scan.hasNextLine())
        {
            sb.append(scan.nextLine());
        }

        try {
            JSONObject root  = new JSONObject(sb.toString());
            JSONObject student = root.getJSONObject("question");
            String q1 = student.getString("section_one_description");
            page_desc.put(1, q1);

            String q2  = student.getString("section_two_description");
            page_desc.put(2, q2);

            JSONArray value2 = student.getJSONArray("section_two_values");
            List<String> list = new ArrayList();
            for(int i = 0; i < value2.length(); i++)
            {
                JSONObject value= value2.getJSONObject(i);
                String innerValue = value.getString("value");
                list.add(innerValue);
            }

            page_values.put(2, list);

            String q3  = student.getString("section_three_description");
            page_desc.put(3, q3);

            String q4  = student.getString("section_four_description");
            page_desc.put(4, q4);

            JSONArray value4 = student.getJSONArray("section_four_values");
            List<String> list2 = new ArrayList();
            for(int i = 0; i < value4.length(); i++)
            {
                JSONObject value= value4.getJSONObject(i);
                String innerValue = value.getString("value");
                list2.add(innerValue);
            }

            page_values.put(4, list2);

            String q5  = student.getString("section_five_description");
            page_desc.put(5, q5);

            JSONArray value5 = student.getJSONArray("section_five_values");
            List<String> list3 = new ArrayList();
            for(int i = 0; i < value5.length(); i++)
            {
                JSONObject value= value5.getJSONObject(i);
                String innerValue = value.getString("value");
                list3.add(innerValue);
            }

            page_values.put(5, list3);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        multiplyChoiceList = new ArrayList();

        parseJSONFile();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.item1:
                    saveToFile();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveToFile()
    {
        String info = "Nickname is " + this.name + "\n";
        info += "Gender is " + this.genderInfo + "\n";
        info += "Date is " + this.date + "\n";
        info += "Food is ";
        for(int i = 0; i < multiplyChoiceList.size(); i++ )
        {
            info+= multiplyChoiceList.get(i) + "  ";
        }
        info += "\n";
        info += "The answer of client is " + this.answerFive;

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        DateFormat df = new SimpleDateFormat("MMddHHmmss");
        Date dateobj = new Date();
        String heartRateFileName = "Demo_" + df.format(dateobj) +".txt";

        File fileHeartRate = new File( root, heartRateFileName);
        try {
            FileOutputStream fileOutputStreamHeartRate = new FileOutputStream(fileHeartRate);
            byte[] contentInBytes = info.getBytes();
            fileOutputStreamHeartRate.write(contentInBytes);
            fileOutputStreamHeartRate.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Save file ok.", Toast.LENGTH_SHORT).show();
        Log.i("PKPK", info);
    }

}
