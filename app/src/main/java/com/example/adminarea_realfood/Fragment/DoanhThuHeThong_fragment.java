package com.example.adminarea_realfood.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.adminarea_realfood.R;

import java.util.ArrayList;
import java.util.List;


public class DoanhThuHeThong_fragment extends Fragment {
    View view;
    int giaoThanhCong = 0, giaoKhongThanhCong = 0;
    AnyChartView anyChartView;

    public DoanhThuHeThong_fragment(int giaoThanhCong,int giaoKhongThanhCong) {
        this.giaoThanhCong = giaoThanhCong;
        this.giaoKhongThanhCong = giaoKhongThanhCong;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.doanh_thu_he_thong_fragment, container, false);
        anyChartView = view.findViewById(R.id.pieChartDoanhThuHeThong);
        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Giao thành công", giaoThanhCong));
        data.add(new ValueDataEntry("Giao không thành công", giaoKhongThanhCong));

        pie.data(data);
        pie.labels().position("outside");
        pie.legend().title().enabled(true);
        pie.legend().title().text("Doanh thu hệ thống").padding(0d, 0d, 10d, 0d);
        pie.legend().position("center-bottom").itemsLayout(LegendLayout.HORIZONTAL).align(Align.CENTER);
        anyChartView.setChart(pie);
        return view;
    }
}