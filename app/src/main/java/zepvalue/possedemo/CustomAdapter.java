package zepvalue.possedemo;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter{

    ArrayList<Service> services = null;
    ArrayList<Programmer> programmers = null;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater = null;
    public static String extraProgrammers = "extraProgrammers";
    private Service service = null;



    public CustomAdapter(Context context, ArrayList<Service> services)
    {
        // TODO Auto-generated constructor stub
        this.services = services;
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return services.size();
    }



    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder=new Holder();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.program_list, null);
            holder.tv = (TextView) convertView.findViewById(R.id.textViewAdapter);
            holder.img = (ImageView) convertView.findViewById(R.id.imageViewAdapter);
            holder.tv.setText(services.get(position).getPlatform());
            int imageId = getResourceId(services.get(position).getPlatform());
            holder.img.setImageResource(imageId);

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "You Clicked " + services.get(position).getPlatform() + " at " + position, Toast.LENGTH_LONG).show();

                    Intent titlesIntent = new Intent(context, TitlesActivity.class);
                    ArrayList<Programmer> programmers = services.get(position).getProgrammers();

                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    service = services.get(position);
                    detailsIntent.putExtra("extraService", service);


                    titlesIntent.putExtra("extraProgrammers", programmers);
                    context.startActivity(titlesIntent);

                }
             });
        }
        return convertView;
    }

    public int getResourceId(String platform)
    {
        switch(platform)
        {
            case "iOS":
                return R.drawable.apple_logo;
            case "Android":
                return R.drawable.android_logo;
            case "Ruby":
                return R.drawable.ruby_logo;
            default:
                return -1;
        }
    }
}