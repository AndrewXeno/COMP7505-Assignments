package adsa2p2;
import java.io.*;
import java.util.*;
public class ProblemTwo {

	/* 
	 * solve the problem by iterative update the minimum tap number 2d array 
	 * return the farmost reached distance.
	 */
	static public int solver(int sizeN,int sizeM, int[] up, int[] drop, int[][] minTap,int[][] forbidden){
		int time;
		for(time=0;time<sizeN;time++){
			int validCount=0;
			for(int height=1;height<sizeM; height++){
				if(minTap[time][height]!=999999){
					validCount++;
					int nextHeight;
					int nextTime=time+1;
					nextHeight = height - drop[time];
					if (nextHeight > 0 && forbidden[nextTime][nextHeight] != 1){
						if(minTap[nextTime][nextHeight]>minTap[time][height]){
							minTap[nextTime][nextHeight]=minTap[time][height];
						}
					}
					if(up[time]==0){
						nextHeight = height + up[time];
						if(forbidden[nextTime][nextHeight] != 1){
							if(minTap[nextTime][nextHeight] > minTap[time][height] + 1){
								minTap[nextTime][nextHeight] = minTap[time][height] + 1;
							}
						}
					}else{
						for(int i = 1; height + i * up[time] < sizeM; i++){
							nextHeight = height + i * up[time];
							if(forbidden[nextTime][nextHeight] != 1){
								if(minTap[nextTime][nextHeight] > minTap[time][height] + i){
									minTap[nextTime][nextHeight] = minTap[time][height] + i;
								}
							}
						}
					}
				}
			}
			if(validCount==0){
				return time-1;
			}
		}
		return time-1;
	}
	
	
	public static void main(String[] args) throws IOException {
		int sizeN = 0, sizeM = 0, pipeNum = 0, initM = 0;
		int[] up = null;
		int[] drop = null;
		int[] pipeTime = null;
		int[][] minTap = null;
		int[][] forbidden = null;
		
		BufferedReader input = new BufferedReader(new FileReader(args[0] + ".in"));
		String line;
		Scanner s;
		try {
			line = input.readLine();
			s = new Scanner(line);
			sizeN = s.nextInt();
			sizeM = s.nextInt();
			pipeNum = s.nextInt();
			s.close();
			
			line = input.readLine();
			s = new Scanner(line);
			initM = s.nextInt();
			s.close();
			
			up = new int[sizeN];
			drop = new int[sizeN];
			pipeTime = new int[pipeNum];
			minTap = new int[sizeN+1][sizeM+1];
			forbidden = new int[sizeN+1][sizeM+1];
			
			for(int i = 0; i < sizeN + 1;i++){
				for(int j = 0; j < sizeM + 1; j++){
					minTap[i][j] = 999999;
					forbidden[i][j] = 0;
					forbidden[i][0] = 1;
					forbidden[i][sizeM] = 1;
				}
			}
			minTap[0][initM] = 0;
			
			line = input.readLine();
			s = new Scanner(line);
			up[0] = s.nextInt();
			for(int i = 1; i < sizeN; i++){
				up[i] = s.nextInt();
			}
			s.close();
			
			line = input.readLine();
			s = new Scanner(line);
			drop[0] = s.nextInt();
			for(int i = 1; i < sizeN; i++){
				drop[i] = s.nextInt();
			}
			s.close();
			
		
			for(int i = 0;i < pipeNum; i++){
				line = input.readLine();
				s = new Scanner(line);
				int time, gap1, gap2;
				time = s.nextInt();
				gap1 = s.nextInt();
				gap2 = s.nextInt();
				pipeTime[i] = time;
				s.close();
				for(int j = 0; j < gap1 + 1; j++){
					forbidden[time][j] = 1;
				}
				for(int j = gap2; j < sizeM + 1; j++){
					forbidden[time][j] = 1;
				}
			}
		} 
		catch (Exception e) {
		} 
		finally {
			input.close(); 
		}
	
		int maxReached=solver(sizeN,sizeM, up, drop, minTap,forbidden);
		int minResult = 999999;
		int passedPipe = 0;
		int out1 = 0;
		int out2;
		for(int i = 0;i < sizeM + 1; i++){
			if(minTap[sizeN][i] < minResult){
				minResult = minTap[sizeN][i];
			}
		}
		
		if (minResult==999999){
			for(int i = 0;i < pipeNum; i++){
				if(pipeTime[i] <= maxReached){
					passedPipe++;
				}
			}
			out1 = 0;
			out2 = passedPipe;
		} else{
			out1 = 1;
			out2 = minResult;
		}
		
		
		FileWriter output = new FileWriter(args[0] + ".out");
		String ls = System.getProperty("line.separator");
		output.write(out1 + ls + out2 + ls);
		output.close();	
	}
}
