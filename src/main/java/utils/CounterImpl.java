package utils;

public class CounterImpl implements Counter {

    private int counter;

    public CounterImpl() {
        reset();
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public void reset() {
        counter=0;
    }
}
