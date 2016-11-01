package com.cards;

import java.awt.Point;
import java.util.*;

public class CardGroup {
	
	public static final String LETTER = "abcdefghijklmnopqrstuvwxyz";
	public static final int CARDGAP = 25;
	private int numCards = 0; // number of cards 
	private LinkedList<Card> cards = null; // cards list
	private Point cardsLocation = null; // the position of the first card in this cards group
	
	/**
	 * Default Constructor
	 */
	CardGroup(){
		this.numCards = 0;
		this.cards = new LinkedList<Card>();
		this.cardsLocation = new Point();
	}
	
	CardGroup(Point p){
		this.numCards = 17;
		this.cards = new LinkedList<Card>();
		this.cardsLocation = new Point(p);
		for(int i =0; i<numCards; i++)
		{
			Point cardP = new Point();
			cardP.setLocation(p.getX(), p.getY()+ i*20);
			Card c = new Card(i,cardP);
			cards.add(c);
		}
	}
	
	CardGroup(int dis,Point p){
		this.numCards = 3;
		this.cards = new LinkedList<Card>();
		this.cardsLocation = new Point(p);
		for(int i =0; i<numCards; i++)
		{
			Point cardP = new Point();
			cardP.setLocation(p.getX() + i*dis, p.getY());
			Card c = new Card(i,cardP);
			cards.add(c);
		}
	}
	/**
	 * Constructor
	 * @param str name array for cards in this group
	 * @param p initial location of this card group
	 */
	CardGroup(String[] str){
		this.numCards = str.length;
		this.cards = new LinkedList<Card>();
		this.cardsLocation = new Point(MainFrame.FRAME_WIDTH/2-115, 15);
		for(int i=0; i<str.length; i++){
			Point cardP = new Point();
			cardP.setLocation(0,0);
			Card c = new Card(str[i]);
			c.setUseful();
			cards.add(c);
		}
	}
	
	CardGroup(String[] str, Point p){
		//1, assign value for instance variable "numCards" and "cardsLocation"
		
		this.numCards = str.length;
		this.cards = new LinkedList<Card>();
		this.cardsLocation = new Point(p);
		//2, assign value for instance variable "cards"
		// assign value of each card in this CardGroup. 
		// the gap between each card is "CARDGAP" along horizontal direction
		for(int i=0; i<str.length; i++){
			Point cardP = new Point();
			cardP.setLocation(p.getX()+ i*CARDGAP, p.getY());
			Card c = new Card(str[i], cardP);
			cards.add(c);
		}
	}
	
	/**
	 * Constructor. Use a subset of the card group to form a new card group.
	 * @param num cards number in the new card group
	 * @param c source cards group LinkedList
	 * @param p initial location of this group. will be recalculated by refreshCardsPosition() later.
	 * @param start starting index in the source cards group
	 * @param end ending index in the source cards group
	 */
	CardGroup(int num, LinkedList<Card> c, Point p, int start, int end){
		//1, assign the value to instance variable "numCards", "cards", "cardsLocation"
		
		this.numCards = num;
		this.cards = new LinkedList<Card>();
		this.cardsLocation = new Point(p);
		this.calPosition();
		//2, extract the subset of the input "LinkedList<Card> c" from the start index "start" to the end index "end"
		// you need to recalculate the position of each card in the new formed card group
		int count = 0;
		int i = 0;
		Iterator<Card> it = c.iterator();
		while(it.hasNext()){
			Card card = it.next();
			if(count >=start && count < end){
				card.setLocation((int)cardsLocation.getX() + i*CARDGAP, (int)cardsLocation.getY());
				card.refreshLocation(cardsLocation.getX() + i*CARDGAP, cardsLocation.getY());
				cards.add(card);
				i++;
			}		
			count ++;
		}
	}

	
	/**
	 * Calculate the new initial location for the card group
	 */
	public void calPosition(){
		int count = this.numCards;
		double x = this.cardsLocation.getX();
		double y = this.cardsLocation.getY();
		if(count%2==0){
			x = MainFrame.FRAME_WIDTH/2 - 70 - (count/2-1)*CARDGAP;
		}
		else{
			x = MainFrame.FRAME_WIDTH/2 - 35 - (count/2)*CARDGAP;
		}
		this.cardsLocation.setLocation(x, y);
	}
	
