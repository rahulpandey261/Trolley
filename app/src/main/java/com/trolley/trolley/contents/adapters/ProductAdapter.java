package com.trolley.trolley.contents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.trolley.trolley.R;
import com.trolley.trolley.model.Category;

import java.util.ArrayList;

/**
 * Created by mihir.shah on 9/29/2015.
 */
public class ProductAdapter extends BaseExpandableListAdapter {

    ArrayList<Category> mCategories;

    Context mContext;

    LayoutInflater mInflater;

    public ProductAdapter(Context context, ArrayList<Category> categories) {
        mCategories = categories;
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return mCategories.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            holder = new GroupViewHolder();
            convertView = mInflater.inflate(R.layout.product_group_view, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.populate(mCategories.get(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChileViewHolder holder;
        if (convertView == null) {
            holder = new ChileViewHolder();
            convertView = mInflater.inflate(R.layout.product_child_view, parent, false);
            holder.gridView = (GridView) convertView.findViewById(R.id.grid);
            convertView.setTag(holder);
        } else {
            holder = (ChileViewHolder) convertView.getTag();
        }
        holder.populate(mCategories.get(groupPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class GroupViewHolder {
        TextView title;

        void populate(Category category) {
            title.setText(category.getName());
        }
    }

    class ChileViewHolder {
        GridView gridView;

        void populate(Category category) {
            final String[] products = category.getProductList();
            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(mContext, R.layout.product_grid_item, R.id.product_name, products);
            gridView.setAdapter(productAdapter);

            float gridHeight = mContext.getResources().getDimension(R.dimen.product_size);
            float spacing = mContext.getResources().getDimension(R.dimen.basic_widget_spacing);
            gridView.getLayoutParams().height = (int) ((gridHeight + spacing) * Math.ceil((float)products.length / 3) - spacing);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "You selected "+products[position], Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
