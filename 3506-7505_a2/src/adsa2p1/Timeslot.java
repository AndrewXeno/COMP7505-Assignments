package adsa2p1;


/* 
 * this class is used to represent timeslots.
 */
public class Timeslot {
	public int start;
	public int end;
	public Timeslot(int a, int b){
		start=a;
		end=b;
	}
	
	/* check if two timeslots overlap */
	public boolean isOverlap(Timeslot t){
		if((t.end<=this.start)||(t.start>=this.end)){
			return false;
		}else return true;
	}
}