	/**
	 * Refresh the new location for each cards in the group
	 * @param dis distance moved along the vertical direction
	 */
	public void refreshCardsPositoin(int dis){
		this.calPosition();
		Iterator<Card> it = this.cards.iterator();
		int i =0;
		while(it.hasNext()){
			Card card = it.next();
			if(card.getUseful() == false)
			{
				card.setLocation((int)cardsLocation.getX() + i*CARDGAP, (int)cardsLocation.getY()-dis);
				card.refreshLocation(cardsLocation.getX() + i*CARDGAP, cardsLocation.getY()-dis);
			}
			if(card.getUseful() == true)
			{
				this.cardsLocation.setLocation(305 + i*18, 15);
				card.setLocation((int)cardsLocation.getX() + i*80, 15);
				card.refreshLocation(cardsLocation.getX() + i*80, 15);
			}
			i++;
		}
	}
	
	public void refreshBackcardsPositoin(){
		int count = this.numCards;
		double x = this.cardsLocation.getX();
		double y = this.cardsLocation.getY();
		if(count%2==0){
			x = MainFrame.FRAME_HEIGHT/2 - 70 - (count/2-1)*20;
		}
		else{
			x = MainFrame.FRAME_HEIGHT/2 - 35 - (count/2)*20;
		}
		this.cardsLocation.setLocation(x, y);
		Iterator<Card> it = this.cards.iterator();
		int i =0;
		while(it.hasNext()){
			Card card = it.next();
			card.setLocation((int)cardsLocation.getX() , (int)cardsLocation.getY() + i*20);
			card.refreshLocation(cardsLocation.getX() , cardsLocation.getY() + i*20);
			i++ ;
		}
	}
	
	/**
	 * Choose cards which been chosen to form a new cards group
	 * @return new formed card group
	 */
	public CardGroup chosenCards(){
		//0. initial some variables that will be used in this method 
		CardGroup sc = new CardGroup();
		LinkedList<Card> l = new LinkedList<Card>();
		//1. find which cards have been chosen, and form a new card group to keep those cards
		Iterator<Card> it = cards.iterator();
		int count = 0;
		while(it.hasNext()){
			Card c = it.next();
			
			if(c.getChosen()==true){
				l.add(c);
				count ++;
			}
		}
	
		//2, return this new card group
		sc.numCards = count;
		sc.cards = l;
		sc.cardsLocation = this.cardsLocation;
		return sc;
	}
	
	/**
	 * Overwrite the toString method
	 */
	public String toString(){
		
		// print out all cards that contained in this card group
		// E.g.:
		// "8 spades"
		// "3 diamonds"
		// "14 diamonds"
		// "7 diamonds"
		// "5 club"
		// "7 hearts"
		StringBuffer str = new StringBuffer();
		Iterator<Card> it = cards.iterator();
		while(it.hasNext()){
			Card c = it.next();
			str.append(c.toString());
			str.append("\n");
		}
		return str.toString();
	}
	
	/**
	 * Get the number of cards in this group
	 * @return number of cards
	 */
	public int getCardsNumber(){
		return this.numCards;
	}
	
	/**
	 * Get the LinkedList cards
	 * @return LinkedList cards
	 */
	public LinkedList<Card> getCards(){
		return this.cards;
	}
	
	/**
	 * Get the initial location of this card group
	 * @return initial location 
	 */
	public Point getCardsLocation(){
		return this.cardsLocation;
	}
	
	/**
	 * Set the number of cards in this group
	 * @param num number of cards will be set
	 */
	public void setCardsNumber(int num){
		this.numCards = num;
	}
	
