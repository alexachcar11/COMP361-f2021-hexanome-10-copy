package networksrc;
import java.io.*;

public interface Action extends Serializable {

        public abstract boolean isValid();

        public abstract void execute();
}
