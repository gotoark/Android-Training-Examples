package application.arkthepro.com.androidtraining.BuildingYourFirstApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import application.arkthepro.com.androidtraining.R;

public class DisplayMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        //Get the Value from Intents
        Intent i=getIntent();
        String message=i.getStringExtra(MainActivity.MSG);
        TextView textView=new TextView(this);
        textView.setText(message);
        textView.setTextSize(40);

       //Adding TextView to Layout

        ViewGroup layout=(ViewGroup)findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
