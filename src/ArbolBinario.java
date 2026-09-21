import java.util.ArrayList;
import java.util.List;

/** Contiene la lógica de inserción, recorridos e impresión del árbol. */
public class ArbolBinario {
    private Nodo raiz;

    public void insertar(int dato) {
        raiz = insertarRecursivo(raiz, dato);
    }

    private Nodo insertarRecursivo(Nodo nodo, int dato) {
        if (nodo == null) return new Nodo(dato);

        if (dato < nodo.dato) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, dato);
        } else if (dato > nodo.dato) {
            nodo.derecho = insertarRecursivo(nodo.derecho, dato);
        }
        return nodo; // No agrega números repetidos.
    }

    public List<Integer> preorden() {
        List<Integer> resultado = new ArrayList<>();
        preorden(raiz, resultado);
        return resultado;
    }

    private void preorden(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            resultado.add(nodo.dato);
            preorden(nodo.izquierdo, resultado);
            preorden(nodo.derecho, resultado);
        }
    }

    public List<Integer> inorden() {
        List<Integer> resultado = new ArrayList<>();
        inorden(raiz, resultado);
        return resultado;
    }

    private void inorden(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            inorden(nodo.izquierdo, resultado);
            resultado.add(nodo.dato);
            inorden(nodo.derecho, resultado);
        }
    }

    public List<Integer> postorden() {
        List<Integer> resultado = new ArrayList<>();
        postorden(raiz, resultado);
        return resultado;
    }

    private void postorden(Nodo nodo, List<Integer> resultado) {
        if (nodo != null) {
            postorden(nodo.izquierdo, resultado);
            postorden(nodo.derecho, resultado);
            resultado.add(nodo.dato);
        }
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
