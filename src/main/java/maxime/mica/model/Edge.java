package maxime.mica.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.UUID;

@XStreamAlias("edge")
public abstract class Edge implements Synchronizable, Comparable<Edge> {
    @XStreamAsAttribute
    private UUID uuid;
    private transient boolean change;
    private Requirement client;
    private Requirement supplier;

    Edge() {
        uuid = UUID.randomUUID();
    }

    protected Requirement getClient() {
        return client;
    }

    protected void setClient(Requirement client) {
        this.client = client;
        client.getModifiableEdges().add(this);
    }

    protected Requirement getSupplier() {
        return supplier;
    }

    protected void setSupplier(Requirement supplier) {
        this.supplier = supplier;
        supplier.getModifiableEdges().add(this);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    protected void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean changed() {
        return change;
    }

    protected void setChanded(boolean change) {
        this.change = change;
    }


    @Override
    public int compareTo(Edge o) {
        return getUUID().compareTo(o.getUUID());
    }
}
