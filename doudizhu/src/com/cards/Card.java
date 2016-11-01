package com.cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Card extends JLabel implements Comparable {
	
	public enum Suits {Diamonds,Club, Hearts, Spades, King};
	private String name = null; // the name of the image file
	private int figure = 0; // the rank of a card
	private Suits suit = null; // the suit of a card
	private Point location = null; // the location of a card
	private Point preLocation = null; // used for recover its original location
	private String url = null; // the url of the image file
	private boolean isback = false; // show the front or back of the card
	private boolean isChosen = false; // the card is selected or not
	private boolean useful = false;
	/**
	 * Constructor
	 * @param str file name in image folder. E.g. "suits-figure"
	 * @param p initial location of this card
	 */
	Card(int i,Point p)
	{
		name = new String("images/others/" + Integer.toString(i) + ".gif");
		this.location = new Point(p);
		this.setIcon(new ImageIcon(name));
        this.setSize(71, 96);
        this.setLocation(location);
        this.setVisible(true);
	}
	
	Card(String str)
	{
		name = new String("copy/" + str + ".gif");
		this.location = new Point(new Point(MainFrame.FRAME_WIDTH/2-115, 15));
		this.preLocation = new Point(new Point(MainFrame.FRAME_WIDTH/2-115, 15));
		this.setIcon(new ImageIcon(name));
		this.setSize(71, 96);
        this.setVisible(false);
	}
	
	Card( String str, Point p){ // input str has the format "suits-figure"
		//0. initial some variables that will be used in this method 

		this.name = new String(str);
		String seprator = new String("-");
		String[] sub = str.split(seprator);
		if(sub.length != 2){
			System.out.println("input strname has problem!");
			System.out.println(str);
			System.out.println(sub[0]);
			System.out.println(sub[1]);
			System.exit(-1);
		}
		int f = 0;
		
		//1. scan the input String "suits-figure", and extract the value of "suits" and "figure" from it
		// Assign the value of "suits" to instance variable suit
		// Assign the value of "figure" to instance variable figure
		// E.g.: for input string "club-3", we will assign "club" to the instance variable suit, and "3" to the instance variable figure
		// Note: for Ace, we will change the value of its figure from "1" to "14"
		//       for 2, we will change the value of its figure from "2" to "16" 

		try {
			f = Integer.parseInt(sub[1]);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(f){
		case 1:
			this.figure = 14; // for Ace
			break;
		case 2:
			this.figure = 16; // for 2
			break;
		default:
			this.figure = f;
			break;
		}
		//this.figure = Integer.parseInt(sub[1]);
		switch(name.charAt(0)){
		case 'c':
		case 'C':
			this.suit = Suits.Club;
			break;
		case 'd':
		case 'D':
			this.suit = Suits.Diamonds;
			break;
		case 'h':
		case 'H':
			this.suit = Suits.Hearts;
			break;
		case 's':
		case 'S':
			this.suit = Suits.Spades;
			break;
		case 'k':
		case 'K':
			this.suit = Suits.King;
			break;
		}
		
		
		//2, Assign value to the rest instance variables: name, location, preLocation, url, isback and isChosen
		this.useful = false;
		this.location = new Point(p);
		this.preLocation = new Point(p);
		//this.useful = false;
		//3, draw this card on the Client Interface
		
		if(isback){
			url = new String("images/rear.gif" );
		}
		else {
			url = new String("images/" + str + ".gif");
		}
		this.setIcon(new ImageIcon(url));
        this.setSize(81, 110);
        this.setLocation(location);
        this.setVisible(true);
        this.addMouseListener(new Moniter());
        //System.out.println("card has been initilized");
	}
	
	/**
	 * Refresh card location
	 * @param x x coordinate 
	 * @param y y coordinate
	 */
	public void refreshLocation(double x, double y){
		this.location.setLocation(x, y);
		this.preLocation.setLocation(x, y);
	}
	
	/**
	 * Get the status of the member: isChosen
	 * @return true or false
	 */
	public boolean getChosen(){
		return isChosen;
	}
	
	/**
	 * Set the status of isChosen
	 */
	public void setChosen(boolean isChosen){
		this.isChosen = isChosen;
	}
	
	
	/**
	 * Set the status of isMove
	 */
	
	public void setUseful(){
		this.useful = true;
	}
	
	public boolean getUseful()
	{
		return this.useful;
	}
	
	/**
	 * Get the figure of the card
	 * @return figure
	 */
	public int getFigure(){
		return figure;
	}
	
	/**
	 * Get the suits of the card
	 * @return Suits
	 */
	public Suits getSuit(){
		return suit;
	}
	
	/**
	 * Get the name of this Card
	 * @return String name
	 */
	public String getCardName(){
		return name;
	}
	
	/**
	 * Rewrite toString() method. When using System.out.println() to print it out,
	 * the format is "rank suit". E.g.: "5 club", "7 hearts", "8 spades" and "6 diamonds" etc. 
	 * @return String 
	 */
	public String toString() {
		
		//write your code here!
		String tmp = null;
		tmp = (suit == Suits.Club)? new String("club")
			: (suit == Suits.Diamonds)? new String("diamonds")
			: (suit == Suits.Hearts)? new String("hearts")
			: (suit == Suits.Spades)? new String("spades")
			: (suit == Suits.King)? new String("king")
			: null;
			  
		return String.valueOf(figure) + " " + tmp;
	}
	
	/**
	 * Overwrite equals() method. Judge two cards is the same or not.
	 * In our implementation, if rank and suit are all equal, then they are judged as equivalent.
	 * @param obj card to be compared
	 * @return true if the figure and suit are the same
	 */
	 public boolean equals(Object obj) {
		
		 // if rank and suit are all equal, return true
		 // otherwise, return false
		 if (obj instanceof Card) {
				Card c = (Card) obj;
				if (figure == c.getFigure() && c.getSuit() != null) {
					return suit.equals(c.getSuit());
				}
			}
			
		return false;
	}
	 
	 /**
	  * Rewrite hashCode() method.
	  */
	 public int hashCode() {
		return name.hashCode();
	}
	 
	@Override
	/**
	 * Override compareTo() method. Will be used by Collections class.
	 */
	public int compareTo(Object obj) {
		
		// we only compare the rank of two cards here. it's different form the equals() method
		// write your code here!
		Card c = (Card)obj;
		if (figure == c.getFigure()){
			return 0;
		}
		else if(figure > c.getFigure()){
			return 1;
		}
		else return -1;
	}
	
	/**
	 * Draw card
	 */
	public void draw(){
		if(isback){
			url = new String("images/rear.gif" );
		}
		else {
			url = new String("images/" + name + ".gif");
		}
		setIcon(new ImageIcon(url));
        setSize(81, 110);
        setLocation(location);
        setVisible(true);
	}
	
	/*
	public void trunBack(){
		isback = true;
		draw();
	}
	*/
	
	/**
	 * Inner class. Used to respond mouse click
	 * @author Louis
	 *
	 */
	class Moniter extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			Card c = (Card)e.getSource();
			//1, if isChosen is true, then move the card back to its original location
			if(c.isChosen == true){
				c.location.setLocation(c.preLocation);
				c.isChosen = false;
			}
			
			//2, if isChosen is false, then move the card to a new location
			// Here we keep the x coordinate unchanged, but y coordinate 20 pixel up
			else {
			    c.preLocation.setLocation(c.location);
			    c.location.setLocation(c.location.getX(), c.location.getY()-20);
			    c.isChosen = true;
			}
			
			//3, draw this card on the Client interface again
			c.isback = false;
			//System.out.println("ischosen=" + isChosen);
			c.draw();
		  }
	}
}
