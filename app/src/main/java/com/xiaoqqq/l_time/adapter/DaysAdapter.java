package com.xiaoqqq.l_time.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.utils.ToastUtils;

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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayName.setText(mDatas.get(position).getDate_name());
        holder.dayDate.setText(mDatas.get(position).getDate_timestamp());
        holder.dayNum.setText("110天");
        holder.mCardView.setOnClickListener(v -> {
            ToastUtils.getInstance().customToast(mContext, "再按一次退出");
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

        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.item_tv_day_name);
            dayDate = itemView.findViewById(R.id.item_tv_day_date);
            dayNum = itemView.findViewById(R.id.item_tv_day_number);
            mCardView = itemView.findViewById(R.id.item_card_view);
        }
    }
}
