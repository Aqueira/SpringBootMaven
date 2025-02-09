package com.example.contracts;

public interface Validator<T, U> {
    public Boolean validate(T firstEntityID, U secondEntityID);
}
