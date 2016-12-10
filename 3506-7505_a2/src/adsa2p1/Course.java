package adsa2p1;

import java.util.*;


/* 
 * this class is used to represent courses. each course is an vertex in the graph  
 * and uses adjacency list to store all overlapping courses
 */
public class Course {
	public String courseName;
	public int roomNum;
	public List<Timeslot> timeList;
	public List<Course> adjList;

	public Course(String str){
		adjList = new ArrayList<Course>();
		timeList = new ArrayList<Timeslot>();
		Scanner s = new Scanner(str);
		courseName = s.next(); 
		while(s.hasNextInt()){
			Timeslot t = new Timeslot(s.nextInt(), s.nextInt());
			timeList.add(t);
		}
		s.close();
	}
	
	public void addAdj(Course c){
		this.adjList.add(c);
	}
	
	/* check each timeslot to see if two courses are overlapping */
	public boolean isOverlap(Course c){
		for(Timeslot t1:this.timeList){
			for(Timeslot t2:c.timeList){
				if(t1.isOverlap(t2)){
					return true;
				}
			}
		}
		return false;
	}
	
	public int getRoom(){
		return this.roomNum;
	}
	
	public String getName(){
		return this.courseName;
	}
	
	public List<Course> getAdjList(){
		return this.adjList;
	}
	
	public void setRoom(int room){
		this.roomNum=room;
	}
	
	public void resetRoom(){
		this.roomNum=0;
	}
}
