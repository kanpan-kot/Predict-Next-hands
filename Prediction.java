package kotodaman;

import java.io.*;

public class Prediction {
	public static void main(String[] args)throws IOException{
		String[] party = {"コトダマン1","コトダマン2","コトダマン3","コトダマン4","コトダマン5","コトダマン6","コトダマン7","コトダマン8","コトダマン9","コトダマン10","コトダマン11","コトダマン12"};
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int selected[] = new int[16];
		int pop[] = new int[16];
		int order[] = new int[8];
		int next[] = new int[16];
		int addr[] = new int[16];
		int hand[] = new int[4]; 
		int count=0,flg=0,turn=0,move=0,tsp=0;
		String str = null;
		
		while(true) {
			//Show your party.
			for(int i=0;i<party.length;i++) {
				System.out.print(party[i] + ":" + i + " ");
				if(i%4==3)
					System.out.print("\n");
			}
			System.out.print("\n");
			
			//Set your hands.
			if(count==0) {
				System.out.println("初期手札を左から順番に番号を半角で入力してください．");
				for(int i=0; i<hand.length; i++) {
					str = br.readLine();
					hand[i] = Integer.parseInt(str);
				}
			}
			
			//Show your hands.
			System.out.println("・現在の手札");
			for(int i=0;i<hand.length;i++) {
				System.out.print(party[hand[i]] + " ");
				if(i==hand.length-1)
					System.out.println("\n");
			}
			
			turn = (count/4)+1;
			move = count+1;
			System.out.println("現在のターン数："+ turn + "ターン目\n・" + move + "手目\n");
			
			if(flg ==2) {
				//Set addr[].
				for(int i=0;i<addr.length;i++) {
					for(int j=0;j<next.length;j++) {
						if(next[j]==i) {
							addr[i] = j;
							break;
						}
					}
				}
				flg++;
			}
			
			if(flg > 2) {
				if(count%4==0)
					tsp = count;
				//Show next hands.
				System.out.println("\n・待ち札");
				for(int i=0;i<3-count%4;i++) {
					System.out.println(party[selected[addr[((count%addr.length)+i)%addr.length]]]);
				}
				System.out.println("\n・次のターン");
				for(int i=0;i<4;i++) {
					System.out.println(party[selected[addr[((tsp%addr.length)+i+3)%addr.length]]]);
				}
				System.out.println("\n");
			}
			
			//Enter selected kotodaman.
			EnterSelectedKotodaman(hand,str,br,selected,count);
			
			if(flg < 2) {
				//Enter popped kotodaman.
				EnterPoppedKotodaman(hand,str,br,pop,count);
				
				//Set new hand.
				for(int i=0;i<hand.length;i++) {
					if(hand[i]==selected[count%selected.length])
						hand[i] = pop[count%selected.length];
				}
				
				//Judge order.
				if(count == selected.length-1) {
					for(int i=0;i<order.length;i++) {
						if(order[i]==0) {
							for(int j=order.length;j<pop.length;j++) {
								if(order[i]!=0) 
									break;
								if(selected[i]==pop[j])
									order[i] = j-i;
							}
						}
					}
					flg++;
				}
				for(int i=0;i<order.length;i++) {
					System.out.println(order[i]);
				}
				
				if(flg == 1) {
					for(int i=0;i<next.length;i++) {
						next[i] = (i + order[i%order.length]) % 16;
					}
					flg++;
					System.out.println("\n\n\n乱数列を特定しました．\nここからは使用したコトダマンのみ入力してください．\n何か入力してください．");
					br.readLine();
					System.out.println("\n\n\n");
				}
			}else {
				//Set new hand.
				for(int i=0;i<hand.length;i++) {
					if(hand[i]==selected[count%selected.length]) {
						hand[i] = selected[addr[(count%addr.length)%addr.length]];
					}
				}
			}
			
			System.out.println("\n-------------------------------------------------------\n");
			count++;
		}
	}
	
	public static void EnterSelectedKotodaman(int hand[], String str, BufferedReader br, int selected[], int count) throws IOException{
		System.out.println("使ったコトダマンの番号を半角で入力してください．");
		str = br.readLine();
		if(JudgeEntered(Integer.parseInt(str),hand)==true) {
			selected[count%selected.length] = Integer.parseInt(str);
		}else {
			System.out.println("正しい数字を入力してください．\n\n");
			EnterSelectedKotodaman(hand,str,br,selected,count);
		}
	}
	
	public static void EnterPoppedKotodaman(int[] hand, String str, BufferedReader br, int[] pop, int count)throws IOException {
		System.out.println("出てきたコトダマンの番号を半角で入力してください．");
		str = br.readLine();
		if(JudgeEntered(Integer.parseInt(str),hand) == false) {
			pop[count%pop.length] = Integer.parseInt(str);
		}else {
			System.out.println("正しい数字を入力してください．\n\n");
			EnterPoppedKotodaman(hand,str,br,pop,count);
		}
	}
	
	public static boolean JudgeEntered(int enter,int hand[]) {
		for(int i=0;i<hand.length;i++) {
			if(enter == hand[i]) {
				return true;
			}
		}
		return false;
	}
}
