package zepvalue.possedemo.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import zepvalue.possedemo.Models.Programmer;
import zepvalue.possedemo.Models.Service;
import zepvalue.possedemo.R;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ArrayList<Programmer> programmers = null;
    public static Service service = null;
    private LatLng markerPosition;
    private String platform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Intent intentFromService = getIntent();
        programmers = (ArrayList<Programmer>) intentFromService.getSerializableExtra("extraProgrammers");
        platform =  intentFromService.getStringExtra("extraPlatform");

        markerPosition = (LatLng) intentFromService.getParcelableExtra("extraMarkerPosition");

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(programmers));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Programmer> programmers;

        public SimpleItemRecyclerViewAdapter(List<Programmer> items) {
            programmers = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = programmers.get(position);
            holder.mIdView.setText(programmers.get(position).getName());
            holder.mView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString("extraPlatform", platform);
                        Programmer programmer = programmers.get(position);
                        arguments.putSerializable("extraProgrammer" , programmer);
                        arguments.putParcelable("extraMarkerPosition", markerPosition);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
                    } else {
                        Context context = v.getContext();
                        Intent itemDetailIntent = new Intent(context, ItemDetailActivity.class);
                        Programmer programmer = programmers.get(position);
                        itemDetailIntent.putExtra("extraProgrammer", programmer);
                        itemDetailIntent.putExtra("extraPlatform", platform);
                        itemDetailIntent.putExtra("extraMarkerPosition", markerPosition);
                        context.startActivity(itemDetailIntent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return programmers.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Programmer mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
