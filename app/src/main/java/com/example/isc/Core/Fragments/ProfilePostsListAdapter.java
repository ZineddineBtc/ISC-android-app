package com.example.isc.Core.Fragments;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.isc.Core.EditPostActivity;
import com.example.isc.Core.MyPost;
import com.example.isc.R;

import java.util.List;


public class ProfilePostsListAdapter extends ArrayAdapter<MyPost> {

    private int resourceLayout;
    private Context mContext;
    private MyPost post;
    private String text;
    private boolean textDisplayedAll;
    private TextView posterName, posterPosition, postedText, postEvents;
    private ImageView posterProfileImage, postedImage;
    private ImageButton postLevelButton, tagColleagueButton, editProfilePostIB;
    private LinearLayout posterProfileLayout;

    public ProfilePostsListAdapter(Context context, int resource, List<MyPost> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
            PAViewHolder holder = createViewHolderFrom(v);
            v.setTag(holder);
        }

        final PAViewHolder holder = (PAViewHolder) v.getTag();

        post = getItem(position);

        if (post != null) {
            if (posterName != null) {
                holder.posterName.setText(post.getMyUser().getFullName());
            }
            if (posterPosition != null) {
                holder.posterPosition.setText(post.getMyUser().getPosition());
            }
            if (postedText != null) {
                if(post.getPostedText().length()>100){
                    text = post.getPostedText().substring(0, 99) +"... "+ Html.fromHtml("<font font-weight=bold> See more </font>");
                    textDisplayedAll = false;
                    holder.postedText.setText(text);
                }else{
                    holder.postedText.setText(post.getPostedText());
                    textDisplayedAll = true;
                }
                holder.postedText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(textDisplayedAll){
                            holder.postedText.setText(text);
                            textDisplayedAll = false;
                        }else{
                            holder.postedText.setText(post.getPostedText());
                            textDisplayedAll = true;
                        }
                    }
                });
            }
            if (posterProfileImage != null) {
                holder.posterProfileImage.setImageResource(post.getMyUser().getProfileImage());
                holder.posterProfileImage.setDrawingCacheEnabled(true);
            }
            if (postedImage != null) {
                holder.postedImage.setImageResource(post.getPostedImage());
                holder.postedImage.setDrawingCacheEnabled(true);
            }
            if (postLevelButton != null) {
                holder.postLevelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pl = "Not specified";
                        if (post.getMyPostLevel() != null) {
                            pl = post.getMyPostLevel();
                        }
                        new AlertDialog.Builder(getContext())
                                .setTitle("Your post is visible to the departments of:")
                                .setMessage(pl)
                                .setPositiveButton("OK", null)
                                .setNegativeButton(null, null)
                                .show();
                    }
                });
            }
            if (tagColleagueButton != null) {
                holder.tagColleagueButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tc = "Not specified";
                        if (post.getMyPostTagColleague() != null) {
                            tc = post.getMyPostTagColleague();
                        }
                        new AlertDialog.Builder(getContext())
                                .setTitle("Your post is visible to the departments of:")
                                .setMessage(tc)
                                .setPositiveButton("OK", null)
                                .setNegativeButton(null, null)
                                .show();
                    }
                });
            }
            if(editProfilePostIB != null){
                editProfilePostIB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(getContext(), v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.post_edit, popup.getMenu());
                        popup.show();
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.edit:
                                        Intent intent = new Intent(getContext(), EditPostActivity.class);
                                        intent.putExtra("position", Integer.toString(position));
                                        getContext().startActivity(intent);
                                        break;
                                    case R.id.delete:
                                        Toast.makeText(getContext(), "delete item "+position, Toast.LENGTH_SHORT).show();
                                        break;
                                }

                                return false;
                            }
                        });
                    }
                });
            }
            if(postEvents != null){
                postEvents.setText(post.getMyPostEvents());
            }
        }
        return v;
    }
    private PAViewHolder createViewHolderFrom(View view) {
        posterName = view.findViewById(R.id.posterName);
        posterPosition = view.findViewById(R.id.posterPosition);
        posterProfileImage = view.findViewById(R.id.posterProfileImage);
        postedText = view.findViewById(R.id.postedText);
        postedImage = view.findViewById(R.id.postedImage);
        postLevelButton = view.findViewById(R.id.postLevelButtonHomeAdapter);
        tagColleagueButton = view.findViewById(R.id.tagColleagueButtonHomeAdapter);
        posterProfileLayout = view.findViewById(R.id.posterProfileLayout);
        editProfilePostIB = view.findViewById(R.id.editProfilePostIB);
        postEvents = view.findViewById(R.id.postEvents);

        return new PAViewHolder(posterName, posterPosition, posterProfileImage,
                postedText, postedImage, postLevelButton, tagColleagueButton,
                editProfilePostIB, posterProfileLayout, postEvents);
    }
}

class PAViewHolder {
    final TextView posterName, posterPosition, postedText, postEvents;
    final ImageView posterProfileImage, postedImage;
    final ImageButton postLevelButton, tagColleagueButton, editProfilePostIB;
    final LinearLayout posterProfileLayout;

    PAViewHolder(TextView posterName, TextView posterPosition, ImageView posterProfileImage,
                  TextView postedText, ImageView postedImage,
                  ImageButton postLevelButton, ImageButton tagColleagueButton,
                  ImageButton editProfilePostIB, LinearLayout posterProfileLayout,
                 TextView postEvents) {
        this.posterName = posterName;
        this.posterPosition = posterPosition;
        this.postedText = postedText;
        this.posterProfileImage = posterProfileImage;
        this.postedImage = postedImage;
        this.postLevelButton = postLevelButton;
        this.tagColleagueButton = tagColleagueButton;
        this.editProfilePostIB = editProfilePostIB;
        this.posterProfileLayout = posterProfileLayout;
        this.postEvents = postEvents;
    }
}
