import java.util.Iterator;

/** Operaciones básicas que debe ofrecer un árbol. */
public interface Arbolable {
    int tamanio();
    boolean estaVacio();
    void vaciar();
    @SuppressWarnings("rawtypes")
    Iterator iterator();
    Object padre(Nodo nodo);
    Lista hijos(Nodo nodo);
    boolean esInterno(Nodo nodo);
    boolean esHoja(Nodo nodo);
    boolean reemplazar(Nodo actual, Nodo nuevo);
}
