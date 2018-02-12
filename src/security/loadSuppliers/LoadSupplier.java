package security.loadSuppliers;

import gui.endavour.Endeavour;

public interface LoadSupplier<T> {

    Endeavour readData(final String login, final String password);

    void sendData();

    void writeData();

    T get();

    void setData(final T t);
}
