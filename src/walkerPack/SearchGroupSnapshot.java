package walkerPack;

public class SearchGroupSnapshot
{
    // TODO: can be done in a better way instead of storing all the resulting string, but good enough for now
    // TODO: consider only storing instance of SearchGroup which is cheaper especially with large sequence collections

    private String result;
    private long time;

    public SearchGroupSnapshot(String result, long time)
    {
        this.result = result;
        this.time = time;
    }

    @Override
    public String toString()
    {
        return result;
    }
}
