package ru.geekbrains.lifecycle;

public final class LifeCyclePresenter {

    private static LifeCyclePresenter instance;

    private int counter;

    private LifeCyclePresenter() {
    }

    public void incrementCounter() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }


    public static LifeCyclePresenter getInstance() {
        if (instance == null) instance = new LifeCyclePresenter();
        return instance;
    }
}
