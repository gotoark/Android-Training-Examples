package application.arkthepro.com.androidtraining.SavingData;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import application.arkthepro.com.androidtraining.R;

public class SavingDatainSQLDB extends AppCompatActivity{
    Activity activity = this;
    ListView userList;
    List<UserDetails> al_userdetails;
    ArrayList<Integer> al_id = new ArrayList<>();
    ArrayList<String> al_name = new ArrayList<>();
    ArrayList<String> al_number = new ArrayList<>();
    ArrayList<byte[]> al_images = new ArrayList<>();
    final DBUtil dbUtil = new DBUtil(this);
    ImageView profile_pic;
    InputStream iStream;
    byte[] profilepic_in_Bytes;
    private static final int PICK_IMAGE = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
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
        profile_pic =(ImageView)findViewById(R.id.profilepic_selector);
        Log.d("DB RESULT", "Oncreate SQL CLASS");
        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check Permission
                // Assume thisActivity is the current activity
                int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                if (permissionCheck == 0 ? true : false) {
                    openGalleryforImage();
                } else {
                    // Here, thisActivity is the current activity
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.

                        } else {

                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    }
                }
                Log.d("Result", "-----------------------Result of permissionCheck :" + permissionCheck);


            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")) {
                    username.setError("Please Enter Name");
                } else if (number.getText().toString().equals("")) {
                    number.setError("Please Enter Mobile Number");
                }else {
                    // profilepic_in_Bytes=ImageUtil.getImageBytes(ImageUtil.getImage(profile_pic.getBackground()));
                    dbUtil.createUser(new UserDetails(username.getText().toString(), number.getText().toString(), ImageUtil.getImageBytes(ImageUtil.getImage(profilepic_in_Bytes))));
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
                        dbUtil.updateUser(username.getText().toString(), number.getText().toString(), ImageUtil.getImageBytes(profile_pic.getDrawingCache()));
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
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
        al_images.clear();
        for (UserDetails l : al_userdetails) {
            al_id.add(l.getId());
            al_name.add(l.getName());
            al_number.add(l.getphoneNumber());
            al_images.add(l.getprofilepic());
            Log.i("DB RESULTS", "****************ID: " + l.getId() + "NAME: " + l.getName() + "Number: " + l.getphoneNumber());
        }

        UserAdapter userAdapter = new UserAdapter(getApplicationContext(), al_id, al_name, al_number,al_images);
        userList.clearDisappearingChildren();
        userList.setAdapter(userAdapter);
        Log.i("RESULT", "-----------------------UserAdapter Assigned");
    }




    public class UserAdapter extends BaseAdapter{
        private Context context;
        private ArrayList<String> al_name;
        private ArrayList<String> al_number;
        private ArrayList<Integer> al_id;
        private ArrayList<byte[]> al_images ;
        private LayoutInflater layoutinflater = null;
        Users_ViewHolder user_modal;


        public class Users_ViewHolder {
            TextView tv_username;
            TextView tv_number;
            TextView tv_id;
            ImageView iv_profilepic;
        }

        public UserAdapter(Context c, ArrayList<Integer> al_id, ArrayList<String> al_name, ArrayList<String> al_number,ArrayList<byte[]> al_profilepic) {
            this.context = c;
            this.al_name = al_name;
            this.al_number = al_number;
            this.al_id = al_id;
            this.al_images = al_profilepic;
            layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("RESULT", "-----------------------UserAdapter Initialized");

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            user_modal= new Users_ViewHolder();
            View view;
            view = layoutinflater.inflate(R.layout.userslist, null);
            user_modal.tv_id = (TextView) view.findViewById(R.id.id);
            user_modal.tv_username = (TextView) view.findViewById(R.id.tv_username);
            user_modal.tv_number = (TextView) view.findViewById(R.id.tv_number);
            user_modal.iv_profilepic = (ImageView) view.findViewById(R.id.iv_profilepic);
            Log.i("RESULT", "-----------------------GET Views");
            //Assign Values
            user_modal.tv_username.setText(al_name.get(position));
            user_modal.tv_number.setText(al_number.get(position));
            user_modal.tv_id.setText("" + al_id.get(position));
            user_modal.iv_profilepic.setImageBitmap(ImageUtil.getImage(profilepic_in_Bytes));
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

    private void openGalleryforImage() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),"Permission Granted",Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openGalleryforImage();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            profile_pic.setImageURI(imageUri);
            try {
                iStream= getContentResolver().openInputStream(imageUri);
                profilepic_in_Bytes= ImageUtil.getBytes(iStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbUtil.close();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
