package application.arkthepro.com.androidtraining.Building_Dynamic_UI_with_Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.arkthepro.com.androidtraining.R;

public class TitleFragment_List extends Fragment  {

    //Fragment A

   //Interface Object
    public OnListItemClickLisenerinterface onListItemClickLisenerinterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("----------", "----------------------------------reateView: " +container);
        View view=inflater.inflate(R.layout.activity_title_fragment__list, container, false);
        ListView listView=(ListView)view.findViewById(R.id.listview);
        List<String> listelements=new ArrayList<String>();
        listelements.add("One");
        listelements.add("Two");
        listelements.add("Three");
        listelements.add("Four");
        listelements.add("Five");
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getActivity(),R.layout.list_elements,listelements);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClickLisenerinterface.OnListItemClicked(id);
              //  Toast.makeText(getActivity().getApplicationContext(),"ID:"+(id+1),Toast.LENGTH_SHORT).show();
            }
        });
       return view;

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onListItemClickLisenerinterface = (OnListItemClickLisenerinterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onListItemClickLisenerinterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onListItemClickLisenerinterface = null;
    }


    public  interface OnListItemClickLisenerinterface {
    public void OnListItemClicked(long id);
}

}
