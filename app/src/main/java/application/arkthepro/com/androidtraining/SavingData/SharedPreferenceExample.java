package application.arkthepro.com.androidtraining.SavingData;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        Button add=(Button)findViewById(R.id.btn_addpreference);

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
                editor.putString(NAME_KEY,editText.getText().toString());
                editor.commit();
                Toast.makeText(getApplicationContext(),"Shared Preference Updated.Plz Restart the App",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
