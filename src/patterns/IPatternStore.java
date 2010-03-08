package patterns;

import java.util.List;

public interface IPatternStore {

    public int getMaxPatternsNum();

    public void savePatterns(List<Pattern> patterns);
    public List<Pattern> getPatterns();

    public void savePattern(Pattern pattern);
    public Pattern getPattern(int id);

    public Pattern removePattern(int id);
    
}
