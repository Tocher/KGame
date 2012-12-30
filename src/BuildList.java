
public class BuildList {
	public Animator img;
	public int w;
	public int h;
	public int size = 100;
	public int frame_stay_right_start;
	public int frame_stay_right_finish;
	
	public BuildList(String name,int row, int col, int w, int h)
	{
		Init init_png = new Init(); 
		this.w = w;
		this.h = h;
		this.img = new Animator(init_png.getArrayList(name, w, h, row, col));
	}
	
	public void frames_load(int sr1,int sr2)
	{
		frame_stay_right_start = sr1;
		frame_stay_right_finish = sr2;
	}
}
