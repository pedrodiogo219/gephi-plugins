/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinícius Resende
 */
class Path implements Comparable<Path> {

    List<String> path; // Lista contendo os vertices do caminho
    int cost; // Custo SPLC total do caminho
    
    
    // Getters e setters
    Path() {
        this.path = new ArrayList<>();
        this.cost = 0;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> vertex) {
        this.path = vertex;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int compareTo(Path other) {
        return other.cost - this.cost;
    }
    
    public String getNoInicial(){
        return path.get(0);
    }
    public String getNoFinal(){
        return path.get(path.size() - 1);
    }
}
