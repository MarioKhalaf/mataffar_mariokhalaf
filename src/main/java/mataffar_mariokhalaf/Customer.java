package mataffar_mariokhalaf;

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void complains() {
        System.out.println(getName() + " is complaining.");
    }
}