package application.arkthepro.com.androidtraining.SavingData;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import application.arkthepro.com.androidtraining.R;

public class SavingDatainSQLDB extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    final DBUtil dbUtil = new DBUtil(this);
    Activity activity = this;
    ListView userList;
    List<UserDetails> al_userdetails;
    EditText username, number;
    Button create, delete_user, update, delete;
    ArrayList<Integer> al_id = new ArrayList<>();
    ArrayList<String> al_name = new ArrayList<>();
    ArrayList<String> al_number = new ArrayList<>();
    ArrayList<byte[]> al_images = new ArrayList<>();
    ImageView profile_pic;
    InputStream iStream;
    byte[] profilepic_in_Bytes;
    ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_datain_sqldb);
        //Create DB instance to access All DB CRUD operations
        userList = (ListView) findViewById(R.id.userlist);


        username = (EditText) findViewById(R.id.edit_text_username);
        number = (EditText) findViewById(R.id.edit_text_mnumber);
        create = (Button) findViewById(R.id.btn_create);
        delete_user = (Button) findViewById(R.id.btn_delete_user);
        update = (Button) findViewById(R.id.btn_update);
        delete = (Button) findViewById(R.id.btn_delete);
        profile_pic = (ImageView) findViewById(R.id.profilepic_selector);
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
                } else {
                     //profilepic_in_Bytes=ImageUtil.getImageBytes(profile_pic.getDrawingCache());
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
                        showToast("Deleted Successfully :", R.color.green);
                        updateList();
                    } else {
                        showToast("Sry No User found with Mobile Number :" + number.getText().toString(), R.color.red);                    }

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
                        dbUtil.updateUser(username.getText().toString(), number.getText().toString(), ImageUtil.getImageBytes(ImageUtil.getImage(profilepic_in_Bytes)));
                        showToast("Updated Successfully :", R.color.green);
                        updateList();
                    } else {
                        showToast("Sry No User found with Mobile Number :" + number.getText().toString(), R.color.red);
                    }

                }


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (al_id.isEmpty()) {
                    showToast("User List is Already Empty :" + number.getText().toString(), R.color.green);
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
        //Handle List item Click Liseners
          /* Very Important
         Dont Forget to add the folling in listitems.xml

            1. android:descendantFocusability="blocksDescendants" in parent View

            2 . android:focusable="false" in Child Views

              Otherwise listview.setOnItemClick Lisener wont works...!! :( :(

          */
        registerForContextMenu(userList);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("LIST RESULT", "-----------------------CLICKED " + position);
                username.setText(al_name.get(position));
                number.setText(al_number.get(position));
                profile_pic.setImageBitmap(ImageUtil.getImage(al_images.get(position)));

            }
        });
