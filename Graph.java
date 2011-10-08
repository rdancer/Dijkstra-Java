/**
 * Class Graph implementation.
 * 
 * Copyright 2011 Jan Minar <rdancer@rdancer.org>  Licensed under the GNU GPL v2.
 *
 * @author Jan Minar <rdancer@rdancer.org> 
 */

import java.nio.*;
import java.io.*;
import java.util.*;

public class Graph
{
    protected int vertexCount;
    protected int edgeCount;
    
    protected double[][] adjacencyMatrix;

    public Graph(File file)
            throws Exception, IOException
    {
        String graphDescription = "";
        Scanner sc = new Scanner(file);
        
        while (sc.hasNext())
            graphDescription += sc.next();
            
       parseGraphDescription(graphDescription);
    }
    
    public Graph(String graphDescription)
            throws Exception
    {
        parseGraphDescription(graphDescription);
    }

    private void parseGraphDescription(String graphDescription)
            throws Exception
    {
        Scanner sc = new Scanner(graphDescription);
        sc.useDelimiter(",?[ \t\n]*");
        vertexCount = sc.nextInt();
        edgeCount = sc.nextInt();
    
        adjacencyMatrix = new double[vertexCount][edgeCount];
                // if the dimensions are not sufficient, we're going to get an exception later

        sc.useDelimiter("[)(, \t\n]*"); 
                // Ignore commas and parentheses, even if they constitute syntax error
        

       while (sc.hasNext())
       {
           // Some invalid input will result in an exception, some will be silently accepted
           
           int firstVertexIndex = sc.nextInt() - 1;
           int secondVertexIndex = sc.nextInt() - 1;

           //
           // XXX Parsing the decimal number with Scanner does not work -- do it by hand
           //
            
           //double thirdNumber = sc.nextDouble(); // not working
           String s = "";
           s += sc.next(); // the integral part
           s += sc.next(); // the decimal point
           s += sc.next(); // the decimal part
           double edgeWeight = Double.valueOf(s).doubleValue();
                    // XXX should use a decimal instead of a double to avoid rounding error

           adjacencyMatrix[firstVertexIndex][secondVertexIndex] = 
           adjacencyMatrix[secondVertexIndex][firstVertexIndex] = edgeWeight;
        }
    }
  
      
    public String toString()
    {
        String s = "";
        
        
        s += adjacencyMatrix().toString()
                .replaceAll("\\[\\[", "[")
                .replaceAll("]]", "]")
                .replaceAll("], \\[", "]\n[");
        s += "\n";

        s += vertexCount + " vert" + (vertexCount == 1 ? "ex" : "ices");
        s += ", ";
        
        s += edgeCount + " edge" + (edgeCount == 1 ? "" : "s");
        
        return s;
    }
    
    public List<List<Double>> adjacencyMatrix()
    {
        List<List<Double>> matrix = new ArrayList<List<Double>>();
        
        for (int vertex = 0; vertex < adjacencyMatrix.length; vertex++)
        {
            List<Double> singleRow = new ArrayList<Double>();
            
            for (int edge = 0; edge < adjacencyMatrix[vertex].length; edge++)
                singleRow.add(adjacencyMatrix[vertex][edge]);
            
            matrix.add(singleRow);
        }
                
        return matrix;
    }
}
