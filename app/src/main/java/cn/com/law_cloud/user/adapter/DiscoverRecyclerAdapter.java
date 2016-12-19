package cn.com.law_cloud.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.law_cloud.user.R;
import cn.com.law_cloud.user.entity.DiscoverRecyclerViewMemeber;

/**
 * Created by 郑成裕 on 2016/12/16.
 */

public class DiscoverRecyclerAdapter extends RecyclerView.Adapter<DiscoverRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private DiscoverRecyclerViewMemeber mDiscoverRecyclerViewMemeber = new DiscoverRecyclerViewMemeber();
    private LayoutInflater mLayoutInflater;

    public DiscoverRecyclerAdapter(Context mContext, DiscoverRecyclerViewMemeber mDiscoverRecyclerViewMemeber) {
        this.mContext = mContext;
        this.mDiscoverRecyclerViewMemeber = mDiscoverRecyclerViewMemeber;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = mLayoutInflater.inflate(R.layout.item_discover_recyclerview, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView_discoverRecyclerView.setImageResource(mDiscoverRecyclerViewMemeber.imagesId[position]);
        holder.textView_title_discoverRecyclerView.setText(mDiscoverRecyclerViewMemeber.listTitle.get(position));
        holder.textView_content_discoverRecyclerView.setText(mDiscoverRecyclerViewMemeber.listContent.get(position));
    }


    @Override
    public int getItemCount() {
        return mDiscoverRecyclerViewMemeber.listTitle.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_discoverRecyclerView;
        TextView textView_title_discoverRecyclerView;
        TextView textView_content_discoverRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView_discoverRecyclerView = (ImageView) itemView.findViewById(R.id.imageView_discoverRecyclerView);
            textView_title_discoverRecyclerView = (TextView) itemView.findViewById(R.id.textView_title_discoverRecyclerView);
            textView_content_discoverRecyclerView = (TextView) itemView.findViewById(R.id.textView_content_discoverRecyclerView);
        }
    }
}
