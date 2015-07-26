package com.example.john.modernartui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getName();

    private RelativeLayout canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvas = ( RelativeLayout ) findViewById( R.id.canvas );
        SeekBar seekBar = ( SeekBar ) findViewById( R.id.seekBar );

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                for (int i = 0; i < canvas.getChildCount(); i++) {

                    View rectangle = canvas.getChildAt(i);


                    int recColor = Color.parseColor((String) rectangle.getTag());
                    int alteredColor = (0x00FFFFFF - (recColor | 0xFF000000)) | (recColor & 0xFF000000);

                    //System.out.println("REC_COLOOOOOR = " + recColor);

                    if (getResources().getColor(R.color.light_gray) != recColor) {


                        int amt = (int) ( 2.55 * ((double) progress / (double) 100));

                        int R = (recColor >> 16) + amt;
                        int G = (recColor >> 8 & 0x00FF) + amt;
                        int B = (recColor & 0x0000FF) + amt;

                        int alteredR = (recColor >> 16) + amt;
                        int alteredG = (recColor >> 8 & 0x00FF) + amt;
                        int alteredB = (alteredColor & 0x0000FF) + amt;

                        rectangle.setBackgroundColor(
                                Color.rgb(
                                        (int) (R + (alteredR - R) * ((double) progress / (double) 100)),
                                        (int) (G + (alteredG - G) * ((double) progress / (double) 100)),
                                        (int) (B + (alteredB - B) * ((double) progress / (double) 100))
                                )
                        );
                    }
                }
            }


        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.more) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage(R.string.text);

            builder.setPositiveButton(R.string.not_now, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            builder.setNegativeButton(R.string.visit, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent moma = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.moma.org/"));
                    startActivity(moma);
                }
            });
            builder.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
