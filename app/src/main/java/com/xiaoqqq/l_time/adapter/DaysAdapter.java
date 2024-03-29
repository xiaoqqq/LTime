package com.xiaoqqq.l_time.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.utils.DateUtils;

import java.util.ArrayList;

public class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<DateBean.DataContentBean> mDatas;

    public DaysAdapter(Context context, ArrayList<DateBean.DataContentBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycleview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayName.setText(mDatas.get(position).getDate_name());
        holder.dayDate.setText(DateUtils.stampToDate(mDatas.get(position).getDate_timestamp()));
        int days = DateUtils.getDaysByMillions(DateUtils.dateToStamp(DateUtils.stampToDate(mDatas.get(position).getDate_timestamp())), System.currentTimeMillis());
        holder.dayNum.setText(days + "天");
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnCardViewLongClickListener.onCardViewLongClicked(holder,position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayName;
        TextView dayDate;
        TextView dayNum;

        ImageView itemSelected;

        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.item_tv_day_name);
            dayDate = itemView.findViewById(R.id.item_tv_day_date);
            dayNum = itemView.findViewById(R.id.item_tv_day_number);
            mCardView = itemView.findViewById(R.id.item_card_view);
            itemSelected = itemView.findViewById(R.id.item_iv_selected);
        }
    }

    private onCardViewLongClickListener mOnCardViewLongClickListener;

    public void setOnCardViewLongClickListener(onCardViewLongClickListener onCardViewLongClickListener) {
        mOnCardViewLongClickListener = onCardViewLongClickListener;
    }

    public interface onCardViewLongClickListener {
        void onCardViewLongClicked(ViewHolder holder, int position);
    }
}
