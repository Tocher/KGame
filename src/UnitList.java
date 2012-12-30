public class UnitList {	
	public Animator img;
	public int w;
	public int h;
	public int ms;
	public int size = 100;
	public int range;
	public int dmg_min;
	public int dmg_max;
	
	public int frame_stay_right_start;
	public int frame_stay_right_finish;
	public int frame_attack_right_start;
	public int frame_attack_right_finish;
	public int frame_run_right_start;
	public int frame_run_right_finish;
	
	public UnitList(String name,int row, int col, int w, int h,int ms,int range,int dmg_min,int dmg_max)
	{
		Init init_png = new Init(); 
		this.w = w;
		this.h = h;
		this.ms = ms;
		this.range = range;
		this.dmg_min = dmg_min;
		this.dmg_max = dmg_max;
		this.img = new Animator(init_png.getArrayList(name, w, h, row, col));
	}
	
	public void frames_load(int sr1,int sr2,int ar1,int ar2,int rr1,int rr2)
	{
		frame_stay_right_start = sr1;
		frame_stay_right_finish = sr2;
		frame_attack_right_start = ar1;
		frame_attack_right_finish = ar2;
		frame_run_right_start = rr1;
		frame_run_right_finish = rr2;
	}
	
	public int getDmg()
	{
		return dmg_min + (int) Math.random()*(dmg_max-dmg_min+1);
	}
}
