package application.arkthepro.com.androidtraining.SavingData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import application.arkthepro.com.androidtraining.R;

public class SharedPreferenceExample extends AppCompatActivity {
 private  String NAME_KEY="last_saved_name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);
        //Create Shared Preeference
        SharedPreferences sharedPreferences=this.getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedPreferences.edit();
       /* if(sharedPreferences.getString(NAME_KEY,"no name")=="no name"){

        }else {

        }*/
        final EditText editText=(EditText)findViewById(R.id.edit_text_preference);
        editText.setText(sharedPreferences.getString(NAME_KEY, "no name"));
        final TextView filecontent=(TextView)findViewById(R.id.filecontent);
        Button add=(Button)findViewById(R.id.btn_addpreference);
        Button addasfile=(Button)findViewById(R.id.btn_addasfile);
        Button readFile=(Button)findViewById(R.id.btn_readfile);

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editText.setText("");
                return false;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(NAME_KEY, editText.getText().toString());
                editor.commit();
                editText.setText(editText.getText().toString());
                Toast.makeText(getApplicationContext(), "Shared Preference Updated.Plz Restart the App", Toast.LENGTH_SHORT).show();
            }
        });

        //Creating File
        String filename="test";
        final File file=new File(getFilesDir(),filename+".txt");


        addasfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if file not exists then create new File
                try {
                    if(!file.exists()){
                        file.createNewFile();
                        Log.d("FILE", "File Created Successfully");
                    }else{
                        Log.d("FILE", "File Already Exists with name :"+file.getName());
                    }
                    FileWriter fw=new FileWriter(file.getAbsoluteFile());
                    BufferedWriter bw=new BufferedWriter(fw);
                    String fcontent=editText.getText().toString();
                    Log.e("FILE", "-----------------------------------Writed: " + fcontent);
                    bw.write(fcontent);
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("FILE RESULT", "Failed to Create File");
                }


            }
        });
        readFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                String text="";
                try {
                    FileReader fileReader=new FileReader(file.getAbsoluteFile());
                    BufferedReader br=new BufferedReader(fileReader);
                    while ((text = br.readLine()) != null) {
                        stringBuilder.append(text +"\n");
                        Log.d("STRING BUILDER","-------------------------------ADDED STRING"+stringBuilder.toString());
                    }
                 filecontent.setText(stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });




    }
}
