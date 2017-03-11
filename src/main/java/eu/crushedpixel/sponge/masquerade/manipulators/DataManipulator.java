package eu.crushedpixel.sponge.masquerade.manipulators;

import eu.crushedpixel.sponge.masquerade.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.masquerades.Masquerade;

import java.util.List;

public interface DataManipulator {

    List<EntityMetadata> getAllEntries();

    Masquerade<?, ?> getMasquerade();

}
