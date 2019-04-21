package kotodaman;

import java.io.*;

public class Prediction {
	public static void main(String[] args)throws IOException{
		String[] party = {"�R�g�_�}��1","�R�g�_�}��2","�R�g�_�}��3","�R�g�_�}��4","�R�g�_�}��5","�R�g�_�}��6","�R�g�_�}��7","�R�g�_�}��8","�R�g�_�}��9","�R�g�_�}��10","�R�g�_�}��11","�R�g�_�}��12"};
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int selected[] = new int[16];
		int pop[] = new int[16];
		int order[] = new int[8];
		int next[] = new int[16];
		int addr[] = new int[16];
		int hand[] = new int[4]; 
		int count=0,flg=0,turn=0,move=0;
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
				System.out.println("������D�������珇�Ԃɔԍ��𔼊p�œ��͂��Ă��������D");
				for(int i=0; i<hand.length; i++) {
					str = br.readLine();
					hand[i] = Integer.parseInt(str);
				}
			}
			
			//Show your hands.
			System.out.println("�E���݂̎�D");
			for(int i=0;i<hand.length;i++) {
				System.out.print(party[hand[i]] + " ");
				if(i==hand.length-1)
					System.out.println("\n");
			}
			
			turn = (count/4)+1;
			move = count+1;
			System.out.println("���݂̃^�[�����F"+ turn + "�^�[����\n�E" + move + "���\n");
			
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
				//Show next hands.
				System.out.println("\n�E�҂��D");
				for(int i=0;i<3;i++) {
					System.out.println(party[selected[addr[(count%16)+(i+1)]]]);
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
				for(int k=0;k<order.length;k++) {
					if(count>selected.length && order[k]==0) {
						for(int i=0; i<order.length;i++) {
							for(int j=8;j<pop.length;j++) {
								if(order[i]!=0) 
									break;
								if(selected[i]==pop[j])
									order[i] = j-i;
							}
							if(order[i]==0) {
								for(int j=0;j<count%selected.length;j++) {
									if(order[i]!=0) 
										break;
									if(selected[i]==pop[j])
										order[i] = j-i;
								}
							}
						}
					}
				}
				
				for(int i=0;i<order.length;i++) {
					if(order[i]!=0) {
						flg = 1;
					}else {
						flg = 0;
						break;
					}
				}
				
				if(flg == 1) {
					for(int i=0;i<next.length;i++) {
						next[i] = (i + order[i%order.length]) % 16;
					}
					flg++;
					System.out.println("\n\n\n���������肵�܂����D\n��������͎g�p�����R�g�_�}���̂ݓ��͂��Ă��������D\n�������͂��Ă��������D");
					br.readLine();
					System.out.println("\n\n\n");
				}
			}else {
				//Set new hand.
				for(int i=0;i<hand.length;i++) {
					if(hand[i]==selected[count%selected.length]) {
						hand[i] = selected[addr[count%selected.length]];
					}
				}
			}
			
			System.out.println("\n-------------------------------------------------------\n");
			count++;
		}
	}
	
	public static void EnterSelectedKotodaman(int hand[], String str, BufferedReader br, int selected[], int count) throws IOException{
		System.out.println("�g�����R�g�_�}���̔ԍ��𔼊p�œ��͂��Ă��������D");
		str = br.readLine();
		if(JudgeEntered(Integer.parseInt(str),hand)==true) {
			selected[count%selected.length] = Integer.parseInt(str);
		}else {
			System.out.println("��������������͂��Ă��������D\n\n");
			EnterSelectedKotodaman(hand,str,br,selected,count);
		}
	}
	
	public static void EnterPoppedKotodaman(int[] hand, String str, BufferedReader br, int[] pop, int count)throws IOException {
		System.out.println("�o�Ă����R�g�_�}���̔ԍ��𔼊p�œ��͂��Ă��������D");
		str = br.readLine();
		if(JudgeEntered(Integer.parseInt(str),hand) == false) {
			pop[count%pop.length] = Integer.parseInt(str);
		}else {
			System.out.println("��������������͂��Ă��������D\n\n");
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
