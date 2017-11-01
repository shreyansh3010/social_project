package com.shreyanshvit.social.Fragments.CheckBill.DataAdapter;

/**
 * Created by SJ on 10/08/2017.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.shreyanshvit.social.R;
import com.shreyanshvit.social.Fragments.CheckBill.AndroidVersion.AndroidVersion;

import com.shreyanshvit.social.Fragments.CheckBill.AndroidVersion.AndroidVersion;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
    private ArrayList<AndroidVersion> android;
    private ArrayList<AndroidVersion> mFilteredList;

    public DataAdapter(ArrayList<AndroidVersion> android) {
        this.android = android;
        this.mFilteredList = android;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        viewHolder.tv_billno.setText(mFilteredList.get(i).getbill_no());
        viewHolder.tv_billdate.setText(mFilteredList.get(i).getbill_date());
        viewHolder.tv_name.setText(mFilteredList.get(i).getname());
        viewHolder.tv_product.setText(mFilteredList.get(i).getproduct());
        viewHolder.tv_udi.setText(mFilteredList.get(i).getuid());
        viewHolder.tv_category.setText(mFilteredList.get(i).getCategory());
        viewHolder.tv_total.setText(mFilteredList.get(i).gettotal() + "/-");
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()){
                    mFilteredList = android;
                }
                else {
                    ArrayList<AndroidVersion> filteredList = new ArrayList<>();
                    for (AndroidVersion androidVersion : android) {
                        if (androidVersion.getname().toLowerCase().contains(charString) || androidVersion.getuid().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (ArrayList<AndroidVersion>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_udi,tv_billdate, tv_product, tv_category, tv_total, tv_billno;
        public ViewHolder(View view) {
            super(view);

            tv_billno = (TextView) view.findViewById(R.id.tv_bill);
            tv_billdate = (TextView) view.findViewById(R.id.tv_date);
            tv_udi = (TextView) view.findViewById(R.id.tv_uid);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_product = (TextView) view.findViewById(R.id.tv_product);
            tv_category = (TextView) view.findViewById(R.id.tv_cat);
            tv_total = (TextView) view.findViewById(R.id.tv_total);


        }
    }

}
