/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vinícius Resende
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        int n = in.nextInt();
        int m = in.nextInt();
        int[] degree = new int[n];
        int[][] splc = new int[n][n];
        Arrays.fill(degree, 0);
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        ArrayList<Edge> edges = new ArrayList<Edge>();
        Map<String, Integer> id = new HashMap<String, Integer>();
        Map<Integer, String> idr = new HashMap<Integer, String>();
        int idx = 0;
        while (m-- > 0) {
            String line = in.next();
            String word[] = line.split(";");
            if (!id.containsKey(word[0])) {
                idr.put(idx, word[0]);
                id.put(word[0], idx++);
            }
            if (!id.containsKey(word[1])) {
                idr.put(idx, word[1]);
                id.put(word[1], idx++);
            }
            int a = id.get(word[0]);
            int b = id.get(word[1]);
            graph[a].add(b);
            if (word[2].equals("UNDIRECTED")) {
                graph[b].add(a);
                degree[a]++;
                edges.add(new Edge(b, a));
            }
            edges.add(new Edge(a, b));
            degree[b]++;
        }
        Splc solve = new Splc();
        for (int i = 0; i < n; i++) {
            solve.dfs(graph, i, splc);
        }
        System.out.println("Valores SPLC de cada aresta: ");
        for (Edge x : edges) {
            System.out.print(idr.get(x.a) + " " + idr.get(x.b) + " = ");
            System.out.println(splc[x.a][x.b]);
        }
        List<Path> allPaths = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) {
                Path singlePath = new Path();
                singlePath.path.add(idr.get(i));
                solve.mainPath(i, 0, singlePath, splc, graph, allPaths, idr);
            }
        }
        System.out.println("Main paths usando SPLC");
        Collections.sort(allPaths);
        int caminho = 1;
        int tmp = 0;
        int relevancia = 0;
        for (Path x : allPaths) {
            System.out.print("Nos do caminho " +  caminho++ + " ");
            System.out.println(x.getPath());
            System.out.println("Cost: " + x.getCost());
            if(tmp != x.getCost()){
                relevancia++;
            }
            System.out.println("Relevancia: " + relevancia);
            tmp = x.getCost();
        }
        System.out.println();
    }

}
