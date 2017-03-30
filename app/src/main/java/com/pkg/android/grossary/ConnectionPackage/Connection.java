package com.pkg.android.grossary.ConnectionPackage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by GAURAV on 02-02-2017.
 */

public class Connection {
    String url;
    Context context;
    StringRequest stringRequest;

    public Connection(String url, Context context) {
        this.url = url;
        this.context = context;
    }

    public void onPost(final String a, final ASyncResponse aSyncResponse) {
        RequestQueue queue = Volley.newRequestQueue(context);
        stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("params1", response);
                        aSyncResponse.processFinish(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "That didn't work!", Toast.LENGTH_LONG).show();
                        Log.d("Connection", volleyError.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", a);
                return params;

            }
        };
        queue.add(stringRequest);
    }

    public void onGet(final ASyncResponse aSyncResponse) {
        RequestQueue queue = Volley.newRequestQueue(context);
        stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("params", response);
                        aSyncResponse.processFinish(response);
                        // Toast.makeText(RetailerActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(context, "That didn't work!", Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(stringRequest);
    }
}
