package patterns;

public interface IPatternStore {

    public int getCount();

    public void savePattern(Pattern pattern);

    public Pattern getPattern(int id);

    public Pattern removePattern(int id);
    
}
