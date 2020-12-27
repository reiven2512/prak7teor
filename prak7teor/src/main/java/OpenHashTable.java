import java.util.Arrays;

public class OpenHashTable extends HashMaker implements HashMaker.HashTable {
    private Pair[] table;

    public void getTable(){
        System.out.println("Таблица идентификаторов");
        for(int i = 0; i < table.length; i++){
            if(table[i] != null){
                System.out.printf("%-15s", table[i].getKey());
                System.out.printf("%-15s", table[i].getValue());
                System.out.println();
            }
        }
    }

    public OpenHashTable() {
        table = new Pair[10000];
    }

    public OpenHashTable(int m) {
        table = new Pair[m];
    }

    public boolean push(String x, Double y) {
        int h = returnHash(x);
        int i=0;
        try{
            if (table[h].isDeleted() ) {
                table[h] = new Pair(x, y);
                return true;
            }
            for (i = h + 1; i != h; i = (i + 1) % table.length) {
                if (table[i].isDeleted() || table[i].getKey().equals(x)) {
                    table[i] = new Pair(x, y);
                    return true;
                }
            }
            return false;
        } catch(NullPointerException e) {
            table[h] = new Pair(x, y);
            return true;
        }
    }

    public boolean delete(String x) {
        int h = returnHash(x);
        try{
            if (table[h].getKey().equals(x)) {
                table[h].deletePair();
                return true;
            }
            for (int i = h + 1; i != h; i = (i + 1) % table.length) {
                if (table[i].getKey().equals(x) && !table[i].isDeleted()) {
                    table[i].deletePair();
                    return true;
                }
            }
            return false;
        } catch (NullPointerException e){
            return false;
        }
    }

    public Double get(String x) {
        int h = returnHash(x);
        try{
            if (table[h].getKey().equals(x) && !table[h].isDeleted()) {
                return table[h].getValue();
            }
            for (int i = h + 1; i != h; i = (i + 1) % table.length) {
                if(table[i].getKey().equals(x) && !table[i].isDeleted()) {
                    return table[h].getValue();
                }
            }
            return null;
        }catch(NullPointerException e){
            return null;
        }
    }
}