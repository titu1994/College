package college.sem5.cn;

import java.util.Scanner;

public class DijekstrasAlgorighm {
	private static final int INFINITY = 999;
	public static void dij(int n,int v,int cost[][],int dist[]) {
		int i,j,u = 0,min;
		boolean visited[] = new boolean[10];
		
		for(i=1;i<=n;i++)
			dist[i]=cost[v][i];
		i=2;
		while(i<=n) {
			min=INFINITY;
			for(j=1;j<=n;j++)
				if(dist[j]<min && !visited[j]){
					min=dist[j];
					u=j;
				}
			visited[u]=true;
			i++;
			for(j=1;j<=n;j++)
				if((dist[u]+cost[u][j]<dist[j]) && !visited[j])
					dist[j]=dist[u]+cost[u][j];
		}
	}
	
	public static void main(String args[]) {
		int n,v,i,j,cost[][] = new int[10][10],dist[] = new int[10];
		
		Scanner sc = new Scanner(System.in);
		
		System.out.printf("\n Enter the number of nodes:");
		n = sc.nextInt();
		System.out.printf("\n Enter the cost matrix:\n");
		for(i=1;i<=n;i++)
			for(j=1;j<=n;j++) {
				cost[i][j] = sc.nextInt();
				if(cost[i][j]==0)
					cost[i][j] = INFINITY;
			}
		System.out.printf("\n Enter the source :");
		v = sc.nextInt();
		dij(n,v,cost,dist);
		System.out.printf("\n Shortest path:\n");
		for(i=1;i<=n;i++)
			if(i!=v)
				System.out.printf("%d->%d,cost=%d\n",v,i,dist[i]);

	}
}
