package com.example.android.quakereport;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import android.graphics.drawable.GradientDrawable;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static final String LOCATION_SEPARATOR= " of ";
    public EarthquakeAdapter(Context context, List<Earthquake> earthqaukes) {
        super(context, 0, earthqaukes);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);
        //formate the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        magnitudeView.setText(formattedMagnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        //get the appropreate background color
        int magnitudecolor = getMagnitudeColor(currentEarthquake.getMagnitude());
        //set the color on the magnitude  circle
        magnitudeCircle.setColor(magnitudecolor);

        String originallocation = currentEarthquake.getLocation();
        String primarylocation;
        String locationoffset;

        if(originallocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originallocation.split(LOCATION_SEPARATOR);
            locationoffset = parts[0]+LOCATION_SEPARATOR;
            primarylocation =parts[1];
        }else {
            locationoffset =getContext().getString(R.string.near_the);
            primarylocation=originallocation;
        }

        TextView primaryLocationView =(TextView) listItemView.findViewById(R.id.location_primary);
        primaryLocationView.setText(primarylocation);

        TextView locationoffsetView= (TextView) listItemView.findViewById(R.id.location_offset);
        locationoffsetView.setText(locationoffset);

        Date dateObject= new Date(currentEarthquake.getTimeInMilliseconds());
        TextView dateView = (TextView) listItemView.findViewById(R.id.date);
        //format data string (i.e. date)
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formateTime(dateObject);
        timeView.setText(formattedTime);
        return listItemView;
    }
   private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor =(int) Math.floor(magnitude);

        switch (magnitudeFloor){
            case 0:
            case 1:
                magnitudeColorResourceId =R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId =R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId =R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId =R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId =R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId =R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId =R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId =R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId =R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }
    private String formatDate(Date dateObject){
        SimpleDateFormat dataFormat =new SimpleDateFormat("LLL dd,yyyy");
        return dataFormat.format(dateObject);
    }

    private String formateTime(Date dateObject){
        SimpleDateFormat timeFormat =new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormate = new DecimalFormat("0.0");
        return magnitudeFormate.format(magnitude);
    }

}

