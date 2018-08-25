package notfastjustfurious.epam.smartparking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import notfastjustfurious.epam.smartparking.R;
import notfastjustfurious.epam.smartparking.entity.OpenParkingSlot;

public class OpenParkingSlotListAdapter extends BaseAdapter{
    private Context context;
    private List<OpenParkingSlot> slotList;
    private LayoutInflater inflater;

    public OpenParkingSlotListAdapter(Context context, List slotList) {
        this.context = context;
        this.slotList = slotList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return slotList.size();
    }

    @Override
    public OpenParkingSlot getItem(int position) {
        return slotList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.open_parkingslot_list_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        OpenParkingSlot currentSlot = getItem(position);

        mViewHolder.slotIDTV.setText(currentSlot.getSlotNumber());
        mViewHolder.slotFillTimeTV.setText(currentSlot.getFillTime());

        return convertView;
    }

    private class MyViewHolder {
        TextView slotIDTV, slotFillTimeTV;

        public MyViewHolder(View item) {
            slotIDTV = (TextView) item.findViewById(R.id.slotIDTextView);
            slotFillTimeTV = (TextView) item.findViewById(R.id.slotFillTimeTextView);
        }
    }
}
