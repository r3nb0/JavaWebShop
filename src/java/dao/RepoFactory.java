package dao;

/**
 *
 * @author r3nb0
 */
public class RepoFactory {
    private static IRepository repo = null;

    public RepoFactory() {}

    public static IRepository getInstance(){
        if (repo == null){
            repo = new Repository();
        }
        return repo;
    }
}
