package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ShapePool implements Serializable
{
    private ShapeFactory shapeFactory;
    private Queue<Shape> shapes;
    
    private ShapePool(HashMap<Integer,Class<Shape>> classes) throws InstantiationException, IllegalAccessException
    {
    	shapes = new LinkedList<Shape>();		
        Random rand = new Random();
    	shapeFactory = new ShapeFactory(classes);
    	for (int i =0 ; i < 100 ; i++)
    	shapes.add(shapeFactory.createShape( rand.nextInt(classes.size()) + 1 ));
    	
    }
    
    // create a shape pool object
    public static ShapePool getInstance(HashMap<Integer,Class<Shape>> classes) throws InstantiationException, IllegalAccessException
    {
        return new ShapePool(classes);
    }
    
    // send shape to client
    public Shape acquireShape()
    {
        if(shapes.size()!=0)    return shapes.poll();
        return null;
    }
    
    // recieve object from client
    public void releaseShape(Shape shape)
    {
        shapes.add(shape);
    }
    
}
