package com.example.communnect;

public interface FirebaseCallback<T> {
    void onCallback(T data);
}