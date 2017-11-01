package com.shreyanshvit.social;


import android.annotation.SuppressLint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgeAnalysis extends Fragment implements OnChartValueSelectedListener {

    View view;
    protected HorizontalBarChart mChart;

    public AgeAnalysis() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_age_analysis, container, false);

        mChart = (HorizontalBarChart) view.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hotelgirnar.ml/social/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final RequestAgeInterface request = retrofit.create(RequestAgeInterface.class);
        Call<AgeResult> call = request.getAge("1");
        call.enqueue(new Callback<AgeResult>() {
            @Override
            public void onResponse(Call<AgeResult> call, Response<AgeResult> response) {
                response.body();
                int a = Integer.parseInt(response.body().geta());
                int b = Integer.parseInt(response.body().getb());
                int c = Integer.parseInt(response.body().getc());
                int d = Integer.parseInt(response.body().getd());
                int e = Integer.parseInt(response.body().gete());
                int f = Integer.parseInt(response.body().getf());
                int g = Integer.parseInt(response.body().getg());
                int h = Integer.parseInt(response.body().geth());
                int i = Integer.parseInt(response.body().geti());
                int j = Integer.parseInt(response.body().getj());

                // mChart.setHighlightEnabled(false);

                mChart.setDrawBarShadow(false);

                mChart.setDrawValueAboveBar(true);

                mChart.getDescription().setEnabled(false);

                // if more than 60 entries are displayed in the chart, no values will be
                // drawn
                mChart.setMaxVisibleValueCount(60);

                // scaling can now only be done on x- and y-axis separately
                mChart.setPinchZoom(false);

                // draw shadows for each bar that show the maximum value
                // mChart.setDrawBarShadow(true);

                mChart.setDrawGridBackground(false);

                XAxis xl = mChart.getXAxis();
                xl.setPosition(XAxis.XAxisPosition.BOTTOM);
                xl.setDrawAxisLine(true);
                xl.setDrawGridLines(false);
                xl.setGranularity(0);

                YAxis yl = mChart.getAxisLeft();
                yl.setDrawAxisLine(true);
                yl.setDrawGridLines(true);
                yl.setAxisMinimum(0); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

                YAxis yr = mChart.getAxisRight();
                yr.setDrawAxisLine(true);
                yr.setDrawGridLines(false);
                yr.setAxisMinimum(0); // this replaces setStartAtZero(true)

                setData(a, b, c,  d, e, f, g, h, i, j);
                mChart.setFitBars(true);
                mChart.animateY(2500);

                Legend l = mChart.getLegend();
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                l.setDrawInside(false);
                l.setFormSize(8f);
                l.setXEntrySpace(4f);

            }

            @Override
            public void onFailure(Call<AgeResult> call, Throwable t) {

            }
        });


        return view;
    }

    private void setData(int a, int b, int c, int d, int e, int f, int g, int h, int i ,int j) {

        int range = 100;
        int barWidth = 9;
        int spaceForBar = 10;
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();


        yVals1.add(new BarEntry(0 * spaceForBar, a, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(1 * spaceForBar, b, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(2 * spaceForBar, c, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(3 * spaceForBar, d, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(4 * spaceForBar, e, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(5 * spaceForBar, f, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(6 * spaceForBar, g, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(7 * spaceForBar, h, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(8 * spaceForBar, i, getResources().getDrawable(R.drawable.ic_menu_camera)));
        yVals1.add(new BarEntry(9 * spaceForBar, j, getResources().getDrawable(R.drawable.ic_menu_camera)));


        BarDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "Age");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10);
            data.setBarWidth(barWidth);
            mChart.setData(data);
        }
    }

    protected RectF mOnValueSelectedRectF = new RectF();
    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mOnValueSelectedRectF;
        mChart.getBarBounds((BarEntry) e, bounds);

        MPPointF position = mChart.getPosition(e, mChart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency());

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        MPPointF.recycleInstance(position);

    }

    @Override
    public void onNothingSelected() {

    }
}
