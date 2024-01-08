package com.nidhi.studentrecord;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editId, editName, editEmail, editCC;
    Button buttonAdd, buttonGetData, buttonUpdate, buttonDelete, buttonViewAll;
 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);

        editId = findViewById(R.id.editText2);
        editName = findViewById(R.id.editText);
        editEmail = findViewById(R.id.editText3);
        editCC = findViewById(R.id.editText4);

        buttonAdd = findViewById(R.id.textView1);
        buttonGetData = findViewById(R.id.textView2);
        buttonUpdate = findViewById(R.id.textView3);
        buttonDelete = findViewById(R.id.textView4);
        buttonViewAll = findViewById(R.id.textView5);


        AddData();
        getData();
        viewAll();
        updateData();
        deleteData();
    }

        public void AddData() {
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isInserted = myDB.insertData(editName.getText().
                                    toString(), editEmail.getText().toString(),
                            editCC.getText().toString());
                    if (isInserted == true) {
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        public void getData(){
            buttonGetData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = editId.getText().toString();

                    if (id.equals(String.valueOf(""))){
                        editId.setError("Enter ID");
                        return;
                    }

                    Cursor cursor = myDB.getData(id);
                    String data = null;

                    if (cursor.moveToNext()){
                        data = "ID: "+ cursor.getString(0) +"\n"+
                                "Name: "+ cursor.getString(1) +"\n"+
                                "Email: "+ cursor.getString(2) +"\n"+
                                "Course Count: "+ cursor.getString(3) +"\n";
                    }
                    showMessage("Data: ", data);


                }
            });
        }

        public void viewAll(){
            buttonViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor cursor = myDB.getAllData();

                    //small test
                    if (cursor.getCount() == 0){
                        showMessage("Error", "Nothing found in DB");
                        return;
                    }

                    StringBuffer buffer = new StringBuffer();

                    while (cursor.moveToNext()){
                        buffer.append("ID: "+cursor.getString(0)+"\n");
                        buffer.append("Name: "+cursor.getString(1)+"\n");
                        buffer.append("Email: "+cursor.getString(2)+"\n");
                        buffer.append("CC: "+cursor.getString(3)+"\n\n");
                    }
                    showMessage("All data", buffer.toString());

                }
            });


        }
        public void updateData(){
            buttonUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isUpdate = myDB.updateData(editId.getText().toString(),
                            editName.getText().toString(),
                            editEmail.getText().toString(),
                            editCC.getText().toString());

                    if (isUpdate == true){
                        Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "OOPSS!", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
        public void deleteData(){
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO: ID should not be empty
                    Integer deletedRow = myDB.deleteData(editId.getText().toString());

                    if (deletedRow > 0){
                        Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "OOPSSS!", Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }


        private void showMessage(String title, String message){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.create();
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.show();
        }

    }
