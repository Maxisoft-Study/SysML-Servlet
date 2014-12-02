package maxime.mica.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import maxime.mica.model.editor.DocumentEdgeEditor;
import maxime.mica.model.editor.DocumentEditor;
import maxime.mica.model.editor.DocumentRequirementEditor;

import java.util.Map;
import java.util.UUID;

@XStreamAlias("document")
public class Document implements Synchronizable {
    @XStreamAsAttribute
    private UUID uuid;
    private String name;

    private SynchronizableMap<Requirement> requirements;
    private SynchronizableMap<Edge> edges;

    public Document(String name) {
        uuid = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public boolean changed() {
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Map<UUID, Requirement> getRequirements() {
        if (requirements == null) {
            requirements = new SynchronizableMap<Requirement>();
        }
        return requirements;
    }

    Map<UUID, Edge> getEdges() {
        if (edges == null) {
            edges = new SynchronizableMap<Edge>();
        }
        return edges;
    }

    @SuppressWarnings("unused")
    private Object readResolve() {
        for (Requirement requirement : getRequirements().values()) {
            requirement.setDocument(this);
        }
        return this;
    }


    public DocumentEditor edit() {
        return new DocumentEditorImpl();
    }

    public class DocumentEditorImpl implements DocumentEditor {

        @Override
        public Document getDocument() {
            return Document.this;
        }

        @Override
        public DocumentEditor setDocumentName(String name) {
            synchronized (Document.this) {
                setName(name);
                return this;
            }
        }

        @Override
        public DocumentRequirementEditor createRequirement(String id, String title, String text) {
            synchronized (Document.this) {
                Requirement requirement = new Requirement();
                requirement.setId(id);
                requirement.setTitle(title);
                requirement.setText(text);
                requirement.setDocument(Document.this);
                getRequirements().put(requirement.getUUID(), requirement);
                return new DocumentRequirementEditorImpl(requirement);
            }
        }


        @Override
        public DocumentEditor deleteRequirement(Requirement requirement) {
            return deleteRequirement(requirement.getUUID());
        }

        @Override
        public DocumentEditor deleteRequirement(UUID uuid) {
            synchronized (Document.this) {
                Requirement requirement = getRequirements().remove(uuid);
                for (Edge edge : requirement.getEdges()) {
                    deleteEdgeFromMap(edge);
                }
                return this;
            }
        }

        @Override
        public <T extends Edge> DocumentEdgeEditor<T> createEdge(Requirement client, Requirement supplier, Class<T> type) {
            synchronized (Document.this) {
                T edge;
                try {
                    edge = type.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e); // TODO
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e); // TODO
                }
                edge.setClient(client);
                edge.setSupplier(supplier);
                getEdges().put(edge.getUUID(), edge);
                return new DocumentEdgeEditorImpl<T>(edge);
            }
        }

        @Override
        public <T extends Edge> DocumentEdgeEditor<T> createEdge(UUID client, UUID supplier, Class<T> type) {
            synchronized (Document.this) {
                Map<UUID, Requirement> requirements = getRequirements();
                return createEdge(requirements.get(client), requirements.get(supplier), type);
            }
        }

        @Override
        public DocumentEditor deleteEdge(Edge edge) {
            synchronized (Document.this) {
                deleteEdgeFromMap(edge);
                for (Requirement requirement : getRequirements().values()) {
                    requirement.getEdges().remove(edge);
                }
                return this;
            }
        }

        @Override
        public DocumentEditor deleteEdge(UUID edge) {
            synchronized (Document.this) {
                return deleteEdge(getEdges().get(edge));
            }
        }

        private void deleteEdgeFromMap(Edge edge) {
            synchronized (Document.this) {
                getEdges().remove(edge.getUUID());
            }
        }
    }

    public class DocumentRequirementEditorImpl extends DocumentEditorImpl implements DocumentRequirementEditor {
        private final Requirement requirement;

        public DocumentRequirementEditorImpl(Requirement requirement) {
            this.requirement = requirement;
        }

        @Override
        public Requirement getRequirement() {
            return requirement;
        }

        @Override
        public <T extends Edge> DocumentEdgeEditor<T> addClient(Requirement client, Class<T> type) {
            return createEdge(client, requirement, type);
        }

        @Override
        public <T extends Edge> DocumentEdgeEditor<T> addSupplier(Requirement supplier, Class<T> type) {
            return createEdge(requirement, supplier, type);
        }

        @Override
        public DocumentEditor delete() {
            deleteRequirement(requirement);
            return new DocumentEditorImpl();
        }
    }

    public class DocumentEdgeEditorImpl<T extends Edge> extends DocumentEditorImpl implements DocumentEdgeEditor<T> {
        private final T edge;

        public DocumentEdgeEditorImpl(T edge) {
            this.edge = edge;
        }

        @Override
        public T getEdge() {
            return edge;
        }

        @Override
        public DocumentEditor delete() {
            return deleteEdge(edge);
        }
    }
}
