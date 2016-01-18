package com.njit.cs610;

import java.util.List;

class GraphStructure
{
  private  List<VertexStructure> vertex;
  private  List<EdgeStructure> edge;
  
  public GraphStructure(List<VertexStructure> vertex, List<EdgeStructure> edge)
  {
    this.vertex = vertex;
    this.edge = edge;
  }
  
  public List<VertexStructure> getvertex()
  {
    return this.vertex;
  }
  
  public List<EdgeStructure> getEdge()
  {
    return this.edge;
  }
}
