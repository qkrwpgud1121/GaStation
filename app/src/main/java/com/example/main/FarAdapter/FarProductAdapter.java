package com.example.main.FarAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.main.FarDto.FarProductTypeModel;
import com.example.main.R;

import java.util.ArrayList;

public class FarProductAdapter extends ArrayAdapter<FarProductTypeModel>
{
    private Context context;
    private int resource;
    private ArrayList<FarProductTypeModel> itemList;
    private LayoutInflater inflater;

    public FarProductAdapter(Context _context, int layoutId, int textViewId, ArrayList<FarProductTypeModel> _itemList) {
        super(_context, layoutId, textViewId, _itemList);
        // other stuff

        context	 = _context;
        resource = layoutId;
        itemList = _itemList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public FarProductTypeModel getItem(int position) {
        return itemList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        FarProductTypeModel rowItem = this.itemList.get(position);

//        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getSidoCode());

        final FarProductAdapter.ViewHolder holder;
        View view = convertView;

        if (view == null)
        {
            view = inflater.inflate(resource, null);
            holder = new FarProductAdapter.ViewHolder();
            assert view != null;

            holder.tv_Far_productName	= (TextView) view.findViewById(R.id.tv_Far_productName);

            view.setTag(holder);
        } else {
            holder = (FarProductAdapter.ViewHolder) view.getTag();
        }


        holder.tv_Far_productName.setText(rowItem.getradiusName() + " / " + rowItem.getradiusCode());


        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.row_far_producttype, parent, false);
        }

        FarProductTypeModel rowItem = this.itemList.get(position);

        TextView row_far_tv_areaName = (TextView) convertView.findViewById(R.id.tv_Far_productName);

        row_far_tv_areaName.setText(rowItem.getradiusName());

        return convertView;
    }

    static class ViewHolder {
        private TextView tv_Far_productName;
    }

}
