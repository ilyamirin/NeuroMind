package patterns;

public interface IPatternStore {
    public void savePatterns(IGetPatternObject gpo, int times);
    public Pattern getPatternById(Long id);
}
