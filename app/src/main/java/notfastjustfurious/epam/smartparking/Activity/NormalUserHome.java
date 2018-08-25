package notfastjustfurious.epam.smartparking.Activity;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import notfastjustfurious.epam.smartparking.R;
import notfastjustfurious.epam.smartparking.adapter.OpenParkingSlotListAdapter;
import notfastjustfurious.epam.smartparking.entity.OpenParkingSlot;
import notfastjustfurious.epam.smartparking.webio.URLHelper;
import notfastjustfurious.epam.smartparking.webio.JSONHelper;

public class NormalUserHome extends AppCompatActivity {

    private ListView openSlotListView;
    private ProgressDialog progress;
    private SwipeRefreshLayout pullToRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_user_home);
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateOpenSlotListView(); // your code
                pullToRefresh.setRefreshing(false);
            }
        });
        openSlotListView = findViewById(R.id.openSlotListView);
        progress= new ProgressDialog(this);
        progress.setTitle("Fetching Open Slots");
        progress.setMessage("Wait while fetching the data...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog


        populateOpenSlotListView();


    }

    private void populateOpenSlotListView() {
        progress.show();
        StringRequest request = new StringRequest(Request.Method.POST, URLHelper.GET_AVAILABLE_SLOT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String string) {
                Log.d("JSON Success", string);
                try {
                    JSONArray jsonArray = new JSONArray(string);
                    if(string.equals("[]") || jsonArray == null) {
                        findViewById(R.id.noSlotAvailableTextView).setVisibility(View.VISIBLE);
                        progress.dismiss();
                        return;
                    }
                    List<OpenParkingSlot> openParkingSlotList = JSONHelper.getOpenParkingSlots(jsonArray);
                    if (openParkingSlotList != null) {
                        openSlotListView.setAdapter(new OpenParkingSlotListAdapter(NormalUserHome.this, openParkingSlotList));
                        setListViewHeightBasedOnChildren(openSlotListView);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(NormalUserHome.this, "Some error occurred!!", Toast.LENGTH_SHORT).show();
                Log.d("JSON Error", "Error occured while JSON Request");
                Log.e("Volley Error", volleyError.getMessage());
                progress.dismiss();
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(NormalUserHome.this);
        rQueue.add(request);



    }



    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
