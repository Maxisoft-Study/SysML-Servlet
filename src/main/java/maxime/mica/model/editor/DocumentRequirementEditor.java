package maxime.mica.model.editor;

import maxime.mica.model.Edge;
import maxime.mica.model.Requirement;

public interface DocumentRequirementEditor extends DocumentEditor {
    Requirement getRequirement();

    <T extends Edge> DocumentEdgeEditor<T> addClient(Requirement client, Class<T> type);

    <T extends Edge> DocumentEdgeEditor<T> addSupplier(Requirement supplier, Class<T> type);

    DocumentEditor delete();
}
