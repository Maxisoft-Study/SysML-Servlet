package maxime.mica.model;

import java.util.UUID;

public interface Synchronizable {
    UUID getUUID();

    boolean changed();
}
