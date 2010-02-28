package patterns;

public interface IPatternStore {
    public void saveOrUpdatePattern(Pattern pattern);
    public Pattern getPatternById(Long id);
}
