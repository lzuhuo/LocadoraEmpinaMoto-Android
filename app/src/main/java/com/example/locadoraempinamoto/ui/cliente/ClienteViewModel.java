package com.example.locadoraempinamoto.ui.cliente;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClienteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClienteViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}