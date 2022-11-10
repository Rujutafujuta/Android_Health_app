package com.example.medi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ListView listView;
    String[] items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewSong);
        displaysongs();
    }


    public ArrayList<Integer> findsong(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        Field fields[]=R.raw.class.getFields();
        for(int count=0;count<fields.length;count++){
            Log.i("Raw Asset: ",fields[count].getName());
            int resourseID = 0;
            try {
                resourseID = fields[count].getInt(fields[count]);
            } catch (IllegalAccessException e) {
                break;
            }
            arrayList.add(resourseID);
        }
        return arrayList;
    }

    void displaysongs(){
        final ArrayList<Integer> mysongs = findsong();
        items = new String[mysongs.size()];
        for(int i=0;i< mysongs.size();i++){
            items[i] =getResources().getResourceName(mysongs.get(i)).replace(".mp3","").replace("com.example.medi:raw/","");
        }
        customAdapter customAdapter = new customAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               /* if(mediaPlayer==null){
                    mediaPlayer= MediaPlayer.create(getApplicationContext(),mysongs.get(i));
                }
                mediaPlayer.start();*/
                startActivity(new Intent(getApplicationContext(),playerActivity.class).putExtra("songs",mysongs).putExtra("pos",i));
            }
        });
    }

    class customAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return items.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            @SuppressLint({"ViewHolder", "InflateParams"})
            View myview  = getLayoutInflater().inflate(R.layout.list_item,null);
            TextView  txtsong= myview.findViewById(R.id.txtsongname);
            txtsong.setSelected(true);
            txtsong.setText(items[i]);
            return myview;
        }
    }
}

