package yontemler;

public class DortluCerceve {
int sol,sag,yukari,asagi;
	
	public DortluCerceve(int sol,int sag,int yukari,int asagi)
	{
		setCerceve(sol,sag,yukari,asagi);
	}

	
	public void setCerceve(int sol,int sag,int yukari,int asagi){
		this.sol=sol;
		this.sag=sag;
		this.yukari=yukari;
		this.asagi=asagi;
	}	
	public int getsag(){ //metotlarini tanimladim cunku bunlari kullanmak icin cagiracagim.
			return sag;
	}
	public int getsol(){
		return sol;
	}
	public int getyukari(){
		return yukari;
		
	}
	public int getasagi(){
		return asagi;
	}
	public int ortalamaDondur(){ //Burasi bize Cift dogrusal ara deger kestirimi ile hesap yapmamiza yariyacak cunku bu deger sol,sag,yukari ve asagi nin
								 //agirlikli ortalamasidir yukaridaki metotlar bizim bu isimizi cok kolaylastirdilar.
	int bolum=4;                 //Bolum 4 tu ama eger bizim pixeldegerimiz kose pixelinin yanindaysa sol degerimiz sifir olabilir eger sol sifirsa
		if(sol==0)				 //bolumu azaltir ve 3 e boleriz oda digerleri icinde ayni islemi yapariz.
			bolum--;
		
		if(sag==0)
			bolum--;
		
		if (yukari==0)
			bolum--;
		
		if(asagi==0)
			bolum--;
		
		bolum=Math.max(bolum,1); //Olaki hepsi sifir cikti sifira bolum olmaz o zamanda hemen max degerimiz bize 1 i veriri ve devam ederiz.
	    int ortalama=(sol+sag+yukari+asagi)/bolum;
	    return ortalama;
			
		
	}
}





