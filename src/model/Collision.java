package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Collision implements Serializable
{
	public Collision() {}
	
	public void detectCollision(Player player,ArrayList<Shape> droppedShapes)
	{
		Stack<Shape> collisionStack = null;
		boolean leftOrRight = false; // right > true , left > false
		for(int i = droppedShapes.size()-1 ; i >= 0 ; i--)
		{
			// detect right or left stack
			if(droppedShapes.get(i).getxMiddle() > player.getxMiddle())// fall to right
			{
				collisionStack = player.getRightStack(); 
				leftOrRight = true;		
			}
			else if(droppedShapes.get(i).getxMiddle() < player.getxMiddle())// fall to left
			{
				collisionStack = player.getLeftStack(); 
				leftOrRight = false;
			}
			
			if(collisionStack!=null)
			{	//collision with the player or the top of the stack
				if(collisionStack.size() == 0) detectCollisionWithPlayer(i,player,droppedShapes,collisionStack,leftOrRight); // with player
				else detectCollisionWithStackTop(i,player,droppedShapes,collisionStack); // with top of stack
			}
			//collisionStack = null;
		}
					
	}
	
	private void detectCollisionWithPlayer(int i,Player player,ArrayList<Shape> droppedShapes,Stack<Shape> collisionStack, boolean leftOrRight) 
	{
	    Shape shape = droppedShapes.get(i);
		//player hand region (detection region)
		int startPlayer = player.getStartDetectionRegion(leftOrRight);
		int endPlayer =  player.getEndDetectionRegion(leftOrRight);
	    
	    //shape detection region 
		int startShape = shape.getStartDetectionRegion();
		int endShape =  shape.getEndDetectionRegion();
		
		// shape range lies in player's hand
		if(!(endShape<startPlayer || startShape>endPlayer) && (shape.getyBottom() >= player.getHandYTop()) && (shape.getyBottom()<= player.getHandYTop()+10))
		{
		    collisionStack.add(shape);
		    droppedShapes.remove(i);
		}
	}

	private void detectCollisionWithStackTop(int i,Player player,ArrayList<Shape> droppedShapes,	Stack<Shape> collisionStack  ) 
	{
	     Shape shape = droppedShapes.get(i);
	    int startShape = shape.getStartDetectionRegion();
		int endShape =  shape.getEndDetectionRegion();
		Shape temp = null;
		Shape peekShape = collisionStack.peek();
		int startPeekShape = peekShape.getStartDetectionRegion();
		int endPeekShape =  peekShape.getEndDetectionRegion();
	
		if(!(endShape<startPeekShape ||startShape>endPeekShape) && (shape.getyBottom() >= peekShape.getyTop()) && (shape.getyBottom() <= peekShape.getyTop()+5) )
		{
			if(!(shape.getColor().equals(peekShape.getColor())))
			{
				collisionStack.add(shape);
				droppedShapes.remove(i);
			}
			else  
			{
				if(collisionStack.size()!=1)
				{	
					collisionStack.pop();
					temp = collisionStack.peek();

					if(temp.getColor().equals(peekShape.getColor()) )
					{
							collisionStack.pop();
    						player.setScore(player.getScore()+10);
					}
					else 
					{
						collisionStack.push(peekShape);
						collisionStack.push(shape);
					}
				}else collisionStack.add(shape);

				droppedShapes.remove(i);
			}

		}
	}


			

}