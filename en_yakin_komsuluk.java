package yontemler;
import vpt.ByteImage;
import vpt.Image;
import vpt.algorithms.display.Display2D;
import vpt.algorithms.io.Load;

	public class en_yakin_komsuluk {
		public static void Atama (Image geciciresim,Image resim,int n) //Fonksiyonda bana verilen resmi yeni olusturucag�m resim ve kac kat� olmas� �c�n
		{															   //parametrelerimiz var.Burda sadece eski resmimizin pixellerini ald�k ve yeni resmimize
			for(int x=0; x<geciciresim.getXDim();x++)				   //n oran�nda bosluklarla yerlestirdik.
			{
				for (int y=0; y<geciciresim.getYDim();y++)
				{
					int p=geciciresim.getXYByte(x, y);
				
					resim.setXYByte(x*n   ,y*n   ,p);
				
				}
			}
		}
	
		public static Image cerceveciz(Image resim){ //Resmin etrafina bir cerceve cizdim yani 10*10 ise artik 12*12 oldu.Yeni boyutlarda ondan +2 kullndim. 
		
			int imggenislik=resim.getXDim(); 
			int imgyukseklik=resim.getYDim(); 
			Image cerceveliresim =new ByteImage(imggenislik+2,imgyukseklik+2);
			cerceveliresim.fill(0); //Fill cercevenin her yerine 0 degeri atiyor yani dolduruyor bunu referans dosyalarindan buldum.
			for(int x=0; x<resim.getXDim(); x++)
			{
				for (int y=0; y<resim.getYDim(); y++)
				{
					int p=resim.getXYByte(x, y);
				
					cerceveliresim.setXYByte(x+1   ,y+1   ,p); //cercevenin kenarlari bos kalsin diye x ve y +1 olarak ataniyor ve en sondakide bos kalicak
															   //cunku parametre olarak gecilen resmin boyutlarini dogude kullandim.
				}
				
			}
			return cerceveliresim;
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

	/*/public static void main(String[] args) 
	{
	    int n=2;
	    Image geciciresim = Load.invoke("D:\\bil376\\fruit.png");
	    int gen = geciciresim.getXDim(); 
        int yuk = geciciresim.getYDim();
	    Image resim =new ByteImage(gen*n,yuk*n);
		resim.fill(0);
		Atama(geciciresim,resim,n);
	    Image cerceveliresim=cerceveciz(resim);
	    interpolasyon(cerceveliresim,true);
	    Display2D.invoke(cerceveliresim);
	
	}/*/

}



