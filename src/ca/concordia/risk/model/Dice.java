package ca.concordia.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class manages the dice entity and data members for the roll activity in attack phase.
 * 
 * @author Pranal
 *
 */
public class Dice 
{
   int numOfDice;
   List<Integer> rollResult;
   
   /**
    * Constructor to initialize dice for the attack phase
    * @param numOfDice stores the number of times the dice has to be rolled
    */
   public Dice(int numOfDice)
   {
	   this.numOfDice = numOfDice;
	   this.rollResult = new ArrayList<>();
   }
   
   /**
    * 
    * @param d number of dice
    * @return rollResult the list of the result obtained on rolling the dice
    */
   public List<Integer> rollDice(int d)
   
   {
	   if(d==1)
		   rollResult.add((int)(Math.random()*6)+1);
	   else 
	   {
		   for(int i=1;i<=d;i++)
		   {
			   rollResult.add((int)(Math.random()*6)+1);
		   }
	   }
	  
	   Collections.sort(rollResult);
	   Collections.reverse(rollResult);
	   return rollResult;
   }
}
