/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static java.lang.Integer.max;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vinícius Resende
 */
public class Splc {

    int dfs(List<Integer> graph[], int u, int[][] splc) { // Busca em profundidade para encontrar quantos vertices sink eu consigo atingir partindo de um determinado nó
        int sinks = (graph[u].size() == 0 ? 1 : 0); // se eu não tenho mais para onde ir, então é um nó sink (1) caso contrário, não é um nó sink (0)
        for (int v : graph[u]) { // Percorro os vizinhos de u
            int k = dfs(graph, v, splc); // k representa a quantidade de nós sink que o vertice v consegue atingir
            splc[u][v] += k; // o peso da aresta u->v será k
            sinks += k; // quantidade total de sinks que eu consigo atingir apartir do vertice u é incrementada
        }
        return sinks; // retorno a quantidade de sinks que meu vertice u consegue atingir
    }

    void removerCiclos(int vis[], int u, List<Integer> graph[]) { // função para remover os ciclos
        for (Integer v : graph[u]) { // para todo vizinho de u
            if (vis[v] == 0) { // se eu não visitei, entao visito
                removerCiclos(vis, v, graph);
            } else { // se eu já visitei e não terminei minha busca, então eu tenho um ciclo
                graph[u].remove(v); // removo está aresta que gera o ciclo
            }
        }
    }

    void mainPath(int u, int w, Path singlePath, int[][] splc, List<Integer> graph[], List<Path> allPaths, Map<Integer, String> idr) { // função para gerar os caminhos principais
        int mx = 0;
        for (int v : graph[u]) { // percorre todos os nós adjacentes de u
            mx = max(mx, splc[u][v]); // descubro qual a aresta com maior peso
        }
        for (int v : graph[u]) { 
            if (mx == splc[u][v]) { // se é uma aresta com custo maximo, entao o vertice v será adicionado no caminho
                singlePath.path.add(idr.get(v)); // idr mantem o nome real do vertice, que são strings e são mapeadas para numeros inteiros para facilitar a implementação)
                mainPath(v, w + splc[u][v], singlePath, splc, graph, allPaths, idr); // vou para o vertice v para ver qual será o proximo vertice do caminho
                singlePath.path.remove(singlePath.path.size() - 1); // se eu já passei por v, então devo remove-lo do caminho atual que estou construindo para que seja construido um novo caminho apartir de u
            }   // podem existir multiplos caminhos principais que contem vertices em comum
        }
        if (graph[u].size() == 0) { // se é um vertice de grau de saida 0
            Path aux = new Path(); 
            aux.setCost(w);
            for (String x : singlePath.getPath()) { // passo os vertices do caminho atual para um caminho auxiliar
                aux.path.add(x);
            }
            allPaths.add(aux); // salvo esse caminho em uma lista de caminhos principais
        }
    }
}
