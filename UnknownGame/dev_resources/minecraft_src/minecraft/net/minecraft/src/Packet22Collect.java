package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet22Collect extends Packet
{
    /** The entity on the ground that was picked up. */
    public int collectedEntityId;

    /** The entity that picked up the one from the ground. */
    public int collectorEntityId;

    public Packet22Collect() {}

    public Packet22Collect(int par1, int par2)
    {
        this.collectedEntityId = par1;
        this.collectorEntityId = par2;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInput par1DataInput) throws IOException
    {
        this.collectedEntityId = par1DataInput.readInt();
        this.collectorEntityId = par1DataInput.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeInt(this.collectedEntityId);
        par1DataOutput.writeInt(this.collectorEntityId);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleCollect(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 8;
    }
}
