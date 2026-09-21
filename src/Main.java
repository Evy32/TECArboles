/** Ejemplo sencillo de un árbol binario de búsqueda. */
public class Main {
    public static void main(String[] args) {
        ArbolBinario arbol = new ArbolBinario();
        int[] datos = {50, 30, 70, 20, 40, 60, 80, 35, 45};

        for (int dato : datos) {
            arbol.insertar(dato);
        }

        System.out.println("=== ÁRBOL BINARIO DE BÚSQUEDA ===\n");
        arbol.mostrar();

        System.out.println("\n=== RECORRIDOS ===");
        System.out.println("Preorden  (raíz, izquierda, derecha): " + arbol.preorden());
        System.out.println("Inorden   (izquierda, raíz, derecha): " + arbol.inorden());
        System.out.println("Postorden (izquierda, derecha, raíz): " + arbol.postorden());
    }
}
