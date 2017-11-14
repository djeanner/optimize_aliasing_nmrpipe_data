import java.awt.*;
import java.awt.event.*;
import java.math.*;

public class Cosa {

/****************************************/
/* factor DR required overs DR		*/
/*double fadr=0.5; was 1.0 before nov.04*/
public int td=0,td0=0;
public double sw=0,sw0=0;
public double  fadr=0.5; //IF change this change at HERE
public double swsw=0,swswt1=0,swswop=0;
public int factorbe=0,factort1=0,factorop=0;
public String comment="";
/*					*/
/* factor DR required overs linewidth	*/
double falv=1.0;
/*					*/
/* addition number of Hz		*/
double addr=0.000/2.0;
/****************************************/


//                 (           sfo,      nbc,           ppm,                 lw,           protdim, Integer.parseInt(tftd1.getText()),optimonlysw_m_v, sasi,       comment+commentopt)/sfo;
//public double Opttd(double in_freq,int nbcNO,double[] ppmNO,double addelta2NO[],double[][] protdim,int in_td,                         double per,     int[][] sasi,String commentdel){
public void Full(){
td=112;
sw=111.1d;
}

//public double Optimize(double in_freq,int nbcNO,double[] ppmNO,double addelta2NO[],double[][] protdim,int in_td,                         double per,     int[][] sasi,String commentdel){
public String Optimize(Dim dim,ControlWindow controlwindow,double per,int in_td,double inputt1max){

int nbc=dim.ppm.size();
double in_freq=controlwindow.disppar.sfo;
double[][] protdim=new double[nbc][3];
for(int i=0;i>nbc;i++){
	protdim[i][0]=1;
	protdim[i][1]=dim.hppm1.get(i);
	protdim[i][2]=dim.hppm2.get(i);
}
int[][] sasi=new int[nbc][nbc];
comment="";
 double[] ol = new double[nbc];
  double[] addelta = new double[nbc];
  double[] ppm= new double[nbc];

// double[][] protdim=   new double[nbcNO][5000];   /// CCOORREECCTTT TTTHHIISSS UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU
//int debug=0;
//int debug2=0;
double sw1h_2dr=0,in_sw;
double max,min;
double refmaxt1;
double sfo;
int	incrtd=0,tdwould,step;
int	firstfound;
int in_si,pr=0;
int l,i,j,k,m,n;
int in_nbcol=1;
int in_id;
/************ form preal ***************/
int si2,tmpi=0,tmpa=0,flagop=0;

double sasd;
double singp;
int	singptd;
/////////////////double  *sasdi;
/////////////////double  *sasdo;
/////////////////int	*sasi;


double tmpd;
double tmpd2;
double tmpd3;
double osw;
double nsw=0;


double[][] sasdi=   new double[nbc+1][nbc+1];
double[][] sasdo=   new double[nbc+1][nbc+1];
int	aflag,iflag=0;
double delta,tmpdmin,tmpdmax;

////////// ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ



max=-20000000;min=5000000;
for(i=0;i<nbc;i++) {
	ppm[i]=dim.ppm.get(i);
	addelta[i]=dim.lw.get(i);
	if(max<ppm[i]) max=ppm[i];
	if(min>ppm[i]) min=ppm[i];
	ol[i]=in_freq*ppm[i];

//	fprintf(stdout,"Signal #%3d :  %10.4lf ppm (%12.2lf Hz) width: %4.1lf Hz\n",i+1,ppmm[i],ol[i],2*addelta[i]);
}
///////////////////////////////////////fill sasi

for(i=0;i<nbc;i++) for(j=0;j<nbc;j++) sasi[i][j]=0;



int flagh=0;
for(i=0;i<dim.hppm1.size();i++) {
	for(j=i+1;j<dim.hppm2.size();j++) if(sasi[i][j]==0) {
				flagh=0;
				if	 (dim.hppm1.get(j)>=dim.hppm1.get(i) && dim.hppm2.get(j)<=dim.hppm2.get(i))             {flagh=1;}//inclusions of i in j - assumes 1>2
				if	 (dim.hppm1.get(i)>=dim.hppm1.get(j) && dim.hppm2.get(i)<=dim.hppm2.get(j))             {flagh=1;}//inclusions      
				if	 (dim.hppm1.get(j)> dim.hppm2.get(i) && dim.hppm1.get(j)<=dim.hppm1.get(i) && flagh==0) {flagh=1;}//imbirc
				if	 (dim.hppm1.get(i)> dim.hppm2.get(j) && dim.hppm1.get(i)<=dim.hppm1.get(j) && flagh==0) {flagh=1;}//imbirc
				if	 (dim.hppm2.get(j)> dim.hppm2.get(i) && dim.hppm2.get(j)<=dim.hppm1.get(i) && flagh==0) {flagh=1;}//imbirc
				if	 (dim.hppm2.get(i)> dim.hppm2.get(j) && dim.hppm2.get(i)<=dim.hppm1.get(j) && flagh==0) {flagh=1;}//imbirc
				if(flagh==0) sasi[i][j]=-1; else sasi[i][j]=0;
				if(flagh==0) sasi[j][i]=-1; else sasi[j][i]=0;
	}
}
for(i=0;i<nbc;i++) {
 for(j=i+1;j<nbc;j++) {
  if(sasi[i][j]==0) if(ol[i]>ol[j]) {
	if((ol[i]-addelta[i])<((ol[j]+addelta[j]+(1d/inputt1max)))) {   //////// HERE CORRECTION needed to take into acound fadr ?? Here assumes 1 ?
		comment=comment+("Signals "+(i+1)+" and "+(j+1)+" at "+ppm[j]+" and "+ppm[i]+" ppm are too close to be resolvable !\n");
		if(((ol[i]-addelta[i])-(ol[j]+addelta[j]))>0) {
			comment=comment+("Considering the line with of each ("+addelta[j]*2.0+" and "+addelta[i]*2+" Hz) the space between them is "+((ol[i]-addelta[i])-(ol[j]+addelta[j]))+" Hz at "+in_freq+" MHz. \n");
			comment=comment+("This is so small that it would require a t1,max as high as "+ 1/(((ol[i]-addelta[i])-(ol[j]+addelta[j])   ))+" s to resolve them (SNR would prob. be to small).\nFor the calculations overlap between them will be allowed!\n" );
		} else {
			comment=comment+("Considering the line with of each ("+(addelta[j]*2.0)+" and "+addelta[i]*2+" Hz) they touch each other at "+in_freq+" MHz.\nIt is impossible to resolved whatever the acquisition conditions.\n"); 
		}
	if((ol[i]+addelta[i])<(ol[j]+addelta[j])){ //j includes i
		comment=comment+("The second includes the first.\n");
		}else{
		if((ol[i]-addelta[i])<(ol[j]-addelta[j])) //i includes j
		{
		comment=comment+("The first includes the second.\n");}
	}
	sasi[i][j]=-1;sasi[j][i]=-1;////
	comment=comment+("\n");
	}
  }else{
	if((ol[j]-addelta[j])<((ol[i]+addelta[i]+(1d/inputt1max)))) { //////// HERE CORRECTION needed to take into acound fadr ?? Here assumes 1 ?
		comment=comment+("Signals "+(i+1)+" and "+(j+1)+" ,at "+ppm[j]+" and "+ppm[i]+" are too close to be resolvable !\n");
		if(((ol[j]-addelta[j])-(ol[i]+addelta[i]))>0){
		comment=comment+("Considering the line with of each ("+addelta[j]*2.0+"and"+addelta[i]*2+" Hz) the space between them is "+((ol[j]-addelta[j])-(ol[i]+addelta[i]))+" Hz at "+in_freq+" MHz. \nThis is so small that it would require a t1,max as high as "+ (1/(((ol[j]-addelta[j])-(ol[i]+addelta[i])   )))+" s to resolve them (SNR would prob. be to small).\nFor the calculations overlap between them will be allowed!\n");
		}
		else{
		comment=comment+("Considering the line with of each ("+addelta[j]*2.0+"and"+addelta[i]*2+" Hz) they touch each other at "+in_freq+" MHz.\nIt it impossible to resolved whatever the acquisition conditions.\n"); 
		}
	if((ol[j]+addelta[j])<(ol[i]+addelta[i])){ //i includes j
		comment=comment+("The second includes the first.\n");
		}else{
		if((ol[j]-addelta[j])<(ol[i]-addelta[i])) //j includes i
		{
		comment=comment+("The first includes the second.\n");}
	}
		sasi[i][j]=-1;sasi[j][i]=-1;////

	System.out.println("\n");
	}
  }
 }
}

////////////////////////////////////////fill sasi


sw1h_2dr=(max-min)*in_freq;
//System.out.println("The full spectral width is "+sw1h_2dr+"Hz\n");
comment+=("The full spectral width is "+(float)sw1h_2dr+"Hz\n");

/*si&factor not used instead td*/
tmpd=sw1h_2dr;
for(i=0;i<nbc;i++) {
	for(j=i+1;j<nbc;j++) if(sasi[i][j]==0){
		if(ol[i]>ol[j]) if(tmpd>((ol[i]-addelta[i])-(ol[j]+addelta[j]))) {
			tmpd=           ((ol[i]-addelta[i])-(ol[j]+addelta[j]));tmpi=i;tmpa=j; }
		if(ol[i]<ol[j]) if(tmpd>((ol[j]-addelta[j])-(ol[i]+addelta[i]))) {
			tmpd=           ((ol[j]-addelta[j])-(ol[i]+addelta[i]));tmpi=j;tmpa=i; }
	}
}

/*neglects line width and width due to proc*/

//System.out.println("The distance between the closest pair of signals that are requested to be resolved is "+tmpd+" Hz (between S"+(tmpi+1)+":"+ppm[tmpi]+" and S"+(tmpa+1)+":"+ppm[tmpa]+" ppm)\n");
comment=comment+("The distance between the closest pair of signals that are requested to be resolved  is "+(float)tmpd+" Hz (between S"+(tmpi+1)+":"+(float)ppm[tmpi]+" and S"+(tmpa+1)+":"+(float)ppm[tmpa]+" ppm)\n");


i=tmpi;j=tmpa;td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);
sw=sw1h_2dr;
/*includes width due to lw */
tmpd=Math.abs(ol[i]-ol[j])-(falv*(addelta[i]+addelta[j])+2.0*addr);

/*increasea little bit the spectralwith to avoir alias of first and list signals*/
td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);
sw=sw1h_2dr+(falv*(addelta[0]+addelta[nbc-1])+4.0*sw/td*fadr+2.0*addr);sw1h_2dr=sw;
td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);
tmpd=Math.abs(ol[i]-ol[j])-(falv*(addelta[i]+addelta[j])+2.0*addr);
td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);
td=(int)((td+1)/2);/*for even number*/
td=(int)(2*td);/*for even number*/

