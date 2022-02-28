package networksrc;
import java.io.*;

public abstract class Action implements Serializable {
        private int gameID;

        public abstract boolean isValid();

        public abstract void execute();
}
