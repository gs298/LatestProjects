package com.njit.cs610;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ShortPath_Dijkstra
{
  private final List<VertexStructure> vertice;
  private final List<EdgeStructure> edges;
  private Set<VertexStructure> settledvertice;
  private Set<VertexStructure> unSettledvertice;
  private Map<VertexStructure, VertexStructure> predecessors;
  private Map<VertexStructure, Integer> distance;
  
  public ShortPath_Dijkstra(GraphStructure graph)
  {
    this.vertice = new ArrayList(graph.getvertex());
    this.edges = new ArrayList(graph.getEdge());
  }
  
  public void execute(VertexStructure src)
  {
    this.settledvertice = new HashSet();
    this.unSettledvertice = new HashSet();
    this.distance = new HashMap();
    this.predecessors = new HashMap();
    this.distance.put(src, Integer.valueOf(0));
    this.unSettledvertice.add(src);
    while (this.unSettledvertice.size() > 0)
    {
      VertexStructure node = getMinimum(this.unSettledvertice);
      this.settledvertice.add(node);
      this.unSettledvertice.remove(node);
      findMinimalDistances(node);
    }
  }
  
  public void findMinimalDistances(VertexStructure node)
  {
    List<VertexStructure> adjacentvertice = getNeighbor(node);
    for (VertexStructure target : adjacentvertice) {
      if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target))
      {
        this.distance.put(target, 
          Integer.valueOf(getShortestDistance(node) + getDistance(node, target)));
        this.predecessors.put(target, node);
        this.unSettledvertice.add(target);
      }
    }
  }
  
  private int getDistance(VertexStructure node, VertexStructure target)
  {
    for (EdgeStructure edge : this.edges) {
      if ((edge.getSource().equals(node)) && 
        (edge.getDestination().equals(target))) {
        return edge.getWeight();
      }
    }
    throw new RuntimeException("Illegal");
  }
  
  private List<VertexStructure> getNeighbor(VertexStructure node)
  {
    List<VertexStructure> neighbors = new ArrayList();
    for (EdgeStructure edge : this.edges) {
      if ((edge.getSource().equals(node)) && 
        (!isSettled(edge.getDestination()))) {
        neighbors.add(edge.getDestination());
      }
    }
    return neighbors;
  }
  
  public VertexStructure getMinimum(Set<VertexStructure> vertexes)
  {
    VertexStructure minimum = null;
    for (VertexStructure vertex : vertexes) {
      if (minimum == null) {
        minimum = vertex;
      } else if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
        minimum = vertex;
      }
    }
    return minimum;
  }
  
  private boolean isSettled(VertexStructure vertex)
  {
    return this.settledvertice.contains(vertex);
  }
  
  public int getShortestDistance(VertexStructure destination)
  {
    Integer d = (Integer)this.distance.get(destination);
    if (d == null) {
      return 2147483647;
    }
    return d.intValue();
  }
  
  public LinkedList<VertexStructure> getPath(VertexStructure target)
  {
    LinkedList<VertexStructure> path = new LinkedList();
    VertexStructure step = target;
    if (this.predecessors.get(step) == null) {
      return null;
    }
    path.add(step);
    while (this.predecessors.get(step) != null)
    {
      step = (VertexStructure)this.predecessors.get(step);
      path.add(step);
    }
    Collections.reverse(path);
    return path;
  }
}
