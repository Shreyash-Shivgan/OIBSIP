package com.oibsip.stopwatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

/**
 * RecyclerView adapter for binding recorded stopwatch laps.
 */
public class LapAdapter extends RecyclerView.Adapter<LapAdapter.LapViewHolder> {

    private final List<LapModel> lapList;

    public LapAdapter(List<LapModel> lapList) {
        this.lapList = lapList;
    }

    @NonNull
    @Override
    public LapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lap, parent, false);
        return new LapViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LapViewHolder holder, int position) {
        LapModel lap = lapList.get(position);
        holder.bind(lap);
    }

    @Override
    public int getItemCount() {
        return lapList.size();
    }

    public static class LapViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvLapNumber;
        private final TextView tvLapDuration;
        private final TextView tvCumulativeTime;

        public LapViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLapNumber = itemView.findViewById(R.id.tvLapNumber);
            tvLapDuration = itemView.findViewById(R.id.tvLapDuration);
            tvCumulativeTime = itemView.findViewById(R.id.tvCumulativeTime);
        }

        public void bind(LapModel lap) {
            tvLapNumber.setText(String.format(Locale.US, "Lap %d", lap.getLapNumber()));
            tvLapDuration.setText(lap.getLapDuration());
            tvCumulativeTime.setText(lap.getCumulativeTime());
        }
    }
}
