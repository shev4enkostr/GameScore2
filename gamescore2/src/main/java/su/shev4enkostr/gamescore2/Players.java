package su.shev4enkostr.gamescore2;

/**
 * Created by stas on 07.04.15.
 */
public class Players
{
    private String name = "no name";
    private int score = 0;

    private static int numberOfPlayer = 0;

    {
        numberOfPlayer++;
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

    public static int getNumberOfPlayer()
    {
        return numberOfPlayer;
    }

    public String getName()
    {
        return this.name;
    }

    public int getScore()
    {
        return this.score;
    }

    public void addScore(int score)
    {
        this.score += score;
    }
}
