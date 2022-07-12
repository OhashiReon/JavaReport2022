import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.lang.NumberFormatException;

public class Othello {
    public static void main(String[] args){
        IPlayer player1 = new HumanPlayer("o");
        IPlayer player2 = new ComputerPlayer("x");
        IPlayer nowPlayer = player1;
        int passCount = 0;

        board b = new board();
        b.show();
        while(true){
            if(b.isBoardFull()){
                System.out.println("ゲームを終了します.");
                break;
            }
            if(passCount == 2){
                System.out.println("お互いに置けるマスがなくなったのでゲームを終了します.");
                break;
            }
            try{
                Thread.sleep(50);
            }catch (InterruptedException e){

            }
            String nowstr = nowPlayer.getBlackOrWhite(); 
            List<Integer[]> settableSquare = b.getSettableSquare(nowstr);
            if(settableSquare.size()>0){
                Collections.shuffle(settableSquare);
                Integer[] SetPos = nowPlayer.put(settableSquare);
                b.put(SetPos[0],SetPos[1],nowstr);
                passCount = 0;
                b.show();
            }else{
                System.out.println(nowPlayer.getBlackOrWhite()+"のプレイヤーは置けるマスがありませんでした.");
                passCount +=1;
            }
            nowPlayer = changePlayer(player1, player2, nowPlayer);
        }
        System.out.println("結果を出力します.");
        b.show();
        b.showResust();

    }
    public static IPlayer changePlayer(IPlayer player1,IPlayer player2,IPlayer nowPlayer){
        if(player1 == nowPlayer){
            return player2;
        }
        return player1;
    }
}

class board{
    List<List<String>> board;
    public board(){
        board = new ArrayList<List<String>>();
        for(int i=0;i<8;i++){
            board.add(new ArrayList<>(Arrays.asList(new String[]{".",".",".",".",".",".",".","."})));
        }
        set(3,3,"o");
        set(4,4,"o");
        set(3,4,"x");
        set(4,3,"x");
    }

    public void show(){
        System.out.println("\\01234567");
        int tmp = 0;
        for(List<String> x:board){
            System.out.print(tmp);
            for(String y:x){
                if(y=="o") System.out.print("\u001b[00;35m");
                else if(y=="x")System.out.print("\u001b[00;33m");
                else System.out.print("\u001b[00;32m");;
                System.out.print(y);
                System.out.print("\u001b[00m");
            }
            tmp += 1;
            System.out.println();
        }
    }
    private void set(int x,int y,String s){
        board.get(y).set(x,s);
    }

    private String get(int x,int y){
        return board.get(y).get(x);
    }

    public void put(int x,int y,String s){
        List<Integer[]> changeSquare = getChangeSquare(x, y, s);
        for(Integer[] i : changeSquare){
            set(i[0],i[1],s);
        }
    }

    public List<Integer[]> getSettableSquare(String s){
        List<Integer[]> settableSquare = new ArrayList<Integer[]>();
        for(int i = 0;i<8;i++){
            for(int j =0;j<8;j++){
                if(get(i,j)=="."){
                    if(getChangeSquare(i, j, s).size()>0){
                        settableSquare.add(new Integer[]{i,j});
                    }
                }
            }
        }
        return settableSquare;
    }
    public void showResust(){
        int onum = 0;
        int xnum = 0;
        for(int i = 0;i<8;i++){
            for(int j =0;j<8;j++){
                if(get(i,j)=="o"){
                    onum +=1;
                }else if(get(i,j)=="x"){
                    xnum +=1;
                }
            }
        }
        System.out.println("oの数:"+onum+" xの数:"+xnum);
        if(onum>xnum){
            System.out.println("oのプレイヤーの勝利");
        }else if(onum<xnum){
            System.out.println("xのプレイヤーの勝利");
        }else{
            System.out.println("引き分け");
        }
    }
    public boolean isBoardFull(){
        int blanknum = 0;
        for(int i = 0;i<8;i++){
            for(int j =0;j<8;j++){
                if(get(i,j)=="."){
                    blanknum +=1;
                }
            }
        }
        if(blanknum ==0){return true;}
        return false;
    }
    private List<Integer[]> getChangeSquare(int x,int y,String s){
        List<Integer[]> changeSquare = new ArrayList<Integer[]>();
        for(int i:new Integer[]{-1,0,1}){
            for(int j:new Integer[]{-1,0,1}){
                List<Integer[]> tmpchangeSquare = new ArrayList<Integer[]>();
                int nowx = x;
                int nowy = y;

                while(true){
                    if(i==0 && j == 0) break;
                    nowx += i;
                    nowy += j;
                    //System.out.println(nowx+" "+nowy);
                    if(nowx<0||nowy<0||nowx>=8||nowy>=8){
                        break;
                    }else if(get(nowx,nowy)=="."){
                        break;
                    }else if(get(nowx,nowy)==s){
                        changeSquare.addAll(tmpchangeSquare);
                        break;
                    }else{
                        tmpchangeSquare.add(new Integer[]{nowx,nowy});
                    }
                }
            }
        }
        if(changeSquare.size()>0) changeSquare.add(new Integer[]{x,y});
        return changeSquare;
    }
}

interface IPlayer{
    public Integer[] put(List<Integer[]> settableSquare);
    public String getBlackOrWhite();
}

class ComputerPlayer implements IPlayer{
    String blackOrWhite;
    public ComputerPlayer(String blackOrWhite){
        this.blackOrWhite = blackOrWhite;
    }
    public Integer[] put(List<Integer[]> settableSquare){
        return settableSquare.get(0);
    }
    public String getBlackOrWhite(){
        return blackOrWhite;
    }
}

class HumanPlayer implements IPlayer{
    String blackOrWhite;
    Scanner scanner;
    public HumanPlayer(String blackOrWhite){
        scanner = new Scanner(System.in);
        this.blackOrWhite = blackOrWhite;
    }
    public Integer[] put(List<Integer[]> settableSquare){
        System.out.println("あなたの手番です.空白区切りで２つの数字を入力してください.");
        System.out.print("設置可能なマス:");
        show(settableSquare);
        while(true){
            try{
                System.out.print("x y=");
                Integer[] pos = new Integer[2];
                String[] pointsStr = scanner.nextLine().split(" ");
                if(pointsStr.length<2){
                    System.out.println("空白区切りで２つの数字を入力してください");
                    continue;
                }
                for(int i = 0;i<2;i++){
                    pos[i] = Integer.parseInt(pointsStr[i]);
                }
                System.out.print("["+pos[0]+","+pos[1]+"]");
                for(Integer[] i:settableSquare){
                    if(i[0]==pos[0] &&i[1]==pos[1]){
                        return pos;
                    }
                }
                System.out.println("そのマスには設置できません.");
            }catch(NumberFormatException e){
                System.out.println("数字で入力してください.");
            }
        }
    }
    public String getBlackOrWhite(){
        return blackOrWhite;
    }
    public static void show(List<Integer[]> s){
        for(Integer[] i:s){
            System.out.print("["+i[0]+","+i[1]+"]");
        }
        System.out.println();
    }
}

