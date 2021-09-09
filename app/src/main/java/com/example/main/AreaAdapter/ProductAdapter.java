package com.example.main.AreaAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.main.AreaDto.ProductTypeModel;
import com.example.main.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<ProductTypeModel>
{
    private Context context;
    private int resource;
    private ArrayList<ProductTypeModel> itemList;
    private LayoutInflater inflater;

    public ProductAdapter(Context _context, int layoutId, int textViewId, ArrayList<ProductTypeModel> _itemList) {
        super(_context, layoutId, textViewId, _itemList);
        // other stuff

        context	 = _context;
        resource = layoutId;
        itemList = _itemList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public ProductTypeModel getItem(int position) {
        return itemList.get(position);
    }

    // 리스트 각 row별 화면
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        ProductTypeModel rowItem = this.itemList.get(position);

//        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getSidoCode());

        final ProductAdapter.ViewHolder holder;
        View view = convertView;

        if (view == null)
        {
            view = inflater.inflate(resource, null);
            holder = new ViewHolder();
            assert view != null;

            holder.tv_productName	= (TextView) view.findViewById(R.id.tv_productName);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        holder.tv_productName.setText(rowItem.getproductName() + " / " + rowItem.getproductCode());


        return view;
    }

    // 리스트 각 row별 화면 (드랍다운 화면)
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.row_producttype, parent, false);
        }

        ProductTypeModel rowItem = this.itemList.get(position);

//        Log.e("TEST", "===== GET VIEW pos : " + position + " / " + rowItem.getSidoCode());

        TextView row_tv_areaName = (TextView) convertView.findViewById(R.id.tv_productName);

        row_tv_areaName.setText(rowItem.getproductName());

        return convertView;
    }

    static class ViewHolder {
        private TextView tv_productName;
    }
}