package model;

import java.io.Serializable;
import java.util.HashMap;

public class ShapeFactory implements Serializable
{
    HashMap<Integer,Class<Shape>> classes;
    public ShapeFactory(HashMap<Integer,Class<Shape>> classes)
    {
        this.classes = classes;
    }
    public Shape createShape(int type) throws InstantiationException, IllegalAccessException
    {
        Class<Shape> required =  classes.get(type);
        if(required != null)	return required.newInstance();
        return null; // class not found
    }
}


