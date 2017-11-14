
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.util.*;
import static java.lang.Math.*;
import java.text.DecimalFormat;

public class Dim {
//chem shifts
	LinkedList<Double> ppm = new LinkedList<Double>();			// chemical shifts
	LinkedList<Double> pos01 = new LinkedList<Double>();		//position in the dimension (0-1) of full-width
	LinkedList<String>    Val_n = new LinkedList<String>();		//sheet number n : ppm = ppm+N*sw
	LinkedList<String>    Lab = new LinkedList<String>();		//sheet number n : ppm = ppm+N*sw
	Double fullwidth;
	Double midwind;
//width		
	LinkedList<Double> lw = new LinkedList<Double>();			// Signal half line width
	LinkedList<Double> width01 = new LinkedList<Double>();		// natural line width in (0-1) units FULL WIDTHS
	LinkedList<Double> width01n = new LinkedList<Double>();		// total (nat + proc) width in (0-1) units FULL WIDTHS
//Overlap	
	LinkedList<Double> overlappos = new LinkedList<Double>();		// width in (0-1) units 
	LinkedList<Double> overlapwidth = new LinkedList<Double>();		// width in (0-1) units 
	LinkedList<Double> overlappos_h = new LinkedList<Double>();		// width in (0-1) units 
	LinkedList<Double> overlapwidth_h = new LinkedList<Double>();		// width in (0-1) units 
	LinkedList<String> overlaptext = new LinkedList<String>();		// width in (0-1) units 
	
//directly detected dimension - to be removed in truly n-dimensional version	
	LinkedList<Double> hppm1 = new LinkedList<Double>(); /* may disapper in future versions*/
	LinkedList<Double> hppm2 = new LinkedList<Double>(); /* may disapper in future versions*/
	Double hoffset;
	Double hwidth;
	
// Ticks
	LinkedList<Double> broad_ticks = new LinkedList<Double>();		//position of braod ticks (0-1)
	LinkedList<Double> narrow_ticks = new LinkedList<Double>();		//position of narrow ticks (0-1)
	LinkedList<String> ticks_labels = new LinkedList<String>();		// labels of ticks
	DecimalFormat formatter = new DecimalFormat("#");
	DecimalFormat format2 = new DecimalFormat("#.###");
	DecimalFormat format3 = new DecimalFormat("#.###");
	public int sheet_number=0;//not really used never change value ... see below
	public void calcTicks(DisplayParam disppar){
			double spaceBetweekTicks=1000,tmp;
			broad_ticks.clear();
			narrow_ticks.clear();
			ticks_labels.clear();
			if(disppar.sw < 5000d) {spaceBetweekTicks=100d;formatter.setMinimumFractionDigits(0);}
			if(disppar.sw < 500d) {spaceBetweekTicks=10d;formatter.setMinimumFractionDigits(0);}
			if(disppar.sw < 50d) {spaceBetweekTicks=1d;formatter.setMinimumFractionDigits(0);}
			if(disppar.sw < 5d) {spaceBetweekTicks=0.1d;formatter.setMinimumFractionDigits(1);}
			if(disppar.sw < .5d) {spaceBetweekTicks=0.01d;formatter.setMinimumFractionDigits(2);}
			if(disppar.sw < .05d) {spaceBetweekTicks=0.001d;formatter.setMinimumFractionDigits(3);}

		for(double here=(disppar.cf-disppar.sw/2d+(double)sheet_number*disppar.sw) - ((disppar.cf-disppar.sw/2d+(double)sheet_number*disppar.sw) % spaceBetweekTicks)+spaceBetweekTicks;here < disppar.cf+disppar.sw/2d+(double)sheet_number*disppar.sw; here+=spaceBetweekTicks ) {
				tmp=((here-disppar.sw/2d-disppar.cf) % disppar.sw) / disppar.sw; if(tmp < 0d) tmp+=1.d;//in ppm
				broad_ticks.add(tmp);
			ticks_labels.add(formatter.format(here)+" + N * "+disppar.sw);
		}
		spaceBetweekTicks/=10d;
		for(double here=(disppar.cf-disppar.sw/2d+(double)sheet_number*disppar.sw) - ((disppar.cf-disppar.sw/2d+(double)sheet_number*disppar.sw) % spaceBetweekTicks)+spaceBetweekTicks;here < disppar.cf+disppar.sw/2d+(double)sheet_number*disppar.sw; here+=spaceBetweekTicks ) {
				tmp=((here-disppar.sw/2d-disppar.cf) % disppar.sw) / disppar.sw; if(tmp < 0d) tmp+=1.d;//in ppm
				narrow_ticks.add(tmp);
		}
//		for(double here=(disppar.cf-disppar.sw/2d+(double)sheet_number*disppar.sw) - ((disppar.cf-disppar.sw/2d+(double)sheet_number*disppar.sw) % spaceBetweekTicks);here < disppar.cf+disppar.sw/2d+(double)sheet_number*disppar.sw; here+=spaceBetweekTicks ) {
//			narrow_ticks.add((here+disppar.sw/2d-disppar.cf)  / disppar.sw);
//		}
	}

	
	public void calcFWMW(){
	//this dimension calculations
		double min=9999999,max=-9999999;
		for(int i=0;i<ppm.size();i++){ // improve thisx
			if(ppm.get(i)>max) max=ppm.get(i);
			if(ppm.get(i)<min) min=ppm.get(i);
		}
		fullwidth=max-min;
		midwind=(max+min)/2.0;
	/// Direct dimension calculations....
		min=9999999;max=-9999999;
		for(int i=0;i<hppm1.size();i++){ // improve this
			if(hppm1.get(i)>max) max=hppm1.get(i);
			if(hppm1.get(i)<min) min=hppm1.get(i);
		}
		for(int i=0;i<hppm2.size();i++){ // improve this
			if(hppm2.get(i)>max) max=hppm2.get(i);
			if(hppm2.get(i)<min) min=hppm2.get(i);
		}
		hoffset=max;
		hwidth=(max-min);
	}



