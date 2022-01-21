package fr.ensimag.deca.deca_runner;

public class DecaResult<T> {
    private T value;

    public DecaResult(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Value : " + this.value;
    }
}
