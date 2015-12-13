package com.capotasto.helpmemorizationapp;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Capotasto on 11/28/15.
 */
public class WordsListAdapter extends ArrayAdapter<Vocabulary> {
    private int layoutResource;
    private LayoutInflater inflater;
    private float density = 2f;
    private ListView listView;

    private Context mContext;
    private ArrayList<String> list;

    public WordsListAdapter(Context context, int resource, ArrayList list) {
        super(context, resource, list);
        this.mContext = context;
        this.layoutResource = resource;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View workingView = null;

        if (convertView == null) {
            workingView = inflater.inflate(layoutResource, null);
        } else {
            workingView = convertView;
        }

        final WordObjectHolder holder = getAudioObjectHolder(workingView);
        //final Vocabulary entry = getItem(position);

        /* set values here */
        TextView wordText = (TextView) workingView.findViewById(R.id.wrod_name);
        wordText.setText(list.get(position));

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.mainView.getLayoutParams();
        params.rightMargin = 0;
        params.leftMargin = 0;
        holder.mainView.setLayoutParams(params);
        workingView.setOnTouchListener(new SwipeDetector(holder, position));

        return workingView;
    }

    private WordObjectHolder getAudioObjectHolder(View workingView) {
        Object tag = workingView.getTag();
        WordObjectHolder holder = null;

        if (tag == null || !(tag instanceof WordObjectHolder)) {
            holder = new WordObjectHolder();
            holder.mainView = (LinearLayout) workingView.findViewById(R.id.word_object_mainview);
            holder.rightView = (RelativeLayout) workingView.findViewById(R.id.word_object_right_view);
            holder.leftView = (RelativeLayout) workingView.findViewById(R.id.word_object_left_view);

            /* initialize other views here */

            workingView.setTag(holder);
        } else {
            holder = (WordObjectHolder) tag;
        }

        return holder;
    }

    public static class WordObjectHolder {
        public LinearLayout mainView;
        public RelativeLayout rightView;
        public RelativeLayout leftView;

        /* other views here */
    }

    public void setListView(ListView view) {
        listView = view;
    }

    public class SwipeDetector implements View.OnTouchListener {

        private static final int MIN_DISTANCE = 300;

        private boolean motionInterceptDisallowed = false;

        private WordObjectHolder holder;
        private int position;

        private float downX, upX;

        public SwipeDetector(WordObjectHolder holder, int position) {

            this.holder = holder;
            this.position = position;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    downX = event.getX();
                    return true;// allow other events like Click to be processed
                }

                case MotionEvent.ACTION_MOVE: {
                    upX = event.getX();
                    float deltaX = downX - upX;

                    if (Math.abs(deltaX) > MIN_DISTANCE && listView != null && !motionInterceptDisallowed) {
                        listView.requestDisallowInterceptTouchEvent(true);
                        motionInterceptDisallowed = true;
                    }

                    if (deltaX > 0) {
                        holder.leftView.setVisibility(View.GONE);
                    } else {
                        holder.leftView.setVisibility(View.VISIBLE);
                    }

                    swipe(-(int) deltaX);
                    return true;
                }

                case MotionEvent.ACTION_UP: {
                    upX = event.getX();
                    float deltaX = upX - downX;
                    if (Math.abs(deltaX) > MIN_DISTANCE) {
                        // left or right
                        swipeRemove();
                    } else {
                        swipe(0);
                    }

                    if (listView != null) {
                        listView.requestDisallowInterceptTouchEvent(false);
                        motionInterceptDisallowed = false;
                    }

                    holder.leftView.setVisibility(View.VISIBLE);
                    return true;
                }

                case MotionEvent.ACTION_CANCEL: {
                    holder.leftView.setVisibility(View.VISIBLE);
                    return false;

                }
            }

            return true;
        }

        private void swipe(int distance) {
            View animationView = holder.mainView;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
            params.rightMargin = -distance;
            params.leftMargin = distance;
            animationView.setLayoutParams(params);

        }

        private void swipeRemove() {
            remove(getItem(position));
            notifyDataSetChanged();

        }
    }

}
