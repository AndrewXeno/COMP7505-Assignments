package adsa2p1;
import java.util.*;
import java.io.*;

public class ProblemOne {

	/* 
	 * the greedy algorithm, takes in an order and assign rooms with smallest 
	 * available room number according to the order 
	 */
	static public int greedy(int courseNum, List<Course> courseList, int[] order){
		int maxRoomNum=0;
		for (int i=0;i<courseNum;i++){
			int[] roomChoices = new int[courseNum+1];
			Course c1 = courseList.get(order[i]);
			for(Course c2: c1.getAdjList()){
				if (c2.getRoom()!=0){
					roomChoices[c2.getRoom()]=1;
				}
			}
			for(int j=1;j<courseNum+1;j++){
				if(roomChoices[j]==0){
					c1.setRoom(j);
					if(j>maxRoomNum){
						maxRoomNum=j;
					}
					break;
				}
			}
		}
		return maxRoomNum;
	}
	
	/* 
	 * reset all courses rooms to default 0 
	 */
	static public void resetAll(List<Course> courseList){
		for(Course c:courseList){
			c.resetRoom();
		}
	}
	
	/* 
	 * swap the value of elements with index a,b in the array 
	 */
	static public void swap(int[] array, int a, int b){
		int temp=array[a];
		array[a]=array[b];
		array[b]=temp;
	}	
	
	/* 
	 * generate full permutation of original order, store all permutations into a list 
	 */
	static public void fullPerm(int[] order, int start, int len, List<int[]> orderList){
		if (start==len-1){
			int copy[] = Arrays.copyOf(order, order.length);
			orderList.add(copy);
		}else{
			for (int i = start; i < len; i ++){
				swap(order, start, i);  
				fullPerm(order, start + 1, len, orderList);  
		     	swap(order, start, i);  
			}  
		}
	}
	
	
	/* 
	 * the optimal algorithm, perform greedy algorithm on each orders in the orderList, 
	 * and keep the best result
	 */
	static public int optimal(int courseNum, List<Course> courseList, List<int[]> orderList){
		int minRoomNum=courseNum+1;
		int optimalOrderIndex=0;
		int i=0;
		for(int[] order: orderList){
			int roomNum=greedy(courseNum, courseList, order);
			if (roomNum<minRoomNum){
				minRoomNum=roomNum;
				optimalOrderIndex=i;
				
			}
			i++;
			resetAll(courseList);
		}
		greedy(courseNum, courseList, orderList.get(optimalOrderIndex));
		return minRoomNum;
	}
	
	
	public static void main(String[] args) throws IOException {
		int courseNum=0;
		int roomNum = 0;
		List<Course> courseList=new ArrayList<Course>();
		BufferedReader input = new BufferedReader(new FileReader(args[0] + ".in"));
		String line;
		Scanner s;
		try {
			line = input.readLine();
			s = new Scanner(line);
			courseNum = s.nextInt();
			s.close();
			
			for (int i=0;i<courseNum;i++){
				line = input.readLine();
				Course c=new Course(line);
				courseList.add(c);
				
			}
		} 
		catch (Exception e) {
		} 
		finally {
			input.close(); 
		}
		
		for(int i=0;i<courseNum-1;i++){
			for(int j=i+1;j<courseNum;j++){
				if(courseList.get(i).isOverlap(courseList.get(j))){
					courseList.get(i).addAdj(courseList.get(j));
					courseList.get(j).addAdj(courseList.get(i));
				}
			}
		}
		
		int[] arrangeOrder = new int[courseNum];
		
		
		if (args[1].equals("greedy")){
			for(int i=0; i<courseNum; i++){
				arrangeOrder[i]=i;
			}
			roomNum=greedy(courseNum, courseList, arrangeOrder);
		}
		if (args[1].equals("optimal")){
			List<int[]> orderList=new ArrayList<int[]>();
			for(int i=0; i<courseNum; i++){
				arrangeOrder[i]=i;
			}
			fullPerm(arrangeOrder, 0, courseNum, orderList);
			roomNum=optimal(courseNum, courseList, orderList);
		}
		
		FileWriter output = new FileWriter(args[0] + ".out");
		String ls = System.getProperty("line.separator");
		output.write(roomNum + ls);
		for(Course c:courseList){
			output.write(c.getName() + " " + c.getRoom()+ ls);
		}
		output.close();	
	}
}
