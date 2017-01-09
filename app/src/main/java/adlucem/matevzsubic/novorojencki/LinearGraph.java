package adlucem.matevzsubic.novorojencki;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class LinearGraph extends Activity {

    private LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_graph);
        chart = (LineChart) findViewById(R.id.chart);

        showChart();
    }

    private void showChart(){
        final String[] quarters = new String[] { "1992", "1993", "1994", "1995","1996", "1997", "1998", "1999","2000", "2001",
                "2002", "2003","2004", "2005", "2006", "2007","2008", "2009", "2010", "2011","2012", "2013", "2014", "2015" };
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }

            // we don't draw numbers, so no decimal digits needed
        };

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        int i = 0;
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        for(List<Entry> entry : Entries.lines){
            LineDataSet setComp1 = new LineDataSet(entry, Entries.names.split(";")[i]);
            setComp1.setColor(getCo((i%10)));
            setComp1.setCircleColor(getCo((i%10)));
            setComp1.setCircleRadius(1);
            setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
            dataSets.add(setComp1);
            i++;
        }
        LineData data = new LineData(dataSets);
        chart.getDescription().setEnabled(false);
        chart.setData(data);
        chart.invalidate(); // refresh
    }
    private int getCo(int random){
        switch (random){
            case 1:
                return Color.GRAY;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.RED;
            case 5:
                return Color.YELLOW;
            case 6:
                return Color.CYAN;
            case 7:
                return Color.MAGENTA;
            case 8:
                return Color.DKGRAY;
            case 9:
                return Color.LTGRAY;
            default:
                return Color.BLACK;
        }
    }
}
