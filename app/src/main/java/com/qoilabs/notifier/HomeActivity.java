package com.qoilabs.notifier;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class HomeActivity extends Activity implements View.OnClickListener {

    private static RequestQueue queue;
    Button btnSave;
    EditText txtTitle;
    EditText txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtMessage = (EditText) findViewById(R.id.txtMessage);

        btnSave.setOnClickListener(this);

        queue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            saveData();
        }
    }

    private void saveData() {
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("response", response.toString());
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        };
        JSONObject params = new JSONObject();
        try {
            params.put("title", txtTitle.getText().toString());
            params.put("message", txtMessage.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://10.0.2.2:3000/save/", params, listener, errorListener);
            queue.add(request);
            txtTitle.setText("");
            txtMessage.setText("");
        } catch (JSONException ex) {
            Log.e("Error", ex.toString());
        }
    }
}
