package com.seatrend.routinginspection.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.seatrend.routinginspection.R;
import com.seatrend.routinginspection.base.Constants;
import com.seatrend.routinginspection.db.DbUtils;
import com.seatrend.routinginspection.entity.PLANEntity;
import com.seatrend.routinginspection.ui.ShowPictureActivity;
import com.seatrend.routinginspection.ui.no_network.N_InspectActivity;
import com.seatrend.routinginspection.utils.JHStateUtils;
import com.seatrend.routinginspection.utils.LogUtil;
import com.seatrend.routinginspection.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ljm
 */
public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHolder> {

    private List<PLANEntity.DataBean.TASK> mList = new ArrayList<>();
    private Context mContext;

    public SlideAdapter(Context context) {
        mContext = context;
    }

    public void setData(ArrayList<PLANEntity.DataBean.TASK> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SlideAdapter.SlideViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.recycle_item_slide_menu, viewGroup, false);
        return new SlideViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final SlideAdapter.SlideViewHolder slideViewHolder, final int position) {
        slideViewHolder.initView(mList.get(position));
        if (!slideViewHolder.mDeleteTv.hasOnClickListeners()) {
            slideViewHolder.mDeleteTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbUtils.getInstance(mContext).deleteDbPlanJudgePicture(mList.get(position).getJhbh());//删除数据库

                    mList.remove(slideViewHolder.getAdapterPosition());
                    notifyItemRemoved(slideViewHolder.getAdapterPosition());
                    LogUtil.Companion.d(position);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder {
        private TextView mDeleteTv;
        private TextView tv_station_time;
        private TextView sName;
        private TextView sState;
        private LinearLayout ll_item;

        private SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            mDeleteTv = itemView.findViewById(R.id.tv_delete);
            sName = itemView.findViewById(R.id.tv_station_name);
            sState = itemView.findViewById(R.id.tv_state);
            tv_station_time = itemView.findViewById(R.id.tv_station_time);
            ll_item = itemView.findViewById(R.id.ll_item);

                ll_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, N_InspectActivity.class);
                        intent.putExtra(Constants.Companion.getJHBH(), mList.get(getAdapterPosition()).getJhbh());
                        intent.putExtra(Constants.Companion.getJHZT(), mList.get(getAdapterPosition()).getJhzt());
                        intent.putExtra(Constants.Companion.getGLBM(), mList.get(getAdapterPosition()).getGlbm());
                        mContext.startActivity(intent);
                    }
                });
        }


        public void initView(PLANEntity.DataBean.TASK task) {

            sName.setText(task.getBmmc());
            sState.setText(JHStateUtils.getJhState(task.getJhzt()));
            tv_station_time.setText(StringUtils.longToStringDataNoHour(task.getXzrq()));

            if ("2".equals(task.getJhzt())) {
                sState.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            } else {
                sState.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            }
        }
    }
}