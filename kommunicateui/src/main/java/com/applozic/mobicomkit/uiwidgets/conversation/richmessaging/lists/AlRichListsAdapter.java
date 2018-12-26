package com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.lists;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applozic.mobicomkit.uiwidgets.R;
import com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.ALRichMessageListener;
import com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.ALRichMessageModel;
import com.applozic.mobicomkit.uiwidgets.conversation.richmessaging.AlRichMessage;
import com.bumptech.glide.Glide;

import java.util.List;

public class AlRichListsAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ALRichMessageModel.AlElementModel> elementList;
    ALRichMessageListener messageListener;

    public AlRichListsAdapter(Context context, List<ALRichMessageModel.AlElementModel> elementList, ALRichMessageListener messageListener) {
        this.context = context;
        this.elementList = elementList;
        this.messageListener = messageListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.al_rich_list_item, parent, false);
        return new AlListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        bindView((AlListItemViewHolder) holder, elementList.get(position));
    }

    public void bindView(AlListItemViewHolder holder, ALRichMessageModel.AlElementModel element) {
        if (!TextUtils.isEmpty(element.getTitle())) {
            holder.headerTv.setVisibility(View.VISIBLE);
            holder.headerTv.setText(element.getTitle().trim());
        } else {
            holder.headerTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(element.getDescription().trim())) {
            holder.detailsTv.setVisibility(View.VISIBLE);
            holder.detailsTv.setText(element.getDescription());
        } else {
            holder.detailsTv.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(element.getImgSrc())) {
            holder.listImage.setVisibility(View.VISIBLE);
            Glide.with(context).load(element.getImgSrc()).into(holder.listImage);
        } else {
            holder.listImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return elementList != null ? elementList.size() : 0;
    }

    private class AlListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView headerTv;
        private TextView detailsTv;
        private RelativeLayout rootLayout;
        private ImageView listImage;

        public AlListItemViewHolder(View itemView) {
            super(itemView);

            headerTv = itemView.findViewById(R.id.listItemHeaderText);
            detailsTv = itemView.findViewById(R.id.listItemText);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            listImage = itemView.findViewById(R.id.listItemImage);

            rootLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = this.getLayoutPosition();
            if (itemPosition != -1 && elementList != null && !elementList.isEmpty()) {
                if (context.getApplicationContext() instanceof ALRichMessageListener) {
                    ((ALRichMessageListener) context.getApplicationContext()).onAction(context, AlRichMessage.LIST_ITEM_CLICK, null, elementList.get(itemPosition));
                } else if (messageListener != null) {
                    messageListener.onAction(context, AlRichMessage.LIST_ITEM_CLICK, null, elementList.get(itemPosition));
                }
            }
        }
    }
}