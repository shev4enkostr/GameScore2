package su.shev4enkostr.gamescore2;
import android.os.*;

/**
 * Created by stas on 07.04.15.
 */
public class Players implements Parcelable
{
    private String name = "no name";
    private int score = 0;
    private int numberOfPlayer = 0;
	private boolean checked = false;

    private static int nextNumberOfPlayer = 0;

    {
        nextNumberOfPlayer++;
    }

    public Players(String name, int score)
    {
        this.name = name;
        this.score = score;
		this.numberOfPlayer = nextNumberOfPlayer - 1;
    }

    public Players(String name)
    {
        this.name = name;
		this.numberOfPlayer = nextNumberOfPlayer - 1;
    }

    public Players()
    {
		this.numberOfPlayer = nextNumberOfPlayer - 1;
	}

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
	
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}
	
	public boolean isChecked()
	{
		return this.checked;
	}
	
	public Players(Parcel in)
	{
		this.name = in.readString();
		this.score = in.readInt();
		this.numberOfPlayer = in.readInt();
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags)
	{
		out.writeString(this.name);
		out.writeInt(this.score);
		out.writeInt(this.numberOfPlayer);
	}

	public static final Parcelable.Creator<Players> CREATOR = new Parcelable.Creator<Players>()
	{
		@Override
		public Players createFromParcel(Parcel in)
		{
			return new Players(in);
		}

		@Override
		public Players[] newArray(int size)
		{
			return new Players[size];
		}
	};
}
