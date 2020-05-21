package com.example.sampleproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        try {
            getEmailData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getEmailData() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://devfrontend.gscmaven.com/wmsweb/webapi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestApi testapi = retrofit.create(TestApi.class);
        Call<List<Email>> call = testapi.getEmails();
        call.enqueue(new Callback<List<Email>>() {
            @Override
            public void onResponse(Call<List<Email>> call, Response<List<Email>> response) {
                if (response.isSuccessful()) {
                    List<Email> values = response.body();
                    for (Email email : values) {
                        Log.d("Walia", "onResponse: " + email.getIdtableEmail() + " " + email.getTableEmailEmailAddress() + " " + email.isTableEmailValidate());
                    }
                    populateTable(values);
                } else {
                    Log.d("Walia", "onResponse: No respones");
                }
            }

            @Override
            public void onFailure(Call<List<Email>> call, Throwable t) {
                Log.d("Walia", "onFailure: check your network");
            }
        });
    }

    void populateTable(List<Email> values) {
        TableRow row;
        TextView t1, t2, t3;
        Log.d("Walia", "populateTable: " + values.size());
        t1 = new TextView(this);
        t2 = new TextView(this);
        t3 = new TextView(this);
        row = new TableRow(this);
        t1.setTextSize(15);
        t2.setTextSize(15);
        t3.setTextSize(15);
        t1.setGravity(Gravity.CENTER_HORIZONTAL);
        t3.setGravity(Gravity.CENTER_HORIZONTAL);
        t1.setTypeface(Typeface.DEFAULT_BOLD);
        t2.setTypeface(Typeface.DEFAULT_BOLD);
        t3.setTypeface(Typeface.DEFAULT_BOLD);
        t1.setText("Sno.");
        t2.setText("Email Address");
        t3.setText("Action");
        t1.setPadding(0, 16, 0, 0);
        t2.setPadding(0, 16, 0, 0);
        t3.setPadding(0, 16, 0, 0);
        t1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        t2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3.0f));
        t3.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        tableLayout.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
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
            t1.setText(values.get(i).getIdtableEmail());
            t1.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            t2.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3.0f));
            editbtn.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
            dltbtn.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
            t2.setText(values.get(i).getTableEmailEmailAddress());
            t1.setGravity(Gravity.CENTER_HORIZONTAL);
            t1.setPadding(8, 8, 16, 8);
            t2.setPadding(0, 8, 0, 8);
            t1.setTextSize(15);
            t2.setTextSize(15);
            t2.setMaxLines(1);
            t2.setEllipsize(TextUtils.TruncateAt.END);
            final TextView finalT = t2;
            final TextView finalT2 = t1;
            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Walia", "onClick: editbutton " + finalT.getText());
                    positiveButtonListener(finalT2.getText().toString());
                }
            });
            final TextView finalT1 = t1;
            dltbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Walia", "onClick: deletebutton " + finalT.getText());
                    negativeButtonListener(Integer.parseInt(finalT1.getText().toString()));
                }
            });
            row.addView(t1);
            row.addView(t2);
            row.addView(editbtn);
            row.addView(dltbtn);
            tableLayout.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tableLayout.setVisibility(View.VISIBLE);
        }
    }

    private void negativeButtonListener(final int id) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Delete email")
                .setMessage("Are you sure you want to delete this email?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://devfrontend.gscmaven.com/wmsweb/webapi/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        TestApi testapi = retrofit.create(TestApi.class);
                        Log.d("Walia", "onClick: id to deleted is = " + id);
                        Call<Void> call = testapi.deleteEmail(id);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Log.d("Walia", "onResponse: deleted succesfully ");
                                tableLayout.setVisibility(View.GONE);
                                try {
                                    tableLayout.removeAllViews();
                                    getEmailData();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("Walia", "onFailure: delete failed!");
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .setIcon(getResources().getDrawable(R.drawable.ic_report_problem_black_24dp))
                .show();
    }

    private void positiveButtonListener(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_custom_view, null);
        builder.setCancelable(true);
        builder.setView(dialogView);
        Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_name = (EditText) dialogView.findViewById(R.id.et_name);
        et_name.setHint("Enter your new email");
        final AlertDialog dialog = builder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textChangeListner(et_name))
                    return;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://devfrontend.gscmaven.com/wmsweb/webapi/email/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TestApi testapi = retrofit.create(TestApi.class);
                Email email = new Email(et_name.getText().toString(), true);
                email.setIdtableEmail(id);
                Call<Email> call = testapi.updateEmail(Integer.parseInt(id), email);
                call.enqueue(new Callback<Email>() {
                    @Override
                    public void onResponse(Call<Email> call, Response<Email> response) {
                        if (response.isSuccessful()) {
                            tableLayout.removeAllViews();
                            try {
                                getEmailData();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.d("Walia", "onResponse: Email update failed. Please check you network");
                        }
                    }

                    @Override
                    public void onFailure(Call<Email> call, Throwable t) {
                        Log.d("Walia", "onFailure: email update failed");
                    }
                });
                dialog.dismiss();
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
            case R.id.refresh:
                try {
                    tableLayout.removeAllViews();
                    getEmailData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    public void generateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alertdialog_custom_view, null);
        builder.setCancelable(true);
        builder.setView(dialogView);
        Button btn_positive = (Button) dialogView.findViewById(R.id.dialog_positive_btn);
        Button btn_negative = (Button) dialogView.findViewById(R.id.dialog_negative_btn);
        final EditText et_name = (EditText) dialogView.findViewById(R.id.et_name);

        final AlertDialog dialog = builder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email email = new Email(et_name.getText().toString(), true);
                if(!textChangeListner(et_name))
                    return;
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://devfrontend.gscmaven.com/wmsweb/webapi/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                TestApi testapi = retrofit.create(TestApi.class);
                Call<Email> call = testapi.createEmail(email);
                call.enqueue(new Callback<Email>() {
                    @Override
                    public void onResponse(Call<Email> call, Response<Email> response) {
                        if (response.isSuccessful()) {
                            Log.d("Walia", "onResponse: email created");
                            tableLayout.setVisibility(View.GONE);
                            try {
                                tableLayout.removeAllViews();
                                getEmailData();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Email> call, Throwable t) {
                        Log.d("Walia", "onFailure: email not created");
                        Toast.makeText(MainActivity.this, "Email not created!", Toast.LENGTH_SHORT);
                    }
                });
                dialog.dismiss();
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

    private boolean textChangeListner(EditText et){
        String input = et.getText().toString().trim();
        if(input.isEmpty()){
            et.setError("Field cannot be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(input).matches()){
            et.setError("Please enter a valid email address");
            return false;
        }else{
            et.setError(null);
            return true;
        }
    }
}