//Enabling the contextual action mode for individual views
        delete.setOnLongClickListener(new View.OnLongClickListener() {
            // Called when the user long-clicks on someView
            public boolean onLongClick(View view) {
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = activity.startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });


        //Creating PopUp menu
        update.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(v);
                return false;
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

        UserAdapter userAdapter = new UserAdapter(getApplicationContext(), al_id, al_name, al_number, al_images);
        userList.clearDisappearingChildren();
        userList.setAdapter(userAdapter);
        Log.i("RESULT", "-----------------------UserAdapter Assigned");


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
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openGalleryforImage();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
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
                iStream = getContentResolver().openInputStream(imageUri);
                profilepic_in_Bytes = ImageUtil.getBytes(iStream);
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

    //Creating Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        String webaddress;
        switch (item.getItemId()) {
            case R.id.ViewSource:
                webaddress = "https://github.com/gotoark/Android-Training-Examples";
                openBrwoser(webaddress);
                return true;
            case R.id.github:
                webaddress = "https://gotoark.github.io/";
                openBrwoser(webaddress);
                return true;
            case R.id.linkedin:
                webaddress = "http://in.linkedin.com/in/gotoark";
                openBrwoser(webaddress);
                return true;
            case R.id.twitter:
                webaddress = "https://twitter.com/gotoark";
                openBrwoser(webaddress);
                return true;
            case R.id.facebook:
                webaddress = "https://facebook.com/gotoark";
                openBrwoser(webaddress);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Creating Options Menu

    public void openBrwoser(String webaddress) {
        Uri webpage = Uri.parse(webaddress);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.i("MENU RESULT", "-------------------------------ContextMenu Created");
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.floating_context_menu, menu);
    }
    //Creating ContextMenu

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Handle item selection
        Log.i("MENU RESULT", "-------------------------------ContextMenu Clicked");
        String webaddress;
        switch (item.getItemId()) {
            case R.id.delete:
                if (number.getText().toString().equals("")) {
                    number.setError("Please Enter Mobile Number");
                } else {

                    if (dbUtil.isExists(DBUtil.KEY_NUMBER, number.getText().toString())) {
                        dbUtil.deleteUser(number.getText().toString());
                        showToast("Deleted Successfully :" + number.getText().toString(), R.color.green);
                        updateList();
                    } else {
                        showToast("Sry No User found with Mobile Number :" + number.getText().toString(), R.color.red);
                    }

                }

                return true;
            case R.id.update:
                if (username.getText().toString().equals("")) {
                    username.setError("Please Enter Name");
                } else if (number.getText().toString().equals("")) {
                    number.setError("Please Enter Mobile Number");
                } else {
                    if (dbUtil.isExists(DBUtil.KEY_NUMBER, number.getText().toString())) {
                        dbUtil.updateUser(username.getText().toString(), number.getText().toString(), ImageUtil.getImageBytes(ImageUtil.getImage(profilepic_in_Bytes)));
                        showToast("Updated Successfully :" + number.getText().toString(), R.color.green);
                        updateList();
                    } else {
                        showToast("Sry No User found with Mobile Number :" + number.getText().toString(), R.color.red);
                    }

                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }


    //Context menu in Action Mode





    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.actionmode_context_menu, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    if (number.getText().toString().equals("")) {
                        number.setError("Please Enter Mobile Number");
                    } else {

                        if (dbUtil.isExists(DBUtil.KEY_NUMBER, number.getText().toString())) {
                            dbUtil.deleteUser(number.getText().toString());
                            showToast("Deleted Successfully :" + number.getText().toString(), R.color.green);
                            updateList();
                        } else {
                            showToast("Sry No User found with Mobile Number :" + number.getText().toString(), R.color.red);
                        }

                    }

                    return true;
                case R.id.update:
                    if (username.getText().toString().equals("")) {
                        username.setError("Please Enter Name");
                    } else if (number.getText().toString().equals("")) {
                        number.setError("Please Enter Mobile Number");
                    } else {
                        if (dbUtil.isExists(DBUtil.KEY_NUMBER, number.getText().toString())) {
                            dbUtil.updateUser(username.getText().toString(), number.getText().toString(), ImageUtil.getImageBytes(ImageUtil.getImage(profilepic_in_Bytes)));
                            showToast("Updated Successfully :" + number.getText().toString(), R.color.green);
                            updateList();
                        } else {
                            showToast("Sry No User found with Mobile Number :" + number.getText().toString(), R.color.red);
                        }

                    }
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    //Creating PopUp Menu
    public void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
    }




    public class UserAdapter extends BaseAdapter {
        Users_ViewHolder user_modal;
        private Context context;
        private ArrayList<String> al_name;
        private ArrayList<String> al_number;
        private ArrayList<Integer> al_id;
        private ArrayList<byte[]> al_images_inBytes;
        private LayoutInflater layoutinflater = null;


        public UserAdapter(Context c, ArrayList<Integer> al_id, ArrayList<String> al_name, ArrayList<String> al_number, ArrayList<byte[]> al_profilepic) {
            this.context = c;
            this.al_name = al_name;
            this.al_number = al_number;
            this.al_id = al_id;
            this.al_images_inBytes = al_profilepic;
            layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Log.i("RESULT", "-----------------------UserAdapter Initialized");

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            user_modal = new Users_ViewHolder();
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
            user_modal.iv_profilepic.setImageBitmap(ImageUtil.getImage(al_images_inBytes.get(position)));
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

        public class Users_ViewHolder {
            TextView tv_username;
            TextView tv_number;
            TextView tv_id;
            ImageView iv_profilepic;
        }


    }
    //Customized Toast
    public void showToast(String msg,int id){
        Toast t=Toast.makeText(getApplicationContext(),msg + number.getText().toString(), Toast.LENGTH_SHORT);
        View tv=t.getView();
       // tv.setBackgroundColor(getResources().getColor(id));
        t.show();
    }






}
