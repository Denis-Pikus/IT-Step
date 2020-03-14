import com.sun.imageio.plugins.wbmp.WBMPImageReader;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Game {
    private ArrayList<Integer> deck = new ArrayList<>();//Имитация колоды карт
    private Player playerFirst;
    private Player playerSecond;
    private LinkedList<Integer> lis1 = new LinkedList<>();
    private LinkedList<Integer> lis2 = new LinkedList<>();
    private int steps = 0;

    public Game(Player playerFirst, Player playerSecond) {
        this.playerFirst = playerFirst;
        this.playerSecond = playerSecond;
    }

    public Player getPlayerFirst() {
        return playerFirst;
    }

    public void setPlayerFirst(Player playerFirst) {
        this.playerFirst = playerFirst;
    }

    public Player getPlayerSecond() {
        return playerSecond;
    }

    public void setPlayerSecond(Player playerSecond) {
        this.playerSecond = playerSecond;
    }

//Метод который тусует карты в колоде
    private void initDeck(){
        for (int i = 0; i < 10; i++) {
            deck.add(i);
        }
        Collections.shuffle(deck);
    }

//Метод реализующий раздачу карт игрокам
    private void distributionOfCards(Player playerFirst, Player playerSecond){
        initDeck();
        for (int i = 0; i < deck.size(); i+=2) {
            lis1.add(deck.get(i));
        }
        for (int i = 1; i < deck.size(); i+=2) {
            lis2.add(deck.get(i));
        }
        playerFirst.setMyMaps(lis1);
        playerSecond.setMyMaps(lis2);
    }

//Метод который определяет, кто ходит первым
    private void firstMove(){
       List<String> list = Arrays.asList(new String[] {"true", "false"});
       Collections.shuffle(list);
       if (list.get(0).equals("true")){
           playerFirst.setFlag(true);
       }
       else playerSecond.setFlag(true);
    }

//Метод для определения правильного окончания шага на котором игрок выиграл партию
    private void showWinner(Player player, int steps){
        int number = Math.abs(steps % 10); //находим последнее число в цифре
        if (steps >= 11 && steps <= 14)
            System.out.println("Выиграл: " + player.toString() + " за " + steps + " шагов");
        else {
            if (number == 0 || number >= 5 && number <= 9)
                System.out.println("Выиграл: " + player.toString() + " за " + steps + " шагов");
            else {
                if (number == 1)
                    System.out.println("Выиграл: " + player.toString() + " за " + steps + " шаг");
                else {
                    if (number >= 2 && number <= 4)
                        System.out.println("Выиграл: " + player.toString() + " за " + steps + " шага");
                }
            }
        }
    }

//Метод имитирующий процесс игры
    public void playGame() {
        int playFirst = 0;
        int playSecond = 0;
        distributionOfCards(playerFirst, playerSecond); //раздаем карты
        playerFirst.show(); //выводим на экран карты игрока после раздачи
        playerSecond.show();
        firstMove(); //определяем ктопервыйходит
        while (!playerFirst.getMyMaps().isEmpty() || !playerSecond.getMyMaps().isEmpty() ||
                steps == 106) {
            try{
                if (playerFirst.isFlag()) {
                playFirst = playerFirst.getMyMaps().peek();
                playerFirst.getMyMaps().poll();
                playSecond = playerSecond.getMyMaps().peek();
                playerSecond.getMyMaps().poll();
                steps++;
                } else {
                    playSecond = playerSecond.getMyMaps().peek();
                    playerSecond.getMyMaps().poll();
                    playFirst = playerFirst.getMyMaps().peek();
                    playerFirst.getMyMaps().poll();
                    steps++;
                }
            }catch (NullPointerException e){
                if (playerFirst.getMyMaps().isEmpty() && steps < 106) {
                   showWinner(playerSecond, steps);
                    break;
                }
                else{
                     showWinner(playerFirst, steps);
                     break;
                    }
                }
            if (playFirst != 0 && playSecond != 9 || playFirst != 9 && playSecond != 0) {
                if (playFirst > playSecond) {
                    playerFirst.getMyMaps().add(playSecond);
                    playerFirst.getMyMaps().add(playFirst);
                    playerFirst.setFlag(true);
                    playerSecond.setFlag(false);
                } else {
                    playerSecond.getMyMaps().add(playFirst);
                    playerSecond.getMyMaps().add(playSecond);
                    playerFirst.setFlag(false);
                    playerSecond.setFlag(true);
                }
            } else if (playFirst == 0 && playSecond == 9) {
                playerFirst.getMyMaps().add(playSecond);
                playerFirst.getMyMaps().add(playFirst);
                playerFirst.setFlag(true);
                playerSecond.setFlag(false);
            } else if (playFirst == 9 && playSecond == 0) {
                playerSecond.getMyMaps().add(playFirst);
                playerSecond.getMyMaps().add(playSecond);
                playerFirst.setFlag(false);
                playerSecond.setFlag(true);
            }
            if (steps == 106) {
                System.out.println("Ботва!");
                break;
            }
        }
    }
}
