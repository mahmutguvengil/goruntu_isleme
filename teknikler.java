package yontemler;
import java.util.Arrays;

import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;



public class teknikler {
		public static Image cerceveciz(Image resim) // resmin etrafina siyah bir cerceve ciziliyor
		{ 
			
			int imggenislik=resim.getXDim(); 
			int imgyukseklik=resim.getYDim(); 
			Image cerceveliresim =new ByteImage(imggenislik+2,imgyukseklik+2);
			cerceveliresim.fill(0); 
			for(int x=0; x<resim.getXDim(); x++)
			{
				for (int y=0; y<resim.getYDim(); y++)
				{
					int p=resim.getXYByte(x, y);
					cerceveliresim.setXYByte(x+1   ,y+1   ,p); 
				}
				
			}
			return cerceveliresim;
		}
		
		public static Image ortalama(Image cerceveliresim){
			int cerceveliresimGenislik=cerceveliresim.getXDim();
			int cerceveliresimYukseklik=cerceveliresim.getYDim();
			Image ortalamaresim =new ByteImage(cerceveliresimGenislik,cerceveliresimYukseklik);
			for(int x=1;x<=cerceveliresimYukseklik-2;x++)
			{
				for(int y=1;y<=cerceveliresimGenislik-2;y++)
				{
					int toplam=0;
					for(int j=-1;j<=1;j++)
					{
						for(int k=-1;k<=1;k++)
						{
							int p=cerceveliresim.getXYByte(x+k, y+j);
							toplam=toplam+p;
						}
					}
					int q=(int)(toplam/9.0);
				    ortalamaresim.setXYByte(x, y, q);
				}
			}
			return ortalamaresim;
		}
		
		public static Image ortanca(Image cerceveliresim){
			int K = 4;
			int cerceveliresimGenislik=cerceveliresim.getXDim();
			int cerceveliresimYukseklik=cerceveliresim.getYDim();
			Image ortancaresim =new ByteImage(cerceveliresimGenislik,cerceveliresimYukseklik);
			int[] P = new int[2*K+1];
			for(int x=1;x<=cerceveliresimYukseklik-2;x++)
			{
				for(int y=1;y<=cerceveliresimGenislik-2;y++)
				{
					int ki=0;
					for(int j=-1;j<=1;j++)
					{
						for(int k=-1;k<=1;k++)
						{
							P[ki]=cerceveliresim.getXYByte(x+k, y+j);
							ki++;
						}
					}
					Arrays.sort(P);
					ortancaresim.setXYByte(x,y,P[K]);
				}
			}
			
			
			
			return ortancaresim;
		}
		
		public static Image Histogram(Image cerceveliresim,int[] cumulativeHistogram){//cumulative diziden gelen her eleman resimdeki piksel sayisina bolonor ve en yoksek piksel deðeri ile carpilacak.
			
			int cerceveliresimGenislik=cerceveliresim.getXDim();
			int cerceveliresimYukseklik=cerceveliresim.getYDim();
			int K=256;
			int M=cerceveliresimGenislik*cerceveliresimYukseklik;
			Image Histogram =new ByteImage(cerceveliresimGenislik,cerceveliresimYukseklik);
			for(int x=0;x<cerceveliresimYukseklik;x++)
			{
				for(int y=0;y<cerceveliresimGenislik;y++)
				{
					int p=cerceveliresim.getXYByte(x, y);
					int b = cumulativeHistogram[p]*(K-1)/M;
					Histogram.setXYByte(x, y, b);
					
		}
	}
			return Histogram;
			
		}
		
		public static int[] histogramDizi(Image cerceveliresim) // pixel deðerleri bir dizide tutuluyor
		{
			int cerceveliresimGenislik=cerceveliresim.getXDim();
			int cerceveliresimYukseklik=cerceveliresim.getYDim();
			int[] dizi=new int[cerceveliresimGenislik*cerceveliresimYukseklik];
			
			for(int x=0;x<cerceveliresimYukseklik;x++)
			{
				for(int y=0;y<cerceveliresimGenislik;y++)
				{
					int p=cerceveliresim.getXYByte(x, y);
					dizi[p]=dizi[p]+1;
				}
			}
			return dizi;
		}
		
