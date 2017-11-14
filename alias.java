//
//  alias.java
//  alias
//
//  Created by Damien Jeannerat on 1/29/07.
//  Copyright (c) 2007 __MyCompanyName__. All rights reserved.
//
//  A simple Java Swing applet
//

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;


public class alias extends JApplet {
	JTextField textfield;

	private String mess; 
	private Dim dim = new Dim();
	private int number_of_dimensions;
    private Font font = new Font("serif", Font.ITALIC + Font.BOLD, 36);
	private Label m_mess; 
	private JTextArea info = new JTextArea();
	private ControlWindow controlwindow;
	private DisplayWindow displaywindow;
	private Cosa cosa = new Cosa();
	int i;
    public void init() {
        // set the default look and feel
		mess = getParameter("message");
		number_of_dimensions=Integer.parseInt(getParameter("number_of_dimensions"));
        /*String laf = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println ("Warning: UnsupportedLookAndFeel: " + laf);
        } catch (Exception exc) {
            System.err.println ("Error loading " + laf + exc);
        }*/
		setLayout (new FlowLayout());
        m_mess = new Label("MessageApplet is Running... "+mess, Label.CENTER); 
//        add(m_mess); 
        add(info); 

////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

				System.err.println ("Changing ...");
				controlwindow = new ControlWindow();//sfo, sw, full spectral widht (for limit in slider)
				///////controlwindow.disppar.sw=-1d;controlwindow.disppar.cf=666.3d;controlwindow.disppar.sfo=666.1d;
				controlwindow.setSize(350, 200);	//controlwindow.pack();
				controlwindow.setLocation(100, 100);	
				controlwindow.setTitle("Control window v1.0");	
				//controlwindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); Not working !!
				controlwindow.addMyEventListener(new MyEventListener() {
						public void myEventOccurred(MyEvent evt) {
						// MyEvent was fired
						controlwindow.setTitle("Not ok CalcPos Control Window :"+controlwindow.Getsw()+" or "+evt.getTD()+" "+evt.getSW()+" "+evt.getOS());
						dim.CalcPos(controlwindow.disppar);
						controlwindow.setTitle("Not ok CalcWidth Control Window :"+controlwindow.Getsw()+" or "+evt.getTD()+" "+evt.getSW()+" "+evt.getOS());

						dim.CalcWidth(controlwindow.disppar);						controlwindow.setTitle("Not ok CalcOverlap Control Window :"+controlwindow.Getsw()+" or "+evt.getTD()+" "+evt.getSW()+" "+evt.getOS());
						dim.CalcOverlap(controlwindow.disppar);						controlwindow.setTitle("OK Control Window :"+controlwindow.Getsw()+" or "+evt.getTD()+" "+evt.getSW()+" "+evt.getOS());
						dim.calcTicks(controlwindow.disppar);controlwindow.setTitle("Control window v1.0");
						displaywindow.update();
						// in case need access to the object :ControlWindow =  evt.getControlWindow(); // http://www.cs.bgu.ac.il/~elhadad/se/events.html
					}
				});
				
				controlwindow.setVisible(true);	
        m_mess.setText("Check dim : " +dim.ppm);

				displaywindow = new DisplayWindow(dim);//sfo, sw, full spectral widht (for limit in slider)
				displaywindow.setSize(350, 200);	//controlwindow.pack();
				displaywindow.setLocation(100, 300);	
				displaywindow.setTitle("Display window v1.0");	
				//   displaywindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); Not working !!
				displaywindow.setVisible(true);		


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

    }
	public void adjustmentValueChanged(AdjustmentEvent e) {
						controlwindow.setTitle("Tcho22.."+controlwindow.Getsw());
	}
    public void paint (Graphics g) {
        super.paint(g);
//        g.setColor(Color.blue);
//        g.setFont(font);
//        g.drawString(mess, 40, 80);
//        g.drawString(""+getWidth(), 40, 120);
//		g.drawLine(0,0,50,50);
		 }
		 
	public void Optimize(String param,String t1max) {

		m_mess.setText("Optimizing...");
		displaywindow.repaint();info.setText("Optimizing...");
		if(Double.parseDouble(param) < 0.d )	info.setText( cosa.Optimize(dim,controlwindow,10.0d,-1					 ,Double.parseDouble(t1max)	) );
		if(Double.parseDouble(param) == 0.d )	info.setText( cosa.Optimize(dim,controlwindow,10.0d,0					 ,Double.parseDouble(t1max)	) );
		if(Double.parseDouble(param) == 1.d )info.setText( cosa.Optimize(dim,controlwindow,10.0d,controlwindow.Gettd(),Double.parseDouble(t1max)	) );
		//control window to provide sfo 
													//10.0 is per, the percentage of added t1max allowed
													//0 means optimize TD as well otherwise value of TD.
		controlwindow.Setsw(cosa.sw);
		controlwindow.Settd(cosa.td);
		m_mess.setText("Opt"+Double.parseDouble(t1max)+"_");
		displaywindow.repaint();
	}
	public void setdata1_5(String isfo,String ippm, String ilw, String ihppm1 ,String ihppm2) {
	
	//Double.parseDouble(message2)
	//	m_mess.setText("setdata1_5 1: " + isfo+" - "+ippm);
		controlwindow.disppar.sfo=Double.parseDouble(isfo);
	//	m_mess.setText("setdata1_5 2: " + sfo+" - "+ippm);
		dim.ppm.clear();
		dim.lw.clear();
		dim.hppm1.clear();
		dim.hppm2.clear();
		for(i=0;i<ippm.split(",").length;i++){
			dim.ppm.add(Double.parseDouble(ippm.split(",")[i]));
			dim.lw.add(Double.parseDouble(ilw.split(",")[i]));
			if(Double.parseDouble(ihppm1.split(",")[i]) > Double.parseDouble(ihppm2.split(",")[i])){
				dim.hppm1.add(Double.parseDouble(ihppm1.split(",")[i]));
				dim.hppm2.add(Double.parseDouble(ihppm2.split(",")[i]));
			}else{
				dim.hppm1.add(Double.parseDouble(ihppm2.split(",")[i]));
				dim.hppm2.add(Double.parseDouble(ihppm1.split(",")[i]));
			}
			m_mess.setText("setdata1_5 4: " + dim.ppm.get(i)+"ok"+i);
	//		hppm[i][1]=Double.parseDouble(ihppm1.split(",")[i]);
	//		hppm[i][2]=Double.parseDouble(ihppm2.split(",")[i]);
		}
		if(controlwindow.disppar.sw==-1d){
			dim.calcFWMW();
			dim.calcTicks(controlwindow.disppar);
			controlwindow.Setsw(dim.fullwidth);
			controlwindow.Setcf(dim.midwind);
		}
		dim.CalcPos(controlwindow.disppar);
		dim.CalcWidth(controlwindow.disppar);
		dim.CalcOverlap(controlwindow.disppar);
		dim.calcTicks(controlwindow.disppar);
		displaywindow.update();
	}
	public void setMessag(String message2) 
    { 
        //process message 
        m_mess.setText("Selection:" + message2);
		displaywindow.repaint();
    }
 }
