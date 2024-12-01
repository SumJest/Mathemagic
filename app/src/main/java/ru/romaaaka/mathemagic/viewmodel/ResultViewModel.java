package ru.romaaaka.mathemagic.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import java.util.List;

import ru.romaaaka.mathemagic.model.ResultsManager;

public class ResultViewModel extends ViewModel {
    private final ResultsManager resultsManager;
    private final MutableLiveData<List<String>> topResults = new MutableLiveData<>();

    public ResultViewModel(Context context) {
        resultsManager = new ResultsManager(context);
        loadTopResults();
    }

    public void loadTopResults() {
        topResults.setValue(resultsManager.getTopResults());
    }

    public LiveData<List<String>> getTopResults() {
        return topResults;
    }

    public void saveResult(String playerName, int score) {
        resultsManager.saveResult(playerName, score);
        loadTopResults();
    }
}

