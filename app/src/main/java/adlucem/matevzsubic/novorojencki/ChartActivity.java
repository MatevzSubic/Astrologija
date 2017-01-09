package adlucem.matevzsubic.novorojencki;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class ChartActivity extends Activity {

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        chart = (PieChart) findViewById(R.id.chart);

        showChart();
    }

    private void showChart(){
        PieDataSet dataSet = new PieDataSet(Entries.pies, "Leto: " + Entries.description);;
        PieData data = new PieData(dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.getDescription().setEnabled(false);
        chart.setData(data);
        chart.animateY(3000);
        chart.invalidate(); // refresh
    }
}
