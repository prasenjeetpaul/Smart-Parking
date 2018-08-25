package notfastjustfurious.epam.smartparking.webio;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import notfastjustfurious.epam.smartparking.entity.OpenParkingSlot;

public class JSONHelper {

    public static List<OpenParkingSlot> getOpenParkingSlots (JSONArray jsonArray) {
        if(jsonArray == null) {
            return null;
        }
        List<OpenParkingSlot> openParkingSlotList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonobject = jsonArray.getJSONObject(i);
                String slotID = jsonobject.getString("slotID");
                String estimatedFillTime = jsonobject.getString("estimatedFillTime");
                OpenParkingSlot openParkingSlot = new OpenParkingSlot(slotID, estimatedFillTime);
                openParkingSlotList.add(openParkingSlot);
                Log.d("Parsed JSON", slotID + "---" + estimatedFillTime);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return openParkingSlotList;
    }
}
