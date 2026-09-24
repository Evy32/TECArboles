/** Representa cada elemento dentro del árbol. */
public class Nodo {
    Object dato;
    Nodo izquierdo;
    Nodo derecho;

    public Nodo(Object dato) {
        this.dato = dato;
    }

    public Object getDato() {
        return dato;
    }
}
