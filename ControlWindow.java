import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;


public class ControlWindow extends JFrame  {

 // Create the listener list
protected javax.swing.event.EventListenerList listenerList = new javax.swing.event.EventListenerList();
DisplayParam disppar = new DisplayParam();
JTextField  textfield;
JTextField tdtextfield;
JTextField ostextfield;
DJPanel can;
Scrollbar scrollbar,scrollbar2;
Scrollbar tdscrollbar,osscrollbar;

private Double sfo_no_cursor;
	public ControlWindow() {
			
			JPanel alldimpanel = new JPanel();
			alldimpanel.setLayout(new GridBagLayout()); //from left to right
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				JPanel firstdimpanel = new JPanel();
				c.weightx = 0.5;// 1/ the number of dimensions !!!!!!!!!!!!!!!!!!
				c.weighty = 1.0;// full width
				c.gridx = 0;
				c.gridy = 0;
				alldimpanel.add(firstdimpanel, c);
				
				JPanel seconddimpanel = new JPanel();
				c.gridx = 1;
				alldimpanel.add(seconddimpanel, c);

				//button = new JButton("Button 3");
				//c.gridx = 2;
				//c.gridy = 0;
				//alldimpanel.add(button, c);	
						//// first dim....
				firstdimpanel.setLayout(new BorderLayout()); 
				firstdimpanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
				firstdimpanel.add(BorderLayout.NORTH,new JLabel("Dimensions 1",JLabel.CENTER));
					JPanel TDpanel = new JPanel();
					TDpanel.setLayout(new BorderLayout()); 
					TDpanel.setBorder(BorderFactory.createLineBorder(Color.RED,1));
				//		toppanel.setBackground(Color.white);
						JLabel tdlabelone = new JLabel("N =",JLabel.CENTER);
					TDpanel.add(BorderLayout.NORTH,tdlabelone);
					//middlepanel.setBackground(Color.white);
							tdscrollbar  = new Scrollbar(Scrollbar.VERTICAL, 1000-disppar.td,50,0, 1048);// initial value, visible, min,max (from 2 to 1000)
							tdscrollbar.setBlockIncrement(10);//default 10
							tdscrollbar.setUnitIncrement(2);
							tdscrollbar.addAdjustmentListener(new AdjustmentListener() {
								public void adjustmentValueChanged(AdjustmentEvent e2) {
											disppar.td=1000-tdscrollbar.getValue();
											updatetd(disppar.td);
								}
							});
					TDpanel.add(BorderLayout.WEST,tdscrollbar);
							tdtextfield = new JTextField(""+disppar.td,4);
					TDpanel.add(BorderLayout.SOUTH,tdtextfield);
							tdtextfield.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent event) { 
									disppar.td=Integer.valueOf(tdtextfield.getText());
									updatetd(disppar.td);
								}
							});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					JPanel SWpanel = new JPanel();
					SWpanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,1));
					SWpanel.setLayout(new BorderLayout()); 
						JLabel labelone = new JLabel("SW =",JLabel.CENTER);
					SWpanel.add(BorderLayout.NORTH,labelone);

							can = new DJPanel(disppar);
							can.setBackground(Color.white);
							//seems useless here !can.setPreferredSize(new Dimension(20, 10));
							//seems useless here ! can.setSize(180,180);//also see alias.java
					SWpanel.add(BorderLayout.CENTER,can);
					//middlepanel.setBackground(Color.white);
							JPanel scrpanel = new JPanel();
							scrpanel.setLayout(new BorderLayout()); 
								scrollbar  = new Scrollbar(Scrollbar.VERTICAL,   10000,     10, 0, 1000000);
									scrollbar.setMinimum((int)(0.1*(double)scrollbar.getMaximum()/disppar.sw));
								scrollbar2  = new Scrollbar(Scrollbar.VERTICAL, 0, 10,-1000, 1000);		
				scrollbar.addAdjustmentListener(new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e1) {
								scrollbar2.setValue(0);
								disppar.sw=((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000));
								updatesw(disppar.sw);
					}
				});
				scrollbar2.addAdjustmentListener(new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e2) {
								disppar.sw=((0.1d/(float)(scrollbar.getValue()+scrollbar2.getValue())*1000000));
								updatesw(disppar.sw);
					}
				});
							scrpanel.add(BorderLayout.EAST,scrollbar);
							scrpanel.add(BorderLayout.WEST,scrollbar2);
					SWpanel.add(BorderLayout.WEST,scrpanel);
							textfield = new JTextField(""+disppar.sw);
							textfield.setColumns(10);
					SWpanel.add(BorderLayout.SOUTH,textfield);
							textfield.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent event) { 
									disppar.sw=Double.parseDouble(textfield.getText());
									updatesw(disppar.sw);
								}
							});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					JPanel Opanel = new JPanel();
					Opanel.setLayout(new BorderLayout()); 
					Opanel.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
				//		toppanel.setBackground(Color.white);
						JLabel oslabelone = new JLabel("Offset=",JLabel.CENTER);
					Opanel.add(BorderLayout.NORTH,oslabelone);
					//middlepanel.setBackground(Color.white);
					sfo_no_cursor=disppar.cf;
							osscrollbar  = new Scrollbar(Scrollbar.VERTICAL, 0,10,-1000, 1010);// initial value, visible, min,max (from 2 to 1000)
							osscrollbar.setBlockIncrement(10);//default 10
							osscrollbar.setUnitIncrement(2);
							osscrollbar.addAdjustmentListener(new AdjustmentListener() {
								public void adjustmentValueChanged(AdjustmentEvent e2) {
											disppar.cf=sfo_no_cursor-((double)(osscrollbar.getValue())/2000d*disppar.sw);
											updateos(disppar.cf);
								}
							});
					Opanel.add(BorderLayout.WEST,osscrollbar);
							ostextfield = new JTextField(""+disppar.cf,4);
					Opanel.add(BorderLayout.SOUTH,ostextfield);
							ostextfield.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent event) { 
									sfo_no_cursor=Double.parseDouble(ostextfield.getText());
									disppar.cf=sfo_no_cursor;
									osscrollbar.setValue(0);
									updateos(disppar.cf);
								}
							});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					firstdimpanel.add(BorderLayout.WEST,TDpanel);
					firstdimpanel.add(BorderLayout.CENTER,SWpanel);
					firstdimpanel.add(BorderLayout.EAST,Opanel);

				//////
				getContentPane().add(alldimpanel);
				this.repaint();
			//	setDefaultCloseOperation(EXIT_ON_CLOSE); DO NOT PUT THIS in Aplets!!
			
			
                
	}
 


    
        // This methods allows classes to register for MyEvents
        public void addMyEventListener(MyEventListener listener) {
            listenerList.add(MyEventListener.class, listener);
        }
    
        // This methods allows classes to unregister for MyEvents
        public void removeMyEventListener(MyEventListener listener) {
            listenerList.remove(MyEventListener.class, listener);
        }
    
        // This private class is used to fire MyEvents
		

        void fireMyEvent() {
		    MyEvent e = new MyEvent(this, disppar);
            Object[] listeners = listenerList.getListenerList();
            // Each listener occupies two elements - the first is the listener class
            // and the second is the listener instance
            for (int i=0; i<listeners.length; i+=2) {
                if (listeners[i]==MyEventListener.class) {
                    ((MyEventListener)listeners[i+1]).myEventOccurred(e);
                }
            }
        }
	public Double Getsw(){
		return disppar.sw;
	}
	public int Gettd(){
		return disppar.td;
	}
	public void Setsw(Double in){
		disppar.sw=in;
		updatesw(disppar.sw);
	}
	public void Settd(int in){
		disppar.td=in;
		updatetd(disppar.td);
	}
	public void Setcf(Double in){
		sfo_no_cursor=in;
		disppar.cf=in;
		updateos(disppar.cf);
	}
	public void updatetd(int tdi){
		tdtextfield.setText(""+tdi);
		tdscrollbar.setValue(1000-tdi);
		can.Settext("TD="+tdi);
		fireMyEvent();
	}
	public void updatesw(Double swi){
		textfield.setText(""+swi);
		scrollbar.setValue((int)(0.1d/swi*(double)scrollbar.getMaximum()));
		can.Settext("SW="+swi);
		fireMyEvent();
	}
	public void updateos(Double osi){
		ostextfield.setText(""+(osi));
		can.Settext("OS="+osi);
		fireMyEvent();
	}
}


