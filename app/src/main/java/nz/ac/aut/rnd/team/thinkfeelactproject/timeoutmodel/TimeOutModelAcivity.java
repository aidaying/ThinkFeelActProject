package nz.ac.aut.rnd.team.thinkfeelactproject.timeoutmodel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import nz.ac.aut.rnd.team.thinkfeelactproject.R;
import nz.ac.aut.rnd.team.thinkfeelactproject.java.DatabaseHandler;

public class TimeOutModelAcivity extends Activity {

    private DatabaseHandler mydb;
    private RelativeLayout relativeLayout;
    private PieChart sosSelection;
    private float[]  yData;
    private String[] xData;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_out_model_acivity);
        mydb = new DatabaseHandler(getApplicationContext());
        XmlPullParserFactory pullParserFactory;
        try{
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();
            InputStream inputStream = getResources().openRawResource(R.raw.timeoutpievalue);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(inputStream,null);
            parserXML(parser);
        }catch(XmlPullParserException | IOException e){
            e.printStackTrace();
        }

        relativeLayout = (RelativeLayout) findViewById(R.id.timeoutpie);
        sosSelection = new PieChart(this);

        relativeLayout.addView(sosSelection);
        relativeLayout.setBackgroundColor(Color.TRANSPARENT);
        moveOffScreen();
        sosSelection.setUsePercentValues(true);
        sosSelection.setEntryLabelColor(Color.BLACK);
        sosSelection.setDescription("");
        sosSelection.setDrawHoleEnabled(true);
        sosSelection.setHoleColor(Color.TRANSPARENT);
        sosSelection.setHoleRadius(30);
        sosSelection.setTransparentCircleRadius(30);

        sosSelection.setMaxAngle(180);
        sosSelection.setRotationAngle(180);
        sosSelection.setRotationEnabled(false);



        ViewGroup.LayoutParams params = sosSelection.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;

        sosSelection.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(final Entry e, Highlight h) {



            }

            @Override
            public void onNothingSelected() {

            }
        });
        addData();

    }



    private void addData(){

        ArrayList<PieEntry> yVals1 = new ArrayList<PieEntry>();
        for(int i = 0; i < yData.length; i++){
            yVals1.add(new PieEntry(yData[i],xData[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(yVals1,"SOS Model");
        pieDataSet.setSliceSpace(3);
        pieDataSet.setSelectionShift(6);


        ArrayList<Integer> colors = new ArrayList<Integer>();
        int[] TIMEOUT_COLORS = {
                Color.rgb(107, 144, 249), Color.rgb(107, 165, 249), Color.rgb(107, 204, 249),
                Color.rgb(107, 223, 249), Color.rgb(107, 240, 249)
        };
        for(int c : TIMEOUT_COLORS){
            colors.add(c);
        }
        colors.add(ColorTemplate.getHoloBlue());
        pieDataSet.setColors(colors);

        PieData pieData = new PieData();
        pieData.setDataSet(pieDataSet);
        pieData.setValueTextSize(1f);
        pieData.setValueTextColor(Color.BLACK);

        sosSelection.setData(pieData);
        sosSelection.highlightValue(null);
        sosSelection.invalidate();
    }
    private void parserXML(XmlPullParser parser) throws XmlPullParserException, IOException{
        int i = 0;
        int j = 0;
        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT){
            String name;
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    yData = new float[4];
                    xData = new String[4];
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if(name.equals("size")){
                        yData[i] = Float.valueOf(parser.nextText());
                        i++;
                    }
                    if(name.equals("detail")){
                        xData[j] = parser.nextText();
                        j++;
                    }
                    break;
                case XmlPullParser.END_TAG:

            }
            eventType = parser.next();
        }

    }
    private void moveOffScreen() {

        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();  // deprecated

        int offset = (int)(height * 0.26); /* percent to move */

        RelativeLayout.LayoutParams rlParams =
                (RelativeLayout.LayoutParams)sosSelection.getLayoutParams();
        rlParams.setMargins(0, 0, 0, -offset);
        sosSelection.setLayoutParams(rlParams);
    }
}

