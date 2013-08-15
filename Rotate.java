package yontemler;

import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

public class Rotate {
	 public static int enkucuk(int[] dizi){ //Bir dizideki en kucuk elemani buluyoruz
		  int min=dizi[0];
			for(int i=1;i<dizi.length;i++)
			{
				if(min > dizi[i])
				{
					min=dizi[i];
				}
			}
		
		  return min;
		}
	 
	 public static int enbuyuk(int[] dizi){ //Bir dizideki en buyuk elemani buluyoruz
			int max=dizi[0];
			for(int i=1;i<dizi.length;i++)
			{
				if(max<dizi[i])
				{
					max=dizi[i];
				}
			}
			return max;
		}
	  
	  public static double donusum(double derece) //Derece olarak verilen aciyi radyana donusturuyoruz cunku sin ve cos deðerleri radyan olrak deðer
	  {											  //aliyor
			double  donusumsonucu= (Math.PI*derece)/180;
			return donusumsonucu;
	  }

	  public static double carp(double[] dizi,int[] dizi1) //Bu fonksiyon ve bulsatir, bulsutun ve carpim fonksiyonlari sadece iki matrisi carpmak 
	  {													   //icin yaratilmistir. Matrisin satirini ve sutununu ayri ayri olarak bulup carpiyoruz.
			double sonuc=0;
			for(int i=0;i<dizi.length;i++)
			{
				sonuc= sonuc + (dizi[i] * dizi1[i]);
			}
			return sonuc;
			
		}
		
		public static double[] bulsatir(double[][] A,int index)
		{
			double[] dizi=new double[A[0].length];
			for (int i=0;i<A[0].length;i++)
			{
				 dizi[i]= A[index][i];
			}
			return dizi;
		}
		
		public static int[] bulsutun(int[][] A,int index1)
		{
			int[] dizi=new int[A.length];
			for (int i=0;i<A.length;i++)
			{
				 dizi[i]= A[i][index1];
			}
			return dizi;
		}
		
		
		
		public static int[][] carpim(double[][] A, int[][] B)
		{
			int[][] sonuc=new int[A.length][B[0].length];
			for (int i=0;i<A.length;i++)
			{
				double[] gecicisatir=bulsatir(A,i);
				for (int j=0;j<B[0].length;j++)
				{
					int[] gecicisutun=bulsutun(B,j);
					sonuc[i][j]=(int)Math.round(carp(gecicisatir,gecicisutun));
				}
			}
		     return sonuc;
		}
		
		public static int[][] indexmatrisi(int satir,int sutun) //Gecici matrisimizi satir uzunlugu aslinda bizim dondurmek istediðimiz resmini satir
		{														//ve sutunu olucak cunku burda yapmaya calistiðimiz resmin kordinat deðerlerini 2 
			int gecicimatris[][]=new int[2][satir*sutun];		//uzunluðunda bir sutunda ve yukarida dediðimiz gibi resmin boyutlari carpimi kadar 
			int tablouzunlugu=0;								//sutun olcaktir
			 for (int x=0;x<satir;x++)
			 {
				 for (int y=0;y<sutun; y++)
				 {
					 gecicimatris[0][tablouzunlugu] = x;
					 gecicimatris[1][tablouzunlugu] = y;
					 tablouzunlugu++;
					 
				}
			 }
			 return gecicimatris;
			
		}

/*/		public static void main(String[] args) {
			
			int derece=180;
			double radyan=donusum(derece); //Donusumumuzu yapiyor
			double[][] donusum_matrisi = {{Math.cos(radyan),-Math.sin(radyan)},{Math.sin(radyan),Math.cos(radyan)}}; //Dondurme formulumuz matris olarak
			Image img = Load.invoke("D:\\bil376\\lenna.png");
			int satir=img.getXDim();
			int sutun=img.getYDim();
			int[][] pointmatrisi=indexmatrisi(satir,sutun); //indexmatrisi fonk. donen matrisi baska bir matriste tutuyoruz
			int[][] yenipointmatrisi=carpim(donusum_matrisi,pointmatrisi); //Ve bu matrise formulumuzu uyguluyoruz sonucuda bir matriste tutuyoruz
			int enkucuk0=enkucuk(yenipointmatrisi[0]); // Yeni matrisimizin satirlarinin en kucuk degerlerini buluyoruz cunku formul sonucu negatif
			int enkucuk1=enkucuk(yenipointmatrisi[1]); //deðerler cikabiliyor buda aslinda kordinat duzleminde pozitif kisimda deðilde negetif
			if(enkucuk0 < 0)						   //kisimda cikmis olabilir demek biz bunlari pozitife ceviriyoruz
			{
				for (int i=0 ;i<yenipointmatrisi[0].length ; i++)
				{
					yenipointmatrisi[0][i]+=-1*enkucuk0;
			    }
			}
			
			if(enkucuk1 < 0)
			{
				for (int i=0 ;i<yenipointmatrisi[1].length ; i++)
				{
					yenipointmatrisi[1][i]+=-1*enkucuk1;
			    }
			}
			
			int enbuyuk0=enbuyuk(yenipointmatrisi[0]);
			int enbuyuk1=enbuyuk(yenipointmatrisi[1]);
			Image resim=new ByteImage(enbuyuk0+1,enbuyuk1+1);
			resim.fill(0);
			
			for (int i=0 ; i<pointmatrisi[0].length; i++){ //Dondurulmus matrisimizi eski haline getiriyoruz
				int x=pointmatrisi[0][i];
				int y=pointmatrisi[1][i];
				int p=img.getXYByte(x, y);
				x=yenipointmatrisi[0][i];
				y=yenipointmatrisi[1][i];
				resim.setXYByte(x, y, p);
			}
			
			Image cerceveliresim=EnYakinKomsuluk.cerceveciz(resim);           //Bu kisimda en yakin komsuluk uygulamak gerekiyor bunuda bir onceki
		    Image Resim= EnYakinKomsuluk.interpolasyon(cerceveliresim, true); //odevde hazirlamistik tekrardan onu kullaniyoruz
			
			 Display2D.invoke(img);
			 Display2D.invoke(Resim);
			
	}/*/
		}



			



