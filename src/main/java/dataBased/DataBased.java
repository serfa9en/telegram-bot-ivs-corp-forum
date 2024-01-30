package dataBased;

public abstract class DataBased {

    public abstract void setData(String userId, String text, int num);
    public abstract String getData(String userId, int num);

    public abstract boolean isPerson(String userId);

    public abstract void setFlag(String userId, String flag);
    public abstract String getFlag(String userId);

    public abstract boolean checkUser(String userId);

}
