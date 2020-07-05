package codegym.model;



public class Cart {


    private Long id;

    private Product products;

    private Integer amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product product) {
        this.products = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public int increaseAmount(){
        return this.amount +1;
    }
}
