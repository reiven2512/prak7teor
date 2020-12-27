public class HashMaker {
    private static double C = 0.6180339887;
    interface HashTable {
        public boolean push(String x, Double y);
        public boolean delete(String x);
        public Double get (String x);
        public void getTable();
    }

    public int returnHash(String x)
    {
        return (int)(10000*((C*convert(x))%1));
    }
    private int convert(String str){
        int res = 0;
        for(char c: str.toCharArray()){
            res += c;
        }
        return res;
    }
}