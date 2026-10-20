import java.util.Scanner;
public class Principal {

    static final int TAMANIO = 20;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Arreglo arreglo = new Arreglo(TAMANIO);
        Ordenamiento ordenamiento = new Ordenamiento();
        Busqueda busqueda = new Busqueda();
        int opcion;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Generar numeros aleatorios");
            System.out.println("2. Mostrar arreglo");
            System.out.println("3. Ordenar con Quicksort");
            System.out.println("4. Buscar un numero");
            System.out.println("5. Salir");
            System.out.print("Elige una opcion: ");

            while (!sc.hasNextInt()) {
                System.out.print("Ingresa un numero válido: ");
                sc.next();
            }
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    arreglo.generarNumeros();
                    break;

                case 2:
                    if (!arreglo.isGenerado()) {
                        System.out.println("Primero genera los numeros (opción 1).");
                    } else {
                        arreglo.mostrar();
                    }
                    break;

                case 3:
                    if (!arreglo.isGenerado()) {
                        System.out.println("Primero genera los numeros (opción 1).");
                    } else {
                        ordenamiento.ordenarQuicksort(arreglo.getListaOriginal(), 0, arreglo.getTamanio() - 1);
                        arreglo.setOrdenado(true);
                        System.out.println("Lista ordenada:");
                        arreglo.mostrar();
                    }
                    break;

                case 4:
                    if (!arreglo.isGenerado()) {
                        System.out.println("Primero genera los numeros (opción 1).");
                        break;
                    }
                    // La búsqueda binaria requiere el arreglo ordenado
                    if (!arreglo.isOrdenado()) {
                        System.out.println("El arreglo no esta ordenado. Ordenando con Quicksort...");
                        ordenamiento.ordenarQuicksort(arreglo.getListaOriginal(), 0, arreglo.getTamanio() - 1);
                        arreglo.setOrdenado(true);
                        arreglo.mostrar();
                    }
                    System.out.print("Numero a buscar: ");
                    while (!sc.hasNextInt()) {
                        System.out.print("Ingresa un numero entero: ");
                        sc.next();
                    }
                    int valorBuscado = sc.nextInt();
                    int posicion = busqueda.buscar(arreglo.getListaOriginal(), valorBuscado, 0, arreglo.getTamanio() - 1);

                    if (posicion == -1) {
                        System.out.println("El numero " + valorBuscado + " no se encuentra en el arreglo.");
                    } else {
                        String lado = busqueda.obtenerLado(posicion, arreglo.getTamanio());
                        System.out.println("Numero " + valorBuscado + " encontrado en la posicion "
                                + posicion + " (lado " + lado + " del arreglo).");
                    }
                    break;

                case 5:
                    System.out.println("Fin del programa.");
                    break;

                default:
                    System.out.println("Opcion no válida.");
            }
        } while (opcion != 5);

        sc.close();
    }
}

/**
 * Clase Arreglo: almacena los 20 números enteros,
 * los genera aleatoriamente y los muestra.
 */
class Arreglo {

    private int listaOriginal[];
    private boolean generado;
    private boolean ordenado;

    public Arreglo(int tamanio) {
        listaOriginal = new int[tamanio];
        generado = false;
        ordenado = false;
    }

    // Genera números aleatorios entre 1 y 50 usando Math.random()
    public void generarNumeros() {
        int number;
        System.out.println("Lista original:");
        for (int i = 0; i < listaOriginal.length; i++) {
            number = (int) (Math.random() * 50 + 1);
            listaOriginal[i] = number;
            System.out.print(listaOriginal[i] + " ");
        }
        System.out.println();
        generado = true;
        ordenado = false;
    }

    // Muestra el arreglo con sus índices
    public void mostrar() {
        for (int i = 0; i < listaOriginal.length; i++) {
            System.out.print("[" + i + "]=" + listaOriginal[i] + " ");
        }
        System.out.println();
    }

    public int[] getListaOriginal() {
        return listaOriginal;
    }

    public int getTamanio() {
        return listaOriginal.length;
    }

    public boolean isGenerado() {
        return generado;
    }

    public boolean isOrdenado() {
        return ordenado;
    }

    public void setOrdenado(boolean ordenado) {
        this.ordenado = ordenado;
    }
}

/**
 * Clase Ordenamiento: ordena un arreglo de menor a mayor
 * con el algoritmo recursivo Quicksort.
 */
class Ordenamiento {

    // Quicksort recursivo (menor a mayor)
    public void ordenarQuicksort(int listaOriginal[], int inferior, int superior) {
        int i, j, central;
        double pivote;
        central = (inferior + superior) / 2;
        pivote = listaOriginal[central];
        i = inferior;
        j = superior;

        System.out.println("Limites que se analizan. Inferior: " + i + " Superior: " + j + " Pivote: " + pivote);

        do {
            while (listaOriginal[i] < pivote) i++;
            while (listaOriginal[j] > pivote) j--;
            if (i <= j) {
                intercambiar(listaOriginal, i, j);
                i++;
                j--;
            }
        } while (i <= j);

        if (inferior < j) {
            ordenarQuicksort(listaOriginal, inferior, j); // recursividad, subconjunto izquierdo
        }
        if (i < superior) {
            ordenarQuicksort(listaOriginal, i, superior); // recursividad, subconjunto derecho
        }
    }

    // Intercambia dos elementos del arreglo
    private void intercambiar(int listaOriginal[], int i, int j) {
        int aux = listaOriginal[i];
        listaOriginal[i] = listaOriginal[j];
        listaOriginal[j] = aux;
    }
}

/**
 * Clase Busqueda: busca un valor en un arreglo ordenado con
 * búsqueda binaria recursiva e indica en qué lado se encontró.
 */
class Busqueda {

    // Búsqueda binaria recursiva
    public int buscar(int listaOriginal[], int valorBuscado, int inferior, int superior) {
        int central;
        if (inferior > superior) {
            return -1;
        } else {
            central = (inferior + superior) / 2;
            if (listaOriginal[central] == valorBuscado)
                return central;
            else if (listaOriginal[central] < valorBuscado)
                return buscar(listaOriginal, valorBuscado, central + 1, superior);
            else
                return buscar(listaOriginal, valorBuscado, inferior, central - 1);
        }
    }

    // Indica si la posición está en el lado izquierdo o derecho del arreglo
    public String obtenerLado(int posicion, int tamanio) {
        int mitad = tamanio / 2;
        if (posicion < mitad) {
            return "IZQUIERDO";
        } else {
            return "DERECHO";
        }
    }
}
