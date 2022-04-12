package networksrc;

import java.io.*;

public interface Action extends Serializable {
        public boolean isValid();

        public void execute();
}
