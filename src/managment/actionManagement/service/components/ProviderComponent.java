package managment.actionManagement.service.components;

public interface ProviderComponent<T> {

    T getValue();

    int getPriority();

    void setPriority(final int priority);


}
