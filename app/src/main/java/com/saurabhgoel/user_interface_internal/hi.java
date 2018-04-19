package com.saurabhgoel.user_interface_internal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class hi extends RecyclerView.Adapter<hi.myholder>{
    private Context mContext;

    List<String> extension,duration,distance;
    hi(Context mContext, List ext,List dur,List dist)
    {
        this.mContext = mContext;

        extension =ext;
        duration= dur;
        distance = dist;

    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_hi,parent,false);

        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(myholder holder, final int position) {


       holder.txt2.setText(extension.get(position));
       holder.txt.setText(duration.get(position));
       holder.txt.setText(distance.get(position));


    }



    @Override
    public int getItemCount() {
        return extension.size();
    }
    public  static  class myholder extends RecyclerView.ViewHolder {

        TextView txt,txt2,txt3;

        public myholder(View itemView) {
            super(itemView);

            txt = (TextView) itemView.findViewById(R.id.duration);
            txt2 = (TextView) itemView.findViewById(R.id.turns);
            txt3 = (TextView) itemView.findViewById(R.id.distance);



        }
    }

}
