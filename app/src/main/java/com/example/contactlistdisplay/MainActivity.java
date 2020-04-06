package com.example.contactlistdisplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    Button getcontact;

    ArrayList<String> storecontact;
    ArrayAdapter<String> readcontact;

    Cursor cursor;

    String phone,number;

    public static final int code = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list);
        getcontact = (Button)findViewById(R.id.button);


        storecontact =new ArrayList<>();


        EnableRuntimePermission();


        getcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                displaycontact();

                readcontact = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, storecontact);

                listView.setAdapter(readcontact);


            }
        });









    }




    public void displaycontact()
    {

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);


        while (cursor.moveToNext())
        {

            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            number = cursor.getColumnName(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            storecontact.add(phone + " "  + ":" + " " + number);

        }



    }


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                Manifest.permission.READ_CONTACTS))
        {

            Toast.makeText(MainActivity.this,"CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.READ_CONTACTS}, code);

        }
    }








    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case code:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(MainActivity.this,"Permission Granted, Now your application can access CONTACTS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(MainActivity.this,"Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
