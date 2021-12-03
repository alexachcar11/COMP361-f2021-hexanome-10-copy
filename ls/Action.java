import java.io.Serializable;

public abstract class Action implements Serializable {
    // TODO: add interface methods
    public abstract boolean isValid();

    public abstract void execute();
}
