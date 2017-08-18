package BackTrack;
public class Linha {
	int i, j, k;
	Linha proximo,anterior;
	boolean checkado;
	static int linhas;
	public Linha(int i, int j, int k){
		this.i = i;
		this.j = j;
		this.k = k;
		linhas = 0;
	}
	public Linha(int i, int j){
		this.i = i;
		this.j = j;
		linhas = 0;
	}
}
