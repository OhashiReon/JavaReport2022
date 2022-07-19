import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Collections;
import java.util.InputMismatchException;

class Card{
    String color = "";
    int number = 0;
    int colorCode = 0;
    public Card(int number,String color){
        this.color = color;
        this.number = number;
        if(color == "r"){
            colorCode = 1;
        }else if(color == "g"){
            colorCode = 2;
        }else if (color == "y"){
            colorCode = 3;
        }else if(color == "b"){
            colorCode =4;
        }
    }
    public void show(){
        System.out.print("\u001b[00;3"+colorCode+"m "+number+color+"\u001b[00m");
    }
}

class Cards{
    List<Card> cards = new ArrayList<Card>();
    public Cards(){

    }
    public void yamaInit(){
        String[] colors = {"r","g","b","y"};
        int[] numbers = {1,2,3,4,5,6,7,8,9};
        for(String y:colors){
            this.cards.add(new Card(0,y));
            for(int x:numbers){
                Card c = new Card(x,y);
                this.cards.add(c);
                this.cards.add(c);
            }
        }
    }
    public List<Card> CanReturn(Card c){
        List<Card> returnableCards = new ArrayList<Card>();
        for(Card x:cards){
            if(c.color == x.color || c.number == x.number){
                returnableCards.add(x);
            }
        }
        //System.out.println();
        return returnableCards;
    }

    
    public Card remove(Card c){
        int index = -1;
        for(Card x:cards){
            index += 1;
            if(x.color == c.color && x.number == c.number){
                break;
            }
        }
        cards.remove(index);
        return c;
    }
    public Card randomPop(){
        shuffle();
        Card c = cards.remove(cards.size()-1);
        return c;
    }
    public void add(Card x){
        this.cards.add(x);
    }
    public void sort(){
        
    }
    public void show(){
        for(Card x:cards){
            x.show();
        }
        //System.out.println();
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
}

interface Iplayable{
    Card returnCard(Card c);
    boolean CanReturn(Card c);
    void add(Card c);
    void show();
    String getName();
    boolean isWin();
}

class HumanPlayer implements Iplayable{
    public Cards cards;
    public String name;
    public HumanPlayer(String name){
        cards = new Cards();
        this.name = name;
    }
    public Card returnCard(Card c){
        System.out.print("あなたの出せるカードは");
        List<Card> returnableCards = cards.CanReturn(c);
        for(Card x:returnableCards){
            x.show();
        }
        System.out.println();
        System.out.println("あなたの出したいカードは前から何番目のカードですか?(最初が0番目)");
        Card returnCard;
        int num=-1;
        while(true){
            System.out.println("0~"+(returnableCards.size()-1)+"の範囲で入力してください");
            try{
                Scanner sc = new Scanner(System.in);
                num = sc.nextInt();
                if(0<=num&&num<returnableCards.size()){
                    break;
                }
                System.out.println("範囲外です");
            }catch (InputMismatchException e){
                System.out.println("数字で入力してください");
            }
        }
        System.out.println(num);

        returnCard = returnableCards.get(num); 
        cards.remove(returnCard);
        return returnCard;
    }
    public void add(Card c){
        cards.add(c);
    }
    public void show(){
        System.out.print("あなたのカードは");
        cards.show();
        System.out.println();
    }
    public boolean CanReturn(Card c){
        if(cards.CanReturn(c).size()!=0){
            return true;
        }else{
            System.out.println("あなたの出せるカードがないので一枚引きます");
            return false;
        }
    }
    public String getName(){
        return this.name;
    }
    public boolean isWin(){
        if(cards.cards.size()==0){
            return true;
        }else{
            return false;
        }
    }
}

class ComputerPlayer implements Iplayable{
    public Cards cards;
    public String name;
    public ComputerPlayer(String name){
        cards = new Cards();
        this.name = name;
    }
    public Card returnCard(Card c){
        //cards.show();
        List<Card> returnableCards = cards.CanReturn(c);
        Card returnCard = returnableCards.get(0); 
        System.out.print("相手は");
        returnCard.show();
        System.out.println("を出しました");
        
        cards.remove(returnCard);
        System.out.println("相手は"+(cards.cards.size())+"枚のカードを持っています.");
        return returnCard;
    }
    public void add(Card c){
        cards.add(c);
    }
    public void show(){
        //System.out.println("CPのカードは");
        //cards.show();
    }
    public boolean CanReturn(Card c){
        if(cards.CanReturn(c).size()!=0){
            return true;
        }else{
            System.out.println("相手は出せるカードがないので一枚引きました");
            System.out.println("相手は"+(cards.cards.size()+1)+"枚のカードを持っています.");
            return false;
        }
    }
    public String getName(){
        return this.name;
    }
    public boolean isWin(){
        if(cards.cards.size()==0){
            return true;
        }else{
            return false;
        }
    }
}


public class Uno {
    public static void main(String[] args){
        Cards yama = new Cards();
        Card nowCard;
        yama.yamaInit();
        yama.shuffle();
        nowCard = yama.randomPop();
        yama.add(nowCard);
        Iplayable player1 = new HumanPlayer("自分");
        Iplayable player2 = new ComputerPlayer("相手");
        for(int i = 0;i<6;i++){
            player1.add(yama.randomPop());
            player2.add(yama.randomPop());
        }
        Iplayable nowPlayer = player1;
        int i =1;
        while(true){
            System.out.println(i+"ターン目=====================");
            System.out.println("現在のカードは");
            nowCard.show();
            System.out.println();
            nowPlayer.show();
            if(nowPlayer.CanReturn(nowCard)){
                nowCard = nowPlayer.returnCard(nowCard);
                yama.add(nowCard);
            }else{
                nowPlayer.add(yama.randomPop());
            }
            if(nowPlayer.isWin()){
                System.out.println(nowPlayer.getName()+"の勝利です");
                break;
            }
            if(nowPlayer == player1){
                nowPlayer = player2;
            }else{
                nowPlayer =player1;
            }
            i++;
        }
    }
}
