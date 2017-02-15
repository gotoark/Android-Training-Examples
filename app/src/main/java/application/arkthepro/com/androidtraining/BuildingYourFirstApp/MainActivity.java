package application.arkthepro.com.androidtraining.BuildingYourFirstApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import application.arkthepro.com.androidtraining.BuildingYourFirstApp.DisplayMessage;
import application.arkthepro.com.androidtraining.R;

public class MainActivity extends AppCompatActivity {
public static final String MSG="messageKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){
        //Invoked when the User Clicks the Send Button
        EditText editText=(EditText)findViewById(R.id.edit_message);
        String editTextMsg=editText.getText().toString();
        Intent i=new Intent(this,DisplayMessage.class);
        i.putExtra(MSG,editTextMsg);
        startActivity(i);
    }
}
