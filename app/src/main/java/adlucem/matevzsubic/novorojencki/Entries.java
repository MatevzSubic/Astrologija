package adlucem.matevzsubic.novorojencki;

import android.support.annotation.NonNull;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by MatevzSubic on 26/12/2016.
 */

public class Entries {
    public static List<PieEntry> pies;
    public static ArrayList<String> labels;
    public static List<List<Entry>> lines;
    public static String names;
    public static String description;

    public static void setEntries(){
        pies = new ArrayList<>();
        labels = new ArrayList<>();
        lines = new ArrayList<>();
        names = new String();
        description = "";
    }
}
