package com.groupr4.android.inclassassignment6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Messages extends AppCompatActivity {

TextView userName;
EditText newThread;
ImageButton logOff, add, delete;
String user_name, token;
ListView lv;
    ArrayAdapter<Threads> adapter;
Threads t;
    ArrayList<Threads> result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        userName = (TextView)findViewById(R.id.textView);
        newThread = (EditText)findViewById(R.id.editText7);
        logOff = (ImageButton)findViewById(R.id.imageButton3);
        add = (ImageButton)findViewById(R.id.imageButton2);


        if(getIntent()!=null && getIntent().getExtras()!=null){
            User user = (User) getIntent().getExtras().getSerializable(MainActivity.user_key);
            token = (String) getIntent().getExtras().getString(MainActivity.token_key);
            user_name = user.firstName.toString() + " "+ user.lastName.toString();
            userName.setText(user_name);
            Log.d("tokenDemo",token.toString());

        }
        logOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                token = "";
                finish();
                Intent intent_logOff = new Intent(Messages.this,MainActivity.class);
                startActivity(intent_logOff);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newThread.getText().toString();
                if(!(text.equals("") || text.equals(null)))
                {
                    t = new Threads();
                    OkHttpClient client = new OkHttpClient();
                    RequestBody formBody = new FormBody.Builder()
                            .add("title", text)
                            .build();
                    Request request = new Request.Builder()
                            .header("Authorization","BEARER "+token)
                            .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread/add")
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {

                            try (ResponseBody responseBody = response.body()) {
                                if (!response.isSuccessful())
                                    throw new IOException("Unexpected code " + response);
                                JSONObject root = new JSONObject(response.body().string());
                                String status = root.getString("status");
                                if (status.equals("ok")) {
                                    String title = root.getString("title");
                                    result.add(new Threads(title));
                                    setAdapter(result);
                                    adapter.notifyDataSetChanged();

                                }

                                //Log.d("demo", per);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                    /*t.title = text;
                    result.add(t);


*/
                }
            }
        });
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Authorization","BEARER "+token)
                .url("http://ec2-18-234-222-229.compute-1.amazonaws.com/api/thread")
                .build();
        client.newCall(request).enqueue(new Callback(){

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    JSONObject root = new JSONObject(responseBody.string());
                    String status = root.getString("status");
                    if (status.equalsIgnoreCase("ok")){
                        result = new ArrayList<>();
                        JSONArray source = root.getJSONArray("threads");
                        for (int i = 0; i < source.length(); i++) {
                            JSONObject sourceJSON = source.getJSONObject(i);
                            Threads threads = new Threads();
                            threads.user_fname = sourceJSON.getString("user_fname");
                            threads.user_lname = sourceJSON.getString("user_lname");
                            threads.user_id = sourceJSON.getString("user_id");
                            threads.id = sourceJSON.getString("id");
                            threads.title = sourceJSON.getString("title");
                            threads.created_at = sourceJSON.getString("created_at");

                            result.add(threads);

                        }

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                setAdapter(result);
                                Log.d("addapDemo","main");
                                }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }
    private void setAdapter(ArrayList<Threads> s) {
        lv = (ListView)findViewById(R.id.myListView);
          adapter = new ArrayAdapter<Threads>(this, R.layout.message_list_view, R.id.textView3, result);
        lv.setAdapter(adapter);


    }
}
