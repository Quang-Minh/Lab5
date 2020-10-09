package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    List<String> dataList = new ArrayList<>();
    List<String> linkList = new ArrayList<>();
    ArrayAdapter adapter;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        AsyncTask<String,Void,String> content = new DongBoDuLieu().execute("https://ngoisao.net/rss/hau-truong.rss");
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        intent = new Intent(MainActivity.this,DetailLink.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String link = linkList.get(i);
                intent.putExtra("URLLink",link);
                startActivity(intent);
            }
        });
    }
    public class DongBoDuLieu extends AsyncTask<String,Void,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("dAT",s);

            XMLParser xmlParser = new XMLParser();
            try {
                Document document = null;

                    document = xmlParser.getDocument(s);

                NodeList nodeList = document.getElementsByTagName("item");
                String title = "";
                for (int i=0; i<nodeList.getLength();i++){
                    Element element = (Element)nodeList.item(i);
                    title+= xmlParser.getValue(element,"title")+"\n";
                    dataList.add(title);
                    linkList.add(xmlParser.getValue(element,"link"));
                }
                adapter.notifyDataSetChanged();
            }  catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);

                InputStreamReader reader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = "";
                while ((line = bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }
    }
}