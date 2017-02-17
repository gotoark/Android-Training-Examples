package application.arkthepro.com.androidtraining.Building_Dynamic_UI_with_Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import application.arkthepro.com.androidtraining.R;


public class List_Explained extends Fragment  {
    //Fragments B
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_list__explained, container, false);
       tv=(TextView)view.findViewById(R.id.text_explained);
        tv.setText("Click list item in Fragment A");
        return view;
    }

    public void setTv(String txt){
        tv.setText(txt);
    }




}
