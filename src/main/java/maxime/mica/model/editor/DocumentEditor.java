package maxime.mica.model.editor;

import maxime.mica.model.Document;
import maxime.mica.model.Edge;
import maxime.mica.model.Requirement;

import java.util.UUID;

public interface DocumentEditor {
    Document getDocument();

    DocumentEditor setDocumentName(String name);

    DocumentRequirementEditor createRequirement(String id, String title, String text);

    DocumentEditor deleteRequirement(Requirement requirement);

    DocumentEditor deleteRequirement(UUID uuid);

    <T extends Edge> DocumentEdgeEditor<T> createEdge(Requirement client, Requirement supplier, Class<T> type);

    <T extends Edge> DocumentEdgeEditor<T> createEdge(UUID client, UUID supplier, Class<T> type);

    DocumentEditor deleteEdge(Edge edge);

    DocumentEditor deleteEdge(UUID edge);
}