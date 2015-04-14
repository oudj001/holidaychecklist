package com.example.justinoud.holidaychecklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.justinoud.holidaychecklist.helper.DatabaseHandler;
import com.example.justinoud.holidaychecklist.model.Checklist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DetailsChecklistActivity extends Activity {

    Button savebutton;
    Button deleteButton;
    Button imageButton;
    Button participant;
    EditText title;
    ImageView show_image;
    protected CharSequence[] _options ;
    protected boolean[] _selections;
    boolean isImageFitToScreen;

    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;
    byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checklist);

        savebutton = (Button) findViewById(R.id.save_checklist);
        participant = (Button) findViewById(R.id.participants);
        title = (EditText) findViewById(R.id.EditTextName);
        show_image = (ImageView) findViewById(R.id.show_image);
        imageButton = (Button) findViewById(R.id.addImage);
        deleteButton = (Button) findViewById(R.id.btnDelete);


        Uri queryUri = ContactsContract.Contacts.CONTENT_URI;

        String[] projection = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME};

        String selection = ContactsContract.Contacts.DISPLAY_NAME + " IS NOT NULL";

        final CursorLoader cursorLoader = new CursorLoader(this,
                queryUri,
                projection,
                selection,
                null,
                null);
        List<String> contacts = new ArrayList<String>();
        Cursor cursor = cursorLoader.loadInBackground();
        if (cursor.moveToFirst()) {
            do {
                String contact = cursor.getString(0);
                System.out.println(contact);
                contacts.add(contact);

            } while (cursor.moveToNext());
        }
        _options = contacts.toArray(new CharSequence[contacts.size()]);
        _selections = new boolean[_options.length];

        // If id is added to activity we use the edit function
        if (this.getIntent().hasExtra("id")) {

            DatabaseHandler db = new DatabaseHandler(this);
            Bundle extras = getIntent().getExtras();

            final int id = extras.getInt("id");
            final Checklist checklist = db.getChecklist(id);

            title.setText(checklist.getTitle());
            participant.setText(checklist.getParticipant().toString());
            imageButton.setVisibility(View.INVISIBLE);

            image = checklist.getImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
            Bitmap ImageBitmap = BitmapFactory.decodeStream(imageStream);
            show_image.setImageBitmap(ImageBitmap);
            savebutton.setText("Edit this checklist!");

            savebutton.setOnClickListener(
                    new View.OnClickListener()
                    {
                        public void onClick(View view) {
                            if (title.getText() != null && participant.getText() != null) {
                                DatabaseHandler db = new DatabaseHandler(view.getContext());

                                checklist.setImage(image);
                                checklist.setTitle(title.getText().toString());
                                checklist.setParticipant(participant.getText());
                                db.UpdateChecklist(checklist);
                            }
                            Intent intentChecklist = new Intent(DetailsChecklistActivity.this, ChecklistActivity.class);
                            startActivity(intentChecklist);
                        }
                    });

        } else {

            deleteButton.setVisibility(View.INVISIBLE);
            Bitmap tempImage = (Bitmap) getIntent().getParcelableExtra("image");

            if (tempImage != null) {
                show_image.setImageBitmap(tempImage);
                deleteButton.setVisibility(View.VISIBLE);
                imageButton.setVisibility(View.INVISIBLE);
                title.setText(getIntent().getStringExtra("title"));
                participant.setText(getIntent().getStringExtra("participants"));
            }

            savebutton.setOnClickListener(
                    new View.OnClickListener() {
                        public void onClick(View view) {
                            if (title.getText() != null && participant.getText() != null) {
                                DatabaseHandler db = new DatabaseHandler(view.getContext());
                                Checklist checklist = new Checklist();
                                byte[] newImage = getIntent().getByteArrayExtra("checklistImage");
                                checklist.setImage(newImage);
                                checklist.setTitle(title.getText().toString());
                                checklist.setParticipant(participant.getText());
                                db.addChecklist(checklist);
                            }
                            Intent intentChecklist = new Intent(DetailsChecklistActivity.this, ChecklistActivity.class);
                            startActivity(intentChecklist);
                        }
                    });

        }


        final String[] option = new String[]{"Take from Camera",
                "Select from Gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
                }

            }
        });
        final AlertDialog dialog = builder.create();

        imageButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.show();
                    }
                }
        );
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        show_image.setImageDrawable(null);
                        deleteButton.setVisibility(View.INVISIBLE);
                        getIntent().removeExtra("checklistImage");
                        show_image.setImageDrawable(getResources().getDrawable(R.drawable.defaultplaceholder));
                        imageButton.setVisibility(View.VISIBLE);
                    }
                }
        );
        participant.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        showDialog(0);
                    }
                });



    }
        /**
         * On activity result
         */

        @Override
        protected Dialog onCreateDialog( int id )
        {
            return new AlertDialog.Builder( this )
                    .setTitle( "Kies de deelnemers" )
                    .setMultiChoiceItems( _options, _selections, new DialogSelectionClickHandler() )
                    .setPositiveButton( "OK", new DialogButtonClickHandler() )
                    .create();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode != RESULT_OK)
                return;

            switch (requestCode) {
                case CAMERA_REQUEST:

                    Bundle extras = data.getExtras();
                    System.out.println(extras);
                    if (extras != null) {
                        Bitmap yourImage = extras.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte imageInByte[] = stream.toByteArray();


                        Intent i = new Intent(DetailsChecklistActivity.this,
                                DetailsChecklistActivity.class);
                        i.putExtra("checklistImage",imageInByte);
                        i.putExtra("image", yourImage);
                        i.putExtra("participants", extras.getString("participants"));
                        i.putExtra("title", extras.getString("title"));
                        startActivity(i);
                        finish();

                    }
                    break;
                case PICK_FROM_GALLERY:
                    Bundle extras2 = data.getExtras();

                    if (extras2 != null) {
                        Bitmap yourImage = extras2.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte imageInByte[] = stream.toByteArray();


                        Intent i = new Intent(DetailsChecklistActivity.this,
                                DetailsChecklistActivity.class);
                        i.putExtra("checklistImage",imageInByte);
                        i.putExtra("image", yourImage);
                        i.putExtra("participants", extras2.getString("participant"));
                        i.putExtra("title", extras2.getString("title"));
                        startActivity(i);
                        finish();
                    }

                    break;
            }
        }

        /**
         * open camera method
         */
        public void callCamera() {
            Bundle extra3 = this.getIntent().getExtras();
            Intent cameraIntent = new Intent(
                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra("crop", "true");
            cameraIntent.putExtra("aspectX", 0);
            cameraIntent.putExtra("aspectY", 0);
            cameraIntent.putExtra("outputX", 200);
            cameraIntent.putExtra("outputY", 150);
            cameraIntent.putExtra("title", title.getText());
            cameraIntent.putExtra("participants", participant.getText());
            startActivityForResult(cameraIntent, CAMERA_REQUEST);

        }

        /**
         * open gallery method
         */

        public void callGallery() {
            Bundle extra3 = this.getIntent().getExtras();
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 0);
            intent.putExtra("aspectY", 0);
            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 150);
            intent.putExtra("return-data", true);
            intent.putExtra("title", title.getText());
            intent.putExtra("participants", participant.getText());
            startActivityForResult(
                    Intent.createChooser(intent, "Complete action using"),
                    PICK_FROM_GALLERY);

        }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_normal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {
            case R.id.menu_checklists:
                Intent intentAddChecklist = new Intent(DetailsChecklistActivity.this, ChecklistActivity.class);
                startActivity(intentAddChecklist);
                return true;
            case R.id.menu_friends:
                Intent intentFriends = new Intent(DetailsChecklistActivity.this, FriendsActivity.class);
                startActivity(intentFriends);
                return true;
            case R.id.menu_info:
                Intent intentInfo = new Intent(DetailsChecklistActivity.this, InfoActivity.class);
                startActivity(intentInfo);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public class DialogSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener
    {
        public void onClick( DialogInterface dialog, int clicked, boolean selected )
        {

        }
    }

    public class DialogButtonClickHandler implements DialogInterface.OnClickListener
    {
        public void onClick( DialogInterface dialog, int clicked ) {
            switch( clicked ) {
                case DialogInterface.BUTTON_POSITIVE:
                    printSelectedParticipants();
                    break;
            }
        }
    }
    protected void printSelectedParticipants()
    {
        List<String> contactSelected = new ArrayList<String>();
        for( int i = 0; i < _options.length; i++ ){

            if (_selections[i] == true) {
                contactSelected.add(_options[i].toString());
            }
        }
        String listString = "";
        int i = 1;
        for (String s : contactSelected )
        {
            if (i++ == contactSelected.size()) {
                listString += s + "";
            } else {
                listString += s + ", ";
            }
        }

        participant.setText(listString);

    }
}
