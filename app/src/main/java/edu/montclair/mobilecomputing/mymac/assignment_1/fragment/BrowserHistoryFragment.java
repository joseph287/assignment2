package edu.montclair.mobilecomputing.mymac.assignment_1.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.montclair.mobilecomputing.mymac.assignment_1.NotesActivity;
import edu.montclair.mobilecomputing.mymac.assignment_1.R;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.NotesContainer;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.NotesDatabase;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.SecurePreferences;


/**
 * Created by Admin on 03-11-2017.  browser page
 */
public class BrowserHistoryFragment extends Fragment {
    RecyclerView rv_scorecard;
    FloatingActionButton addnotes;
    TextView notesdescription;


    private static final String[] BOOKMARK_PROJECTION = {
            "title", // Browser.BookmarkColumns.TITLE
            "url", // Browser.BookmarkColumns.URL
            "date", // Browser.BookmarkColumns.DATE
            "created", // Browser.BookmarkColumns.DATE
    };
    // Copied from android.provider.Browser.BOOKMARKS_URI:
    private static final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");

    private static final String BOOKMARK_SELECTION = "bookmark = 1 AND url IS NOT NULL";

    private final List<String[]> titleURLs = new ArrayList<>();

    ArrayList<NotesContainer> notesArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browserfragment, container, false);


        rv_scorecard = (RecyclerView) view.findViewById(R.id.rv_scorecard);
        notesdescription = (TextView) view.findViewById(R.id.notesdescription);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_scorecard.setLayoutManager(layoutManager);
//        setAdapter();
        getHistory();

        return view;
    }

    private void getHistory() {
        Cursor cursor = getActivity().getContentResolver().query(BOOKMARKS_URI, BOOKMARK_PROJECTION,
                BOOKMARK_SELECTION, null, null);
        if (cursor == null) {
//            Log.w(TAG, "No cursor returned for bookmark query");
//            finish();
        }
        try {

            while (cursor.moveToNext()) {
                NotesContainer notesContainer = new NotesContainer();
                notesContainer.setTitle(cursor.getString(0));
                notesContainer.setDescription(cursor.getString(1));
//                notesContainer.setDate("" + cursor.getInt(2));
                notesContainer.setDate(cursor.getLong(3));
//                titleURLs.add(new String[]{cursor.getString(0), cursor.getString(1)});
                notesArrayList.add(notesContainer);
            }
            if (notesArrayList.size() == 0) {
                notesdescription.setVisibility(View.VISIBLE);
            } else {
                notesdescription.setVisibility(View.GONE);
            }

            NotesAdapter notesAdapter = new NotesAdapter(getActivity(), notesArrayList);
            rv_scorecard.setAdapter(notesAdapter);
        } finally {
            cursor.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getHistory();
    }

    public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<NotesContainer> chatsList;
        LayoutInflater inflater;

        public NotesAdapter(Activity context, ArrayList<NotesContainer> chatsList) {
            this.chatsList = chatsList;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.browseitem, parent, false);
            return new NewsViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder_main, final int position) {
            NotesContainer chatcon = chatsList.get(position);
            if (holder_main instanceof NewsViewHolder) {
                final NewsViewHolder holder = (NewsViewHolder) holder_main;
                holder.txttitle.setText(chatcon.getTitle());
                holder.txtdescription.setText(chatcon.getDescription());
                String dateString = new SimpleDateFormat("MM/dd/yyyy hh:mm").format(new Date(chatcon.getDate()));
                holder.txtdate.setText(dateString);
            }
        }

        @Override
        public int getItemCount() {
            return chatsList.size();
        }

        public class NewsViewHolder extends RecyclerView.ViewHolder {
            TextView txtdescription, txttitle, txtdate;

            public NewsViewHolder(View v) {
                super(v);
                txttitle = (TextView) v.findViewById(R.id.txttitle);
                txtdate = (TextView) v.findViewById(R.id.txtdate);
                txtdescription = (TextView) v.findViewById(R.id.txtdescription);
                v.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(chatsList.get(getAdapterPosition()).getDescription()));
                        startActivity(intent);
                    }
                });
            }

        }

    }
}
