package maxime.mica.model.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import maxime.mica.model.Synchronizable;
import maxime.mica.model.SynchronizableMap;

import java.util.ArrayList;
import java.util.List;


public class SynchronisableMapConverter implements Converter {
    @Override
    public boolean canConvert(Class aClass) {
        return SynchronizableMap.class.isAssignableFrom(aClass);
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        final SynchronizableMap<?> source = (SynchronizableMap<?>) o;
        marshallingContext.convertAnother(new ArrayList<Synchronizable>(source.values()));
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        final List serializedMap = (List) unmarshallingContext.convertAnother(null, List.class);
        return SynchronizableMap.createFrom(serializedMap);
    }
}