	public void CalcPos(DisplayParam disppar){/// units 0-1
		Double tmp,tmp2,tmp3;
		pos01.clear();
		Val_n.clear();
		Lab.clear();
		for(int i=0;i<ppm.size();i++){

			tmp =((ppm.get(i)-disppar.sw/2d-disppar.cf) % disppar.sw) / disppar.sw; //in ppm
				if(tmp > 0d){
					pos01.add(tmp);
					tmp2=((ppm.get(i)-disppar.sw/2d-disppar.cf)  - ((ppm.get(i)-disppar.sw/2d-disppar.cf) % disppar.sw)) / disppar.sw; //in ppm
					tmp2+=1d;
					tmp3=((ppm.get(i)-disppar.sw/2d-disppar.cf) % disppar.sw) -disppar.sw/2d+disppar.cf; //in ppm

				}else {
					pos01.add(tmp+1d);
					tmp2=((ppm.get(i)-disppar.sw/2d-disppar.cf)  - ((ppm.get(i)-disppar.sw/2d-disppar.cf) % disppar.sw)) / disppar.sw; //in ppm
					tmp3=((ppm.get(i)-disppar.sw/2d-disppar.cf) % disppar.sw) +disppar.sw/2d+disppar.cf; //in ppm
				}
				Val_n.add("S"+(i+1)+":"+format2.format(tmp3)+" N="+tmp2.intValue()+" ("+format3.format(ppm.get(i))+")");
				Lab.add("S"+(i+1));

			tmp2=((ppm.get(i)-disppar.sw/2d-disppar.cf)  - ((ppm.get(i)-disppar.sw/2d-disppar.cf) % disppar.sw)) / disppar.sw; //in ppm

		}
	}
	
	
	
	public void CalcWidth(DisplayParam disppar){ /// units 0-1
//		vwn[j]=       ((double)height*2d*lw[i] / ( (sw[0]) * sfo ));
//  	vw [j]=vwn[j]+(height*2d*(sfo*sw[0]/(double)td[0])/(sw[0]*sfo));
		Double tmp;
		int tmpi;
		width01.clear();
		width01n.clear();
		tmpi=disppar.td;
		for(int i=0;i<lw.size();i++){
			tmp=(lw.get(i)*2d/disppar.sfo) / disppar.sw; //full withd in ppm  unit 0-1
			width01n.add(tmp);//full width
			if(tmpi!=0.0d) tmp+=(2.000d/tmpi); // for QSINE processing ingnoring fact that LW do not simply add : convolution products....
			width01.add(tmp); //full width
		}
	}	
	
	
	
