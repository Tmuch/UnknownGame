package net.minecraft.src;

import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.imageio.ImageIO;

public class ModResourcePack implements ResourcePack
{
    private final Class modClass;

    public ModResourcePack(Class modClass)
    {
        this.modClass = modClass;
    }

    public InputStream func_110590_a(ResourceLocation var1) throws IOException
    {
        return this.modClass.getResourceAsStream("/assets/minecraft/" + var1.func_110623_a());
    }

    public boolean func_110589_b(ResourceLocation var1)
    {
        try
        {
            return this.func_110590_a(var1) != null;
        }
        catch (IOException var3)
        {
            return false;
        }
    }

    public Set func_110587_b()
    {
        return DefaultResourcePack.field_110608_a;
    }

    public MetadataSection func_135058_a(MetadataSerializer var1, String var2) throws IOException
    {
        return var1.func_110503_a(var2, new JsonObject());
    }

    public BufferedImage func_110586_a() throws IOException
    {
        return ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/" + (new ResourceLocation("pack.png")).func_110623_a()));
    }

    public String func_130077_b()
    {
        return this.modClass.getSimpleName();
    }
}
