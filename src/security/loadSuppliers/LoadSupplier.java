package security.loadSuppliers;

import gui.endavour.Endeavour;

public interface LoadSupplier {

    Endeavour readData(final String login, final String password);

    void sendData();

    void writeData();
}
