package patterns;

import java.util.List;

public interface IPatternStore {

    public void savePatterns(IGetPatternObject gpo, int times);
    public List<IPattern> getPatterns();

    public void savePattern(IPattern pattern);
    public IPattern getPatternById(Long id);
    
}