	/**
	 * Judge if the selected cards would be played or not
	 * @return true if this card group can be played
	 */
	@SuppressWarnings("unchecked")
	public boolean isValid(){
		//0. initial some variables that will be used in this method 
		CardGroup cg = this;
		boolean sign = false;
		int figure = 0;
		int tmpStart = 0;
		int tmpEnd = 0;
		StringBuffer strBuffer = new StringBuffer();
		
		//1. sort this card group first
		try {
			Collections.sort(cg.cards);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // sort the card group
		
		//2. for each card in the sorted card group, using the value of its "figure" as the index to get one letter from the static String "LETTER"
		// E.g.: the "figure" of one card is 4, then we can find the 4th letter in "LETTER" is "d". 
		// using those founded letters for each card to construct an new input string  
		Iterator<Card> it = cg.cards.iterator();
		while(it.hasNext()){
			Card c = it.next();
			figure = c.getFigure();
			strBuffer.append(LETTER.charAt(figure));
			
		}
		
		//3. call functions from PlayCardLogic to judge this input string is valid or not
		// if it is valid, then return true. otherwise, return false.
		String str = strBuffer.toString();
		if( str.length()==1 || PlayCardLogic.isPairs(str,tmpStart,tmpEnd) 
				|| PlayCardLogic.isThrees(str,tmpStart,tmpEnd)
				|| PlayCardLogic.isFour(str,tmpStart,tmpEnd)
				|| PlayCardLogic.isSerials(str,tmpStart,tmpEnd)
				|| PlayCardLogic.isThreePlusOne(str,tmpStart,tmpEnd)
				|| PlayCardLogic.isThreePlusTwo(str,tmpStart,tmpEnd))
		{
			sign = true;
		}
		return sign;
		
	}
	
	/**
	 * Judge current card group is bigger than the compared one or not
	 * @param cg card group used to be compared
	 * @return true if current card is bigger
	 */
	@SuppressWarnings("unchecked")
	public boolean biggerThan(CardGroup cg){
		//0. initial some local variables that will be used in this method 
		int tmpStart = 0;
		int tmpEnd = 0;
		int cgStart=0;
		int cgEnd=0;
		String str1 = new String();
		String str2 = new String();
		StringBuffer strBuffer1 = new StringBuffer();
		StringBuffer strBuffer2 = new StringBuffer();
		//1. sort this.cards first. 
		// for each card in this.cards, using its figure as the index to find a letter in LETTER.
		// connect all these letters to form a string. Here we use "str1" to represent this string.
		try {
			Collections.sort(this.cards);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<Card> it1 = this.cards.iterator();
		Boolean bln=true;
		while(it1.hasNext()){
			Card c = it1.next();
			strBuffer1.append(LETTER.charAt(c.getFigure()));
			while(bln){
				tmpStart=c.getFigure();
				bln=false;
			}
			tmpEnd=c.getFigure();
		}
		str1 = strBuffer1.toString();		
		// 2. if this.numCards ! = cg.numCards, return true if str1 represents the same Four cards.
		// otherwise, return false.
		// 3. if this.numCards == cg.numCards, do the following judgment:		
			// 3.1. sort cg.cards first. 
			// for each card in this.cards, using its figure as the index to find a letter in LETTER.
			// connect all these letters to form a string. Here we use "str2" to represent this string.
			try {
				Collections.sort(cg.cards);	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//cg.sortByFigure();
			Iterator<Card> it2 = cg.cards.iterator();
			bln=true;
			while(it2.hasNext()){
				Card c = it2.next();
				strBuffer2.append(LETTER.charAt(c.getFigure()));
				while(bln){
					cgStart=c.getFigure();
					bln=false;
				}
				cgEnd=c.getFigure();
			}
			str2 = strBuffer2.toString();		
			// 3.2. if the following two conditions satisfied, then return true. otherwise, return false.
			//      1) str1 and str2 belong to the same type of hand. E.g.: Pairs, Threes or Three plus One etc.
			//      2) the largest rank in str1 is bigger than the largest rank in str	
			
		if(this.numCards!=cg.numCards&&str1.length()== 4)
		{
			if(str1.charAt(0) == str1.charAt(1) && str1.charAt(1) == str1.charAt(2) && str1.charAt(2) == str1.charAt(3))
				return true;
			else	
				return false;
		}
		else
		{
  			  if(numCards==1
  					  ||PlayCardLogic.isPairs(str1,tmpStart,tmpEnd)&&PlayCardLogic.isPairs(str2,cgStart,cgEnd)
  					  ||PlayCardLogic.isThrees(str1,tmpStart,tmpEnd)&&PlayCardLogic.isThrees(str2,cgStart,cgEnd)
  					  ||PlayCardLogic.isFour(str1,tmpStart,tmpEnd)&&PlayCardLogic.isFour(str2,cgStart,cgEnd)){
				  if(tmpEnd>cgEnd){
					  return true;
				  }				 
			   }
  			  else if(PlayCardLogic.isThreePlusOne(str1,tmpStart,tmpEnd)&& PlayCardLogic.isThreePlusOne(str2,cgStart,cgEnd)){
  				  if(str1.charAt(0)!=str1.charAt(1)&&str2.charAt(0)!=str2.charAt(1)){
  					  if(str1.charAt(1)>str2.charAt(1))return true;
  				  }
  				  else if(str1.charAt(0)!=str1.charAt(1)&&str2.charAt(0)==str2.charAt(1)){
  					  if(tmpEnd>cgStart)return true;
  				  }
  				  else if(str1.charAt(0)==str1.charAt(1)&&str2.charAt(0)!=str2.charAt(1)){
  					  if(tmpStart>cgEnd)return true;					
  				  }
  				  else if(str1.charAt(0)==str1.charAt(1)&&str2.charAt(0)==str2.charAt(1)){
  					  if(tmpStart>cgStart)return true;
  				  }
  			  }
  			 else if(PlayCardLogic.isThreePlusTwo(str1,tmpStart,tmpEnd)&&PlayCardLogic.isThreePlusTwo(str2,cgStart,cgEnd)){
  				if(str1.charAt(0)!= str1.charAt(2)&&str2.charAt(0)!= str2.charAt(2)){
  					  if(tmpEnd>cgEnd)return true;
  				}
  			
  			    else if(str1.charAt(0)!= str1.charAt(2)&&str2.charAt(0)==str2.charAt(2)){
  			    	  if(tmpEnd>cgStart) return true;
  			    } 
  			    else if(str1.charAt(0)== str1.charAt(2)&&str2.charAt(0)!=str2.charAt(2)){
  					  if(tmpStart>cgEnd) return true;
  			    }
  				else if(str1.charAt(0)== str1.charAt(2)&&str2.charAt(0)==str2.charAt(2)) {
  					if(tmpStart>cgStart) return true;
  				}
  			  }
  	   }//else de 
		return false;
	}

	
	/**
	 * Delete cards from the source card group which appears in the card group cg
	 * @param cg card group used for indexing
	 * @return true if this process without any problem
	 */
	public boolean deleteCards(CardGroup cg){
		
			//1. if there is no card left in "cg" or in the source card group, we can not delete any more cards.
			// and return false for this case.
		try {
			if(cg.numCards == 0 || this.numCards == 0)
				return false;
		
			//2. For each card appeared in "cg", delete the same one from the source card group
			// if no problem happen, return true.
			Iterator<Card> it = cg.cards.iterator();
			while(it.hasNext()){
				Card c = it.next();
				if(c.getChosen()==true){
					this.numCards --;
					this.cards.remove(c);
				}
			}
			this.refreshCardsPositoin(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	/**
	 * Use Collections to shuffle the cards sequence
	 */
	public void shuffleCards(){
		
		// write your code here!
		Collections.shuffle(this.cards);
	}
	
	/**
	 * Sort cards by its rank
	 */
	@SuppressWarnings("unchecked")
	public void sortByFigure(){
		
		// write your code here!
		Collections.sort(this.cards);
		this.refreshCardsPositoin(0);
	}
	
	/**
	 * Sorts cards by its suits
	 */
	public void sortBySuits(){
		
		// write your code here!
		Card c1 = null;
		Card c2 = null;
		Card tmp = null;
		int pos = 0;
		for(int i=0; i<this.numCards-1; i++){
			c1 = this.cards.get(i);
			tmp = c1;
			pos = -1;
			for(int j=i+1; j<this.numCards; j++){
				c2 = this.cards.get(j);
				if(c2.getSuit().compareTo(tmp.getSuit())<0){
					tmp = c2;
					pos = j;
				}
			}
			if(pos != -1){
				this.cards.set(pos, c1);
				this.cards.set(i, tmp);
			}
			
		}
		this.refreshCardsPositoin(0);
	}
	
	/**
	 * Judge the card list is empty or not
	 * @return true if the card list is empty
	 */
	public boolean isFinished(){
		if(this.numCards != this.cards.size()){
			System.out.println("need to check here!");
		}
		return this.cards.size() == 0;
	}
	
	/**
	 * Hide cards group 
	 */
	public void hideCards(){
		Iterator<Card> it = this.getCards().iterator();
		while(it.hasNext()){
			Card c = it.next();
			c.setVisible(false);
		}
	}
	
	/**
	 * Uncover cards group
	 */
	public void uncoverCards(){
		Iterator<Card> it = this.getCards().iterator();
		while(it.hasNext()){
			Card c = it.next();
			c.setVisible(true);
		}
	}
	
	/**
	 * Set the status of isChosen to be false for each cards in this group
	 */
	public void unChosen(){
		Iterator<Card> it = this.getCards().iterator();
		while(it.hasNext()){
			Card c = it.next();
			c.setChosen(false);
			//c.setMoved(false);
		}
	}
}
