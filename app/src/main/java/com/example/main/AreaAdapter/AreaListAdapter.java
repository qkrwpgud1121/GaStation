package com.example.main.AreaAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.main.AreaDto.AreaListModel;
import com.example.main.R;

import java.util.ArrayList;

public class AreaListAdapter extends ArrayAdapter<AreaListModel>
{
    private Context context;
    private int resource;
    private ArrayList<AreaListModel> itemList;
    private LayoutInflater inflater;

    public AreaListAdapter(Context _context, int layoutId, ArrayList<AreaListModel> _itemList) {
        super(_context, layoutId, _itemList);
        // other stuff

        context	 = _context;
        resource = layoutId;
        itemList = _itemList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public AreaListModel getItem(int position) {
        return itemList.get(position);
    }

    public void setList (ArrayList<AreaListModel> _itemList)
    {
        itemList = _itemList;

        Log.e("TEST", "===== setList : " + itemList.size());
    }

    // 리스트 각 row별 화면
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        AreaListModel rowItem = this.itemList.get(position);

        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getUNI_ID());

        final ViewHolder holder;
        View view = convertView;

        if (view == null)
        {
            view = inflater.inflate(resource, null);
            holder = new ViewHolder();
            assert view != null;

            holder.row_tv_UNI_ID	= (TextView) view.findViewById(R.id.row_tv_UNI_ID);
            holder.row_tv_PRICE	= (TextView) view.findViewById(R.id.row_tv_PRICE);
            holder.row_POLL_DIV_CO	= (TextView) view.findViewById(R.id.row_POLL_DIV_CO);
            holder.row_tv_OS_NM	= (TextView) view.findViewById(R.id.row_tv_OS_NM);
            holder.row_tv_VAN_ADR	= (TextView) view.findViewById(R.id.row_tv_VAN_ADR);
            holder.row_tv_NEW_ADR	= (TextView) view.findViewById(R.id.row_tv_NEW_ADR);
            holder.row_tv_GIS_X_COOR	= (TextView) view.findViewById(R.id.row_tv_GIS_X_COOR);
            holder.row_tv_GIS_Y_COOR	= (TextView) view.findViewById(R.id.row_tv_GIS_Y_COOR);
            holder.row_tv_latitude = (TextView) view.findViewById(R.id.row_tv_latitude);
            holder.row_tv_longitude = (TextView) view.findViewById(R.id.row_tv_longitude);


            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.row_tv_UNI_ID.setText("UNI_ID : " + rowItem.getUNI_ID());
        holder.row_tv_PRICE.setText("PRICE : " + rowItem.getPRICE());
        holder.row_POLL_DIV_CO.setText("POLL_DIV_CO : " + rowItem.getPOLL_DIV_CD());
        holder.row_tv_OS_NM.setText("OS_NM : " + rowItem.getOS_NM());
        holder.row_tv_VAN_ADR.setText("VAN_ADR : " + rowItem.getVAN_ADR());
        holder.row_tv_NEW_ADR.setText("NEW_ADR : " + rowItem.getNEW_ADR());
        holder.row_tv_GIS_X_COOR.setText("GIS_X_COOR : " + rowItem.getGIS_X_COOR());
        holder.row_tv_GIS_Y_COOR.setText("GIS_Y_COOR : " + rowItem.getGIS_Y_COOR());
        holder.row_tv_latitude.setText("Latitude : " + rowItem.getLatitude());
        holder.row_tv_longitude.setText("Longitude : " + rowItem.getLongitude());


        return view;
    }

    static class ViewHolder {
        private TextView row_tv_UNI_ID;
        private TextView row_tv_PRICE;
        private TextView row_POLL_DIV_CO;
        private TextView row_tv_OS_NM;
        private TextView row_tv_VAN_ADR;
        private TextView row_tv_NEW_ADR;
        private TextView row_tv_GIS_X_COOR;
        private TextView row_tv_GIS_Y_COOR;
        private TextView row_tv_latitude;
        private TextView row_tv_longitude;
    }
}