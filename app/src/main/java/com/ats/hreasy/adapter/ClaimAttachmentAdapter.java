package com.ats.hreasy.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.model.ClaimProofList;
import com.ats.hreasy.model.ClaimTrailstatus;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ClaimAttachmentAdapter extends RecyclerView.Adapter<ClaimAttachmentAdapter.MyViewHolder> {

    private ArrayList<ClaimProofList> msgList;
    private Context context;

    public ClaimAttachmentAdapter(ArrayList<ClaimProofList> msgList, Context context) {
        this.msgList = msgList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivPhoto;

        public MyViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.ivPhoto);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_claim_attachment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ClaimProofList model = msgList.get(position);

        String imageUri = String.valueOf(model.getCpDocPath());
        try {
            Picasso.with(context).load(Constants.IMAGE_URL + "" + imageUri).placeholder(context.getResources().getDrawable(R.drawable.img_default)).into(holder.ivPhoto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + Constants.DOC_URL + model.getCpDocPath()));
                    context.startActivity(browserIntent);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
