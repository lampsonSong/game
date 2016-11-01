package com.cards;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CardServer extends Frame{
	
	private CardGroup cg = null;  //  @jve:decl-index=0:
	private CardGroup[] cgArray = new CardGroup[3];
	private int counter = 0;
	public CardGroup cgextra = new CardGroup();
	public static final String URL = "images/"; 
	public int playnum = 0;
	private boolean isNewGame = false; // used to record this is a new round or not

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4690432324852478593L;
	
	public boolean getIsNewGame(){
		return this.isNewGame;
	}
	
	public void setIsNewGame(boolean sign){
		this.isNewGame = sign;
	}
	
	TextArea ta = new TextArea();
	public void launchFrame()
	{
		this.setTitle("CardServer");
		add(ta, BorderLayout.CENTER);
		setBounds(100,100,300,300);	
		this.setResizable(false);
		this.addWindowListener(
			new WindowAdapter() 
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			}
			);
		setVisible(true);
	}
	
	ServerSocket server = null;
	Collection<ClientConn> cClient = new ArrayList<ClientConn>();
	
	/**
	 * Constructor
	 * @param port
	 * @throws Exception
	 */
	public CardServer(int port) throws Exception
	{
		server = new ServerSocket(port);
		launchFrame();
	}
	
	/**
	 * Kernel function when run the server
	 * @throws Exception
	 */
	public void startServer() throws Exception
	{
		initCardGroup();
		deal();
		while(true)
		{
			// think carefully about the logic in this function
			// write your code here!
			// hint: 1) if this is a new game, server need to deal cards to each client
			// 2) otherwise, server just forward the message got from on client to the other client
			// Note: we should regular the number of clients can only be two here!
			if(isNewGame == false && playnum<3)
			{
				while(true)
				{
					Socket s = server.accept();
					cClient.add( new ClientConn(s) );
					MainFrame.turnFlag = playnum;
					sendCards(cgArray[playnum],s);
					playnum++;
					ta.append("NEW-CLIENT " + s.getInetAddress() + ":" + s.getPort());
					ta.append("\n" + "CLIENTS-COUNT: " + cClient.size() + "\n\n");
					isNewGame = true;
				}
			}
		}
	}
	
	/**
	 * Inner class. used to create a thread for receiving message sent from one client,
	 * and forward it to the other client 
	 * @author Louis
	 *
	 */
	class ClientConn implements Runnable
	{
		Socket s = null;
		public ClientConn(Socket s)
		{
			this.s = s;
			(new Thread(this)).start();
		}
		
		public void send(String str) throws IOException
		{
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(str);
		}
		
		public void dispose()
		{
			try {
				if (s != null) s.close();
				cClient.remove(this);
				ta.append("A client out! \n");
				ta.append("CLIENT-COUNT: " + cClient.size() + "\n\n");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void run()
		{
			
			// think carefully about the logic in this function
			// write your code here!
			// hint: 1) read message through the socket
			// 2) send this message to the other client
			// note: make sure to open and close the socket properly
			try {			
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String str = dis.readUTF();
				while(str != null && str.length() !=0)
				{
					int i = 0;
					if(str.charAt(0) == 'S' && str.charAt(1) != 'S')
					{
						isNewGame = false;
						initCardGroup();
						deal();
						ta.append("New Game Started!\n\n");
						counter = counter + Integer.parseInt(str.substring(2,3));
					}
					if(str.charAt(0) == 'S' && str.charAt(1) == 'S')
					{
						
						counter = counter + Integer.parseInt(str.substring(3,4));
						System.out.println("counter=" + counter);
					}
					for(Iterator<ClientConn> it = cClient.iterator(); it.hasNext(); )
					{
						
						ClientConn cc = (ClientConn)it.next();
						if(str.charAt(0) == 'B' || str.charAt(0) == 'M')			//B代表抢地主，M 是代表聊天框消息
						{
							cc.send(str);
						}
						else if(counter == 3)
						{
							sendCards(cgArray[i], cc.s);
							
							if(i == 2)
								counter = 0;
							i++;
						}
						else if(this != cc && str.charAt(1) != 'S')
						{
							cc.send(str);
						}
					}
					str = dis.readUTF();
				}
				this.dispose();
			} 
			catch (Exception e) 
			{
				System.out.println("client quit");
				this.dispose();
			}
			
			
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		CardServer cs = new CardServer(8888);
		cs.startServer();
	}
	
	private void initCardGroup(){
		int times = 53;
		int remove = -1;
		String[] str = null;
		Point p2 = new Point(10,400);
		str = readImageFolder(URL);
		System.out.println(str.length);
		cg = new CardGroup(str,p2);
		if(cgextra != null)
		{
			cgextra.getCards().removeAll(cgextra.getCards());
		}
		for(int count = 0; count<3; count++)
		{
			double d = Math.random()*times;
			if(remove != (int)(d))
			{
				remove = (int)(d);
				cgextra.getCards().add(cg.getCards().get(remove));
				cg.getCards().remove((int)(d));
				times--;
			}
			else
				count--;
		}
		cg.setCardsNumber(cg.getCards().size());
		cgextra.setCardsNumber(cgextra.getCards().size());
		System.out.println("cgextra=" + cgextra.getCards());
		System.out.println("cg size=" + cg.getCards());
		deal();
	}
	
	private void deal(){
		int num = cg.getCardsNumber()/3;
		cg.shuffleCards();
		try {
			cgArray[0] = new CardGroup(num, cg.getCards(), cg.getCardsLocation(),0,num);
			cgArray[1] = new CardGroup(num, cg.getCards(), cg.getCardsLocation(),num,2*num);
			cgArray[2] = new CardGroup(num, cg.getCards(), cg.getCardsLocation(),2*num,3*num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This function will send the initial cards information to the client when every new round start
	 * The message sent from the server to the client is like this "I:N:xxxx-n:xxxx-n"
	 * where "I" means the initial cards for this round
	 * where "N" means the number of cards in this hand
	 * where "xxxx-n" is the card name. E.g.: "club-3"
	 * @param cg card group will be dealt. Actually, we will construct a message to represent cards in this group, and 
	 * and sent this message to the client through its socket
	 * @param s socket
	 * @throws IOException
	 */
	public void sendCards(CardGroup cg, Socket s) throws IOException{ 
		//1. generate a StringBuffer object, say "sbuffOut", and set the initial content as "I:".
		// get the number of cards in cg, and append this value at the end of sbuffOut. then append sbuffOut with ":" 
		StringBuffer sbuffOut = new StringBuffer();
		sbuffOut.append(sendcgextra(cgextra));
		sbuffOut.append("I:");
		sbuffOut.append(cg.getCardsNumber());
		//2. for each card in cg, get its name and append to sbuffOut. Each card name will be followed by ":"
		Iterator<Card> it = cg.getCards().iterator();
		while(it.hasNext())
		{
			Card card = it.next();
			sbuffOut.append(":");
			sbuffOut.append(card.getSuit());
			sbuffOut.append("-");
			if(card.getFigure() == 14)
				sbuffOut.append("1");
			else if(card.getFigure() == 16)
				sbuffOut.append("2");
			else sbuffOut.append(card.getFigure());
		}
		sbuffOut.append(MainFrame.turnFlag);
		//3. after constructing the message "I:N:xxxx-n:xxxx-n", send it to the client through the socket
		try{
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(sbuffOut.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String sendcgextra(CardGroup cg)
	{
		StringBuffer sbuffOut = new StringBuffer();
		sbuffOut.append("E:");
		sbuffOut.append(cg.getCardsNumber());
		Iterator<Card> it = cg.getCards().iterator();
		while(it.hasNext())
		{
			Card card = it.next();
			sbuffOut.append(":");
			sbuffOut.append(card.getSuit());
			sbuffOut.append("-");
			if(card.getFigure() == 14)
				sbuffOut.append("1");
			else if(card.getFigure() == 16)
				sbuffOut.append("2");
			else sbuffOut.append(card.getFigure());
		}
		return sbuffOut.toString();
	}
	/**
	 * Read image directory to obtain image file name. the format of the file name should be "suits-number.gif"
	 * @param url image directory
	 * @return String array that recording file name
	 */
	public String[] readImageFolder(String url){
		String tmp = new String();
		String[] str = null;
		Pattern p = Pattern.compile("[a-z]{4,8}-\\d{1,2}\\.gif");
		HashSet<String> hs = new HashSet<String>();
		File f = new File(url);
		if(!f.exists()|| !f.isDirectory()){
			System.out.println("please input an valid directory name!");
			return null;
		}
		File[] fArray = f.listFiles();
		for(int i=0; i<fArray.length; i++){
			if(fArray[i].isFile()){
				tmp = fArray[i].getName();
				Matcher m = p.matcher(tmp);
				if(m.matches()){
					hs.add(tmp.substring(0, tmp.length()-4));
				}
			}
		}
		if(str == null){
			str = new String[hs.size()];
			Iterator<String> it = hs.iterator();
			int j = 0;
			while(it.hasNext()){
				tmp = it.next();
				str[j] = new String(tmp);
				j++;
			}
		}
		return str;
	}
	
}
