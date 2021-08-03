package pt.isec.a2019137625.Connect4.Logica.dados;

import java.io.Serial;
import java.io.Serializable;

public class Coordenada implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int x;
    private final int y;

    public Coordenada(final int x, final int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + x + y + ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordenada that = (Coordenada) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return (x * 31) ^ y;
    }
}
