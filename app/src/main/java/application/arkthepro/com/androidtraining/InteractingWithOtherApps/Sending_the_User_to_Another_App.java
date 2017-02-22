package application.arkthepro.com.androidtraining.InteractingWithOtherApps;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import application.arkthepro.com.androidtraining.R;

public class Sending_the_User_to_Another_App extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_the__user_to__another__app);
        Button btn_call = (Button) findViewById(R.id.makecall);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:123456789");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);

            }
        });
        Button btn_map = (Button) findViewById(R.id.map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Map point based on address
                Uri location = Uri.parse("geo:0,0?q=Periyanayagi Amman Temple, Periyakuppam, Tiruchchepuram, Tamil Nadu");
// Or map point based on latitude/longitude
// Uri location = Uri.parse("geo:11.6038957,79.7559298?z=14"); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);

            }
        });
        Button btn_browser = (Button) findViewById(R.id.browser);
        btn_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://gotoark.github.io/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);

            }
        });
        Button btn_email = (Button) findViewById(R.id.email);
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
// The intent does not have a URI, so declare the "text/plain" MIME type
                emailIntent.setData(Uri.parse("mailto:")); //fileter only email apps
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"gotoark@gmail.com"}); // recipients
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Wishe from a Friend");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Hey Rajesh..!! Just Tried Your App. Thanks a Lot..!!");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
// You can also attach multiple items by passing an ArrayList of Uris
                startActivity(emailIntent);

            }
        });
        Button btn_calender = (Button) findViewById(R.id.calender);
        btn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Calendar.getInstance().getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Calendar.getInstance().getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "Teddy's BirthDay");
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Cuddalore,INDIA");
                startActivity(calendarIntent);
            }
        });
        Button btn_playstore = (Button) findViewById(R.id.playstore);
        btn_playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PlayStoreintent = new Intent(Intent.ACTION_VIEW);
                PlayStoreintent.setData(Uri.parse("market://details?id=com.example.android"));
                //Caution: If you invoke an intent and there is no app available on the device that can handle the intent, your app will crash.
                //Verify There is an App to Receive the Intent
                PackageManager packageManager = getPackageManager();
                List<ResolveInfo> activities = packageManager.queryIntentActivities(PlayStoreintent, 0);
                boolean isIntentSafe = activities.size() > 0;
                if(isIntentSafe){
                    startActivity(PlayStoreintent);
                }else {
                    Toast.makeText(getApplicationContext(),"Sorry PlayStore Not Installed",Toast.LENGTH_SHORT).show();
                };;
            }
        })
;
    }
}
