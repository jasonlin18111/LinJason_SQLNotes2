package com.example.linj6200.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editNumber;
    EditText editAddress;
    EditText editSearch;
    private String string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editText_name);
        editNumber = (EditText) findViewById(R.id.editText_number);
        editAddress = (EditText) findViewById(R.id.editText_address);
        editSearch = (EditText) findViewById(R.id.editText_Search);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");
    }

    public void addData(View view){
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");
        boolean isInserted = myDb.insertData(editName.getText().toString(), editNumber.getText().toString(), editAddress.getText().toString());
        if(isInserted==true){
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }

    }
///*
    public void viewData(View view){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: received cursor");
        if (res.getCount()==0){
            showMessage("Error", "No data found in database");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            //Append res column 0, 1, 2, 3 to the buffer - see Stringbuffer and cursor api's
            //Delimit each of the "appends" with line feed "\n"
            buffer.append("\nName: " + res.getString(1));
            buffer.append("\nNumber: " + res.getString(2));
            buffer.append("\nAddress: " + res.getString(3));
        }
        showMessage("Data", buffer.toString());
        string = buffer.toString();
    }

    private void showMessage(String title, String message){
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.linj6200.mycontactapp MESSAGE";
    public void searchRecord(View view ){
        Log.d("MyContactApp", "MainActivity: Launching SearchActivity");
        Intent intent = new Intent(this, SearchActivity.class);
        int index = string.indexOf(editSearch.getText().toString());
        String str = "Name: ";
        if(index>=0) {
            for (int i = 0; i < 3; i++) {
                Log.d("MyContactApp", "MainActivity: Launching SearchActivity2");
                while (string.substring(index).indexOf("\n") != 0){
                    Log.d("MyContactApp", "MainActivity: Launching SearchActivity3");
                    str += string.substring(index, index + 1);
                    index++;
                }
                str += "\n";
                index++;
            }
        }
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }
}
