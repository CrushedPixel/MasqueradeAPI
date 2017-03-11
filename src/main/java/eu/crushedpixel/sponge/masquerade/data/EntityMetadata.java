package eu.crushedpixel.sponge.masquerade.data;

import eu.crushedpixel.sponge.masquerade.manipulators.DataManipulator;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;

import java.util.ArrayList;

public abstract class EntityMetadata<T, U> {

    /**
     * Whether this field's value has priority over the masked player's metadata.
     */
    @Getter @Setter
    protected boolean overridesPlayerData = false;

    private final DataManipulator dataManipulator;

    @Getter
    protected final DataEntry<T> dataEntry;

    public EntityMetadata(DataManipulator dataManipulator, DataParameter<T> key, T initialValue) {
        this(dataManipulator, new DataEntry<>(key, initialValue));
    }

    protected EntityMetadata(DataManipulator dataManipulator, DataEntry<T> dataEntry) {
        this.dataManipulator = dataManipulator;
        this.dataEntry = dataEntry;
    }

    /**
     * Manipulates an outgoing DataEntry value about to be sent to a client.
     * @return <code>null</code> if the DataEntry wasn't modified and can be ignored, otherwise the DataEntry to send
     */
    public DataEntry<T> handleOutgoingDataEntry(DataEntry<T> dataEntry) {
        if (dataEntry.getKey() != this.dataEntry.getKey()) return dataEntry;
        if (overridesPlayerData) return null;

        this.dataEntry.setValue(dataEntry.getValue());
        return this.dataEntry;
    }

    public abstract void setValue(U value);

    public abstract U getValue();

    protected void sendValue() {
        SPacketEntityMetadata packetEntityMetadata = new SPacketEntityMetadata();
        packetEntityMetadata.entityId = dataManipulator.getMasquerade().getEntityID();
        packetEntityMetadata.dataManagerEntries = new ArrayList<>();
        packetEntityMetadata.dataManagerEntries.add(dataEntry);

        dataManipulator.getMasquerade().sendToAll(packetEntityMetadata);
    }

}