class DJPanel extends JPanel{
	Insets ins;
	String text="Mid. win";
	DisplayParam disppar;
	//COnstructr a DJ panel
	DJPanel(DisplayParam idisppar){
		disppar=idisppar;
		//setBorder(BorderFactory.createLineBorder(Color.RED,5));
	}
	//OVerrid the paint Component() method
	protected void paintComponent(Graphics g){
	final int space_at_top=15;
	final int space_at_bot=15;
	final int main_scale_line_pos=10;
	final int cursor_pos=0;
	final int longtick_width=8;
	final int shorttick_width=4;
	final int cursor_width=20;
	final int values_position=20;
	int pos;
	int haut=getHeight()-(space_at_top+space_at_bot);
	Float ser[]={100f, 10f, 1f, 0.1f};

		super.paintComponent(g);
		//g.drawLine(0,0,10,10);
		//g.drawLine(0,180,180, 0);
		//g.drawString(text+" "+this.getWidth()+" "+this.getHeight(), getWidth()/2,  getHeight()/2);
		g.drawLine(main_scale_line_pos,space_at_top,main_scale_line_pos,space_at_top+haut);
		if(disppar.sw>0d){
				pos= (int)(((double)haut)*(3.0d-Math.log(disppar.sw)/Math.log(10d))/4.0d);
			if(pos>=0 && pos<=haut) g.drawLine(cursor_pos,space_at_top+pos,cursor_pos+cursor_width,space_at_top+pos);
		//	g.drawString(""+haut+" "+pos+" "+Math.log(disppar.sw)/Math.log(10d), getWidth()/2,  getHeight()/2);
		}
		for(Float ii: ser){
			pos= (int)(((double)haut)*(3.0d-Math.log(ii)/Math.log(10d))/4.0d);
			if(pos>=0 && pos<=haut) g.drawLine(main_scale_line_pos,space_at_top+pos,main_scale_line_pos+longtick_width,space_at_top+pos);
			g.drawString(Float.toString(ii),values_position,space_at_top+pos);
			for(Float iii=2f*ii;iii<(10f*ii);iii+=ii){
				pos= (int)(((double)haut)*(3.0d-Math.log(iii)/Math.log(10d))/4.0d);
				if(pos>=0 && pos<=haut) g.drawLine(main_scale_line_pos,space_at_top+pos,main_scale_line_pos+shorttick_width,space_at_top+pos);
			}
 		}
	}
	public void Settext(String txt){
		text=txt+"";this.repaint();
	}

}