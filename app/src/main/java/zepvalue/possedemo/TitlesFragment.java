package zepvalue.possedemo;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zepvalue on 8/18/2016.
 */
public class TitlesFragment extends ListFragment {
    private boolean mDualPane;
    private int mCurCheckPosition = 0;

    private ArrayList<String> programmerNames = new ArrayList<>();

    private ArrayList<Programmer> programmers = null;
    private List<String> list3 = Arrays.asList("String A", "String B", "String C");

    private Programmer programmer = null;


    //REMEMBER TO SEND THE POSITION ON THE TITLES LIST


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DataManager store = DataManager.getInstance(getActivity());

        programmers   = ((TitlesActivity)this.getActivity()).getProgrammers();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.my_list, getList());

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        // This is first created when the phone is switched to landscape
        // mode

        setListAdapter(adapter);

        View detailsFrame = getActivity().findViewById(R.id.details);


        // Check that a view exists and is visible
        // A view is visible (0) on the screen; the default value.
        // It can also be invisible and hidden, as if the view had not been
        // added.
        //
        mDualPane = detailsFrame != null
                && detailsFrame.getVisibility() == View.VISIBLE;


        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane) {
            // In dual-pane mode, the list view highlights the selected
            // item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        } else {
            // We also highlight in uni-pane just for fun
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            getListView().setItemChecked(mCurCheckPosition, true);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    public ArrayList<String> getList()
    {
        for (int k = 0; k < programmers.size(); k++)
        {
            programmerNames.add(k, programmers.get(k).getName());

        }
        return programmerNames;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        showDetails(position);

        programmer = programmers.get(position);

        Bundle bundle = new Bundle();
        bundle.putSerializable("extraProgrammerFromFragment", programmer);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
//
//        Intent detailsIntent = new Intent(getActivity(), DetailsActivity.class);
//        detailsIntent.putExtra("extraProgrammer", programmer);

    }

    // Helper function to show the details of a selected item, either by
    // displaying a fragment in-place in the current UI, or starting a whole
    // new activity in which it is displayed.

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    void showDetails(int index) {
        mCurCheckPosition = index;

        // The basic design is mutli-pane (landscape on the phone) allows us
        // to display both fragments (titles and details) with in the same
        // activity; that is FragmentLayout -- one activity with two
        // fragments.
        // Else, it's single-pane (portrait on the phone) and we fire
        // another activity to render the details fragment - two activities
        // each with its own fragment .
        //
        if (mDualPane) {
            // We can display everything in-place with fragments, so update
            // the list to highlight the selected item and show the data.
            // We keep highlighted the current selection
            getListView().setItemChecked(index, true);

            // Check what fragment is currently shown, replace if needed.
            TitlesFragment titles = (TitlesFragment) getFragmentManager()
                    .findFragmentById(R.id.details);
            if (titles == null || titles.getShownIndex() != index) {
                // Make new fragment to show this selection.

                titles = TitlesFragment.newInstance(index);


                // Execute a transaction, replacing any existing fragment
                // with this one inside the frame.
                FragmentTransaction ft = getFragmentManager()
                        .beginTransaction();
                ft.replace(R.id.details, titles);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }

        } else {
            // Otherwise we need to launch a new activity to display
            // the dialog fragment with selected text.
            // That is: if this is a single-pane (e.g., portrait mode on a
            // phone) then fire DetailsActivity to display the details
            // fragment

            // Create an intent for starting the DetailsActivity
            Intent intent = new Intent();

            // explicitly set the activity context and class
            // associated with the intent (context, class)
            intent.setClass(getActivity(), DetailsActivity.class);

            intent.putExtra("index", index);
            startActivity(intent);
        }
    }

    private static TitlesFragment newInstance(int index) {
        TitlesFragment f = new TitlesFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);



        return f;
    }
}