refmaxt1=(double)td/sw1h_2dr/2.0;
comment=comment+"\nTo resolve all signals (except the pairs that are allowed to overlap or are impossible to resolve), a full spectrum needs: \nTD0= "+td+" SW0="+(float)sw1h_2dr+" Hz ("+((float)sw1h_2dr/(float)in_freq)  +"ppm) (t1max="+(float)refmaxt1+" s) at "+in_freq+" MHz\n";

td0=td;sw0=sw1h_2dr;

if(in_td==-1) {td=td0;sw=sw0/in_freq;return(comment);} 

tmpd2=0.0;


////////// ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ
//when arrive here if sasi==-1 impossible to separate will not need to be separated keep -1 value
//when arrive here if sasi==0  possible to separate will depend on protdim



///
/* OLD SYSTEM...
for(i=0;i<nbc;i++) {
	for(j=i+1;j<nbc;j++) if(sasi[i][j]==0) {
		k=0;//in case no protdim, need to be separated
		m=1;
		while(m<=2*((protdim[i][0]))) {
			n=1;
			while(n<=2*((protdim[j][0]))) {
			if(m==1 && n==1) k=1;//if protdim, start value: need no separation
if	((protdim[j][n+0]<=protdim[i][m+0] && protdim[j][n+0]>=protdim[i][m+1]) ||
	 (protdim[j][n+0]<=protdim[i][m+1] && protdim[j][n+0]>=protdim[i][m+0]) ||      
	 (protdim[j][n+1]<=protdim[i][m+0] && protdim[j][n+1]>=protdim[i][m+1]) ||
	 (protdim[j][n+1]<=protdim[i][m+1] && protdim[j][n+1]>=protdim[i][m+0]) ||
	 (protdim[i][m+0]<=protdim[j][n+0] && protdim[i][m+0]>=protdim[j][n+1]) ||
	 (protdim[i][m+0]<=protdim[j][n+1] && protdim[i][m+0]>=protdim[j][n+0]) ||      
	 (protdim[i][m+1]<=protdim[j][n+0] && protdim[i][m+1]>=protdim[j][n+1]) ||
	 (protdim[i][m+1]<=protdim[j][n+1] && protdim[i][m+1]>=protdim[j][n+0]) ){
			k=0;//need to be separeted
			}
			n+=2;}
		m+=2;}
		if(k==1) sasi[i][j]=-1; else sasi[i][j]=0;
		if(k==1) sasi[j][i]=-1; else sasi[j][i]=0;
	}
}*/

