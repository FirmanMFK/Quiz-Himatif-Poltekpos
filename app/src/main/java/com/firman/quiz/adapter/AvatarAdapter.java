package com.firman.quiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firman.quiz.R;
import com.firman.quiz.model.Avatar;
import com.firman.quiz.widget.AvatarView;

/**
 * Created by Firman on 10/26/2016.
 */

public class AvatarAdapter extends BaseAdapter {

    private static final Avatar[] mAvatars = Avatar.values();

    private final LayoutInflater mLayoutInflater;

    public AvatarAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.item_avatar, parent, false);
        }
        setAvatar((AvatarView) convertView, mAvatars[position]);
        return convertView;
    }

    private void setAvatar(AvatarView mIcon, Avatar avatar) {
        mIcon.setImageResource(avatar.getDrawableId());
        mIcon.setContentDescription(avatar.getNameForAccessibility());
    }

    @Override
    public int getCount() {
        return mAvatars.length;
    }

    @Override
    public Object getItem(int position) {
        return mAvatars[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
