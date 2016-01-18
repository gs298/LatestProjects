package com.njit.cs610;

class EdgeStructure
{
  private  VertexStructure src;
  private  VertexStructure dest;
  private  int wt;
  
  public EdgeStructure(VertexStructure source, VertexStructure destination, int weight)
  {

    this.src = source;
    this.dest = destination;
    this.wt = weight;
  }
  

  
  public VertexStructure getDestination()
  {
    return this.dest;
  }
  
  public VertexStructure getSource()
  {
    return this.src;
  }
  
  public int getWeight()
  {
    return this.wt;
  }
  
  public String toString()
  {
    return this.src + " " + this.dest;
  }
}
