package org.tensorflow.lite.examples.detection.Record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.database.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {

    private ArrayList<Record> mData = null;

    RecordAdapter(ArrayList<Record> list) {
        mData = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.record_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String[] timeSplitList = mData.get(position).getSaveTime().split("_");
        String year = timeSplitList[0];
        String month = timeSplitList[1];
        String day = timeSplitList[2];
        String hour = timeSplitList[3];
        String minute = timeSplitList[4];
        String second = timeSplitList[5];
        holder.tv_index.setText(String.valueOf(position + 1));
        holder.saveTime.setText(year + "년 " + month + "월 " + day + "일 " + hour + "시 " + minute + "분 " + second + "초");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_index;
        TextView saveTime;

        ViewHolder(View itemView) {
            super(itemView);
            tv_index = itemView.findViewById(R.id.tv_index);
            saveTime = itemView.findViewById(R.id.tv_save_time);
        }

    }
}
