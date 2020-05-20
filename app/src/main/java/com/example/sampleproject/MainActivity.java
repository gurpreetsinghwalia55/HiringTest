package com.example.sampleproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        ArrayList<Email> values = getEmailData();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        populateTable(values);
    }

    private ArrayList<Email> getEmailData() {
        final ArrayList<Email> values = new ArrayList<Email>();
        OkHttpClient client = new OkHttpClient();
        String url = "http://devfrontend.gscmaven.com/wmsweb/webapi/email/";
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("Walia", "Check your network");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonresponse = response.body().string();
                    try {
                        JSONArray obj = new JSONArray(jsonresponse);
                        for (int i = 0; i < obj.length(); i++) {
                            String result = obj.getString(i);
                            JSONObject value= new JSONObject(result);
                            Email currValue = new Email(value.getString("idtableEmail"), value.getString("tableEmailEmailAddress"), value.getBoolean("tableEmailValidate"));
                            values.add(currValue);
                        }
                        for (int i = 0; i < values.size(); i++) {
                            Log.d("Walia", "id "+values.get(i).id+" email "+values.get(i).email+" validated"+values.get(i).validated);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }
            }
        });
        Log.d("Walia", "doInBackground: "+values.size());
        return values;
    }

    void populateTable(ArrayList<Email> values) {
        TableRow row;
        TextView t1, t2;
        Log.d("Walia", "populateTable: "+values.size());
        ImageView editbtn, dltbtn;
        for (int i = 0; i < values.size(); i++) {
            row = new TableRow(this);
            t1 = new TextView(this);
            t2 = new TextView(this);
            editbtn = new ImageView(this);
            dltbtn = new ImageView(this);
            editbtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit_black_24dp));
            dltbtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_delete_black_24dp));
            Integer serial_number = (i + 1);
            t1.setText(values.get(i).id);
            t2.setText(values.get(i).email);
            t1.setGravity(Gravity.CENTER_HORIZONTAL);
            t1.setPadding(32, 8, 16, 8);
            t2.setPadding(0, 8, 0, 8);
            t1.setTextSize(15);
            t2.setTextSize(15);
            final TextView finalT = t2;
            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Walia", "onClick: editbutton "+ finalT.getText());
                    positiveButtonListener();
                }
            });
            dltbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Walia", "onClick: deletebutton "+ finalT.getText());
                    negativeButtonListener();
                }
            });
            row.addView(t1);
            row.addView(t2);
            row.addView(editbtn);
            row.addView(dltbtn);
            tableLayout.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        }
    }

    private void negativeButtonListener() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete email")
                .setMessage("Are you sure you want to delete this email?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", null)
                .setIcon(getResources().getDrawable(R.drawable.ic_report_problem_black_24dp))
                .show();
    }

    private void positiveButtonListener() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_custom_view, null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_name = (EditText) dialogView.findViewById(R.id.et_name);
        et_name.setText("Enter your new email");
        final AlertDialog dialog = builder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                generateDialog();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    public void generateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_custom_view, null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_name = (EditText) dialogView.findViewById(R.id.et_name);

        final AlertDialog dialog = builder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