//here sasi==0 need separation ==-1 does not need separation

//	System.out.println("System after elim touching/including\n");for(m=0;m<nbc;m++){ 		System.out.println("Signal "+m+" at "+ppm[m]+" ppm ("+ol[m]*2+" Hz) lw = "+addelta[m]+" Hz");}
////////////////////////////////////////////////////////////////

tdwould=td;
step=0;
firstfound=0;
/********************************** explanation *****************************************************************/
/* in: step (0:init 1:power of two jumps 2:td increment 2 3:out)						*/
/*	used in td management and later										*/ 
/* in: tdwould number of point required for max resolution - used for init value of cutting values of td 	*/
/* sw1h_2dr: specrtal width in F1 										*/
/********* td management ********/
/*	incrtd			*/
/********* first step    ********/
/*	aflag			*/
/*	sasi	interger fold#	-1 for impossible  overlap*/
/*	sasdi 	in		*/
/*	sasdo	out		*/
/***************************  end   explanation *****************************************************************/

while(step<3){
if(step==1) if(firstfound==0) {
       if(incrtd>1) {td=td+incrtd;incrtd=incrtd/2;firstfound=0;}
       else{step=2;
//	   System.out.println("Min TD : td="+td);

//System.out.println("TD="+factorbe+" sw="+swsw+" dr="+(swsw/(double)factorbe)+" (t1max="+((double)factorbe/swsw/2.0)+")\n");
//        fprintf(f_ou2 ,"TD=%d sw=%lf dr=%lf (t1max=%lf)\n",factorbe,swsw,swsw/(double)factorbe,(double)factorbe/swsw/2.0);
                if(flagop==0)if(((double)factorbe/swsw/2.0)<(1d+per/100.0d)*refmaxt1) {swswop=swsw;factorop=factorbe;flagop=1;
//System.out.println("Conditions above correspond to the minimal TD with 10% more t1 ! t1max(aliased spectrum)="+(double)factorbe/swsw/2.0+" < 1.1*t1max(full spectrum)=1.1"+refmaxt1+"\n");
//fprintf(f_ou2 , "Conditions above are optimal! t1max(aliased spectrum)=%lf < 1.1*t1max(full spectrum)=1.1*%lf\n",(double)factorbe/swsw/2.0,refmaxt1);
//if((double)factorbe/swsw/2.0>0.1)
//System.out.println("Warning. t1,max is more than 100 ms. When using experiments with gradient coding during t1 (like the standard HMBC exp), diffusion may decrease SNR. See Helvetica Chemica Acta Vol 87 (2004) pp 2190-2207 for discussion and a solution.\n");
//if((double)factorbe/swsw/2.0>0.5)
//System.out.println("Warning. t1,max is more than 500 ms. This time evolution may be too long if your molecules relaxes quickly. If this is the case, use a smaller value of TD.\n");
                        }
       }
}else{
       if(incrtd>1) {td=td-incrtd;incrtd=incrtd/2;firstfound=0;}
       else{step=2;
//        fprintf(f_ou2 ,"TD=%d sw=%lf dr=%lf (t1max=%lf)\n",factort1,swswt1,swswt1/(double)factort1,(double)factort1/swswt1/2.0);
                if(flagop==0)if(((double)factorbe/swsw/2.0)<(1d+per/100.0d)*refmaxt1) {swswop=swsw;factorop=factorbe;flagop=1;System.out.println("-----BBBBBBBBBB");
//System.out.println("TD="+factorbe+" sw="+swsw+" dr="+(swsw/(double)factorbe)+" (t1max="+((double)factorbe/swsw/2.0)+")\n");
//fprintf(stdout,"Conditions above are optimal! t1max(alised spectrum)=%lf < 1.1*t1max(full spectrum)=1.1*%lf\n",(double)factorbe/swsw/2.0,refmaxt1);
//fprintf(f_ou2 ,"Conditions above are optimal! t1max(alised spectrum)=%lf < 1.1*t1max(full spectrum)=1.1*%lf\n",(double)factorbe/swsw/2.0,refmaxt1);
//System.out.println("t1,max(aliased spectrum)="+(double)factorbe/swsw/2.0+" < 1.1*t1max(full spectrum)=1.1*"+refmaxt1+"\n");
//if((double)factorbe/swsw/2.0>0.1)
//System.out.println("Warning. t1,max is more than 100 ms. When using experiments with gradient coding during t1 (like the standard HMBC exp), diffusion may decrease SNR. See Helvetica Chemica Acta Vol 87 (2004) pp 2190-2207 for discussion and a solution.\n");
//if((double)factorbe/swsw/2.0>0.5)
//System.out.println("Warning. t1,max is more than 500 ms. This time evolution may be too long if your molecules relaxes quickly. If this is the case, use a smaller value of TD.\n");
                        }
       }
}
if(step==0) {td=(int)Math.pow(2,((int)(Math.log(tdwould-1)/Math.log(2)))+2);incrtd=td/2;step=1;}
//System.out.println("Debug step="+step+"td="+td);
if(step==2) {td=td+2;if(td>tdwould) step=3;}
if(step==2) {if( flagop==1) step=3;}// if shortest t1 not requestion stop here

/* end loop management */
if(in_td!=0) {td=in_td;step=2;} 
/* OPTIM SW*/
//System.out.println("test td*="+td+" step: "+step+" per: "+per+" flagop: "+flagop+"firstfound: "+firstfound+" refmaxt1: "+refmaxt1);


 //double sw,osw;
 //int i,j,k,l;
sw=100000d*sw1h_2dr;
aflag=0;
//if(debug2) fprintf(stdout,"\n\ntd=%d \n",td);/* ddump */
/*init*/
for(i=0;i<nbc;i++) {
	for(j=i+1;j<nbc;j++) {
		if(sasi[i][j]>=0) sasi[i][j]=0;	/* reset if not set to -1 */
		sasdi[i][j]=1.0e10;			/* reset */
		sasdo[i][j]=0.0;				/* reset */
	}
}
/* STEP FASTINITSWEEP */
/* set sasdo and tmpdmin : smaller sasdo */
/* determine aflag=1 if td is no sufficient that is if imbrication starts at first already */
	tmpdmin=sw1h_2dr;
//if(debug2)	fprintf(stdout,"nbc=%d sw=%lf\n",nbc,sw);/* ddump */
	for(i=0;i<nbc;i++) {
//	if(debug2)	fprintf(stdout,"i=%d :",i);/* ddump */
		for(j=i+1;j<nbc;j++) if(sasi[i][j]>=0) {
//		if(debug2)	fprintf(stdout,"%d:(Inf-",j);/* ddump */
			sasd=Math.abs(ol[i]-ol[j]);
			sasdo[i][j]=(sasd-falv*(addelta[i]+addelta[j])-2*addr  )*td/(fadr* 4.0); 
			if(tmpdmin>sasdo[i][j]) tmpdmin=sasdo[i][j];
		delta=falv*(addelta[i]+addelta[j])+2*addr+ fadr*4.0  *sasd/((double)td);/*was sw is sasd*/ /********* 4.0     *******/

/*see if imbricated in-s */
			if(sasdo[i][j]<sasd+delta)	{ sasdo[i][j]=0.0;aflag=1;}/* if imbricaion starts it never stops */
			if(sasdo[i][j]<0)		{ sasdo[i][j]=0.0;aflag=1;}
//			if(debug2)fprintf(stdout,"%lf) ",sasdo[i][j]);/* ddump */
			if(aflag!=0) {j=nbc;i=nbc;}/* gen break */
		}else{ 
//		if(debug2)fprintf(stdout,"%d:(Y)",j);/* ddump */
		}
//		if(debug2)fprintf(stdout,"\n");/* ddump */
	}
sw=tmpdmin;
osw=sw;
/* STEP MAINLOOP sw*/
for(l=0;l>-1;l++){
if(aflag==0) {
/* set sasi */
	for(i=0;i<nbc;i++) {
		for(j=i+1;j<nbc;j++) {
			if(sasi[i][j]>=0) sasi[i][j]=(int)(Math.abs(ol[i]-ol[j])/sw+0.5);
		}
	}
	tmpdmax=0;
	tmpdmin=sw1h_2dr;
	iflag=0;
	nsw=tmpdmin;
	for(i=0;i<nbc;i++) {
//		if(debug2)fprintf(stdout,"i=%d :",i);/* ddump */
		for(j=i+1;j<nbc;j++) if(sasi[i][j]>0) {
			k=0;
			sasd=Math.abs(ol[i]-ol[j])/(sasi[i][j]+k);

delta=(falv*(addelta[i]+addelta[j])+ 2.0*addr+ fadr*4.0  *sw/((double)td))/(sasi[i][j]+k);/********* 4.0     *******/
				sasdi[i][j]=sasd+delta;
				sasdo[i][j]=sasd-delta;
				if(tmpdmax<sasd+delta) tmpdmax=sasd+delta;
				if(tmpdmin>sasd-delta) tmpdmin=sasd-delta;
			k=1;
			sasd=Math.abs(ol[i]-ol[j])/(sasi[i][j]+k);
delta=(falv*(addelta[i]+addelta[j])+ 2.0*addr+fadr*4.0  *sasd/((double)td))/(sasi[i][j]+k);/*was sw is sasd*/ /********* 4.0     *******/
/*see if imbricated in-s */
				if(sasdo[i][j]<sasd+delta)	{ sasdo[i][j]=0.0;aflag=1;}/* if imbricaion starts it never stops */
				if(sasdo[i][j]<0)			{ sasdo[i][j]=0.0;aflag=1;}
			if(aflag!=0) {j=nbc;i=nbc;}/* gen break */
			if(sw<sasdi[i][j] && sw>sasdo[i][j] && sasdo[i][j]<nsw) 
				{nsw=sasdo[i][j];iflag=1;}
		}else{//if(debug2)fprintf(stdout,"(X)");
		}
	}
}

 if(iflag==0) { /* OK ! */
       if(aflag==0) { /* ok! */
                       if(step==2)if(flagop==0)if(((double)td/sw/2.0)<(1d+per/100.d)*refmaxt1) {swswop=sw;factorop=td;flagop=1;}//line below copied here ...otherwise does not always find... has to do with tmpdmin...

		if((tmpdmin/td>tmpd2) || (step==1) ) {
			tmpd2=tmpdmin/td;
                       if(step==2)if(flagop==0)if(((double)td/sw/2.0)<(1d+per/100.d)*refmaxt1) {swswop=sw;factorop=td;flagop=1;

                       }

                       if(step==1) { swsw=sw;factorbe=td;}
                       if(step==2) { swswt1=sw;factort1=td; }
                       if(firstfound==0) { firstfound=1;} else { firstfound=1; }
                       if(incrtd<3)for(i=0;i<nbc;i++) { /* this is not always called !! */

				nsw=(ol[i]+sw/2);
				nsw=nsw-((double)((int)nsw/sw))*sw;//Math.modulo(nsw,sw);
			//	nsw=Math.remainder(nsw,sw);    MOD MODULOS
			//	nsw=Math.modul(nsw,sw);

				if(nsw<0) nsw=sw+nsw;
			}
		}
		break;
	}else{break;}
}else{
		if(aflag==1) break;
		sw=nsw;
}

}/* END MAINLOOP sw*/

if(in_td!=0) {step=5; //out of TD loop
	if(factorop==0){factorop=in_td;swswop=sw1h_2dr;}
	}
}/* end main loop td*/
		   


