package com.example.apix.unielection;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;


/**
 * Created by Apix on 28/08/2017.
 */

public class CandidateAdapter extends RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder> {

    List<Candidate> candidates;
    Context context;
    int selectedPosition = -1;

    private SelectedPositionInterface interfaceclick;

    RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.drawable.prof)
            .error(R.drawable.ic_error)
            .fallback(R.drawable.prof);

    public CandidateAdapter(Context context,SelectedPositionInterface interfaceclick,List<Candidate> candidates) {
        super();
        this.candidates = candidates;
        this.context = context;
        this.interfaceclick = interfaceclick;
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView program;
        TextView yos;
        TextView gender;
        ImageView imageView;
        RadioButton radioButton;
        CardView cardView;

        public CandidateViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            program = (TextView)itemView.findViewById(R.id.program);
            yos = (TextView)itemView.findViewById(R.id.yos);
            gender = (TextView)itemView.findViewById(R.id.gender);
            imageView = (ImageView)itemView.findViewById(R.id.profImg);
            radioButton = (RadioButton) itemView.findViewById(R.id.radio_btn);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
        }
    }

    public CandidateAdapter.CandidateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.candidate_layout,null);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CandidateAdapter.CandidateViewHolder holder, final int position) {
        holder.name.setText(candidates.get(position).name);
        holder.program.setText(candidates.get(position).program);
        holder.yos.setText("Year of Study: "+candidates.get(position).yos);
        holder.gender.setText(candidates.get(position).gender);
        holder.radioButton.setChecked(position == selectedPosition);
        GlideApp.with(context).load("http://192.168.43.125/uni-election/img/candidates/"+candidates.get(position).img_url).apply(requestOptions).into(holder.imageView);
        holder.radioButton.setTag(position);
        holder.cardView.setTag(position);
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
                interfaceclick.onItemClick(holder, candidates.get(position).id);
            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCheckChanged(v);
                interfaceclick.onItemClick(holder, candidates.get(position).id);
            }
        });
    }

    private void itemCheckChanged(View v) {
        selectedPosition = (Integer) v.getTag();
        notifyDataSetChanged();
    }

    //Return the selectedPosition item
    public int getSelectedItem() {
        if (selectedPosition != -1) {
//            Toast.makeText(context, "Selected Item : " + selectedPosition, Toast.LENGTH_SHORT).show();
            return selectedPosition;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }


}
