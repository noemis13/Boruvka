package boruvka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ns
 */
/* Cria grafo por meio da lista de adjacêcia */
public class Grafo {

    public Map<String, ArrayList<String>> grafo = new HashMap<>();
    public Map<String, Integer> vertices = new HashMap<>();
    public ArrayList<String> arestas = new ArrayList<>();
    public ArrayList<String> peso = new ArrayList<>();
    int numAresta = 0;

    
    public void addVertice(String vertice) {

        //verifica se já existe o vertice
        if (!vertices.containsKey(vertice)) {
            vertices.put(vertice, numAresta);
            numAresta++;
        }
    }

    
    public void addArestaPeso(int valorPeso, String vertice, String aresta){
        int v;
        for (Map.Entry<String, Integer> entrada : vertices.entrySet()) {
            if(entrada.getKey().equals(vertice)){
                v = entrada.getValue();
                peso.add((v + " " + valorPeso + " " + aresta));
            }
        }
    }

    public void addGrafo() {
        for (Map.Entry<String, Integer> g : vertices.entrySet()) {
            ArrayList<String> conteudoAresta = findAresta(g.getValue());
            grafo.put(g.getKey(), conteudoAresta);

        }
    }

    public ArrayList<String> findAresta(int valorAresta) {
        ArrayList<String> conteudoAresta = new ArrayList<>();
        for (int i = 0; i < peso.size(); i++) {
            String[] split = peso.get(i).split(" ");
             
            if (split[0].equals(Integer.toString(valorAresta))) {
                conteudoAresta.add(split[1]+"="+split[2]);
                
            }
        }

        return conteudoAresta;
    }

    public void printGrafo() {
        for (Map.Entry< String, ArrayList<String>> g : grafo.entrySet()) {
            System.out.println(g.getKey() + "->" + g.getValue());
            
        }

    }

    public Map<String, ArrayList<String>> getGrafo() {
        return grafo;
    }

    

    
}