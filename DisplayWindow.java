import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import static java.lang.Math.*;
import java.text.DecimalFormat;


class CenDisplayPanel extends JPanel implements  MouseListener, MouseMotionListener{
	Insets ins;
	private int mouseX,mouseY,mousedown=0;
	private Dim dim= new Dim();
	CenDisplayPanel(Dim idim){
		dim=idim;
		//setBorder(BorderFactory.createLineBorder(Color.RED,5));
			addMouseMotionListener(this);
			addMouseListener(this);

	}
	protected void paintComponent(Graphics g){
		int tmpx,tmpy;
		double tmpp,tmp,tmph,tmpph;
		double addmelist[]={-1d, 0d, 1d};
		super.paintComponent(g);
			g.setColor(Color.black);
		//all black lines side to side	
		for(int i=0;i<dim.ppm.size();i++){
					g.setColor(Color.black);
			tmpp=(dim.hoffset-0.5d*(dim.hppm1.get(i)+dim.hppm2.get(i)))/dim.hwidth; // 0-1 units
			tmpp=tmpp*(double)getWidth();
			tmp=abs(dim.hppm1.get(i)-dim.hppm2.get(i))/dim.hwidth;//full width
			tmp=tmp*(double)getWidth();

		    	if(mouseX>=(int)(tmpp-0.5d*tmp))
				 if(mouseX<=(int)(tmpp-0.5d*tmp)+                                                                            (int)(tmp))
				 if(mouseY>=                     (int)((dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight()))
				 if(mouseY<=                     (int)((dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight())+            (int)(    dim.width01.get(i)*(double)getHeight())) {
						g.setColor(Color.red);
	//	dim.SetSheet_number(dim.Val_n.get(i).intValue());   
				}

			g.drawLine(0,(int)(dim.pos01.get(i)*(double)getHeight()),getWidth(),(int)(dim.pos01.get(i)*(double)getHeight()));
		}
		//signals
		for(double addme : addmelist) //to see signals also at top and bottom when at the edge
			for(int i=0;i<dim.ppm.size();i++){
			// proton dimension
			tmpp=(dim.hoffset-0.5d*(dim.hppm1.get(i)+dim.hppm2.get(i)))/dim.hwidth; // 0-1 units
			tmpp=tmpp*(double)getWidth();
			tmp=abs(dim.hppm1.get(i)-dim.hppm2.get(i))/dim.hwidth;//full width
			tmp=tmp*(double)getWidth();
			
        	g.setColor(Color.yellow);
				g.fillRect((int)(tmpp-0.5d*tmp),(int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight()),(int)(tmp),(int)(addme+dim.width01.get(i)*(double)getHeight()));
        	g.setColor(Color.green);
				g.fillRect((int)(tmpp-0.5d*tmp),(int)((addme+dim.pos01.get(i)-0.5d*dim.width01n.get(i))*(double)getHeight()),(int)(tmp),(int)(addme+dim.width01n.get(i)*(double)getHeight()));
				g.setColor(Color.black);				 
				 if(mouseX>=(int)(tmpp-0.5d*tmp))
				 if(mouseX<=(int)(tmpp-0.5d*tmp)+                                                                            (int)(tmp))
				 if(mouseY>=                     (int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight()))
				 if(mouseY<=                     (int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight())+            (int)(addme+dim.width01.get(i)*(double)getHeight())) {
						g.setColor(Color.red);
	//	dim.SetSheet_number(dim.Val_n.get(i).intValue());   
				}
				g.drawRect((int)(tmpp-0.5d*tmp),(int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight()),(int)(tmp),(int)(addme+dim.width01.get(i)*(double)getHeight()));
			if(mouseX>=(int)(tmpp-0.5d*tmp))
				 if(mouseX<=(int)(tmpp-0.5d*tmp)+                                                                            (int)(tmp))
				 if(mouseY>=                     (int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight()))
				 if(mouseY<=                     (int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight())+            (int)(addme+dim.width01.get(i)*(double)getHeight())) {
						g.setColor(Color.red);
						tmpx=(int)(tmpp-0.5d*tmp);
						tmpy=(int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight());
						if(tmpy<20){tmpy=20;tmpx+=20;}
						if((getWidth()-tmpx)<250)tmpx=getWidth()-250;
						//	done below for text to be above 	g.drawString(dim.Val_n.get(i),tmpx,tmpy);

				}
				//g.drawString(""+dim.Val_n.get(i).intValue(),(int)(tmpp-0.5d*tmp),(int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight()));

		}
		for(int i=0;i<dim.overlappos.size();i++){
		//overalp
			//if(i==0) g.drawString(""+(dim.overlapwidth.get(i))+" "+(dim.overlappos.get(i))+" "+  (dim.overlapwidth_h.get(i))+" "+(dim.overlappos_h.get(i)) , getWidth()/2,  getHeight()/2);

			tmp=dim.overlapwidth.get(i);//full widths
			tmp=tmp*(double)getHeight();
			
			tmpp=dim.overlappos.get(i);
			tmpp=tmpp*(double)getHeight()-0.5d*tmp;
			
			tmph=dim.overlapwidth_h.get(i);// full widths
			tmph=tmph*(double)getWidth();
			
			tmpph=dim.overlappos_h.get(i)-0.5d*dim.overlapwidth_h.get(i);
			tmpph=tmpph*(double)getWidth();
				g.setColor(Color.red);
				g.fillRect((int)(tmpph),(int)(tmpp),  (int)(tmph),(int)(tmp) );
				g.setColor(Color.black);
				g.drawRect((int)(tmpph),(int)(tmpp),  (int)(tmph),(int)(tmp) );
				g.setColor(Color.blue);
				//g.drawString(dim.overlaptext.get(i),(int)(tmpph+tmph),(int)(tmpp-1));
		}
		//g.drawString("mid wind "+ getWidth()+" "+ getHeight()+" : "+dim.ppm.get(0)+" >"+dim.ppm.size(), getWidth()/2,  getHeight()/2);
		//g.drawString("md wind "+ getWidth()+" "+ getHeight()+" : > "+dim.pos01+" "+dim.ppm.size(), getWidth()/2,  getHeight()/2);
						g.setColor(Color.red);
			//	g.setColor(Color.red);	g.drawString(":"+dim.ticks_labels+".", 10,  10);

		if(mousedown==1) {
				g.setColor(Color.red);
				//g.drawString(""+mouseX+" "+mouseY,mouseX,mouseY);
				g.setColor(Color.gray);
				g.drawLine(0,mouseY,getWidth(),mouseY);
				g.drawLine(mouseX,0,mouseX,getHeight());

		}
		//signals second time for lables to be on top...
		for(double addme : addmelist) //to see signals also at top and bottom when at the edge
			for(int i=0;i<dim.ppm.size();i++){
			// proton dimension
			tmpp=(dim.hoffset-0.5d*(dim.hppm1.get(i)+dim.hppm2.get(i)))/dim.hwidth; // 0-1 units
			tmpp=tmpp*(double)getWidth();
			tmp=abs(dim.hppm1.get(i)-dim.hppm2.get(i))/dim.hwidth;//full width
			tmp=tmp*(double)getWidth();
			if(mouseX>=(int)(tmpp-0.5d*tmp) &
				 mouseX<=(int)(tmpp-0.5d*tmp)+                                                                            (int)(tmp) &
				 mouseY>=                     (int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight()) &
				 mouseY<=                     (int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight())+            (int)(addme+dim.width01.get(i)*(double)getHeight())) {
						g.setColor(Color.red);
						tmpx=(int)(tmpp-0.5d*tmp);
						tmpy=(int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight());
						if(tmpy<20){tmpy=20;tmpx+=20;}
						if((getWidth()-tmpx)<250)tmpx=getWidth()-250;
						g.drawString(dim.Val_n.get(i),tmpx,tmpy);

				}else{
						tmpx=(int)(tmpp-0.5d*tmp);
						tmpy=(int)((addme+dim.pos01.get(i)-0.5d*dim.width01.get(i))*(double)getHeight());
						g.setColor(Color.black);
						g.drawString(dim.Lab.get(i),tmpx,tmpy);
				}
		}

	}
	public void mouseMoved(MouseEvent me){mouseX=me.getX();mouseY=me.getY();repaint();}
	public void mouseDragged(MouseEvent me){mouseX=me.getX();mouseY=me.getY();repaint();}
	
	public void mouseExited(MouseEvent me){;}
	public void mouseEntered(MouseEvent me){;}
	public void mouseClicked(MouseEvent me){;}
	public void mousePressed(MouseEvent me){mousedown=1;repaint();}
	public void mouseReleased(MouseEvent me){mousedown=0;repaint();}
 
	
}
class RightDisplayPanel extends JPanel{
	Insets ins;
	private Dim dim= new Dim();
	RightDisplayPanel(Dim idim){
		dim=idim;
		//setBorder(BorderFactory.createLineBorder(Color.RED,5));
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawString("F1", 120,  getHeight()/2);
		g.setColor(Color.blue);
		g.drawLine(0,0,0,getHeight());
		g.setColor(Color.BLACK);
/*		for(int i=0;i<dim.ppm.size();i++){
				g.drawLine(0,(int)(dim.pos01.get(i)*(double)getHeight()),getWidth(),(int)(dim.pos01.get(i)*(double)getHeight()));
		}*/
		g.setColor(Color.blue);
		
		for(int i=0;i<dim.broad_ticks.size();i++){
				g.drawLine(0,(int)(dim.broad_ticks.get(i)*(double)getHeight()),6,(int)(dim.broad_ticks.get(i)*(double)getHeight()));
				g.drawString(dim.ticks_labels.get(i), 7,  (int)(dim.broad_ticks.get(i)*(double)getHeight()) + 4 );
		}
		for(int i=0;i<dim.narrow_ticks.size();i++){
				g.drawLine(0,(int)(dim.narrow_ticks.get(i)*(double)getHeight()),3,(int)(dim.narrow_ticks.get(i)*(double)getHeight()));
		}


	}
}
class BottomDisplayPanel extends JPanel{
	Insets ins;
	private Dim dim= new Dim();
	double spaceBetweekTicks=1000,tmpd;
	DecimalFormat formatter = new DecimalFormat("#");
	int usablewidth;
	int width_right_panel;
	BottomDisplayPanel(Dim idim,int iwidth_right_panel){
		dim=idim;
		width_right_panel=iwidth_right_panel;
		//setBorder(BorderFactory.createLineBorder(Color.RED,5));
	}
	protected void paintComponent(Graphics g){
		usablewidth=getWidth()-width_right_panel;
		super.paintComponent(g);
		g.setColor(Color.black);
		g.drawString("F2", usablewidth/2,29);
		g.setColor(Color.blue);
		g.drawLine(0,0,usablewidth,0);
			if(dim.hwidth < 2000d) {spaceBetweekTicks=100d;formatter.setMinimumFractionDigits(0);}
			if(dim.hwidth < 200d) {spaceBetweekTicks=10d;formatter.setMinimumFractionDigits(0);}
			if(dim.hwidth < 20d) {spaceBetweekTicks=1d;formatter.setMinimumFractionDigits(0);}
			if(dim.hwidth< 2d) {spaceBetweekTicks=0.1d;formatter.setMinimumFractionDigits(1);}
			if(dim.hwidth < .2d) {spaceBetweekTicks=0.01d;formatter.setMinimumFractionDigits(2);}
			if(dim.hwidth < .20d) {spaceBetweekTicks=0.001d;formatter.setMinimumFractionDigits(3);}
			
	//			tmpp=(dim.hoffset-0.5d*(dim.hppm1.get(i)+dim.hppm2.get(i)))/dim.hwidth; // 0-1 units
	//		tmpp=tmpp*(double)getWidth();
		
		
		for(double here=(dim.hoffset) - ((dim.hoffset) % spaceBetweekTicks);here > dim.hoffset-dim.hwidth; here-=spaceBetweekTicks ) {
					tmpd=(-here+dim.hoffset)  / dim.hwidth;
					g.drawLine((int)(tmpd*usablewidth),0,(int)(tmpd*usablewidth),6);
					g.drawString(formatter.format(here),(int)(tmpd*usablewidth)-4,18);
		}
		spaceBetweekTicks/=10d;
		for(double here=(dim.hoffset) - ((dim.hoffset) % spaceBetweekTicks);here > dim.hoffset-dim.hwidth; here-=spaceBetweekTicks ) {
					tmpd=(-here+dim.hoffset)  / dim.hwidth;
					g.drawLine((int)(tmpd*usablewidth),0,(int)(tmpd*usablewidth),3);
		}

	
	}
}
class TopDisplayPanel extends JPanel{
		int width_right_panel;
		TopDisplayPanel(int iwidth_right_panel){
				width_right_panel=iwidth_right_panel;
			}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.blue);
		g.drawLine(0,0,getWidth()-width_right_panel,0);
			}
}
class LeftDisplayPanel extends JPanel{
		LeftDisplayPanel(){
			}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.blue);
		g.drawLine(0,0,0,getHeight());
			}
}
public class DisplayWindow extends JFrame   {
	//private Dim dim;	
	public CenDisplayPanel canc;
	public RightDisplayPanel canr;
	public LeftDisplayPanel canl;
//	public TopDisplayPanel cant;
	public BottomDisplayPanel canb;
	public TopDisplayPanel cant;
	public int i;
	public int width_right_panel=150;
	public DisplayWindow(Dim idim) {
	setTitle("bnumber of elements"+idim.ppm.size());
	//	dim = new Dim();
	//	dim=idim;
			JPanel wholepanel = new JPanel();
			wholepanel.setLayout(new BorderLayout());
				JPanel drawpanel = new JPanel();
				drawpanel.setLayout(new BorderLayout());
					canc = new CenDisplayPanel(idim);
					//can.setBackground(Color.white);
				drawpanel.add(BorderLayout.CENTER,canc);
					canr = new RightDisplayPanel(idim);
					canr.setPreferredSize(new Dimension(width_right_panel, 1));
					//can.setBackground(Color.white);
				drawpanel.add(BorderLayout.EAST,canr);
					canb = new BottomDisplayPanel(idim,width_right_panel);
					canb.setPreferredSize(new Dimension(1, 30));
					//canb.setBackground(Color.white);
				drawpanel.add(BorderLayout.SOUTH,canb);
					cant = new TopDisplayPanel(width_right_panel);
					cant.setPreferredSize(new Dimension(1, 1));
					//canb.setBackground(Color.white);
				drawpanel.add(BorderLayout.NORTH,cant);
					canl = new LeftDisplayPanel();
					canl.setPreferredSize(new Dimension(1, 1));
					//canb.setBackground(Color.white);
				drawpanel.add(BorderLayout.WEST,canl);

			wholepanel.add(BorderLayout.CENTER,drawpanel);
		getContentPane().add(wholepanel);
			repaint();
	}
	public void update(){
		repaint();
	}


	
}


