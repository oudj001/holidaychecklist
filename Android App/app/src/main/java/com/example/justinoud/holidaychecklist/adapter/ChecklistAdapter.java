package com.example.justinoud.holidaychecklist.adapter;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justinoud.holidaychecklist.R;
import com.example.justinoud.holidaychecklist.helper.DatabaseHandler;
import com.example.justinoud.holidaychecklist.model.Checklist;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ChecklistAdapter extends ArrayAdapter<Checklist>{
	 Context context;
     TextView participant;
     TextView title;
     ListView listView;
     ImageView btn_delete;

	    int layoutResourceId;

	    ArrayList<Checklist> data=new ArrayList<Checklist>();
	    public ChecklistAdapter(Context context, int layoutResourceId, ArrayList<Checklist> data) {
	        super(context, layoutResourceId, data);
	        this.layoutResourceId = layoutResourceId;
	        this.context = context;
	        this.data = data;
	    }


	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View row = convertView;
	        ImageHolder holder = null;
	        final int pos = position;
	        if(row == null)
	        {
	            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	            row = inflater.inflate(layoutResourceId, parent, false);

	            holder = new ImageHolder();
	            title = (TextView)row.findViewById(R.id.checklist_title);
                participant = (TextView)row.findViewById(R.id.checklist_participants);
                btn_delete = (ImageView)row.findViewById(R.id.discard_btn);

	            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
	            row.setTag(holder);

                btn_delete.setVisibility(View.VISIBLE);
                btn_delete.setOnClickListener(buttonClickListener);

	        }
	        else
	        {
	            holder = (ImageHolder)row.getTag();
	        }

	        Checklist picture = data.get(position);

	        title.setText(picture.getTitle());
            participant.setText(picture.getParticipant());
	        //convert byte to bitmap take from contact class
	        
	        byte[] outImage=picture.getImage();
	        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
	        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
	        holder.imgIcon.setImageBitmap(theImage);
	       return row;
	       
	    }
	   
	    static class ImageHolder
	    {
	        ImageView imgIcon;
	    }

    private OnClickListener buttonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = listView.getPositionForView((View) v.getParent());
            DatabaseHandler db = new DatabaseHandler(getContext());
            Cursor cursor = db.fetchAllIDs();
            if(cursor!=null){
                if(cursor.moveToFirst()){
                    cursor.moveToPosition(position);
                    int checklistid = cursor.getInt(cursor.getColumnIndex("_id"));
                    Checklist checklist = db.getChecklist(checklistid);
                    db.deleteChecklist(checklist);
                }
            }
        }
    };
	}


