package networksrc;

import java.io.*;

import org.minueto.MinuetoFileException;

public interface Action extends Serializable {
        public boolean isValid();

        public void execute() throws MinuetoFileException;
}
