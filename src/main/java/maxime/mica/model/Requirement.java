package maxime.mica.model;

import java.util.Collections;
import java.util.Set;

public final class Requirement {
    private String title;
    private String id;
    private String text;

    private Set<Edge> edges;

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
        return Collections.unmodifiableSet(edges);
    }

    Set<Edge> getModifiableEdges(){
        return edges;
    }

    void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }
}
