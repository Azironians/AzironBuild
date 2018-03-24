package managment.actionManagement.service.components.handleComponet;

public final class IllegalSwitchOffHandleComponentException extends Exception {

    public IllegalSwitchOffHandleComponentException(){
        super();
    }

    public IllegalSwitchOffHandleComponentException(final String message){
        super(message);
    }

    public IllegalSwitchOffHandleComponentException(final Throwable cause){
        super(cause);
    }

    public IllegalSwitchOffHandleComponentException(final String message
            , final Throwable cause){
        super(message, cause);
    }
}