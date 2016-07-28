package com.hx.jrperson.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hx.jrperson.R;
import com.hx.jrperson.bean.entity.ServiceThreeEntity;

import java.util.List;

/**
 * 发布订单 价格选择列表适配器
 * Created by ge on 2016/3/4.
 */
public class IssueOrdorGutAdapter extends BaseAdapter{

    private int before;
    private double allPrice;
    private Context context;
    private List<ServiceThreeEntity.DataMapBean.ServicesBean> list;
    ///////////////////////////////////////
    private int numBus=0;//设置计数器


    public IssueOrdorGutAdapter(Context context) {
        super();
        this.context = context;
    }

    public void addData(List<ServiceThreeEntity.DataMapBean.ServicesBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_issue_ordor, null);
            viewHolder = new ViewHolder();
            viewHolder.addAndSubTextView = (TextView) convertView.findViewById(R.id.addAndSubTextView);
          //  viewHolder.ordor_subjectTV = (TextView) convertView.findViewById(R.id.ordor_subjectTV);
            viewHolder.unitPriceTV = (TextView) convertView.findViewById(R.id.unitPriceTV);
            viewHolder.issue_addIB = (ImageButton) convertView.findViewById(R.id.issue_addIB);//加号
            viewHolder.issue_subIB = (ImageButton) convertView.findViewById(R.id.issue_subIB);//减号
            //////////////////////////////////////////////////////
            viewHolder.classificationName= (TextView) convertView.findViewById(R.id.classificationName);
             viewHolder.serviceGutTVs= (TextView) convertView.findViewById(R.id.subject_nameTVs);
            viewHolder.giveUsDetil= (ImageView) convertView.findViewById(R.id.giveUsDetil);
           viewHolder.subject_nameTVs= (TextView) convertView.findViewById(R.id.subject_nameTVs);
            //////////////  ////////      /////////////////
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (list != null){
            ServiceThreeEntity.DataMapBean.ServicesBean bean = list.get(position);

            double minPriceD = bean.getPrice_min();
            String minPrice= String.format("%.2f", minPriceD);
//            double maxPriceD = bean.getPrice_max();
//            String maxPrice= String.format("%.2f", maxPriceD);
            viewHolder.unitPriceTV.setText(minPrice + " " + bean.getUnit());
            before = bean.getBeforCount();
            viewHolder.addAndSubTextView.setText(before + "");
            ///////////////////////////////////////////////////////////////////
            viewHolder.classificationName.setText(list.get(position).getService());
            viewHolder.serviceGutTVs.setText(list.get(position).getDecription());
            final ViewHolder finalViewHolder = viewHolder;
            viewHolder.giveUsDetil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (numBus==0){
                    finalViewHolder.subject_nameTVs.setVisibility(View.VISIBLE);
                        numBus=1;
                    }else{
                        finalViewHolder.subject_nameTVs.setVisibility(View.GONE);
                        numBus=0;
                    }

                }
            });
            /////////////////////////////////////////////////
        }
        viewHolder.issue_addIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allPrice = list.get(position).getPrice_min();
                before = list.get(position).getBeforCount();
                if (before < 99){
                    onClickIssueOrdorListener.onClickIssueOrdor(v, allPrice, position, before);
                }
            }
        });
        viewHolder.issue_subIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                before = list.get(position).getBeforCount();
                if (before >= 1){
                    allPrice = -list.get(position).getPrice_min();
                    onClickIssueOrdorListener.onClickIssueOrdor(v, allPrice, position, before);
                }
            }
        });
        return convertView;
    }

    private OnClickIssueOrdorListener onClickIssueOrdorListener;

    public void setOnClickIssueOrdorListener(OnClickIssueOrdorListener onClickIssueOrdorListener){
        this.onClickIssueOrdorListener = onClickIssueOrdorListener;
    }

    public interface OnClickIssueOrdorListener{
        void onClickIssueOrdor(View v, double all, int position, int befor);
    }


    class ViewHolder{

        private TextView addAndSubTextView;
        private TextView ordor_subjectTV, unitPriceTV;
        private ImageButton issue_addIB, issue_subIB;
        ///////////////////////////
        private TextView classificationName,serviceGutTVs,subject_nameTVs;
        private ImageView giveUsDetil;


        public ViewHolder getHolder(View view){
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            return viewHolder;
        }

    }


}
