package com.example.locadoraempinamoto.ui.locacao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LocacaoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LocacaoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}