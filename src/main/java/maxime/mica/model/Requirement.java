package maxime.mica.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

@XStreamAlias("requirement")
public final class Requirement implements Synchronizable {
    @XStreamAsAttribute
    private UUID uuid;
    private transient boolean change;

    private String id;
    private String title;
    private String text;

    private transient Document document;
    private transient Set<Edge> edges;

    Requirement() {
        uuid = UUID.randomUUID();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Edge> getEdges() {
        return Collections.unmodifiableSet(getModifiableEdges());
    }

    Set<Edge> getModifiableEdges() {
        if (edges == null) {
            edges = new TreeSet<Edge>();
        }
        return edges;
    }

    public Document getDocument() {
        return document;
    }

    void setDocument(Document document) {
        if (this.document != null) {
            throw new IllegalStateException("This requirement is already bind to a document");
        }
        this.document = document;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean changed() {
        return change;
    }
}
