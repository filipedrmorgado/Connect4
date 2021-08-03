package pt.isec.a2019137625.Connect4.Logica.memento;

import java.io.*;

public class Memento implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final byte[] snapshot;

    public Memento(Object obj) throws IOException {
        ByteArrayOutputStream baos;
        ObjectOutputStream oos = null;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            snapshot = baos.toByteArray();
        }finally {
            if(oos!=null){
                oos.close();
            }
        }
    }

    public Object getSnapshot() throws IOException, ClassNotFoundException {
        if (snapshot == null)
            return null;
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(snapshot))) {
            return ois.readObject();
        }
    }
}