	public void CalcOverlap(DisplayParam disppar){/// units  0-1
		int i,j,flagh,flag;
		double fromh=0d,toh=0d,from=0d,to=0d,ppm1i,ppm1j,ppm2i,ppm2j;
		double addmelist1[]={-1d, 0d, 1d};
		double addmelist2[]={-1d, 0d, 1d};
		overlappos.clear();
		overlapwidth.clear();
		overlappos_h.clear();
		overlapwidth_h.clear();
		overlaptext.clear();
		for(double addme1 : addmelist1) //to see overlap with signals at top and bottom of the windows...
		for(double addme2 : addmelist2) //to see overlap with signals at top and bottom of the windows...
		for(i=0;i<pos01.size();i++)
		for(j=0;j<i;j++){
			//center
			 if(abs(addme1+pos01.get(i)-(addme2+pos01.get(j)))<0.5d*(width01.get(i)+width01.get(j))) {//overlap in F1 (width01 are full-widths)
			 //determine center in F1
				ppm1i=addme1+pos01.get(i)+0.5d*width01.get(i);
				ppm2i=addme1+pos01.get(i)-0.5d*width01.get(i);
				ppm1j=addme2+pos01.get(j)+0.5d*width01.get(j);
				ppm2j=addme2+pos01.get(j)-0.5d*width01.get(j);
				flag=0;
				if	 (ppm1j>=ppm1i && ppm2j<=ppm2i)            {flag=1;from=ppm1i;to=ppm2i;}//inclusions of i in j - assumes 1>2 - therefore from > to
				if	 (ppm1i>=ppm1j && ppm2i<=ppm2j)            {flag=1;from=ppm1j;to=ppm2j;}//inclusions of j in i - assumes 1>2    
				if	 (ppm1j> ppm2i && ppm1j<=ppm1i && flag==0) {flag=1;from=ppm1j;to=ppm2i;}//imbirc
				if	 (ppm1i> ppm2j && ppm1i<=ppm1j && flag==0) {flag=1;from=ppm1i;to=ppm2j;}//imbirc
				//flag also indicates overlap in F1...
				flagh=0;
				if	 (hppm1.get(j)>=hppm1.get(i) && hppm2.get(j)<=hppm2.get(i))             {flagh=1;fromh=hppm1.get(i);toh=hppm2.get(i);}//inclusions of i in j - assumes 1>2
				if	 (hppm1.get(i)>=hppm1.get(j) && hppm2.get(i)<=hppm2.get(j))             {flagh=1;fromh=hppm1.get(j);toh=hppm2.get(j);}//inclusions      
				if	 (hppm1.get(j)> hppm2.get(i) && hppm1.get(j)<=hppm1.get(i) && flagh==0) {flagh=1;fromh=hppm1.get(j);toh=hppm2.get(i);}//imbirc
				if	 (hppm1.get(i)> hppm2.get(j) && hppm1.get(i)<=hppm1.get(j) && flagh==0) {flagh=1;fromh=hppm1.get(i);toh=hppm2.get(j);}//imbirc
				if	 (hppm2.get(j)> hppm2.get(i) && hppm2.get(j)<=hppm1.get(i) && flagh==0) {flagh=1;fromh=hppm1.get(i);toh=hppm2.get(j);}//imbirc
				if	 (hppm2.get(i)> hppm2.get(j) && hppm2.get(i)<=hppm1.get(j) && flagh==0) {flagh=1;fromh=hppm1.get(j);toh=hppm2.get(i);}//imbirc

					if(flagh == 1) { //overlap in F2
					overlappos.add(0.5d*(to+from));
					overlapwidth.add(from-to);// full width
					toh=(hoffset-toh)/hwidth;// unit 0-1
					fromh=(hoffset-fromh)/hwidth;// unit 0-1 From now on to>from ....
					overlappos_h.add(0.5d*(toh+fromh));
					overlapwidth_h.add(toh-fromh);// full width
					overlaptext.add("S"+(i+1)+"-S"+(j+1));// full width
				}

			}
			//top
			// if(overlap) add rectangle
			//bot
			// if(overlap) add rectangle
		}
	}
/*	public int GetSheet_number(){
		return sheet_number;
	}
	public void SetSheet_number(int tmp){
		if(tmp != sheet_number){
			sheet_number=tmp;
			//calcTicks(disppar);
		}
	}*/
}
