package kotodaman;

import java.io.*;

public class Prediction {
    public static void main(String[] args)throws IOException{
        String[] party = new String[12];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int selected[] = new int[16];
        int pop[] = new int[16];
        final int order[] = new int[8];
        final int next[] = new int[16];
        final int addr[] = new int[16];
        int hand[] = {-1, -1, -1, -1};
        int count=0,flg=0,turn=0,move=0,turnstartpoint=0,maxlen=0,rest = 0;
        String str = null;

        while(true) {
            if(count==0) {
                //Set your party.
                SetParty(party, br, maxlen);
            }

            //Show your party.
            ShowParty(party);

            //Set your hands.
            if(count==0) {
                SetHands(hand,str,br);
            }

            //Show your hands.
            ShowHands(hand,party);

            turn = (count/4)+1;
            move = count+1;
            System.out.println("現在のターン数："+ turn + "ターン目\n・" + move + "手目\n");

            if(flg > 0) {
                if(count%4==0)
                    turnstartpoint = count;
                //Show next hands.
                System.out.println("\n・待ち札");
                for(int i=0;i<3-count%4;i++) {
                    System.out.println(party[selected[addr[((count%addr.length)+i)%addr.length]]]);
                }
                System.out.println("\n・次のターン");
                for(int i=0;i<4;i++) {
                    System.out.println(party[selected[addr[((turnstartpoint%addr.length)+i+3)%addr.length]]]);
                }
                System.out.println("\n");
            }

            //Enter selected kotodaman.
            EnterSelectedKotodaman(hand,str,br,selected,count);

            if(flg == 0) {
                //Enter popped kotodaman.
                EnterPoppedKotodaman(hand,str,br,pop,count);

                //Set new hand.
                SwitchHand(hand,selected,pop[count%selected.length],count);

                //Judge order.
                if(count == selected.length-2) {
                    for(int i=0;i<order.length;i++) {
                    	for(int j=order.length;j<pop.length;j++) {
                            if(order[i]!=0)
                                break; 
                            if(selected[i]==pop[j]) {
                                order[i] = j-i;
                            	break;
                    		}
                        }
                    	if(order[i] == 0)
                    		rest = i;
                    }
                    order[rest] = count + 1 - rest;
                    for(int i=0;i<next.length;i++) {
                        next[i] = (i + order[i%order.length]) % 16;
                    }
                    System.out.println("\n\n\n乱数列を特定しました．\nここからは使用したコトダマンのみ入力してください．\n何か入力してください．");
                    br.readLine();
                    System.out.println("\n\n\n");
                    
                    //Set addr[].
                    Setaddr(addr,next);
                    flg++;
                }
            }else {
                //Set new hand.
                SwitchHand(hand, selected, selected[addr[(count%addr.length)%addr.length]], count);
            }

            System.out.println("\n-------------------------------------------------------\n");
            count++;
        }
    }

    static void SetHands(int[] hand, String str , BufferedReader br)throws IOException {
        System.out.println("初期手札を左から順番に番号を半角で入力してください．");
        for(int i=0; i<hand.length; i++) {
            SetHandsContent(hand, i, str, br);
        }
    }
    
    static void SetHandsContent(int[] hand, int i, String str, BufferedReader br)throws IOException {
    	str = br.readLine();
    	if(JudgeEntered(Integer.parseInt(str), hand) == false && Integer.parseInt(str) >= 0 && Integer.parseInt(str) <= 11) {
    		hand[i] = Integer.parseInt(str);
    	}else {
    		System.out.println("正しい数字を入力してください．\n\n");
    		SetHandsContent(hand, i, str, br);
    	}
    }

    static void ShowHands(int[] hand, String[] party) {
        System.out.println("・現在の手札");
        for(int i=0;i<hand.length;i++) {
            System.out.print(party[hand[i]] + " ");
            if(i==hand.length-1)
                System.out.println("\n");
        }
    }

    static void EnterSelectedKotodaman(int[] hand, String str, BufferedReader br, int[] selected, int count) throws IOException{
        System.out.println("使ったコトダマンの番号を半角で入力してください．");
        str = br.readLine();
        if(JudgeEntered(Integer.parseInt(str),hand)==true) {
            selected[count%selected.length] = Integer.parseInt(str);
        }else {
            System.out.println("正しい数字を入力してください．\n\n");
            EnterSelectedKotodaman(hand,str,br,selected,count);
        }
    }

    static void EnterPoppedKotodaman(int[] hand, String str, BufferedReader br, int[] pop, int count)throws IOException {
        System.out.println("出てきたコトダマンの番号を半角で入力してください．");
        str = br.readLine();
        if(JudgeEntered(Integer.parseInt(str),hand) == false) {
            pop[count%pop.length] = Integer.parseInt(str);
        }else {
            System.out.println("正しい数字を入力してください．\n\n");
            EnterPoppedKotodaman(hand,str,br,pop,count);
        }
    }

    static boolean JudgeEntered(int enter,int hand[]) {
        for(int i=0;i<hand.length;i++) {
            if(enter == hand[i]) {
                return true;
            }
        }
        return false;
    }

    static void ShowParty(String[] party) {
        for(int i=0;i<party.length;i++) {
            System.out.printf("%-7s", party[i]);
            if(i<10)
                System.out.print(": " + i);
            else
                System.out.print(":" + i);
            if(i%4==3)
                System.out.print("\n");
            else
                System.out.print(" | ");
        }
        System.out.print("\n");
    }

    static void Setaddr(int[] addr, int[] next) {
        for(int i=0;i<addr.length;i++) {
            for(int j=0;j<next.length;j++) {
                if(next[j]==i) {
                    addr[i] = j;
                    break;
                }
            }
        }
    }

    static void SetParty(String[] party, BufferedReader br, int maxlen)throws IOException {
        System.out.println("パーティに入っているコトダマンの名前をすべて入力してください．");
        for(int i=0;i<party.length;i++) {
            party[i] = br.readLine();
            if(party[i].length() > maxlen)
                maxlen = party[i].length();
        }
        for(int i = 0;i<party.length;i++) {
            for(int j=0;j<maxlen-party[i].length();j++) {
                party[i] = party[i] + "　";
            }
        }
    }

    static void SwitchHand(int[] hand, int[] selected, int nextkotnum, int count) {
        for(int i=0;i<hand.length;i++) {
            if(hand[i]==selected[count%selected.length])
                hand[i] = nextkotnum;
        }
    }
}
