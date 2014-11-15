package maxime.mica.model;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import maxime.mica.model.converter.SynchronisableMapConverter;

import java.util.Collection;
import java.util.TreeMap;
import java.util.UUID;

@XStreamConverter(SynchronisableMapConverter.class)
public class SynchronizableMap<E extends Synchronizable> extends TreeMap<UUID, E> {

    public static <T extends Synchronizable> SynchronizableMap<T> createFrom(Collection<T> items) {
        SynchronizableMap<T> ret = new SynchronizableMap<T>();
        for (T item : items) {
            ret.put(item.getUUID(), item);
        }
        return ret;
    }
}
