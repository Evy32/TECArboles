import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** Árbol binario de búsqueda que también implementa el TAD Arbolable. */
public class ArbolBinario implements Arbolable {
    private Nodo raiz;

    public void insertar(int dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private Nodo insertarRecursivo(Nodo nodo, int dato) {
        if (nodo == null) return new Nodo(dato);

        int datoActual = (Integer) nodo.dato;
        if (dato < datoActual) nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
        else if (dato > datoActual) nodo.derecho = insertarRecursivo(nodo.derecho, dato);
        return nodo; // No agrega números repetidos.
    }

    @Override
    public int tamanio() {
        return contar(raiz);
    }

    private int contar(Nodo nodo) {
        return nodo == null ? 0 : 1 + contar(nodo.izquierdo) + contar(nodo.derecho);
    }

    @Override
    public boolean estaVacio() {
        return raiz == null;
    }

    @Override
    public void vaciar() {
        raiz = null;
    }

    /** El iterador recorre el árbol en preorden. */
    @Override
    @SuppressWarnings("rawtypes")
    public Iterator iterator() {
        List<Nodo> nodos = new ArrayList<>();
        nodosPreorden(raiz, nodos);
        return nodos.iterator();
    }

    @Override
    public Object padre(Nodo nodo) {
        return buscarPadre(raiz, nodo);
    }

    private Nodo buscarPadre(Nodo actual, Nodo buscado) {
        if (actual == null || actual == buscado) return null;
        if (actual.izquierdo == buscado || actual.derecho == buscado) return actual;
        Nodo encontrado = buscarPadre(actual.izquierdo, buscado);
        return encontrado != null ? encontrado : buscarPadre(actual.derecho, buscado);
    }

    @Override
    public Lista hijos(Nodo nodo) {
        Lista resultado = new Lista();
        if (pertenece(nodo)) {
            if (nodo.izquierdo != null) resultado.add(nodo.izquierdo);
            if (nodo.derecho != null) resultado.add(nodo.derecho);
        }
        return resultado;
    }

    @Override
    public boolean esInterno(Nodo nodo) {
        return pertenece(nodo) && (nodo.izquierdo != null || nodo.derecho != null);
    }

    @Override
    public boolean esHoja(Nodo nodo) {
        return pertenece(nodo) && nodo.izquierdo == null && nodo.derecho == null;
    }

    /** Sustituye el valor de un nodo existente, sin cambiar sus hijos. */
    @Override
    public boolean reemplazar(Nodo actual, Nodo nuevo) {
        if (nuevo == null || !pertenece(actual)) return false;
        actual.dato = nuevo.dato;
        return true;
    }

    private boolean pertenece(Nodo buscado) {
        return contiene(raiz, buscado);
    }

    private boolean contiene(Nodo actual, Nodo buscado) {
        return actual != null && (actual == buscado || contiene(actual.izquierdo, buscado)
                || contiene(actual.derecho, buscado));
    }

    public List<Object> preorden() {
        List<Object> resultado = new ArrayList<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(Nodo nodo, List<Object> resultado) {
        if (nodo != null) {
            resultado.add(nodo.dato);
            preorden(nodo.izquierdo, resultado);
            preorden(nodo.derecho, resultado);
        }
    }

    public List<Object> inorden() {
        List<Object> resultado = new ArrayList<>();
        inorden(raiz, resultado);
        return resultado;
    }

    private void inorden(Nodo nodo, List<Object> resultado) {
        if (nodo != null) {
            inorden(nodo.izquierdo, resultado);
            resultado.add(nodo.dato);
            inorden(nodo.derecho, resultado);
        }
    }

    public List<Object> postorden() {
        List<Object> resultado = new ArrayList<>();
        postorden(raiz, resultado);
        return resultado;
    }

    private void postorden(Nodo nodo, List<Object> resultado) {
        if (nodo != null) {
            postorden(nodo.izquierdo, resultado);
            postorden(nodo.derecho, resultado);
            resultado.add(nodo.dato);
        }
    }

    private void nodosPreorden(Nodo nodo, List<Nodo> resultado) {
        if (nodo != null) {
            resultado.add(nodo);
            nodosPreorden(nodo.izquierdo, resultado);
            nodosPreorden(nodo.derecho, resultado);
        }
    }

    /** Construye un árbol cuando se conocen sus recorridos preorden e inorden. */
    public static ArbolBinario desdeRecorridos(String preorden, String inorden) {
        if (preorden == null || inorden == null || preorden.length() != inorden.length()) {
            throw new IllegalArgumentException("Los recorridos deben tener la misma cantidad de nodos.");
        }
        ArbolBinario arbol = new ArbolBinario();
        int[] posicionPreorden = {0};
        arbol.raiz = construir(preorden, inorden, 0, inorden.length() - 1, posicionPreorden);
        return arbol;
    }

    private static Nodo construir(String preorden, String inorden, int inicio, int fin,
                                  int[] posicionPreorden) {
        if (inicio > fin) return null;
        if (posicionPreorden[0] >= preorden.length()) {
            throw new IllegalArgumentException("Los recorridos no son válidos.");
        }
        char valor = preorden.charAt(posicionPreorden[0]++);
        int posicionInorden = inorden.indexOf(valor, inicio);
        if (posicionInorden < inicio || posicionInorden > fin) {
            throw new IllegalArgumentException("Los recorridos no son válidos.");
        }
        Nodo nodo = new Nodo(valor);
        nodo.izquierdo = construir(preorden, inorden, inicio, posicionInorden - 1, posicionPreorden);
        nodo.derecho = construir(preorden, inorden, posicionInorden + 1, fin, posicionPreorden);
        return nodo;
    }

    /** Da el postorden directamente a partir de preorden e inorden. */
    public static String postordenDesdeRecorridos(String preorden, String inorden) {
        ArbolBinario arbol = desdeRecorridos(preorden, inorden);
        StringBuilder resultado = new StringBuilder();
        for (Object valor : arbol.postorden()) resultado.append(valor);
        return resultado.toString();
    }

    /** Muestra el árbol girado: la rama derecha queda arriba. */
    public void mostrar() {
        mostrar(raiz, "", true);
    }

    private void mostrar(Nodo nodo, String prefijo, boolean esIzquierdo) {
        if (nodo == null) return;
        mostrar(nodo.derecho, prefijo + (esIzquierdo ? "│   " : "    "), false);
        System.out.println(prefijo + (esIzquierdo ? "└── " : "┌── ") + nodo.dato);
        mostrar(nodo.izquierdo, prefijo + (esIzquierdo ? "    " : "│   "), true);
    }
}
