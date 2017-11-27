package boruvka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Boruvka {

    /* -- Passos
#verificar se o peso de todas as arestas são diferentes
#não deve ter ciclos negativos

#verificar, para cada vertice, a menor aresta que o liga

#para cada componente gerada(considerar como se fosse um vertice), selecionar a aresta de menor peso que a liga a outra componente

#finalizar quando todo mundo tiver ligado com todo mundo
  
    
 SOLUÇÃO APENAS PARA GRAFOS CONEXOS   
     */
    public ArrayList<String> subgrafo = new ArrayList<>();
    public ArrayList<String> verticesDoGrafo = new ArrayList<>();
    public static Map<String, ArrayList<String>> grafo = new HashMap<>();

    public static void main(String[] args) {
        Grafo g = new Grafo();

        g.addVertice("a");
        g.addVertice("b");
        g.addVertice("c");
        g.addVertice("d");
        g.addVertice("e");
        g.addVertice("f");

        //g.addArestaPeso(-4, "a", "b");
        //g.addArestaPeso(-4, "b", "a");
        g.addArestaPeso(1, "b", "c");
        g.addArestaPeso(1, "c", "b");
        g.addArestaPeso(2, "a", "c");
        g.addArestaPeso(2, "c", "a");
        g.addArestaPeso(5, "b", "d");
        g.addArestaPeso(5, "d", "b");
        g.addArestaPeso(8, "c", "d");
        g.addArestaPeso(8, "d", "c");
        g.addArestaPeso(2, "d", "e");
        g.addArestaPeso(2, "e", "d");
        g.addArestaPeso(10, "e", "c");
        g.addArestaPeso(10, "c", "e");
        g.addArestaPeso(2, "e", "f");
        g.addArestaPeso(2, "f", "e");
        g.addArestaPeso(6, "f", "d");
        g.addArestaPeso(6, "d", "f");

        g.addGrafo();
        g.printGrafo();
        System.out.println("\n");
        grafo = g.getGrafo();

        Boruvka b = new Boruvka();

        //verificar dps
        b.verificaCiclosNegativos(grafo);

        b.verticesGrafo();

        //calcular Boruvka
        String componete = b.calculaBoruvkaPrimeiraIteracao("a");
        String[] splitComponente = componete.split("=");

        b.calculaBoruvka(splitComponente[0], splitComponente[2]);

    }

    public void verificaCiclosNegativos(Map<String, ArrayList<String>> grafo) {

        //Pegar apenas os pesos
        for (Map.Entry< String, ArrayList<String>> g : grafo.entrySet()) {
            ArrayList<String> pesoVertice = new ArrayList<>();

            String vertice = g.getKey();
            pesoVertice = g.getValue();

            //Separar pesos
            for (int i = 0; i < pesoVertice.size(); i++) {
                String[] split = pesoVertice.get(i).split("=");
                if (Integer.valueOf(split[0]) < 0) {
                    int pesoNeg = Integer.valueOf(split[0]);
                    String verticePeso = split[1];

                }

            }

        }

    }

    public void verticesGrafo() {
        //armazena todos os vertices do grafo
        for (Map.Entry<String, ArrayList<String>> g : grafo.entrySet()) {
            verticesDoGrafo.add(g.getKey());
        }

    }

    public String calculaBoruvkaPrimeiraIteracao(String verticeInicial) {
        ArrayList<String> adjacentes = new ArrayList<String>();
        ArrayList<String> vertices = new ArrayList<String>();
        for (Map.Entry<String, ArrayList<String>> g : grafo.entrySet()) {
            vertices.add(g.getKey());
            if (g.getKey().equals(verticeInicial)) {
                adjacentes = g.getValue();

            }
        }
        //encontrar o menor peso
        String valorMenor = encontraMenorPeso(adjacentes);
        String[] splitMenor = valorMenor.split("=");
        int menor = Integer.valueOf(splitMenor[0]);
        String adjMenor = splitMenor[1];

        //formar componente e encontrar o proximo menor
        String componentes = verticeInicial + "=" + menor + "=" + adjMenor;
        subgrafo.add(componentes);

        //gerar grafo com as componentes
        for (Map.Entry<String, ArrayList<String>> g : grafo.entrySet()) {
            String[] split = componentes.split("=");

            ArrayList<String> adj = new ArrayList<>();
            adj = g.getValue();
            String p = split[1] + "=" + split[2];
            if (g.getKey().equals(split[0])) {
                //eliminar aresta da componente e gerar grafo com componentes
                if (adj.contains(p)) {
                    adj.remove(p);
                }
            }
            //verificar se grafo é diirigido
            String pesoDirigido = split[1] + "=" + split[0];
            if (g.getKey().equals(split[2]) && adj.contains(pesoDirigido)) {
                adj.remove(pesoDirigido);
            }
        }

        return componentes;
    }

    public void calculaBoruvka(String verticeInicial, String verticeComponente) {

        ArrayList<String> adjacentes = new ArrayList<String>();
        ArrayList<String> adjacentesComponente = new ArrayList<String>();

        for (Map.Entry<String, ArrayList<String>> g : grafo.entrySet()) {
            if (g.getKey().equals(verticeInicial)) {
                adjacentes = g.getValue();
            }
            if (g.getKey().equals(verticeComponente)) {
                adjacentesComponente = g.getValue();
            }
        }

        //encontrar o menor peso vertice inicial
        int menor1 = Integer.MAX_VALUE;
        String adjMenor1 = null;
        if (!adjacentes.isEmpty()) {
            String valorMenor = encontraMenorPeso(adjacentes);
            String[] splitMenor = valorMenor.split("=");
            menor1 = Integer.valueOf(splitMenor[0]);
            adjMenor1 = splitMenor[1];

        }

        int menor = menor1;
        String adjMenor = adjMenor1;
        String vertice = verticeInicial;

        //encontrar o menor peso vertice componente
        if (!adjacentesComponente.isEmpty()) {
            String valorMenorComponente = encontraMenorPeso(adjacentesComponente);
            String[] splitMenorComponente = valorMenorComponente.split("=");

            int menor2 = Integer.valueOf(splitMenorComponente[0]);
            String adjMenor2 = splitMenorComponente[1];

            //verificar qual dos dois menores dos vertice é o menor
            if (menor2 < menor1) {
                menor = menor2;
                adjMenor = adjMenor2;
                vertice = verticeComponente;
            }
        }

        //formar componente e encontrar o proximo menor
        String componentes = vertice + "=" + menor + "=" + adjMenor;
        subgrafo.add(componentes);

        //gerar grafo com as componentes
        for (Map.Entry<String, ArrayList<String>> g : grafo.entrySet()) {
            String[] split = componentes.split("=");

            ArrayList<String> novoAdj = new ArrayList<>();
            ArrayList<String> adj = new ArrayList<>();
            adj = g.getValue();
            String p = split[1] + "=" + split[2];

            if (g.getKey().equals(split[0])) {
                //eliminar aresta da componente e gerar grafo com componentes
                if (adj.contains(p)) {
                    adj.remove(p);
                }
            }
            //verificar se grafo é diirigido
            String pesoDirigido = split[1] + "=" + split[0];
            if (g.getKey().equals(split[2]) && adj.contains(pesoDirigido)) {
                adj.remove(pesoDirigido);
            }

        }

        //verifica se passou por todos os vertices do grafo
        boolean componentesVazias = false;
        int pos = verticesDoGrafo.size();
        if(verticesDoGrafo.get(pos-1).equals(adjMenor)){
            componentesVazias = true;
        }
        
        if (componentesVazias == true) {
            System.out.println("FIM DO ALGORITMO! ");
            System.out.println("Árvore geradora mínima: " + subgrafo);

        } else {
            //criar próximas componentes
            calculaBoruvka(verticeComponente, adjMenor);
        }

    }

    public String encontraMenorPeso(ArrayList<String> adjacentes) {
        int menor = Integer.MAX_VALUE;
        String valor = null;
        String adjMenor = null;
        for (int i = 0; i < adjacentes.size(); i++) {
            String[] peso = adjacentes.get(i).split("=");
            if (Integer.valueOf(peso[0]) < menor) {
                menor = Integer.valueOf(peso[0]);
                adjMenor = peso[1];
                valor = adjacentes.get(i);
            }
        }

        return valor;

    }
}
