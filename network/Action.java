import java.io.Serializable;

public abstract class Action implements Serializable {
        // TODO: add interface methods
        public abstract boolean isValid();

        public abstract void execute();

        private void writeObject(java.io.ObjectOutputStream out)
                        throws IOException {
        }

        private void readObject(java.io.ObjectInputStream in)
                        throws IOException, ClassNotFoundException {
        }

        private void readObjectNoData()
                        throws ObjectStreamException {
        }
}
