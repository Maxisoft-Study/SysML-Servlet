package maxime.mica.model;

public abstract class Edge {
    private Requirement client;
    private Requirement supplier;

    protected Requirement getClient() {
        return client;
    }

    protected void setClient(Requirement client) {
        this.client = client;
    }

    protected Requirement getSupplier() {
        return supplier;
    }

    protected void setSupplier(Requirement supplier) {
        this.supplier = supplier;
    }
}
