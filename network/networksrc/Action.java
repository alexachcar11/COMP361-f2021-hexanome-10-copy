package networksrc;

import java.io.*;

public interface Action extends Serializable {
        public default boolean isValid() {
                return true;
        }

        public void execute();
}
