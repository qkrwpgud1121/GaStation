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

import com.example.main.AreaDto.AreaSidoModel;
import com.example.main.R;

import java.util.ArrayList;

public class SidoAdapter extends ArrayAdapter<AreaSidoModel>
{
    private Context context;
    private int resource;
    private ArrayList<AreaSidoModel> itemList;
    private LayoutInflater inflater;

    public SidoAdapter(Context _context, int layoutId, int textViewId, ArrayList<AreaSidoModel> _itemList) {
        super(_context, layoutId, textViewId, _itemList);
        // other stuff

        context	 = _context;
        resource = layoutId;
        itemList = _itemList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public AreaSidoModel getItem(int position) {
        return itemList.get(position);
    }

    // 리스트 각 row별 화면
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        AreaSidoModel rowItem = this.itemList.get(position);

//        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getSidoCode());

        final ViewHolder holder;
        View view = convertView;

        if (view == null)
        {
            view = inflater.inflate(resource, null);
            holder = new ViewHolder();
            assert view != null;

            holder.row_tv_areaName	= (TextView) view.findViewById(R.id.row_tv_areaName);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.row_tv_areaName.setText(rowItem.getSidoName() + " / " + rowItem.getSidoCode());


        return view;
    }

    // 리스트 각 row별 화면 (드랍다운 화면)
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.row_textonly, parent, false);
        }

        AreaSidoModel rowItem = this.itemList.get(position);

        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getSidoCode());

        TextView row_tv_areaName = (TextView) convertView.findViewById(R.id.row_tv_areaName);

        row_tv_areaName.setText(rowItem.getSidoName());

        return convertView;
    }

    static class ViewHolder {
        private TextView row_tv_areaName;
    }
}