package boruvka;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author ns
 */
public class BuscaEmLargura {

    public int buscaLargura(String verticeInicial, String verticeFinal, Map<String, ArrayList<String>> grafo) {

        ArrayList<String> cor = new ArrayList<>();
        ArrayList<Integer> distancia = new ArrayList<>();
        ArrayList<String> predecessor = new ArrayList<>();

        for (Map.Entry< String, ArrayList<String>> g : grafo.entrySet()) {
            cor.add("b"); //branco
            distancia.add(0);
            predecessor.add(null);
        }

        int posVerticeInicial = findPos(verticeInicial, grafo);
        String valorVerticeInicial = null;
        for (Map.Entry< String, ArrayList<String>> g : grafo.entrySet()) {
            if (g.getKey().equals(verticeInicial)) {
                valorVerticeInicial = g.getKey();
            }
        }
        cor.remove(posVerticeInicial);
        cor.add(posVerticeInicial, "c"); //cinza

        distancia.remove(posVerticeInicial);
        distancia.add(posVerticeInicial, 0);

        predecessor.remove(posVerticeInicial);
        predecessor.add(posVerticeInicial, null);

        Queue fila = new PriorityQueue();
        fila.offer(valorVerticeInicial);

        while (!fila.isEmpty()) {
            Object u = fila.poll();

            ArrayList<String> vertices = new ArrayList<>();

            for (Map.Entry< String, ArrayList<String>> g : grafo.entrySet()) {
                if (g.getKey().equals(u)) {
                    vertices = g.getValue();
                }
            }

            int posDeU = findPos(u.toString(), grafo);

            if (!vertices.isEmpty()) {

                for (int i = 0; i < vertices.size(); i++) {
                    int posVertices = findPos(vertices.get(i), grafo);

                    if (cor.get(posVertices).equals("b")) {
                        cor.remove(posVertices);
                        cor.add(posVertices, "c");

                        int distanciaDeU = distancia.get(posDeU);
                        distancia.remove(posVertices);
                        distancia.add(posVertices, distanciaDeU + 1);

                        predecessor.remove(posVertices);
                        predecessor.add(posVertices, u.toString());

                        fila.offer(vertices.get(i));
                    }
                }
            }
            cor.remove(posDeU);
            cor.add(posDeU, "p");

        }

        //retorna a distancia
        int posFinal = findPos(verticeFinal, grafo);
        int distanciaTotal = distancia.get(posFinal);

        return distanciaTotal;

    }

    public int findPos(String valor, Map<String, ArrayList<String>> grafo) {
        int cont = 0;
        int pos = 0;

        for (Map.Entry< String, ArrayList<String>> g : grafo.entrySet()) {
            if (g.getKey().equals(valor)) {
                pos = cont;
            }
            cont++;

        }
        return pos;
    }

}
