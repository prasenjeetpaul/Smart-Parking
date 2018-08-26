package notfastjustfurious.epam.smartparking.webio;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import notfastjustfurious.epam.smartparking.entity.DedicatedUserAttribute;
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

    public static DedicatedUserAttribute getDedicatedUserAttribute(String jsonObjectInput) {
        if(jsonObjectInput == null) {
            return null;
        }
        DedicatedUserAttribute userAttribute = new DedicatedUserAttribute();
        try {
            Log.d("jsonString", jsonObjectInput);
            JSONObject jsonObject = new JSONObject(jsonObjectInput);
            userAttribute.setLoginTime(jsonObject.getString("loginTime"));
            userAttribute.setLogoutTime(jsonObject.getString("logoutTime"));
            userAttribute.setSlotID(jsonObject.getString("slotID"));
            userAttribute.setFailedInstanceNumber(jsonObject.getString("failedInstanceNumber"));
            userAttribute.setNoOfStars(jsonObject.getString("stars"));

            if(jsonObject.isNull("isDelay")){
                Log.d("DelayNull", "yes");
                userAttribute.setDelay(null);
            } else {
                Log.d("DelayNull", jsonObject.getString("isDelay"));
                userAttribute.setDelay(jsonObject.getString("isDelay"));
            }
            if(jsonObject.isNull("isFloat")){
                Log.d("FloatNull", "yes");
                userAttribute.setIsFloat(null);
            } else {
                Log.d("FloatNull", jsonObject.getString("isFloat"));
                userAttribute.setIsFloat(jsonObject.getString("isFloat"));
            }


            /*userAttribute.setDelay(jsonObject.getString(jsonObject.getString("isDelay")));
            Log.d("IsDelayfromJSON", jsonObject.getString("isDelay"));
            userAttribute.setIsFloat(jsonObject.getString(jsonObject.getString("isFloat")));
            Log.d("Parsed JSON", userAttribute.toString());*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userAttribute;
    }
}
