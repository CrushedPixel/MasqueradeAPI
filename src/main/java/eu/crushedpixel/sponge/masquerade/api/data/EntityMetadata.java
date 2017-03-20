package eu.crushedpixel.sponge.masquerade.api.data;

import com.google.common.base.Preconditions;
import eu.crushedpixel.sponge.masquerade.api.masquerades.AbstractMasquerade;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketEntityMetadata;

import java.util.ArrayList;

public abstract class EntityMetadata<T, U> {

    /**
     * Whether this value may be non-explicitly changed by the server.
     */
    protected boolean allowValueChange = true;

    private final AbstractMasquerade masquerade;

    protected final DataEntry<T> dataEntry;

    public EntityMetadata(AbstractMasquerade masquerade, DataParameter<T> parameter, T initialValue) {
        this(masquerade, new DataEntry<>(parameter, initialValue));
    }

    protected EntityMetadata(AbstractMasquerade masquerade, DataEntry<T> dataEntry) {
        Preconditions.checkNotNull(dataEntry);
        this.masquerade = masquerade;
        this.dataEntry = dataEntry;
    }

    /**
     * Manipulates an outgoing DataEntry value about to be sent to a client.
     * @return <code>null</code> if the DataEntry wasn't applied and can be ignored, otherwise the DataEntry to send
     */
    public DataEntry<T> handleOutgoingDataEntry(DataEntry<T> dataEntry) {
        if (dataEntry.getKey() != this.dataEntry.getKey()) return null;
        if (!allowValueChange) return null;

        this.dataEntry.setValue(dataEntry.getValue());
        return this.dataEntry;
    }

    public void setValue(U value) {
        dataEntry.setValue(convertToInternal(value));
        sendValue();
    }

    public U getValue() {
        return convertToExternal(dataEntry.getValue());
    }

    protected abstract U convertToExternal(T value);
    protected abstract T convertToInternal(U value);

    protected void sendValue() {
        SPacketEntityMetadata packetEntityMetadata = new SPacketEntityMetadata();
        packetEntityMetadata.entityId = masquerade.getEntityID();
        packetEntityMetadata.dataManagerEntries = new ArrayList<>();
        packetEntityMetadata.dataManagerEntries.add(dataEntry);

        masquerade.sendToAll(packetEntityMetadata);
    }

    public boolean isAllowValueChange() {
        return allowValueChange;
    }

    public void setAllowValueChange(boolean allowValueChange) {
        this.allowValueChange = allowValueChange;
    }

    public DataEntry<T> getDataEntry() {
        return dataEntry;
    }

}