td=factorbe;sw=swsw;

comment=comment+("\nMinimal number of increments:\n");
comment=comment+("TDa="+td+" SWa="+(float)sw+" dr="+(float)(sw/(double)td)+" (t1max="+(float)((double)td/sw/2.0)+")");
if((double)td/sw/2.0>0.5){
comment=comment+("\nWarning. t1,max is more than 500 ms. This time evolution may be too long if your molecules relaxes quickly. If this is the case, use a smaller value of TD.");
  }
comment=comment+("\n");

td=factorop;sw=swswop;
comment=comment+("\nOptimal conditions allowing t1,max to exceed the t1,max of the full spectrum by "+per+"% :\n");
comment=comment+("TDa="+td+" SWa="+(float)sw+" dr="+(float)(sw/(double)td)+" Hz (t1max="+(float)((double)td/sw/2.0)+")");
if((double)td/sw/2.0>0.5){
comment=comment+("TDa="+td+" SWa="+(float)sw+" dr="+(float)(sw/(double)td)+" (t1max="+(float)((double)td/sw/2.0)+")");
}
swsw=0;
comment=comment+("\n\n\n      You gain a factor "+td0+"/"+factorop+"="+(float)(td0/factorop)+" in acquisition time using spectral aliasing !");

td=factorop;sw=swswop/in_freq;
////////////////////////////////////////////////////////////////
return(comment);/////////////////
	
}
}
