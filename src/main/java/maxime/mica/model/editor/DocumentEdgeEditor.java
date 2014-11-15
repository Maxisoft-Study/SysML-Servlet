package maxime.mica.model.editor;

import maxime.mica.model.Edge;

public interface DocumentEdgeEditor<T extends Edge> extends DocumentEditor {
    T getEdge();

    DocumentEditor delete();
}
