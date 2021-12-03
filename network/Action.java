import java.io.*;

public abstract class Action implements Serializable {
        public abstract boolean isValid();

        public abstract void execute();
}
