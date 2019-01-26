package Database;

import java.util.Objects;

public class Item {

    private String name, description;
    private int id, quantity, weight, cost;

    public Item(String name, int quantity, int weight, int cost, String description) {
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
        this.cost = cost;
        this.description = description;
    }

    public int getId(){
        return id;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public int getCost() {
        return cost;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + this.weight;
        hash = 53 * hash + this.cost;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.weight != other.weight) {
            return false;
        }
        if (this.cost != other.cost) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Cost: " + cost + "\n" +
               "Weight: " + weight + "\n" +
               "Quantity: " + quantity + "\n" +
               "Description: " + description; 
    }

}
