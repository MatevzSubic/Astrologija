package adlucem.matevzsubic.novorojencki;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends Activity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Entries.setEntries();

        View v = findViewById(R.id.button);
        //set event listener
        v.setOnClickListener(this);

        //View c = findViewById(R.id.button3);
        //set event listener
        //c.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        if(arg0.getId() == R.id.button){
            //define a new Intent for the second Activity
            Intent intent = new Intent(this,Main2Activity.class);

            //start the second Activity
            this.startActivity(intent);
        }
        //else if(arg0.getId() == R.id.button3){
            //define a new Intent for the second Activity
           // Intent intent = new Intent(this,Settings.class);

            //start the second Activity
           // this.startActivity(intent);
      //  }
    }

}