		public static int[] cumulativeHistogramHesabi(int[] cumulativeHistogramDizi){ // yukaridaki diziden gelen deðerler bunu birikimli histogramini elde ediyorum
																					  // bir onceki elemanla topluyorum
			
			for(int i=1;i<cumulativeHistogramDizi.length;i++)
			{
				cumulativeHistogramDizi[i]=cumulativeHistogramDizi[i-1]+cumulativeHistogramDizi[i];
				
			}
			
			return cumulativeHistogramDizi;
		}
		
		static public void Sobel(Image cerceveliresim)
		{
			int cerceveliresimGenislik=cerceveliresim.getXDim();
			int cerceveliresimYukseklik=cerceveliresim.getYDim();
			int dikey[][] = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
			int yatay[][] = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
			int toplamYatay=0,toplamDikey=0,q=0,w=0;
			Image sobel = new ByteImage(cerceveliresimGenislik, cerceveliresimYukseklik, 1);
			for (int x = 0; x < cerceveliresimGenislik; x++)
			{
				for (int y = 0; y < cerceveliresimYukseklik; y++)
					{
						for (int d = x - 1 ; d <= x + 1 ; d++)
						{
							for (int b = y - 1 ; b <= y + 1 ; b++) 
							{
								if (d < 0 || b < 0 || d > cerceveliresimGenislik - 1 || b > cerceveliresimYukseklik - 1)
								{
									continue;
								}
								else
								{
									toplamDikey = toplamDikey + cerceveliresim.getXYByte(d, b) * dikey[w][q];
									toplamYatay = toplamYatay + cerceveliresim.getXYByte(d, b) * yatay[w][q];
									w++;
								}
							}
							q++;
							w = 0;
						}
						q = 0;
						w = 0;
						sobel.setXYByte(x, y, Math.abs( Math.abs(toplamDikey) + Math.abs(toplamYatay) ));
						toplamYatay=0;toplamDikey=0;
					}
			}
			Display2D.invoke(sobel);
		}
		
		public static Image keyfifilitre(Image cerceveliresim){ // filtreyi kullanicidan alip resim ostonde gezdiriyorum
			
			int cerceveliresimGenislik=cerceveliresim.getXDim();
			int cerceveliresimYukseklik=cerceveliresim.getYDim();
			Image keyfiresim =new ByteImage(cerceveliresimGenislik,cerceveliresimYukseklik);
		
			double[][] filitre = {{0.075, 0.125, 0.075},
                    			{0.125, 0.200, 0.125},
                    				{0.075, 0.125, 0.075},};
			
			
			for (int x=1; x<=cerceveliresimYukseklik-2; x++) {
	            for (int y=1; y<=cerceveliresimGenislik-2; y++) {
	              
	                double toplam = 0;
	                for (int j=-1; j<=1; j++) {
	                    for (int i=-1; i<=1; i++) {
	                        int p = cerceveliresim.getXYByte(y+i,x+j);
	                      
	                        double c = filitre[j+1][i+1]; 
	                        toplam = toplam + c * p;
	                    }
	                }
	                int q = (int) toplam;
	                keyfiresim.setXYByte(y,x,q);  
	            }
	        }
				return keyfiresim;
			
		}
		
	
		

public static void main(String[] args) {



	Image resim = Load.invoke("D:\\bil376\\lenna.png");
	Image cerceveliresim=cerceveciz(resim);
	Image Ortalamaresim=ortalama(cerceveliresim);
	Image Ortancaresim=ortanca(cerceveliresim);
	int[] histogramDizi=histogramDizi(cerceveliresim);
	int[] cumulativeHistogramHesabi=cumulativeHistogramHesabi(histogramDizi);
	Image Histogram=Histogram(cerceveliresim,cumulativeHistogramHesabi);
	Image keyfiresim=keyfifilitre(cerceveliresim);
	
	Sobel(cerceveliresim);
	Display2D.invoke(cerceveliresim);
	Display2D.invoke(Ortalamaresim);
	Display2D.invoke(Ortancaresim);
	Display2D.invoke(Histogram);
	Display2D.invoke(resim);
    Display2D.invoke(keyfiresim);
	
	

	
	
	
	
	
	
}
}