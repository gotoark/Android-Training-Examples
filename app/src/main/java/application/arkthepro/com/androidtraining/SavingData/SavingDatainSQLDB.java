package application.arkthepro.com.androidtraining.SavingData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import application.arkthepro.com.androidtraining.R;

public class SavingDatainSQLDB extends AppCompatActivity {
    Activity a = this;
    ListView userList;
    List<UserDetails> al_userdetails;
    ArrayList<Integer> al_id = new ArrayList<>();
    ArrayList<String> al_name = new ArrayList<>();
    ArrayList<String> al_number = new ArrayList<>();
    final DBUtil dbUtil = new DBUtil(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_datain_sqldb);
        //Create DB instance to access All DB CRUD operations
        userList = (ListView) findViewById(R.id.userlist);
        final EditText username = (EditText) findViewById(R.id.edit_text_username);
        final EditText number = (EditText) findViewById(R.id.edit_text_mnumber);
        final Button create = (Button) findViewById(R.id.btn_create);
        Button delete_user = (Button) findViewById(R.id.btn_delete_user);
        Button update = (Button) findViewById(R.id.btn_update);
        Button delete = (Button) findViewById(R.id.btn_delete);
        Log.d("DB RESULT", "Oncreate SQL CLASS");
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    username.setError("Please Enter Name");
                } else if (number.getText().toString().equals("")) {
                    number.setError("Please Enter Mobile Number");
                } else {
                    dbUtil.createUser(new UserDetails(username.getText().toString(), number.getText().toString()));
                    updateList();
                }
            }
        });
        delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number.getText().toString().equals("")) {
                    number.setError("Please Enter Mobile Number");
                } else {

                    if (dbUtil.isExists(DBUtil.KEY_NUMBER, number.getText().toString())) {
                        dbUtil.deleteUser(number.getText().toString());
                        updateList();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sry No User found with Mobile Number :" + number.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        updateList();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    username.setError("Please Enter Name");
                } else if (number.getText().toString().equals("")) {
                    number.setError("Please Enter Mobile Number");
                } else {
                    if (dbUtil.isExists(DBUtil.KEY_NUMBER, number.getText().toString())) {
                        dbUtil.updateUser(username.getText().toString(), number.getText().toString());
                        updateList();
                    } else {
                        Toast.makeText(getApplicationContext(), "Sry No User found with Mobile Number :" + number.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (al_id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "User List is Already Empty", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(a);
                    alertDialogBuilder.setMessage("Are You Sure to DELETE Table Values...?");
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dbUtil.emptyTable(getApplicationContext());
                            updateList();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = alertDialogBuilder.create();
                    dialog.setTitle("Warning...!!!");
                    dialog.setIcon(android.R.drawable.ic_delete);
                    dialog.show();

                }


            }
        });
    }

    public void updateList() {
        al_userdetails = dbUtil.getAllUsers();
        //Empty the Array List
        al_id.clear();
        al_name.clear();
        al_number.clear();
        for (UserDetails l : al_userdetails) {
            al_id.add(l.getId());
            al_name.add(l.getName());
            al_number.add(l.getphoneNumber());
            Log.i("DB RESULTS", "****************ID: " + l.getId() + "NAME: " + l.getName() + "Number: " + l.getphoneNumber());
        }

        UserAdapter userAdapter = new UserAdapter(getApplicationContext(), al_id, al_name, al_number);
        userList.clearDisappearingChildren();
        userList.setAdapter(userAdapter);
        Log.i("RESULT", "-----------------------UserAdapter Assigned");
    }


    public class UserAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<String> al_name;
        private ArrayList<String> al_number;
        private ArrayList<Integer> al_id;
        private LayoutInflater layoutinflater = null;

        public class Users_ViewHolder {
            TextView tv_username;
            TextView tv_number;
            TextView tv_id;
        }

        public UserAdapter(Context c, ArrayList<Integer> al_id, ArrayList<String> al_name, ArrayList<String> al_number) {
            this.context = c;
            this.al_name = al_name;
            this.al_number = al_number;
            this.al_id = al_id;
            layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("RESULT", "-----------------------UserAdapter Initialized");

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Users_ViewHolder user_modal = new Users_ViewHolder();
            View view;
            view = layoutinflater.inflate(R.layout.userslist, null);
            user_modal.tv_id = (TextView) view.findViewById(R.id.id);
            user_modal.tv_username = (TextView) view.findViewById(R.id.tv_username);
            user_modal.tv_number = (TextView) view.findViewById(R.id.tv_number);
            Log.i("RESULT", "-----------------------GET Views");
            //Assign Values
            user_modal.tv_username.setText(al_name.get(position));
            user_modal.tv_number.setText(al_number.get(position));
            user_modal.tv_id.setText("" + al_id.get(position));
            Log.i("RESULT", "-----------------------Values Assigned");
            return view;
        }

        @Override
        public int getCount() {
            return al_id.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }


}
