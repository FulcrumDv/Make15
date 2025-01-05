package entities;


// There will be two elements displayed name and score
public class HighScoreEntry {
    // There will be two elements displayed name and score
    private String name;
    private int score;

    public HighScoreEntry(){}

    public HighScoreEntry(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    @Override
    public String toString(){
        return name + " : : " + score;
    }




}
