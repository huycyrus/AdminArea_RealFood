package com.example.adminarea_realfood;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

public class PieChartFragment extends Fragment {
    int donHangGiaoThanhCong = 0, donHangGiaoKhongThanhCong = 0, donHangDangDiGiao = 0;
    AnyChartView anyChartView;

    public PieChartFragment(int donHangGiaoThanhCong, int donHangGiaoKhongThanhCong, int donHangDangDiGiao) {
        this.donHangGiaoThanhCong = donHangGiaoThanhCong;
        this.donHangGiaoKhongThanhCong = donHangGiaoKhongThanhCong;
        this.donHangDangDiGiao = donHangDangDiGiao;

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
        android.view.View view = inflater.inflate(R.layout.piechart_fragment, container, false);
        anyChartView = view.findViewById(R.id.piechartThongKeShipper);
        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Đơn hàng giao thành công", donHangGiaoThanhCong));
        data.add(new ValueDataEntry("Đơn hàng giao không thành công", donHangGiaoKhongThanhCong));
        data.add(new ValueDataEntry("Đơn hàng đang đi giao hàng ", donHangDangDiGiao));

        pie.data(data);
        pie.labels().position("outside");
        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Trạng thái đơn hàng")
                .padding(0d, 0d, 5d, 0d);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        anyChartView.setChart(pie);
        return view;
    }
}
