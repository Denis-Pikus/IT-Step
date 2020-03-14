import org.w3c.dom.ls.LSOutput;

import java.util.LinkedList;

public class Player {
    private String name;
    private LinkedList<Integer> myMaps; // Карты игрока
    private boolean flag = false; //флаг для определения хода игрока

    public Player() {
        name = "Вася";
    }
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public LinkedList<Integer> getMyMaps() {
        return myMaps;
    }

    public void setMyMaps(LinkedList<Integer> myMaps) {
        this.myMaps = myMaps;
    }

//Метод выводит на экран карты игрока после раздачи
    public void show(){
        System.out.print(this + " карты на руках после раздачи: ");
        System.out.println(this.getMyMaps());
    }

    @Override
    public String toString() {
        return "игрок " + name;
    }
}
