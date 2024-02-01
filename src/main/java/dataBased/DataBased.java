package dataBased;

public abstract class DataBased {

    public abstract void setData(String userId, String text, int num);
    public abstract String getData(String userId, int num);

    public abstract boolean isPerson(String userId);

    public abstract void setFlag(String userId, String flag);
    public abstract String getFlag(String userId);

    public abstract boolean checkUser(String userId);

    public abstract int getCount(String data);

    public abstract String[] getDataArray(String data, int ind);

    public abstract void updateDate(String userId, String tableName, int ind);

}
