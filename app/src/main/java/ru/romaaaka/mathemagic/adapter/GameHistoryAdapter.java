package ru.romaaaka.mathemagic.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import ru.romaaaka.mathemagic.R;
import ru.romaaaka.mathemagic.database.GameRecord;

public class GameHistoryAdapter extends RecyclerView.Adapter<GameHistoryAdapter.GameHistoryViewHolder> {

    private List<GameRecord> gameHistory;

    public void setGameHistory(List<GameRecord> gameHistory) {
        this.gameHistory = gameHistory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_game_history, parent, false);
        return new GameHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHistoryViewHolder holder, int position) {
        GameRecord gameRecord = gameHistory.get(position);
        holder.difficultyTextView.setText("Difficulty: " + gameRecord.difficulty);
        holder.scoreTextView.setText("Score: " + gameRecord.score);
        holder.errorsTextView.setText("Mistakes: " + gameRecord.errors);
    }

    @Override
    public int getItemCount() {
        return gameHistory != null ? gameHistory.size() : 0;
    }

    public static class GameHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView difficultyTextView, scoreTextView, errorsTextView;

        public GameHistoryViewHolder(View itemView) {
            super(itemView);
            difficultyTextView = itemView.findViewById(R.id.difficultyTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
            errorsTextView = itemView.findViewById(R.id.errorsTextView);
        }
    }
}
