package com.example.blnsft.observers;

import android.arch.lifecycle.Observer;

public interface Observable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
