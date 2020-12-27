public class Pair{
    private final String key;
    private final Double value;
    private boolean deleted;

    public Pair(String key, Double value) {
        this.key = key;
        this.value = value;
        this.deleted = false;
    }
    public String getKey() {
        return key;
    }

    public Double getValue() {
        return value;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean deletePair(){
        if (!this.deleted){
            this.deleted = true;
            return true;
        }else{
            return false;
        }
    }
}