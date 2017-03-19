package eu.crushedpixel.sponge.masquerade.api.manipulators;

import eu.crushedpixel.sponge.masquerade.api.data.EntityMetadata;
import eu.crushedpixel.sponge.masquerade.api.masquerades.Masquerade;

import java.util.List;

public interface DataManipulator {

    List<EntityMetadata> getAllEntries();

    Masquerade<?, ?> getMasquerade();

}
