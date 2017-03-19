package eu.crushedpixel.sponge.masquerade.api.data;

import eu.crushedpixel.sponge.masquerade.api.manipulators.DataManipulator;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;

import java.util.ArrayList;

public abstract class EntityMetadata<T, U> {

    /**
     * Whether this field's value has priority over the masked player's metadata.
     */
    protected boolean overridesPlayerData = false;

    private final DataManipulator dataManipulator;

    protected final DataEntry<T> dataEntry;

    private final String name;

    public EntityMetadata(DataManipulator dataManipulator, DataParameter<T> key, T initialValue, String name) {
        this(dataManipulator, new DataEntry<>(key, initialValue), name);
    }

    protected EntityMetadata(DataManipulator dataManipulator, DataEntry<T> dataEntry, String name) {
        this.dataManipulator = dataManipulator;
        this.dataEntry = dataEntry;
        this.name = name;
    }

    /**
     * Manipulates an outgoing DataEntry value about to be sent to a client.
     * @return <code>null</code> if the DataEntry wasn't applied and can be ignored, otherwise the DataEntry to send
     */
    public DataEntry<T> handleOutgoingDataEntry(DataEntry<T> dataEntry) {
        if (dataEntry.getKey() != this.dataEntry.getKey()) return null;
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

    public boolean overridesPlayerData() {
        return overridesPlayerData;
    }

    public void setOverridesPlayerData(boolean overridesPlayerData) {
        this.overridesPlayerData = overridesPlayerData;
    }

    public DataEntry<T> getDataEntry() {
        return dataEntry;
    }

    public String getName() {
        return name;
    }
}
