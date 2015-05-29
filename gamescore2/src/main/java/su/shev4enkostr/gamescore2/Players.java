package su.shev4enkostr.gamescore2;

/**
 * Created by stas on 07.04.15.
 */
public class Players
{
    private String name = "no name";
    private int score = 0;
    private int numberOfPlayer = 0;

    private static int nextNumberOfPlayer = 0;

    {
        numberOfPlayer++;
        nextNumberOfPlayer++;
    }

    public Players(String name, int score)
    {
        this.name = name;
        this.score = score;
    }

    public Players(String name)
    {
        this.name = name;
    }

    public Players()
    {}

    public void setName(String name)
    {
        this.name = name;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public static int getNextNumberOfPlayer()
    {
        return nextNumberOfPlayer;
    }

    public String getName()
    {
        return this.name;
    }

    public int getScore()
    {
        return this.score;
    }

    public int getNumberOfPlayer()
    {
        return this.numberOfPlayer;
    }

    public void addScore(int score)
    {
        this.score += score;
    }
}
