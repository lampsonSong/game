package com.cards;

import   javax.swing.*;   
import   java.awt.*;   
import   java.awt.event.*;   

class   TestTimer  extends   JFrame   {   
    private int  i=10;   
    JPanel   panel; 
    JLabel 	la=new JLabel();
    Timer timer=new  Timer(1000,new  action(this));   
	
	TestTimer(){   
        panel=new JPanel();   
	    getContentPane().add(panel);    
        panel.add(la);     
        setSize(80,60);     
        setLocation(300,300);
        setResizable(false);
		setVisible(true); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          
        timer.start();   
    }
      
    class  action  implements  ActionListener {
        TestTimer temp;
		
		action(TestTimer temp){
		    this.temp=temp;
		}	
        public   void   actionPerformed(ActionEvent   e) {  
              la.setText("还有:"+i+"秒");   
              i--;  		
           if(i==-1){   
                JOptionPane.showMessageDialog(null, "时间到啦！");   
                timer.stop();   
				//temp.setVisible(false);
            } 
		    
        }  
    }
}

public class TimeCounter { 
    public   static   void   main(String  args[]){   
        new  TestTimer();   
    }   
}