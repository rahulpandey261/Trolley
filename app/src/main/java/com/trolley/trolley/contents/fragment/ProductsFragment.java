package com.trolley.trolley.contents.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.trolley.trolley.R;
import com.trolley.trolley.contents.adapters.ProductAdapter;
import com.trolley.trolley.model.Category;

import java.util.ArrayList;

/**
 * Created by mihir.shah on 9/29/2015.
 */
public class ProductsFragment extends Fragment {

    ExpandableListView mListView;

    ProductAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        initializeView(view);
        return view;
    }

    void initializeView(View view) {
        mListView = (ExpandableListView) view.findViewById(R.id.list);
        mListView.setOnGroupExpandListener(mGroupExpandListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new ProductLoaderTask(getActivity()).execute();
    }

    ExpandableListView.OnGroupExpandListener mGroupExpandListener = new ExpandableListView
            .OnGroupExpandListener() {
        int currentPosition = -1;

        @Override
        public void onGroupExpand(int groupPosition) {
            if (groupPosition != currentPosition) {
                mListView.collapseGroup(currentPosition);
                currentPosition = groupPosition;
            }
        }
    };

    class ProductLoaderTask extends AsyncTask<String, Integer, String> {

        Context mContext;

        ProductLoaderTask(Context context) {
            mContext = context;
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            ArrayList<Category> categories = new ArrayList<Category>();

            Category aCat = new Category("Fruits", new String[]{"Apple", "Banana", "Mango", "Orange", "Guava"});
            Category bCat = new Category("Grains", new String[]{"Wheat", "Millet", "Rice"});
            Category cCat = new Category("Dairy", new String[]{"Milk", "Curd", "Cheese", "Ghee", "Butter", "Butter", "Lassi"});
            Category dCat = new Category("Misc", new String[]{"Sugar", "Oil"});

            categories.add(aCat);
            categories.add(bCat);
            categories.add(cCat);
            categories.add(dCat);

            mAdapter = new ProductAdapter(getActivity(), categories);
            mListView.setAdapter(mAdapter);

            super.onPostExecute(string);
        }
    }
}
