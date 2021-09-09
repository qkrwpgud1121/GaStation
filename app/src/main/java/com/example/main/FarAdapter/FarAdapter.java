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

import com.example.main.FarDto.FarModel;
import com.example.main.R;

import java.util.ArrayList;

public class FarAdapter extends ArrayAdapter<FarModel>
{
    private Context context;
    private int resource;
    private ArrayList<FarModel> itemList;
    private LayoutInflater inflater;

    public FarAdapter(Context _context, int layoutId, int textViewId, ArrayList<FarModel> _itemList) {
        super(_context, layoutId, textViewId, _itemList);
        // other stuff

        context	 = _context;
        resource = layoutId;
        itemList = _itemList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public FarModel getItem(int position) {
        return itemList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FarModel rowItem = this.itemList.get(position);

        final FarAdapter.ViewHolder holder;
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(resource, null);
            holder = new ViewHolder();
            assert view != null;

            holder.row_far_tv_radiusName = (TextView) view.findViewById(R.id.row_far_tv_radiusName);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.row_far_tv_radiusName.setText(rowItem.getFarproductName() + " / " + rowItem.getFarproductCode());


        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.row_far_textonly, parent, false);
        }

        FarModel rowItem = this.itemList.get(position);

        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getFarproductCode());

        TextView row_far_tv_radiusName = (TextView) convertView.findViewById(R.id.row_far_tv_radiusName);

        row_far_tv_radiusName.setText(rowItem.getFarproductName());

        return convertView;
    }
    static class ViewHolder {
        private TextView row_far_tv_radiusName;
    }

}
