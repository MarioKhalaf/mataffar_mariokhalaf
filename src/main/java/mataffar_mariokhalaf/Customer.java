package mataffar_mariokhalaf;

class Customer {
    private String name;

    public Customer() {
        this.name = getName();
    }
    
    public void setName() {
        int id = 0;
        this.name = "Customer " + id;
        id++;
    }
    public String getName() {
        return name;
    }
}