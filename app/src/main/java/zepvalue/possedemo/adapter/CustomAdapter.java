package zepvalue.possedemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import zepvalue.possedemo.Models.Service;
import zepvalue.possedemo.R;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomHolder>
{

    ArrayList<Service> services = null;
    private LayoutInflater layoutInflater;
    private ItemClickCallBack itemCallback;

    public interface ItemClickCallBack
    {
        void onItemClick(int p);
    }

    public void setItemCallback(final ItemClickCallBack itemCallback)
    {
        this.itemCallback = itemCallback;
    }

    public CustomAdapter(Context context, ArrayList<Service> services)
    {
        this.layoutInflater = LayoutInflater.from(context);
        this.services = services;

    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);
        return new CustomHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
        holder.title.setText(services.get(position).getPlatform());
        int imageId = getResourceId(services.get(position).getPlatform());
        holder.icon.setImageResource(imageId);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView title;
        private ImageView icon;
        private View container;

        public CustomHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lbl_item_text);
            icon = (ImageView) itemView.findViewById(R.id.im_item_icon);
            container = itemView.findViewById(R.id.container_item_root);
            container.setOnClickListener(this);
        }


        @Override
        public void onClick(View view)
        {
            if(view.getId() == R.id.container_item_root)
            {

            }
        }
    }

    public int getResourceId(String platform)
    {
        switch(platform)
        {
            case "iOS":
                return R.drawable.apple_logo_black;
            case "Android":
                return R.drawable.android_logo;
            case "Ruby":
                return R.drawable.ruby_logo_black;
            default:
                return -1;
        }
    }
}
