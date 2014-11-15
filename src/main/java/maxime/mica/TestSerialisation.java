package maxime.mica;

import com.thoughtworks.xstream.XStream;
import maxime.mica.model.*;


public class TestSerialisation {


    public static void main(String... args) {
        XStream xstream = new XStream();

        xstream.processAnnotations(
                new Class[]{
                        Synchronizable.class,
                        Requirement.class,
                        Document.class,
                        Decomposition.class,
                        Derive.class,
                        Edge.class,
                        Refines.class,
                        Satisfy.class,
                        Test.class,
                        SynchronizableMap.class
                });
        xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
        Document doc = new Document("test");
        Requirement c3po = doc.edit()
                .createRequirement("c3po", "some title", "may the force be with you")
                .getRequirement();

        doc.edit()
                .createRequirement("r2d2", "of course", "bip bip")
                .addClient(c3po, Satisfy.class);
        xstream.toXML(doc, System.out);
    }

}
