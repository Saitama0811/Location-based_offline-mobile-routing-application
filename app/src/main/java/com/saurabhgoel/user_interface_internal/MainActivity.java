package com.saurabhgoel.user_interface_internal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

Toolbar tlb;
public static RecyclerView list;
RecyclerView.LayoutManager layoutManager;
FloatingActionButton fab;
EditText loca,dest;
String txt3, txt4;
ImageView img;
public static  hi adapter ;

    private static final int SMS_PERMISSION_CODE = 0;

    private EditText mNumberEditText;
    public static TextView txt;
    private String mUserMobilePhone;
    public static ArrayList<String> lst=new ArrayList<>();
    public static ArrayList<String> duration=new ArrayList<>();
    public static ArrayList<String> distance=new ArrayList<>();
    String  txt1,txt2;
    public static ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tlb=(Toolbar)findViewById(R.id.tool);
        list=(RecyclerView)findViewById(R.id.list);
        fab=(FloatingActionButton)findViewById(R.id.fabBtn);
        loca=(EditText)findViewById(R.id.loca);
        dest=(EditText)findViewById(R.id.dest);
        img=(ImageView)findViewById(R.id.arrow);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        list.setLayoutManager(layoutManager);
        adapter= new hi(MainActivity.this,lst,duration,distance);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.move);
                Animation shake2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.moviedown);
                findViewById(R.id.dest).startAnimation(shake);
                findViewById(R.id.loca).startAnimation(shake2);
                txt1= dest.getHint().toString();
                txt2= loca.getHint().toString();
                txt3= dest.getText().toString();
                txt4= loca.getText().toString();


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        dest.setHint(txt2);
                        loca.setHint(txt1);
                        dest.setText(txt4);
                        loca.setText(txt3);
                    }
                }, 270);

                Toast.makeText(MainActivity.this, "animation started", Toast.LENGTH_SHORT).show();

            }
        });
        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }

        //initViews();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasValidPreConditions()) return;

                SmsHelper.sendDebugSms("+918826206121",  dest.getText().toString()+";"+loca.getText().toString());

                Toast.makeText(getApplicationContext(), R.string.toast_sending_sms, Toast.LENGTH_SHORT).show();
                pd=new ProgressDialog(MainActivity.this);
                pd.setMessage(("getting location...."));
                pd.setCancelable(false);
                pd.show();
            }
        });


        setSupportActionBar(tlb);
        /*String[] arr={"asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd",
                "asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd","asd",
                "asd","asd","asd","asd","asd","asd","asd",
                "asd","asd","asd","asd","asd","asd","asd",};
        list=(RecyclerView)findViewById(R.id.list);


        int len=arr.length;
        for(int i=0;i<len;i++){
            lst.add(arr[i]);
        }
        list.setAdapter(adapter);

*/
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoption,menu);
        return super.onCreateOptionsMenu(menu);
    }*/
   private boolean hasValidPreConditions() {
       if (!hasReadSmsPermission()) {
           requestReadAndSendSmsPermission();
           return false;
       }

       if (!SmsHelper.isValidPhoneNumber("+918826206121")) {
           Toast.makeText(getApplicationContext(), R.string.error_invalid_phone_number, Toast.LENGTH_SHORT).show();
           return false;
       }
       return true;
   }

    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            Log.d("PERMIT", "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS},
                SMS_PERMISSION_CODE);
    }
}
