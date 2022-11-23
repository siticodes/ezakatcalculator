package com.example.ezakatcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText gram, value;
    Button calcBtn, resetBtn;
    TextView output1, output2, output3;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    float gweight;
    float gvalue;

    SharedPreferences sharedPref;
    SharedPreferences sharedPref2;
    private Menu menu;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){

    }

    public void onNothingSelected(AdapterView<?> parent){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        gram = (EditText) findViewById(R.id.goldweight);
        value = (EditText) findViewById(R.id.goldvalue);
        output1 = (TextView) findViewById(R.id.totalgold);
        output2 = (TextView) findViewById(R.id.zakatpay);
        output3 = (TextView) findViewById(R.id.totalzakat);
        calcBtn = (Button) findViewById(R.id.btncal);
        resetBtn = (Button) findViewById(R.id.btnreset);

        calcBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        sharedPref = this.getSharedPreferences("weight", Context.MODE_PRIVATE);
        gweight = sharedPref.getFloat("weight", 0.0F);

        sharedPref2 = this.getSharedPreferences("value", Context.MODE_PRIVATE);
        gvalue = sharedPref2.getFloat("value", 0.0F);

        gram.setText(" " + gweight);
        value.setText(" " + gvalue);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.about:
                // Toast.makeText(this."About",Toast.LENGTH_LONG.show();
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        try {
            switch (v.getId()) {
                case R.id.btncal:
                    calc();
                    break;

                case R.id.btnreset:
                    gram.setText(" ");
                    value.setText(" ");
                    output1.setText("Total Gold Value: RM ");
                    output2.setText("Zakat Payable: RM ");
                    output3.setText("Total Zakat: RM ");
                    break;
            }
        }
        catch(java.lang.NumberFormatException nfe) {
            Toast.makeText(this, "Input Missing!", Toast.LENGTH_LONG).show();
        }
        catch(Exception exp){
            Toast.makeText(this, "Unknown Exception" + exp.getMessage(), Toast.LENGTH_LONG).show();

            Log.d("Exception", exp.getMessage());
        }
    }

    public void calc(){
        DecimalFormat df = new DecimalFormat("##.00");
        float gweight = Float.parseFloat(gram.getText().toString());
        float gvalue = Float.parseFloat(value.getText().toString());
        String stat = spinner.getSelectedItem().toString();
        double tValue, uruf, zakatpay, tZakat;

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat("weight", gweight);
        editor.apply();

        SharedPreferences.Editor editor2 = sharedPref2.edit();
        editor2.putFloat("value", gvalue);
        editor2.apply();

        if(stat.equals("Keep")){
            tValue = gweight * gvalue;
            uruf = gweight - 85;

            if(uruf >= 0.0){
                zakatpay = uruf * gvalue;
                tZakat = zakatpay * 0.025;
            }

            else{
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }

            output1.setText("Total Gold Value: RM " + df.format(tValue));
            output2.setText("Zakat Payable: RM " + df.format(zakatpay));
            output3.setText("Total Zakat: RM " + df.format(tZakat));
        }

        else{
            tValue = gweight * gvalue;
            uruf = gweight - 200;

            if(uruf >= 0.0){
                zakatpay = uruf * gvalue;
                tZakat = zakatpay * 0.025;
            }

            else{
                zakatpay = 0.0;
                tZakat = zakatpay * 0.025;
            }

            output1.setText("Total Gold Value: RM " + df.format(tValue));
            output2.setText("Zakat Payable: RM " + df.format(zakatpay));
            output3.setText("Total Zakat: RM " + df.format(tZakat));
        }
    }
}