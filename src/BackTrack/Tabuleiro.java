package BackTrack;
import java.io.IOException;
import java.util.Scanner;
public class Tabuleiro{
	static int cont = 0;
	static char tabuleiro[][] = new char[8][8];
	static int rainhas = 0;
	static Rainhas[] r = new Rainhas[8];
	static boolean ok = true;
	static int frame;
	static Scanner scanner = new Scanner(System.in);
	static void Start(Linha l){
		while(l.proximo != null)
			l = l.proximo;
		l.k = l.j;
		if(l.i < 8){
			l.j = procuraColuna(tabuleiro[l.i], l.k);
			if(l.j == l.k){
				if(l.k != 0)
					l.j = procuraColuna(tabuleiro[l.i],l.k+1);

				if(l.j == 0 && l.i == 0 && !ok) 
					l.j = 1;
				if(rainhas < 8 && r[rainhas] != null){
					if(r[rainhas].x == l.i && r[rainhas].y == l.j && !r[rainhas].check)
						l.j = procuraColuna(tabuleiro[l.i],l.k+1);

					if(r[rainhas].x == 0 && r[rainhas].y == 7){
						l.j = 0;
					}
					r[rainhas].check = true;
				}
				if(l.i == 0 && l.j < l.k && l.k == 7 && tabuleiro[l.i][0] != 'x')
					l.j = 0;
			}
			ok = false;
			if(l.j != -1){
				r[rainhas++] = new Rainhas(l.i, l.j);
				preencher(l.i, l.j);
				Linha l2 = new Linha(l.i+1,0);
				l.proximo = l2;
				l2.anterior = l;
				try {
					ScreenManager.show(tabuleiro);						
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				Linha anterior = l.anterior;
				anterior.proximo = null;
				l.anterior = null;
				limpar(r[rainhas-1].x, r[rainhas-1].y);
				rainhas--;
				verificar();
				try {
					ScreenManager.show(tabuleiro);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			System.out.println();
			Linha anterior = l.anterior;
			anterior.proximo = null;
			l.anterior = null;
			limpar(r[rainhas-1].x, r[rainhas-1].y);
			rainhas--;
			verificar();
			frame = 5000;
		}
	}
	static void verificar() {
		for(int k = 0; k < rainhas;k++){
			preencher(r[k].x, r[k].y);
		}
	}
	static void limpar(int i, int j) {
		tabuleiro[i][j] = '_';
		int k = 1;
		while(i+k < 8){
			tabuleiro[i+k][j] = '_';
			k++;
		}
		k=1;
		while(j+k < 8){
			tabuleiro[i][j+k] = '_';
			k++;
		}
		k=1;
		while(i-k >= 0){
			tabuleiro[i-k][j] = '_';
			k++;
		}
		k = 1;
		while(j-k >= 0){
			tabuleiro[i][j-k] = '_';
			k++;
		}
		k =1;
		while(i+k < 8 && j+k < 8){
			tabuleiro[i+k][j+k] = '_';
			k++;
		}
		k = 1;
		while(i-k >= 0 && j-k >= 0){
			tabuleiro[i-k][j-k] = '_';
			k++;
		}
		k=1;
		while(i + k < 8 && j-k >=0){
			tabuleiro[i+k][j-k] = '_';
			k++;
		}
		k = 1;
		while(i-k >= 0 && j+k <8){
			tabuleiro[i-k][j+k] = '_';
			k++;
		}
	}
	static void printar() {
		for(int i = 0; i < 8;i++){
			for(int j = 0; j < 8;j++){
				System.out.print(tabuleiro[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("============================================");
	}
	static int procuraColuna(char[] linha, int k) {
		for(int i = k;i < 8;i++){
			if(linha[i] == '_'){
				return i;
			}
		}
		return -1;
	}
	static void zerar(){
		int i = 0, j = 0;
		for(i = 0;i < 8;i++){
			for(j = 0;j < 8;j++){
				tabuleiro[i][j] = '_';
			}
		}
	}
	static void preencher(int i, int j) {
		tabuleiro[i][j] = 'R';
		int k = 1;
		while(i+k < 8){
			tabuleiro[i+k][j] = 'x';
			k++;
		}
		k=1;
		while(j+k < 8){
			tabuleiro[i][j+k] = 'x';
			k++;
		}
		k=1;
		while(i-k >= 0){
			tabuleiro[i-k][j] = 'x';
			k++;
		}
		k = 1;
		while(j-k >= 0){
			tabuleiro[i][j-k] = 'x';
			k++;
		}
		k =1;
		while(i+k < 8 && j+k < 8){
			tabuleiro[i+k][j+k] = 'x';
			k++;
		}
		k = 1;
		while(i-k >= 0 && j-k >= 0){
			tabuleiro[i-k][j-k] = 'x';
			k++;
		}
		k=1;
		while(i + k < 8 && j-k >=0){
			tabuleiro[i+k][j-k] = 'x';
			k++;
		}
		k = 1;
		while(i-k >= 0 && j+k <8){
			tabuleiro[i-k][j+k] = 'x';
			k++;
		}
	}
	//	public static void main(String[] args) throws InterruptedException, IOException {
	//		zerar();
	//		Linha l = new Linha(0,0);
	//		frame = 100;
	//		while(true){
	//			Start(l);
	//			//Thread.sleep(frame);
	//		}
	//	}
}