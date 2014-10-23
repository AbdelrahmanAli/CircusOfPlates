package model;

import java.util.Stack;

public class StackIterator 
{
    private Stack<Shape> stack;
    private int index;
    
	public StackIterator(Stack<Shape> stack) 
	{ 
		this.stack = stack;	
		index = 0;
	}
	
	public Shape first() 
	{
	    return stack.peek();
	}
	public void next()
	{
	    index++;
	}
	
	public boolean isDone()
	{
	    return index==stack.size();
	}

    public Shape currentItem()
    {
    	Shape shape = null ;
        Stack<Shape> temp = new Stack<Shape>();

        for(int i = 0 ; i <=index ; i++)
        {
            if(i==index)   
            	shape = stack.peek();
            else    temp.push(stack.pop());
        }
        for (int i = 0 ; i< index ; i ++ )    stack.push(temp.pop());
        
        return shape;
    }
}
