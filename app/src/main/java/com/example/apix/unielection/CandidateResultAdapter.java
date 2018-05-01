package com.example.apix.unielection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Apix on 04/09/2017.
 */

public class CandidateResultAdapter extends RecyclerView.Adapter<CandidateResultAdapter.CandidateViewHolder> {

    Context context;
    List<CandidateResult> candidates;

    RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.prof)
            .error(R.drawable.ic_error)
            .fallback(R.drawable.prof);

    public CandidateResultAdapter(Context context,List<CandidateResult> candidates) {
        this.context = context;
        this.candidates = candidates;
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name;
        TextView program;
        TextView yos;
        TextView gender;
        TextView votes;

        public CandidateViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.img);
            name = (TextView)itemView.findViewById(R.id.name);
            program = (TextView)itemView.findViewById(R.id.program);
            yos = (TextView)itemView.findViewById(R.id.yos);
            gender = (TextView)itemView.findViewById(R.id.gender);
            votes = (TextView)itemView.findViewById(R.id.votes);
        }
    }

    @Override
    public CandidateResultAdapter.CandidateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.candidate_result_layout,null);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CandidateResultAdapter.CandidateViewHolder holder, int position) {
        GlideApp.with(context).load("http://192.168.43.125/uni-election/img/candidates/"+candidates.get(position).img_url).apply(requestOptions).into(holder.img);
        holder.name.setText(candidates.get(position).name);
        holder.program.setText(candidates.get(position).program);
        holder.yos.setText("Year Of Study: "+candidates.get(position).yos);
        holder.gender.setText(candidates.get(position).gender);
        holder.votes.setText(String.valueOf(candidates.get(position).votes));
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }
}
