package adlucem.matevzsubic.novorojencki;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class Main2Activity extends Activity implements View.OnClickListener{

    TextView tv1, tv2;
    ScrollView scrollView;

    private enum Iskanje{
        POSAMEZNO, OSTALO, NEVELJAVNO
    }
    private Iskanje iskanje = Iskanje.OSTALO;
    private EditText ime;
    private NodeList nList, nList2;
    private String text = "";
    private String text2 = "";
    private String zadnjiVpis, zadnjiSpol, zadnjiLeto;
    private Spinner spinner, spinner2;
    private Map<Integer, String> list;
    private Map<String, String> list2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv1=(TextView)findViewById(R.id.name);
        tv2=(TextView)findViewById(R.id.name1);
        tv1.setTypeface(null, Typeface.BOLD);
        tv2.setTypeface(null, Typeface.ITALIC);
        scrollView=(ScrollView) findViewById(R.id.scrollView);
        zadnjiVpis = "dsadfsdfdsfslfgodpfgd";
        zadnjiSpol = "";
        zadnjiLeto = "";

        list = new HashMap<>();
        list2 = new HashMap<>();

        ime = (EditText) findViewById(R.id.editText);

        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spol, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.leto, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        try {
            InputStream is = getAssets().open("ImenaDecki.xml");
            InputStream is1 = getAssets().open("ImenaDekleta.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);
            Document doc2 = dBuilder.parse(is1);

            Element element=doc.getDocumentElement();
            element.normalize();

            Element element2=doc2.getDocumentElement();
            element2.normalize();


            tv2.setText("\n ŠTEVILO:");
            tv1.setText("\n IME: ");
            nList = doc.getElementsByTagName("Oseba");
            nList2 = doc2.getElementsByTagName("Oseba");
            scrollView.addView(tv1);
            scrollView.addView(tv2);
            setContentView(scrollView);

        } catch (Exception e) {
            e.printStackTrace();}

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        text = savedInstanceState.getString("text", "");
        text2 = savedInstanceState.getString("text2", "");
        tv1.setText(text);
        tv2.setText(text2);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("text", text);
        outState.putString("text2", text2);
        super.onSaveInstanceState(outState);
    }

    private Integer getNumbers(Element element2){
        int skupek = 0;
        for(int i = 0;i<24; i++){
            int m = 1992 + i;
            skupek += getValue("Leto" + m, element2).equals("-") ? 0 : Integer.parseInt(getValue("Leto" + m, element2));
        }
        return skupek;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button2) {
            View vie = this.getCurrentFocus();
            if (vie != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(vie.getWindowToken(), 0);
            }
            if(!zadnjiVpis.equals(ime.getText().toString()) || !zadnjiSpol.equals(spinner.getSelectedItem().toString())
                    || !zadnjiLeto.equals(spinner2.getSelectedItem().toString())) {
                zadnjiVpis = ime.getText().toString();
                zadnjiSpol = spinner.getSelectedItem().toString();
                zadnjiLeto = spinner2.getSelectedItem().toString();text = "";
                text2 = "";
                tv1.setText(null);
                tv2.setText(null);
                if (!(ime.getText().toString().length() < 13)) {
                    showDialog("Prosimo, vpišite krajši niz!");
                    iskanje = Iskanje.NEVELJAVNO;
                    tv1.setText("\n Opozorilo: ");
                    tv2.setText("\nVpišite krajši niz!");
                } else if (isAlpha(ime.getText().toString().replace(" ", ""))) {
                    if (spinner2.getSelectedItem().toString().equals("Skupaj")) {
                        iskanje = Iskanje.OSTALO;
                        setByName(ime.getText().toString() != null ? ime.getText().toString() : "", spinner.getSelectedItem().toString().equals("M") ? nList : nList2);

                    } else if (spinner2.getSelectedItem().toString().equals("Posamezno")) {
                        if (ime.getText().toString().length() < 1) {
                            showDialog("Niz mora vsebovati najmanj 1 črko!");
                            iskanje = Iskanje.NEVELJAVNO;
                            tv1.setText("\n Opozorilo: ");
                            tv2.setText("\nVpišite bolj podroben niz!");
                        } else {
                            iskanje = Iskanje.POSAMEZNO;
                            setPosamezno(ime.getText().toString() != null ? ime.getText().toString() : "", spinner.getSelectedItem().toString().equals("M") ? nList : nList2);

                        }

                    } else {
                        iskanje = Iskanje.OSTALO;
                        setByYear(ime.getText().toString() != null ? ime.getText().toString() : "", spinner2.getSelectedItem().toString(), spinner.getSelectedItem().toString().equals("M") ? nList : nList2);

                    }
                } else {
                    showDialog("Vpisan niz vsebuje neveljavne znake!");

                    iskanje = Iskanje.NEVELJAVNO;
                    tv1.setText("\n Opozorilo: ");
                    tv2.setText("\nVpisan niz vsebuje neveljavne znake!");
                }
            }
        }
        else{
                if (Entries.pies.size() < 60 && 0 < Entries.pies.size() && (view.getId() == R.id.button4) && !Entries.description.equals("Posamezno")
                        && iskanje == Iskanje.OSTALO) {
                    Intent intent = new Intent(this, ChartActivity.class);

                    this.startActivity(intent);
                } else if (Entries.lines.size() < 20 && 0 < Entries.lines.size() && (view.getId() == R.id.button4) && Entries.description.equals("Posamezno")
                        && iskanje == Iskanje.POSAMEZNO) {
                    Intent intent = new Intent(this, LinearGraph.class);

                    this.startActivity(intent);
                } else if (iskanje == Iskanje.NEVELJAVNO) {
                    showDialog("Neveljaven prikaz!");
                } else if (60 <= Entries.pies.size() || 7 < Entries.lines.size()) {
                    showDialog("Število vnosov je previsoko!");
                } else {
                    showDialog("Število vnosov je prenizko!");
                }
        }

    }
    private void setPosamezno(String niz, NodeList list){
        int length = 0;
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element2 = (Element) node;
                if (node.getAttributes().getNamedItem("name").getNodeValue().toLowerCase().startsWith(niz.toLowerCase())) {
                    length++;
                    list2.put(getStrings(element2),node.getAttributes().getNamedItem("name").getNodeValue());

                }
            }

        }
        Log.i("Seznam", list.toString());
        if(length == 0){
            tv2.setText("\n ŠTEVILO:");
            tv1.setText("\n IME: ");
            iskanje = Iskanje.NEVELJAVNO;
            showDialog("Ni bilo najdenih imen!");
        }
        else{
            Entries.description = "Posamezno";
            izpisi(list2);

        }


    }

    private void izpisi(Map<String, String> seznam){
        text = "\n";
        text2 = "\n";
        if(!Entries.lines.isEmpty()){
            Entries.lines.clear();
        }
        Entries.names = "";
        tv1.setText(null);
        tv2.setText(null);
        int l = 1;
            Map<String, String> treeMap = new TreeMap<String, String>(seznam).descendingMap();
            text += String.format(" IME:") + "\n" + "\n";
            text2 += String.format(" ŠTEVILO:") + "\n" + "\n";
            for (Map.Entry<String, String> e : treeMap.entrySet()) {
                text += String.format("  " + e.getValue()) + ":" + "\n";
                text2 += String.format("   ") + "\n";
                List<Entry> valsComp1 = new ArrayList<Entry>();
                for(int z = 0; z < e.getKey().split(";").length; z++){
                    Entry c1e1 = new Entry(z, Integer.parseInt(e.getKey().split(";")[z]));
                    valsComp1.add(c1e1);
                    text += String.format("   " + (1992 + z)) + "\n";
                    text2 += String.format("   ") + e.getKey().split(";")[z] + "\n";
                }
                Entries.lines.add(valsComp1);
                Entries.names += e.getValue() + ";";
                text +=   "\n";
                text2 += "\n";
                l++;
            }
            tv1.setText(text);
            tv2.setText(text2);
        Log.i("Graf", Entries.lines.toString());
        seznam.clear();

    }
    private String getStrings(Element element2){
        String skupek = "";
        for(int i = 0;i<24; i++){
            int m = 1992 + i;
            skupek += getValue("Leto" + m, element2).equals("-") ? "0;" : (getValue("Leto" + m, element2) + ";");
        }
        Log.i("Skupek: ", skupek);
        return skupek;
    }
    private void setByName(String niz,NodeList nList) {
        tv1.setText(null);
        tv2.setText(null);
        int length = 0;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element2 = (Element) node;
                if (node.getAttributes().getNamedItem("name").getNodeValue().toLowerCase().startsWith(niz.toLowerCase())) {
                    length++;
                    list.put(getNumbers(element2),node.getAttributes().getNamedItem("name").getNodeValue());

                }
            }

        }
        Log.i("Seznam", list.toString());
        if(length == 0){
            tv2.setText("\n ŠTEVILO:");
            tv1.setText("\n IME: ");
            iskanje = Iskanje.NEVELJAVNO;
            showDialog("Ni bilo najdenih imen!");
        }
        else {
            Entries.description = "Skupaj";
            izpis(list);
        }
    }
    private void setByYear(String niz,String leto, NodeList nList) {
        tv1.setText(null);
        int length = 0;
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (node.getAttributes().getNamedItem("name").getNodeValue().toLowerCase().startsWith(niz.toLowerCase())) {
                    Element element2 = (Element) node;
                    if(!getValue("Leto" + leto, element2).equals("-")) {
                        length++;
                        list.put(Integer.parseInt(getValue("Leto" + leto, element2)),node.getAttributes().getNamedItem("name").getNodeValue());
                    }
                }
            }

        }
        Log.i("Seznam", list.toString());
        if(length == 0){
            tv2.setText("\n ŠTEVILO:");
            tv1.setText("\n IME: ");
            iskanje = Iskanje.NEVELJAVNO;
            showDialog("Ni bilo najdenih imen!");
        }
        else {
            Entries.description = leto;
            izpis(list);
        }
    }
    private void showDialog(String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }
    private void izpis(Map<Integer, String> seznam){
        text = "\n";
        text2 = "\n";
        if(!Entries.pies.isEmpty()) {
            Entries.pies.clear();
        }
        if(!Entries.labels.isEmpty()) {
            Entries.labels.clear();
        }
        tv1.setText(null);
        tv2.setText(null);
        int l = 1;
            Map<Integer, String> treeMap = new TreeMap<Integer, String>(seznam).descendingMap();
            text += String.format(" IME:") + "\n" + "\n";
            text2 += String.format(" ŠTEVILO:") + "\n" + "\n";
            for (Map.Entry<Integer, String> e : treeMap.entrySet()) {
                l++;
                text += String.format("   " + e.getValue()) + "\n" + "\n";
                text2 += String.format("   " + e.getKey()) + "\n" + "\n";
                Entries.pies.add(new PieEntry(e.getKey(), e.getValue()));
                Entries.labels.add(e.getValue());
            }
            tv1.setText(text);
            tv2.setText(text2);
        seznam.clear();

    }
}