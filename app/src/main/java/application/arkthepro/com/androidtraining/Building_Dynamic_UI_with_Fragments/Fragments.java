package application.arkthepro.com.androidtraining.Building_Dynamic_UI_with_Fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import application.arkthepro.com.androidtraining.R;

public class Fragments extends FragmentActivity {
    static boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        Button swap_Fragment=(Button)findViewById(R.id.swap_Fragment);
        //Controlling Fragments
       final HelloFragment helloFragment=new HelloFragment();
       final GoodByeFragment goodByeFragment=new GoodByeFragment();
        //Set default Fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentcontainer,helloFragment).commit();
        swap_Fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit)
                            .replace(R.id.fragmentcontainer,helloFragment)
                            .addToBackStack(null)
                            .commit();
                    flag=false;
                }else{
                    getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.enter,R.anim.exit)
                            .replace(R.id.fragmentcontainer, goodByeFragment)
                            .addToBackStack(null)
                            .commit();
                    flag=true;
                }

            }
        });
    }
}
