package yontemler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;
import yontemler.Rotate;
import yontemler.Dosya_Secici;

public class NewJFrame extends javax.swing.JFrame {


private JButton jButton9;
private JButton jButton10;
private JButton jButton1;
private JButton jButton5;
private JButton jButton4;
private JButton jButton3;
private JButton jButton2;
private JButton jButton6;
private JButton jButton7;
private JButton jButton8;
private JButton jButton11;
public static String bos;
Dosya_Secici s=new Dosya_Secici();







public static Image cerceveciz(Image resim)
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
	
public static Image Histogram(Image cerceveliresim,int[] cumulativeHistogram){
	
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

public static int[] histogramDizi(Image cerceveliresim)
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

public static int[] cumulativeHistogramHesabi(int[] cumulativeHistogramDizi){
	
	
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
	
	public static void Atama (Image geciciresim,Image resim,int n) //Fonksiyonda bana verilen resmi yeni olusturucagim resim ve kac kati olmasi icin
	{															   //parametrelerimiz var.Burda sadece eski resmimizin pixellerini aldik ve yeni resmimize
		for(int x=0; x<geciciresim.getXDim();x++)				   //n oraninda bosluklarla yerlestirdik.
		{
			for (int y=0; y<geciciresim.getYDim();y++)
			{
				int p=geciciresim.getXYByte(x, y);
			
				resim.setXYByte(x*n   ,y*n   ,p);
			
			}
		}
	}
	
	public static int islemyap(int pixeldegeri,DortluCerceve cerceve,boolean metot)
	{
		if(metot=false) //false ise burda en yakin komsuluk metodo devreye giriyor ve calisiyor.En yakin komsulukun amaci bosluga kendisinin en yakin solundaki
		{				//orasi yoksa en yakin yukarisi orasida yuksa sol ust caprazi yerlestirilir.Borda onu yaptik.
			if(pixeldegeri==0)
			{
				int solkomsuluk=cerceve.getsol();
				if(solkomsuluk!=0)
				{
					return solkomsuluk;
				}
				else
				{
					int yukarikomsuluk=cerceve.getyukari();
					return yukarikomsuluk;
				}
			}
			else
				return pixeldegeri;
		
	
	
		}
		else
		{
			if(pixeldegeri==0) //cift dogrusal kestirim icin ortalama hesaplandi.
			{
				int ortalama=cerceve.ortalamaDondur();
				return ortalama;
			}
			else
				return pixeldegeri;
		}
}


public static void interpolasyon(Image cerceveliresim,boolean metot)//metoto 0 en yakin kopmsuluk metot1  cift dogrusal//
{
	DortluCerceve cerceve=new DortluCerceve(0,0,0,0); //sinifimizin nesnesini yarattik ve siyah yani sifir yani bos olarak terlestirdik//
	int CresimGen=cerceveliresim.getXDim(); 
	int CresimYuk=cerceveliresim.getYDim(); 
	for(int x=1; x<CresimGen-1 ;x++){ //cercevemizin sol ve sag kkose pixelleri ne dokunmamak icim x 1 den genisilikin bir eksigi kadar//
		
		for(int y=1; y<CresimYuk-1 ;y++){
			int pixeldegeri=cerceveliresim.getXYByte(x, y);
			int sol=cerceveliresim.getXYByte(x-1, y); //bir pixelin sol,sag,yukari,asagi degerleri bu dort satirda bulunur//
			int sag=cerceveliresim.getXYByte(x+1, y); //pixel 3,3 olsun solu 2,3 sagi ,4,3 gibi//
			int yukari=cerceveliresim.getXYByte(x, y-1);
			int asagi=cerceveliresim.getXYByte(x, y+1);
			cerceve.setCerceve(sol, sag, yukari, asagi);
			int yeniPixelDegeri =islemyap(pixeldegeri,cerceve,true);
			cerceveliresim.setXYByte(x ,y ,yeniPixelDegeri); //pixeldegerlerinin cerceveli resmimize atadik//
		}
	}
	
}

public static Image keyfifilitre(Image cerceveliresim){
	
	int cerceveliresimGenislik=cerceveliresim.getXDim();
	int cerceveliresimYukseklik=cerceveliresim.getYDim();
	Image keyfiresim =new ByteImage(cerceveliresimGenislik,cerceveliresimYukseklik);
	
	//double number;
	//String input;
	
	String input = JOptionPane.showInputDialog ( "Filitre matrisinin 1. elemanini giriniz : [0][0]");
	double n = Double.parseDouble(input);
	String input1 = JOptionPane.showInputDialog( "Filitre matrisinin 2. elemanini giriniz : [1][0]");
	double n1 = Double.parseDouble(input1);
	String input2 = JOptionPane.showInputDialog( "Filitre matrisinin 3. elemanini giriniz : [2][0]");
	double n2 = Double.parseDouble(input2);
	String input3 = JOptionPane.showInputDialog( "Filitre matrisinin 4. elemanini giriniz : [0][1]");
	double n3 = Double.parseDouble(input3);
	String input4 = JOptionPane.showInputDialog( "Filitre matrisinin 5. elemanini giriniz : [1][1]");
	double n4 = Double.parseDouble(input4);
	String input5 = JOptionPane.showInputDialog( "Filitre matrisinin 6. elemanini giriniz : [2][1]");
	double n5 = Double.parseDouble(input5);
	String input6 = JOptionPane.showInputDialog( "Filitre matrisinin 7. elemanini giriniz : [0][2]");
	double n6 = Double.parseDouble(input6);
	String input7 = JOptionPane.showInputDialog( "Filitre matrisinin 8. elemanini giriniz : [1][2]");
	double n7 = Double.parseDouble(input7);
	String input8 = JOptionPane.showInputDialog( "Filitre matrisinin 9. elemanini giriniz : [2][2]");
	double n8 = Double.parseDouble(input8);

	double[][] filitre = {{n, n1, n2},
            			  {n3, n4, n5},
            			  {n6, n7, n8}};
	
	
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
		
		
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewJFrame inst = new NewJFrame();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public NewJFrame() {
		
		super();
		initGUI();
		
	}
	
	private void initGUI() {
		
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jButton1 = new JButton();
				jButton1.setText("Ortanca Filitresi");
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				jButton2 = new JButton();
				jButton2.setText("Ortalama Filitresi");
				jButton2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton2ActionPerformed(evt);
					}
				});
			}
			{
				jButton4 = new JButton();
				jButton4.setText("Cumulative Histogram");
				jButton4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton4ActionPerformed(evt);
					}
				});
				
			}
			{
				jButton5 = new JButton();
				jButton5.setText("Sobel Gradyan\u0131");
				jButton5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton5ActionPerformed(evt);
					}
				});
			}
			{
				jButton6 = new JButton();
				jButton6.setText("Çerceve Çiz");
				jButton6.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton6ActionPerformed(evt);
					}
				});
			}
			{
				jButton7 = new JButton();
				jButton7.setText("Resim Dondur");
				jButton7.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton7ActionPerformed(evt);
					}
				});
				
			}
			{
				jButton8 = new JButton();
				jButton8.setText("En Yakin Komsuluk");
				jButton8.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton8ActionPerformed(evt);
					}
				});
				
			}
			{
				jButton11 = new JButton();
				jButton11.setText("Keyfi Bir Filitre(Filitre Elemanlar\u0131 De\u011fi\u015fken)");
				jButton11.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton11ActionPerformed(evt);
					}
				});
			}
			{
				jButton10 = new JButton();
				jButton10.setText("Resmi Ac");
				jButton10.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton10ActionPerformed(evt);
					}
				});
			}
			{
				jButton9 = new JButton();
				jButton9.setText("Dosya Yolu El ile");
				jButton9.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton9ActionPerformed(evt);
					}
				});
			}
			{
				jButton3 = new JButton();
				jButton3.setText("Dosya Yolu Sec-Önizlemeli");
				jButton3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton3ActionPerformed(evt);
					}
				});
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jButton9, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jButton1, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jButton2, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jButton3, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(jButton10, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jButton4, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jButton5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, Short.MAX_VALUE)
				.addComponent(jButton11, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 1, GroupLayout.PREFERRED_SIZE)
				.addComponent(jButton6, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jButton7, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(jButton8, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(32, 32));
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(jButton10, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jButton3, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
				    .addComponent(jButton9, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
				.addGap(62)
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(jButton4, GroupLayout.Alignment.LEADING, 0, 298, Short.MAX_VALUE)
				    .addComponent(jButton2, GroupLayout.Alignment.LEADING, 0, 298, Short.MAX_VALUE)
				    .addComponent(jButton1, GroupLayout.Alignment.LEADING, 0, 298, Short.MAX_VALUE)
				    .addComponent(jButton5, GroupLayout.Alignment.LEADING, 0, 298, Short.MAX_VALUE)
				    .addComponent(jButton6, GroupLayout.Alignment.LEADING, 0, 298, Short.MAX_VALUE)
				    .addComponent(jButton7, GroupLayout.Alignment.LEADING, 0, 298, Short.MAX_VALUE)
				    .addComponent(jButton8, GroupLayout.Alignment.LEADING, 0, 298, Short.MAX_VALUE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(jButton11, 0, 292, Short.MAX_VALUE)))
				.addContainerGap());

			pack();
			this.setSize(609, 343);
		} catch (Exception e) {
		    //add your error handling code here
			e.printStackTrace();
		}
	}
	
	private void jButton9ActionPerformed(ActionEvent evt) {
	     bos = (String)JOptionPane.showInputDialog("Ör = D:\\bil376\\lenna.png","Dosya yolu sec" );
	    }
	
	private void jButton1ActionPerformed(ActionEvent evt) {
	
		
		
		Image resim = Load.invoke(bos);
		Image Ortancaresim=ortanca(resim);
		Display2D.invoke(Ortancaresim);
		
		
	}
	
	private void jButton2ActionPerformed(ActionEvent evt) {
		
		
		Image resim = Load.invoke(bos);
		
		Image Ortalamaresim=ortalama(resim);
		Display2D.invoke(Ortalamaresim);
		
		
	}
	
	private void jButton3ActionPerformed(ActionEvent evt) {
		
		bos=s.yol;
		JFrame f = new Dosya_Secici();
	    f.setBounds(300, 300, 300, 75);
	    f.setVisible(true);
	  
	    
	    
	    
	    
}
	
	private void jButton4ActionPerformed(ActionEvent evt) {
		
		
		Image resim = Load.invoke(bos);
		
		int[] histogramDizi=histogramDizi(resim);
		int[] cumulativeHistogramHesabi=cumulativeHistogramHesabi(histogramDizi);
		Image Histogram=Histogram(resim,cumulativeHistogramHesabi);
		Display2D.invoke(Histogram);
		
		
	}
	
	private void jButton5ActionPerformed(ActionEvent evt) {
		
		
		Image resim = Load.invoke(bos);
		Sobel(resim);
	}
	
	private void jButton6ActionPerformed(ActionEvent evt) {
		
		
		Image resim = Load.invoke(bos);
		Image cerceveliresim=cerceveciz(resim);
		Display2D.invoke(cerceveliresim);
		
		
	}
	
	private void jButton7ActionPerformed(ActionEvent evt){
		
		
		
		
		int derece=Integer.parseInt(JOptionPane.showInputDialog( "Derece Giriniz"));
		
		double radyan=donusum(derece); //Donusumumuzu yapiyor
		double[][] donusum_matrisi = {{Math.cos(radyan),-Math.sin(radyan)},{Math.sin(radyan),Math.cos(radyan)}}; //Dondurme formulumuz matris olarak
		Image img = Load.invoke(bos);
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
		
		 
		 Display2D.invoke(Resim);
		

		
	}
	
	private void jButton8ActionPerformed(ActionEvent evt) {
		
	
		    int n=Integer.parseInt(JOptionPane.showInputDialog( "Boyut giriniz"));
		    
		    Image geciciresim = Load.invoke(bos);
		    int gen = geciciresim.getXDim(); 
	        int yuk = geciciresim.getYDim();
		    Image resim =new ByteImage(gen*n,yuk*n);
			resim.fill(0);
			Atama(geciciresim,resim,n);
		    Image cerceveliresim=cerceveciz(resim);
		    interpolasyon(cerceveliresim,false);
		    Display2D.invoke(cerceveliresim);
		
		
	}
	
private void jButton10ActionPerformed(ActionEvent evt) {
		Image resim = Load.invoke(bos);
		Display2D.invoke(resim);
		
		
		
	}

	private void jButton11ActionPerformed(ActionEvent evt) {
		Image resim = Load.invoke(bos);
		Image keyfifilitree=keyfifilitre(resim);
		Display2D.invoke(keyfifilitree);
	}

}
