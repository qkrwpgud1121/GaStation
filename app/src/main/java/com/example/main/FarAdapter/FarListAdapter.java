package com.example.main.FarAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.main.FarDto.FarListModel;
import com.example.main.R;

import java.util.ArrayList;

public class FarListAdapter extends ArrayAdapter<FarListModel>
{
    private Context context;
    private int resource;
    private ArrayList<FarListModel> itemList;
    private LayoutInflater inflater;

    public FarListAdapter(Context _context, int layoutId, ArrayList<FarListModel> _itemList) {
        super(_context, layoutId, _itemList);
        // other stuff

        context	 = _context;
        resource = layoutId;
        itemList = _itemList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public FarListModel getItem(int position) {
        return itemList.get(position);
    }

    public void setList (ArrayList<FarListModel> _itemList){

        itemList = _itemList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        FarListModel rowItem = this.itemList.get(position);

        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getUNI_ID());

        final FarListAdapter.ViewHolder holder;
        View view = convertView;

        if (view == null)
        {
            view = inflater.inflate(resource, null);
            holder = new FarListAdapter.ViewHolder();
            assert view != null;

            holder.F_row_tv_UNI_ID	= (TextView) view.findViewById(R.id.F_row_tv_UNI_ID);
            holder.F_row_tv_PRICE	= (TextView) view.findViewById(R.id.F_row_tv_PRICE);
            holder.F_row_POLL_DIV_CO	= (TextView) view.findViewById(R.id.F_row_POLL_DIV_CO);
            holder.F_row_tv_OS_NM	= (TextView) view.findViewById(R.id.F_row_tv_OS_NM);
            holder.F_row_tv_DISTANCE	= (TextView) view.findViewById(R.id.F_row_tv_DISTANCE);
            holder.F_row_tv_GIS_X_COOR	= (TextView) view.findViewById(R.id.F_row_tv_GIS_X_COOR);
            holder.F_row_tv_GIS_Y_COOR	= (TextView) view.findViewById(R.id.F_row_tv_GIS_Y_COOR);

            view.setTag(holder);
        } else {
            holder = (FarListAdapter.ViewHolder) view.getTag();
        }


        holder.F_row_tv_UNI_ID.setText("UNI_ID : " + rowItem.getUNI_ID());
        holder.F_row_tv_PRICE.setText("PRICE : " + rowItem.getPRICE());
        holder.F_row_POLL_DIV_CO.setText("POLL_DIV_CO : " + rowItem.getPOLL_DIV_CD());
        holder.F_row_tv_OS_NM.setText("OS_NM : " + rowItem.getOS_NM());
        holder.F_row_tv_DISTANCE.setText("DISTANCE : " + rowItem.getDISTANCE());
        holder.F_row_tv_GIS_X_COOR.setText("GIS_X_COOR : " + rowItem.getGIS_X_COOR());
        holder.F_row_tv_GIS_Y_COOR.setText("GIS_Y_COOR : " + rowItem.getGIS_Y_COOR());


        return view;
    }

    static class ViewHolder {
        private TextView F_row_tv_UNI_ID;
        private TextView F_row_tv_PRICE;
        private TextView F_row_POLL_DIV_CO;
        private TextView F_row_tv_OS_NM;
        private TextView F_row_tv_DISTANCE;
        private TextView F_row_tv_GIS_X_COOR;
        private TextView F_row_tv_GIS_Y_COOR;
    }
}
