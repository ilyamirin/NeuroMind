package patterns;

import java.util.List;

public interface IPatternStore {

    public void savePatterns(IGetPatternObject gpo, int times);
    public List<Pattern> getPatterns();

    public void savePattern(Pattern pattern);
    public Pattern getPatternById(Long id);
    
}
