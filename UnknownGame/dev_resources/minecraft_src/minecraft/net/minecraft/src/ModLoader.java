package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.security.DigestException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import net.minecraft.client.main.Main;
import net.minecraft.server.MinecraftServer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class ModLoader
{
    private static final Map<Integer, BaseMod> blockModels = new HashMap();
    private static final Map<Integer, Boolean> blockSpecialInv = new HashMap();
    private static final File cfgdir;
    private static final File cfgfile;
    public static Level cfgLoggingLevel;
    private static Map < String, Class <? extends Entity >> classMap = null;
    private static long clock = 0L;
    private static Field field_armorList = null;
    private static Field field_modifiers = null;
    private static Field field_TileEntityRenderers = null;
    private static boolean hasInit = false;
    private static final Map<BaseMod, Boolean> inGameHooks = new HashMap();
    private static final Map<BaseMod, Boolean> inGUIHooks = new HashMap();
    private static Minecraft instance = null;
    private static final Map<BaseMod, Map<KeyBinding, boolean[]>> keyList = new HashMap();
    private static String langPack = null;
    private static Map<String, Map<String, String>> localizedStrings = new HashMap();
    private static final File logfile = new File(Minecraft.getMinecraft().mcDataDir, "ModLoader.txt");
    private static final Logger logger = Logger.getLogger("ModLoader");
    private static FileHandler logHandler = null;
    private static Method method_RegisterEntityID = null;
    private static Method method_RegisterTileEntity = null;
    private static final File modDir;
    private static final LinkedList<BaseMod> modList = new LinkedList();
    private static int nextBlockModelID = 1000;
    private static final Map<Integer, Map<String, Integer>> overrides = new HashMap();
    private static final Map<String, BaseMod> packetChannels = new HashMap();
    public static final Properties props = new Properties();
    private static BiomeGenBase[] standardBiomes;
    public static final String VERSION = "ModLoader 1.6.2";
    private static NetClientHandler clientHandler = null;
    private static final List<ICommand> commandList = new LinkedList();
    private static final Map<Integer, List<TradeEntry>> tradeItems = new HashMap();
    private static final Map<Integer, BaseMod> containerGUIs = new HashMap();
    private static final Map < Class <? extends Entity > , EntityTrackerNonliving > trackers = new HashMap();
    private static final Map<Item, IBehaviorDispenseItem> dispenserBehaviors = new HashMap();
    private static final Map<String, Icon> customTextures = new HashMap();
    private static SoundPool soundPoolSounds;
    private static SoundPool soundPoolStreaming;
    private static SoundPool soundPoolMusic;
    private static List resourcePacks;

    public static void addAchievementDesc(Achievement achievement, String name, String desc)
    {
        try
        {
            if (achievement.getName().contains("."))
            {
                String[] nosuchfieldexception = achievement.getName().split("\\.");

                if (nosuchfieldexception.length == 2)
                {
                    String id = nosuchfieldexception[1];
                    String nameKey = "achievement." + id;
                    String descKey = "achievement." + id + ".desc";
                    addLocalization(nameKey, name);
                    addLocalization(descKey, desc);
                    setPrivateValue(StatBase.class, achievement, 1, name);
                    setPrivateValue(Achievement.class, achievement, 3, descKey);
                }
                else
                {
                    setPrivateValue(StatBase.class, achievement, 1, name);
                    setPrivateValue(Achievement.class, achievement, 3, desc);
                }
            }
            else
            {
                setPrivateValue(StatBase.class, achievement, 1, name);
                setPrivateValue(Achievement.class, achievement, 3, desc);
            }
        }
        catch (IllegalArgumentException var7)
        {
            logger.throwing("ModLoader", "AddAchievementDesc", var7);
            throwException(var7);
        }
        catch (SecurityException var8)
        {
            logger.throwing("ModLoader", "AddAchievementDesc", var8);
            throwException(var8);
        }
        catch (NoSuchFieldException var9)
        {
            logger.throwing("ModLoader", "AddAchievementDesc", var9);
            throwException(var9);
        }
    }

    public static void addEntityTracker(BaseMod mod, Class <? extends Entity > entityClass, int id, int viewDistance, int updateFrequency, boolean trackMotion)
    {
        if (entityClass == null)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            if (!Entity.class.isAssignableFrom(entityClass))
            {
                Exception exception = new Exception(entityClass.getCanonicalName() + " is not an entity.");
                logger.throwing("ModLoader", "addEntityTracker", exception);
                throwException(exception);
            }

            trackers.put(entityClass, new EntityTrackerNonliving(mod, entityClass, id, viewDistance, updateFrequency, trackMotion));
        }
    }

    public static Map < Class <? extends Entity > , EntityTrackerNonliving > getTrackers()
    {
        return Collections.unmodifiableMap(trackers);
    }

    public static int addAllFuel(int id, int metadata)
    {
        logger.finest("Finding fuel for " + id);
        int result = 0;

        for (Iterator iter = modList.iterator(); iter.hasNext() && result == 0; result = ((BaseMod)iter.next()).addFuel(id, metadata))
        {
            ;
        }

        if (result != 0)
        {
            logger.finest("Returned " + result);
        }

        return result;
    }

    public static void addAllRenderers(Map < Class <? extends Entity > , Render > renderers)
    {
        if (!hasInit)
        {
            init();
            logger.fine("Initialized");
        }

        Iterator i$ = modList.iterator();

        while (i$.hasNext())
        {
            BaseMod mod = (BaseMod)i$.next();
            mod.addRenderer(renderers);
        }
    }

    public static int addArmor(String s)
    {
        try
        {
            String[] illegalaccessexception = (String[])((String[])field_armorList.get((Object)null));
            List list = Arrays.asList(illegalaccessexception);
            ArrayList arraylist = new ArrayList();
            arraylist.addAll(list);

            if (!arraylist.contains(s))
            {
                arraylist.add(s);
            }

            int i = arraylist.indexOf(s);
            field_armorList.set((Object)null, arraylist.toArray(new String[0]));
            return i;
        }
        catch (IllegalArgumentException var5)
        {
            logger.throwing("ModLoader", "AddArmor", var5);
            throwException("An impossible error has occured!", var5);
        }
        catch (IllegalAccessException var6)
        {
            logger.throwing("ModLoader", "AddArmor", var6);
            throwException("An impossible error has occured!", var6);
        }

        return -1;
    }

    public static void addBiome(BiomeGenBase biomegenbase)
    {
        BiomeGenBase[] abiomegenbase = GenLayerBiome.biomeArray;
        List list = Arrays.asList(abiomegenbase);
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(list);

        if (!arraylist.contains(biomegenbase))
        {
            arraylist.add(biomegenbase);
        }

        GenLayerBiome.biomeArray = (BiomeGenBase[])((BiomeGenBase[])arraylist.toArray(new BiomeGenBase[0]));
    }

    public static void addCommand(ICommand cmd)
    {
        commandList.add(cmd);
    }

    public static void addDispenserBehavior(Item item, IBehaviorDispenseItem behavior)
    {
        dispenserBehaviors.put(item, behavior);
    }

    public static void registerServer(MinecraftServer server)
    {
        instance.entityRenderer = new EntityRendererProxy(instance);
        ICommandManager manager = server.getCommandManager();

        if (manager instanceof CommandHandler)
        {
            CommandHandler handler = (CommandHandler)manager;
            Iterator i$ = commandList.iterator();

            while (i$.hasNext())
            {
                ICommand behavior = (ICommand)i$.next();
                handler.registerCommand(behavior);
            }

            i$ = dispenserBehaviors.entrySet().iterator();

            while (i$.hasNext())
            {
                Entry behavior1 = (Entry)i$.next();
                BlockDispenser.dispenseBehaviorRegistry.putObject(behavior1.getKey(), behavior1.getValue());
            }
        }
    }

    public static void addLocalization(String s, String s1)
    {
        addLocalization(s, "en_US", s1);
    }

    public static void addLocalization(String s, String s1, String s2)
    {
        Object obj;

        if (localizedStrings.containsKey(s1))
        {
            obj = (Map)localizedStrings.get(s1);
        }
        else
        {
            obj = new HashMap();
            localizedStrings.put(s1, (Map)obj);
        }

        ((Map)obj).put(s, s2);
    }

    public static void addTrade(int profession, TradeEntry entry)
    {
        Object list = null;

        if (tradeItems.containsKey(Integer.valueOf(profession)))
        {
            list = (List)tradeItems.get(Integer.valueOf(profession));
        }
        else
        {
            list = new LinkedList();
            tradeItems.put(Integer.valueOf(profession), (List)list);
        }

        ((List)list).add(entry);
    }

    public static List<TradeEntry> getTrades(int profession)
    {
        if (profession != -1)
        {
            return tradeItems.containsKey(Integer.valueOf(profession)) ? Collections.unmodifiableList((List)tradeItems.get(Integer.valueOf(profession))) : null;
        }
        else
        {
            LinkedList list = new LinkedList();
            Iterator i$ = tradeItems.values().iterator();

            while (i$.hasNext())
            {
                List entry = (List)i$.next();
                list.addAll(entry);
            }

            return list;
        }
    }

    private static void addMod(ClassLoader classloader, String s)
    {
        try
        {
            String throwable = s.split("\\.")[0];

            if (throwable.contains("$"))
            {
                return;
            }

            if (props.containsKey(throwable) && (props.getProperty(throwable).equalsIgnoreCase("no") || props.getProperty(throwable).equalsIgnoreCase("off")))
            {
                return;
            }

            Package package1 = ModLoader.class.getPackage();

            if (package1 != null)
            {
                throwable = package1.getName() + "." + throwable;
            }

            Class class1 = classloader.loadClass(throwable);

            if (!BaseMod.class.isAssignableFrom(class1))
            {
                return;
            }

            setupProperties(class1);
            resourcePacks.add(new ModResourcePack(class1));
            BaseMod basemod = (BaseMod)class1.newInstance();

            if (basemod != null)
            {
                modList.add(basemod);
                logger.fine("Mod Initialized: \"" + basemod.toString() + "\" from " + s);
                System.out.println("Mod Initialized: " + basemod.toString());
            }
        }
        catch (Throwable var6)
        {
            logger.fine("Failed to load mod from \"" + s + "\"");
            System.out.println("Failed to load mod from \"" + s + "\"");
            logger.throwing("ModLoader", "addMod", var6);
            throwException(var6);
        }
    }

    public static void addName(Object obj, String s)
    {
        addName(obj, "en_US", s);
    }

    public static void addName(Object obj, String s, String s1)
    {
        String s2 = null;
        Exception exception13;

        if (obj instanceof Item)
        {
            Item exception1 = (Item)obj;

            if (exception1.getUnlocalizedName() != null)
            {
                s2 = exception1.getUnlocalizedName() + ".name";
            }
        }
        else if (obj instanceof Block)
        {
            Block exception11 = (Block)obj;

            if (exception11.getUnlocalizedName() != null)
            {
                s2 = exception11.getUnlocalizedName() + ".name";
            }
        }
        else if (obj instanceof ItemStack)
        {
            ItemStack exception12 = (ItemStack)obj;
            String s3 = Item.itemsList[exception12.itemID].getUnlocalizedName(exception12);

            if (s3 != null)
            {
                s2 = s3 + ".name";
            }
        }
        else
        {
            exception13 = new Exception(obj.getClass().getName() + " cannot have name attached to it!");
            logger.throwing("ModLoader", "AddName", exception13);
            throwException(exception13);
        }

        if (s2 != null)
        {
            addLocalization(s2, s, s1);
        }
        else
        {
            exception13 = new Exception(obj + " is missing name tag!");
            logger.throwing("ModLoader", "AddName", exception13);
            throwException(exception13);
        }
    }

    public static void addRecipe(ItemStack itemstack, Object ... aobj)
    {
        CraftingManager.getInstance().addRecipe(itemstack, aobj);
    }

    public static void addShapelessRecipe(ItemStack itemstack, Object ... aobj)
    {
        CraftingManager.getInstance().addShapelessRecipe(itemstack, aobj);
    }

    public static void addSmelting(int i, ItemStack itemstack, float xp)
    {
        FurnaceRecipes.smelting().addSmelting(i, itemstack, xp);
    }

    public static void addSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype)
    {
        addSpawn(class1, i, j, k, enumcreaturetype, (BiomeGenBase[])null);
    }

    public static void addSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase[] abiomegenbase)
    {
        if (class1 == null)
        {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        else if (enumcreaturetype == null)
        {
            throw new IllegalArgumentException("spawnList cannot be null");
        }
        else
        {
            if (abiomegenbase == null)
            {
                abiomegenbase = standardBiomes;
            }

            for (int l = 0; l < abiomegenbase.length; ++l)
            {
                List list = abiomegenbase[l].getSpawnableList(enumcreaturetype);

                if (list != null)
                {
                    boolean flag = false;
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext())
                    {
                        SpawnListEntry spawnlistentry = (SpawnListEntry)iterator.next();

                        if (spawnlistentry.entityClass == class1)
                        {
                            spawnlistentry.itemWeight = i;
                            spawnlistentry.minGroupCount = j;
                            spawnlistentry.maxGroupCount = k;
                            flag = true;
                            break;
                        }
                    }

                    if (!flag)
                    {
                        list.add(new SpawnListEntry(class1, i, j, k));
                    }
                }
            }
        }
    }

    public static void addSpawn(String s, int i, int j, int k, EnumCreatureType enumcreaturetype)
    {
        addSpawn(s, i, j, k, enumcreaturetype, (BiomeGenBase[])null);
    }

    public static void addSpawn(String s, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase[] abiomegenbase)
    {
        Class class1 = (Class)classMap.get(s);

        if (class1 != null && EntityLiving.class.isAssignableFrom(class1))
        {
            addSpawn(class1, i, j, k, enumcreaturetype, abiomegenbase);
        }
    }

    public static void genericContainerRemoval(World world, int i, int j, int k)
    {
        IInventory iinventory = (IInventory)world.getBlockTileEntity(i, j, k);

        if (iinventory != null)
        {
            for (int l = 0; l < iinventory.getSizeInventory(); ++l)
            {
                ItemStack itemstack = iinventory.getStackInSlot(l);

                if (itemstack != null)
                {
                    double d = world.rand.nextDouble() * 0.8D + 0.1D;
                    double d1 = world.rand.nextDouble() * 0.8D + 0.1D;
                    EntityItem entityitem;

                    for (double d2 = world.rand.nextDouble() * 0.8D + 0.1D; itemstack.stackSize > 0; world.spawnEntityInWorld(entityitem))
                    {
                        int i1 = world.rand.nextInt(21) + 10;

                        if (i1 > itemstack.stackSize)
                        {
                            i1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= i1;
                        entityitem = new EntityItem(world, (double)i + d, (double)j + d1, (double)k + d2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                        double d3 = 0.05D;
                        entityitem.motionX = world.rand.nextGaussian() * d3;
                        entityitem.motionY = world.rand.nextGaussian() * d3 + 0.2D;
                        entityitem.motionZ = world.rand.nextGaussian() * d3;

                        if (itemstack.hasTagCompound())
                        {
                            entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                        }
                    }

                    iinventory.setInventorySlotContents(l, (ItemStack)null);
                }
            }
        }
    }

    public static List<BaseMod> getLoadedMods()
    {
        return Collections.unmodifiableList(modList);
    }

    public static Logger getLogger()
    {
        return logger;
    }

    public static Minecraft getMinecraftInstance()
    {
        if (instance == null)
        {
            try
            {
                ThreadGroup nosuchfieldexception = Thread.currentThread().getThreadGroup();
                int i = nosuchfieldexception.activeCount();
                Thread[] athread = new Thread[i];
                nosuchfieldexception.enumerate(athread);
                int k;

                for (k = 0; k < athread.length; ++k)
                {
                    System.out.println(athread[k].getName());
                }

                for (k = 0; k < athread.length; ++k)
                {
                    if (athread[k].getName().equals("Minecraft main thread"))
                    {
                        instance = (Minecraft)getPrivateValue(Thread.class, athread[k], "target");
                        break;
                    }
                }
            }
            catch (SecurityException var4)
            {
                logger.throwing("ModLoader", "getMinecraftInstance", var4);
                throw new RuntimeException(var4);
            }
            catch (NoSuchFieldException var5)
            {
                logger.throwing("ModLoader", "getMinecraftInstance", var5);
                throw new RuntimeException(var5);
            }
        }

        return instance;
    }

    public static Object getPrivateValue(Class class1, Object obj, int i) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field illegalaccessexception = class1.getDeclaredFields()[i];
            illegalaccessexception.setAccessible(true);
            return illegalaccessexception.get(obj);
        }
        catch (IllegalAccessException var4)
        {
            logger.throwing("ModLoader", "getPrivateValue", var4);
            throwException("An impossible error has occured!", var4);
            return null;
        }
    }

    public static Object getPrivateValue(Class class1, Object obj, String s) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field illegalaccessexception = class1.getDeclaredField(s);
            illegalaccessexception.setAccessible(true);
            return illegalaccessexception.get(obj);
        }
        catch (IllegalAccessException var4)
        {
            logger.throwing("ModLoader", "getPrivateValue", var4);
            throwException("An impossible error has occured!", var4);
            return null;
        }
    }

    public static int getUniqueBlockModelID(BaseMod basemod, boolean flag)
    {
        int i = nextBlockModelID++;
        blockModels.put(Integer.valueOf(i), basemod);
        blockSpecialInv.put(Integer.valueOf(i), Boolean.valueOf(flag));
        return i;
    }

    private static void init()
    {
        hasInit = true;

        try
        {
            instance = Minecraft.getMinecraft();
            classMap = (Map)getPrivateValue(EntityList.class, (Object)null, 0);
            Field throwable = Minecraft.class.getDeclaredFields()[63];
            throwable.setAccessible(true);
            resourcePacks = (List)throwable.get(instance);
            field_modifiers = Field.class.getDeclaredField("modifiers");
            field_modifiers.setAccessible(true);
            field_TileEntityRenderers = TileEntityRenderer.class.getDeclaredFields()[0];
            field_TileEntityRenderers.setAccessible(true);
            field_armorList = RenderBiped.class.getDeclaredFields()[5];
            field_modifiers.setInt(field_armorList, field_armorList.getModifiers() & -17);
            field_armorList.setAccessible(true);
            Field[] basemod1 = BiomeGenBase.class.getDeclaredFields();
            LinkedList iterator1 = new LinkedList();

            for (int nosuchmethodexception2 = 0; nosuchmethodexception2 < basemod1.length; ++nosuchmethodexception2)
            {
                Class class1 = basemod1[nosuchmethodexception2].getType();

                if ((basemod1[nosuchmethodexception2].getModifiers() & 8) != 0 && class1.isAssignableFrom(BiomeGenBase.class))
                {
                    BiomeGenBase biomegenbase = (BiomeGenBase)basemod1[nosuchmethodexception2].get((Object)null);

                    if (!(biomegenbase instanceof BiomeGenHell) && !(biomegenbase instanceof BiomeGenEnd))
                    {
                        iterator1.add(biomegenbase);
                    }
                }
            }

            standardBiomes = (BiomeGenBase[])((BiomeGenBase[])iterator1.toArray(new BiomeGenBase[0]));

            try
            {
                method_RegisterTileEntity = TileEntity.class.getDeclaredMethod("a", new Class[] {Class.class, String.class});
            }
            catch (NoSuchMethodException var7)
            {
                method_RegisterTileEntity = TileEntity.class.getDeclaredMethod("addMapping", new Class[] {Class.class, String.class});
            }

            method_RegisterTileEntity.setAccessible(true);

            try
            {
                method_RegisterEntityID = EntityList.class.getDeclaredMethod("a", new Class[] {Class.class, String.class, Integer.TYPE});
            }
            catch (NoSuchMethodException var6)
            {
                method_RegisterEntityID = EntityList.class.getDeclaredMethod("addMapping", new Class[] {Class.class, String.class, Integer.TYPE});
            }

            method_RegisterEntityID.setAccessible(true);
        }
        catch (SecurityException var9)
        {
            logger.throwing("ModLoader", "init", var9);
            throwException(var9);
            throw new RuntimeException(var9);
        }
        catch (NoSuchFieldException var10)
        {
            logger.throwing("ModLoader", "init", var10);
            throwException(var10);
            throw new RuntimeException(var10);
        }
        catch (NoSuchMethodException var11)
        {
            logger.throwing("ModLoader", "init", var11);
            throwException(var11);
            throw new RuntimeException(var11);
        }
        catch (IllegalArgumentException var12)
        {
            logger.throwing("ModLoader", "init", var12);
            throwException(var12);
            throw new RuntimeException(var12);
        }
        catch (IllegalAccessException var13)
        {
            logger.throwing("ModLoader", "init", var13);
            throwException(var13);
            throw new RuntimeException(var13);
        }

        try
        {
            loadConfig();

            if (props.containsKey("loggingLevel"))
            {
                cfgLoggingLevel = Level.parse(props.getProperty("loggingLevel"));
            }

            if (props.containsKey("grassFix"))
            {
                RenderBlocks.cfgGrassFix = Boolean.parseBoolean(props.getProperty("grassFix"));
            }

            logger.setLevel(cfgLoggingLevel);

            if ((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null)
            {
                logHandler = new FileHandler(logfile.getPath());
                logHandler.setFormatter(new SimpleFormatter());
                logger.addHandler(logHandler);
            }

            logger.fine("ModLoader 1.6.2 Initializing...");
            System.out.println("ModLoader 1.6.2 Initializing...");
            File var14 = new File(ModLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            modDir.mkdirs();
            readFromClassPath(var14);
            readFromModFolder(modDir);
            sortModList();
            Iterator var15 = modList.iterator();

            while (var15.hasNext())
            {
                BaseMod var17 = (BaseMod)var15.next();
                var17.load();
                logger.fine("Mod Loaded: \"" + var17.toString() + "\"");
                System.out.println("Mod Loaded: " + var17.toString());

                if (!props.containsKey(var17.getClass().getSimpleName()))
                {
                    props.setProperty(var17.getClass().getSimpleName(), "on");
                }
            }

            Iterator var18 = modList.iterator();

            while (var18.hasNext())
            {
                BaseMod var16 = (BaseMod)var18.next();
                var16.modsLoaded();
            }

            System.out.println("Done.");
            props.setProperty("loggingLevel", cfgLoggingLevel.getName());
            props.setProperty("grassFix", Boolean.toString(RenderBlocks.cfgGrassFix));
            instance.gameSettings.keyBindings = registerAllKeys(instance.gameSettings.keyBindings);
            instance.gameSettings.loadOptions();
            instance.func_110436_a();
            initStats();
            saveConfig();
        }
        catch (Throwable var8)
        {
            logger.throwing("ModLoader", "init", var8);
            throwException("ModLoader has failed to initialize.", var8);

            if (logHandler != null)
            {
                logHandler.close();
            }

            throw new RuntimeException(var8);
        }
    }

    private static void initStats()
    {
        int hashset;
        String var1;

        for (hashset = 0; hashset < Block.blocksList.length; ++hashset)
        {
            if (!StatList.oneShotStats.containsKey(Integer.valueOf(16777216 + hashset)) && Block.blocksList[hashset] != null && Block.blocksList[hashset].getEnableStats())
            {
                var1 = StatCollector.translateToLocalFormatted("stat.mineBlock", new Object[] {Block.blocksList[hashset].getLocalizedName()});
                StatList.mineBlockStatArray[hashset] = (new StatCrafting(16777216 + hashset, var1, hashset)).registerStat();
                StatList.objectMineStats.add(StatList.mineBlockStatArray[hashset]);
            }
        }

        for (hashset = 0; hashset < Item.itemsList.length; ++hashset)
        {
            if (!StatList.oneShotStats.containsKey(Integer.valueOf(16908288 + hashset)) && Item.itemsList[hashset] != null)
            {
                var1 = StatCollector.translateToLocalFormatted("stat.useItem", new Object[] {Item.itemsList[hashset].getStatName()});
                StatList.objectUseStats[hashset] = (new StatCrafting(16908288 + hashset, var1, hashset)).registerStat();

                if (hashset >= Block.blocksList.length)
                {
                    StatList.itemStats.add(StatList.objectUseStats[hashset]);
                }
            }

            if (!StatList.oneShotStats.containsKey(Integer.valueOf(16973824 + hashset)) && Item.itemsList[hashset] != null && Item.itemsList[hashset].isDamageable())
            {
                var1 = StatCollector.translateToLocalFormatted("stat.breakItem", new Object[] {Item.itemsList[hashset].getStatName()});
                StatList.objectBreakStats[hashset] = (new StatCrafting(16973824 + hashset, var1, hashset)).registerStat();
            }
        }

        HashSet var5 = new HashSet();
        Iterator var6 = CraftingManager.getInstance().getRecipeList().iterator();

        while (var6.hasNext())
        {
            IRecipe iterator2 = (IRecipe)var6.next();

            if (iterator2.getRecipeOutput() != null)
            {
                var5.add(Integer.valueOf(iterator2.getRecipeOutput().itemID));
            }
        }

        var6 = FurnaceRecipes.smelting().getSmeltingList().values().iterator();

        while (var6.hasNext())
        {
            ItemStack var7 = (ItemStack)var6.next();
            var5.add(Integer.valueOf(var7.itemID));
        }

        Iterator var8 = var5.iterator();

        while (var8.hasNext())
        {
            int k = ((Integer)var8.next()).intValue();

            if (!StatList.oneShotStats.containsKey(Integer.valueOf(16842752 + k)) && Item.itemsList[k] != null)
            {
                String s3 = StatCollector.translateToLocalFormatted("stat.craftItem", new Object[] {Item.itemsList[k].getStatName()});
                StatList.objectCraftStats[k] = (new StatCrafting(16842752 + k, s3, k)).registerStat();
            }
        }
    }

    public static boolean isGUIOpen(Class class1)
    {
        Minecraft minecraft = getMinecraftInstance();
        return class1 == null ? minecraft.currentScreen == null : (minecraft.currentScreen == null && class1 != null ? false : class1.isInstance(minecraft.currentScreen));
    }

    public static boolean isModLoaded(String s)
    {
        Iterator iterator = modList.iterator();
        BaseMod basemod;

        do
        {
            if (!iterator.hasNext())
            {
                return false;
            }

            basemod = (BaseMod)iterator.next();
        }
        while (!s.contentEquals(basemod.getName()));

        return true;
    }

    public static void loadConfig() throws IOException
    {
        cfgdir.mkdir();

        if (cfgfile.exists() || cfgfile.createNewFile())
        {
            if (cfgfile.canRead())
            {
                FileInputStream fileinputstream = new FileInputStream(cfgfile);
                props.load(fileinputstream);
                fileinputstream.close();
            }
        }
    }

    public static void onItemPickup(EntityPlayer entityplayer, ItemStack itemstack)
    {
        Iterator iterator = modList.iterator();

        while (iterator.hasNext())
        {
            BaseMod basemod = (BaseMod)iterator.next();
            basemod.onItemPickup(entityplayer, itemstack);
        }
    }

    public static void onTick(float f, Minecraft minecraft)
    {
        minecraft.mcProfiler.endSection();
        minecraft.mcProfiler.endSection();
        minecraft.mcProfiler.startSection("modtick");

        if (!hasInit)
        {
            init();
            logger.fine("Initialized");
        }

        if (langPack == null || minecraft.gameSettings.language != langPack)
        {
            Map l = null;

            try
            {
                l = (Map)getPrivateValue(StringTranslate.class, StringTranslate.getInstance(), 3);
            }
            catch (SecurityException var12)
            {
                logger.throwing("ModLoader", "AddLocalization", var12);
                throwException(var12);
            }
            catch (NoSuchFieldException var13)
            {
                logger.throwing("ModLoader", "AddLocalization", var13);
                throwException(var13);
            }

            langPack = minecraft.gameSettings.language;

            if (l != null)
            {
                if (localizedStrings.containsKey("en_US"))
                {
                    l.putAll((Map)localizedStrings.get("en_US"));
                }

                if (!langPack.contentEquals("en_US") && localizedStrings.containsKey(langPack))
                {
                    l.putAll((Map)localizedStrings.get(langPack));
                }
            }
        }

        long l1 = 0L;
        Iterator iterator2;
        Entry entry;

        if (minecraft.thePlayer != null && minecraft.thePlayer.worldObj != null)
        {
            l1 = minecraft.thePlayer.worldObj.getWorldTime();
            iterator2 = inGameHooks.entrySet().iterator();

            while (iterator2.hasNext())
            {
                entry = (Entry)iterator2.next();

                if ((clock != l1 || !((Boolean)entry.getValue()).booleanValue()) && !((BaseMod)entry.getKey()).onTickInGame(f, minecraft))
                {
                    iterator2.remove();
                }
            }
        }

        if (minecraft.currentScreen != null)
        {
            iterator2 = inGUIHooks.entrySet().iterator();

            while (iterator2.hasNext())
            {
                entry = (Entry)iterator2.next();

                if ((clock != l1 || !((Boolean)entry.getValue()).booleanValue() || minecraft.thePlayer == null || minecraft.thePlayer.worldObj == null) && !((BaseMod)entry.getKey()).onTickInGUI(f, minecraft, minecraft.currentScreen))
                {
                    iterator2.remove();
                }
            }
        }

        if (clock != l1)
        {
            iterator2 = keyList.entrySet().iterator();

            while (iterator2.hasNext())
            {
                entry = (Entry)iterator2.next();
                Iterator iterator3 = ((Map)entry.getValue()).entrySet().iterator();

                while (iterator3.hasNext())
                {
                    Entry entry3 = (Entry)iterator3.next();
                    int i = ((KeyBinding)entry3.getKey()).keyCode;
                    boolean flag;

                    if (i < 0)
                    {
                        i += 100;
                        flag = Mouse.isButtonDown(i);
                    }
                    else
                    {
                        flag = Keyboard.isKeyDown(i);
                    }

                    boolean[] aflag = (boolean[])((boolean[])entry3.getValue());
                    boolean flag1 = aflag[1];
                    aflag[1] = flag;

                    if (flag && (!flag1 || aflag[0]))
                    {
                        ((BaseMod)entry.getKey()).keyboardEvent((KeyBinding)entry3.getKey());
                    }
                }
            }
        }

        clock = l1;
        minecraft.mcProfiler.endSection();
        minecraft.mcProfiler.startSection("render");
        minecraft.mcProfiler.startSection("gameRenderer");
    }

    public static void openGUI(EntityPlayer entityplayer, GuiScreen guiscreen)
    {
        if (!hasInit)
        {
            init();
            logger.fine("Initialized");
        }

        Minecraft minecraft = getMinecraftInstance();

        if (minecraft.thePlayer == entityplayer)
        {
            if (guiscreen != null)
            {
                minecraft.displayGuiScreen(guiscreen);
            }
        }
    }

    public static void populateChunk(IChunkProvider ichunkprovider, int i, int j, World world)
    {
        if (!hasInit)
        {
            init();
            logger.fine("Initialized");
        }

        Random random = new Random(world.getSeed());
        long l = random.nextLong() / 2L * 2L + 1L;
        long l1 = random.nextLong() / 2L * 2L + 1L;
        random.setSeed((long)i * l + (long)j * l1 ^ world.getSeed());
        Iterator iterator = modList.iterator();

        while (iterator.hasNext())
        {
            BaseMod basemod = (BaseMod)iterator.next();

            if (world.provider.isSurfaceWorld())
            {
                basemod.generateSurface(world, random, i << 4, j << 4);
            }
            else if (world.provider.isHellWorld)
            {
                basemod.generateNether(world, random, i << 4, j << 4);
            }
        }
    }

    private static void readFromClassPath(File file) throws FileNotFoundException, IOException
    {
        logger.finer("Adding mods from " + file.getCanonicalPath());
        ClassLoader classloader = ModLoader.class.getClassLoader();
        String s2;

        if (file.isFile() && (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")))
        {
            logger.finer("Zip found.");
            URL var8 = file.toURI().toURL();
            FileInputStream var10 = new FileInputStream(file);
            ZipInputStream var11 = new ZipInputStream(var10);
            s2 = null;

            while (true)
            {
                ZipEntry zipentry = var11.getNextEntry();

                if (zipentry == null)
                {
                    var10.close();
                    break;
                }

                String path = zipentry.getName();

                if (!zipentry.isDirectory() && path.startsWith("mod_") && path.endsWith(".class"))
                {
                    addMod(classloader, path);
                }
            }
        }
        else if (file.isDirectory())
        {
            Package package1 = ModLoader.class.getPackage();

            if (package1 != null)
            {
                String afile = package1.getName().replace('.', File.separatorChar);
                file = new File(file, afile);
            }

            logger.finer("Directory found.");
            File[] var9 = file.listFiles();

            if (var9 != null)
            {
                for (int i = 0; i < var9.length; ++i)
                {
                    s2 = var9[i].getName();

                    if (var9[i].isFile() && s2.startsWith("mod_") && s2.endsWith(".class"))
                    {
                        addMod(classloader, s2);
                    }
                }
            }
        }
    }

    private static void readFromModFolder(File file) throws IOException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException
    {
        ClassLoader classloader = Main.class.getClassLoader();
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
        method.setAccessible(true);

        if (!file.isDirectory())
        {
            throw new IllegalArgumentException("folder must be a Directory.");
        }
        else
        {
            File[] afile = file.listFiles();
            Arrays.sort(afile);
            int j;
            File file2;

            if (classloader instanceof URLClassLoader)
            {
                for (j = 0; j < afile.length; ++j)
                {
                    file2 = afile[j];

                    if (file2.isDirectory() || file2.isFile() && (file2.getName().endsWith(".jar") || file2.getName().endsWith(".zip")))
                    {
                        method.invoke(classloader, new Object[] {file2.toURI().toURL()});
                    }
                }
            }

            for (j = 0; j < afile.length; ++j)
            {
                file2 = afile[j];

                if (file2.isDirectory() || file2.isFile() && (file2.getName().endsWith(".jar") || file2.getName().endsWith(".zip")))
                {
                    logger.finer("Adding mods from " + file2.getCanonicalPath());
                    String s2;

                    if (file2.isFile())
                    {
                        logger.finer("Zip found.");
                        URL package1 = file2.toURI().toURL();
                        FileInputStream afile1 = new FileInputStream(file2);
                        ZipInputStream k = new ZipInputStream(afile1);
                        s2 = null;

                        while (true)
                        {
                            ZipEntry zipentry = k.getNextEntry();

                            if (zipentry == null)
                            {
                                k.close();
                                afile1.close();
                                break;
                            }

                            String path = zipentry.getName();

                            if (!zipentry.isDirectory() && path.startsWith("mod_") && path.endsWith(".class"))
                            {
                                addMod(classloader, path);
                            }
                        }
                    }
                    else if (file2.isDirectory())
                    {
                        resourcePacks.add(new FolderResourcePack(file2));
                        Package var12 = ModLoader.class.getPackage();

                        if (var12 != null)
                        {
                            String var13 = var12.getName().replace('.', File.separatorChar);
                            file2 = new File(file2, var13);
                        }

                        logger.finer("Directory found.");
                        File[] var14 = file2.listFiles();

                        if (var14 != null)
                        {
                            for (int var15 = 0; var15 < var14.length; ++var15)
                            {
                                s2 = var14[var15].getName();

                                if (var14[var15].isFile() && s2.startsWith("mod_") && s2.endsWith(".class"))
                                {
                                    addMod(classloader, s2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void clientCustomPayload(Packet250CustomPayload packet)
    {
        if (packet.channel.equals("ML|OpenTE"))
        {
            try
            {
                DataInputStream basemod = new DataInputStream(new ByteArrayInputStream(packet.data));
                int guiID = basemod.read();
                int contID = basemod.readInt();
                int x = basemod.readInt();
                int y = basemod.readInt();
                int z = basemod.readInt();
                byte dim = (byte)basemod.read();
                EntityClientPlayerMP player = instance.thePlayer;

                if (player.dimension != dim)
                {
                    return;
                }

                if (containerGUIs.containsKey(Integer.valueOf(contID)))
                {
                    BaseMod basemod1 = (BaseMod)containerGUIs.get(Integer.valueOf(contID));

                    if (basemod1 != null)
                    {
                        GuiContainer gui = basemod1.getContainerGUI(player, contID, x, y, z);

                        if (gui == null)
                        {
                            return;
                        }

                        instance.displayGuiScreen(gui);
                        player.openContainer.windowId = guiID;
                    }
                }
            }
            catch (IOException var11)
            {
                var11.printStackTrace();
            }
        }
        else if (packetChannels.containsKey(packet.channel))
        {
            BaseMod basemod2 = (BaseMod)packetChannels.get(packet.channel);

            if (basemod2 != null)
            {
                basemod2.clientCustomPayload(clientHandler, packet);
            }
        }
    }

    public static void serverCustomPayload(NetServerHandler serverHandler, Packet250CustomPayload packet250custompayload)
    {
        if (packetChannels.containsKey(packet250custompayload.channel))
        {
            BaseMod basemod = (BaseMod)packetChannels.get(packet250custompayload.channel);

            if (basemod != null)
            {
                basemod.serverCustomPayload(serverHandler, packet250custompayload);
            }
        }
    }

    public static void registerContainerID(BaseMod mod, int id)
    {
        containerGUIs.put(Integer.valueOf(id), mod);
    }

    public static void clientOpenWindow(Packet100OpenWindow par1Packet100OpenWindow) {}

    public static void serverOpenWindow(EntityPlayerMP player, Container container, int id, int x, int y, int z)
    {
        try
        {
            Field e = EntityPlayerMP.class.getDeclaredFields()[17];
            e.setAccessible(true);
            int winID = e.getInt(player);
            winID = winID % 100 + 1;
            e.setInt(player, winID);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            DataOutputStream stream = new DataOutputStream(bytestream);
            stream.write(winID);
            stream.writeInt(id);
            stream.writeInt(x);
            stream.writeInt(y);
            stream.writeInt(z);
            stream.write(player.dimension);
            player.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("ML|OpenTE", bytestream.toByteArray()));
            player.openContainer = container;
            player.openContainer.windowId = winID;
            player.openContainer.addCraftingToCrafters(player);
        }
        catch (Exception var10)
        {
            var10.printStackTrace();
        }
    }

    public static KeyBinding[] registerAllKeys(KeyBinding[] akeybinding)
    {
        LinkedList linkedlist = new LinkedList();
        linkedlist.addAll(Arrays.asList(akeybinding));
        Iterator iterator = keyList.values().iterator();

        while (iterator.hasNext())
        {
            Map map = (Map)iterator.next();
            linkedlist.addAll(map.keySet());
        }

        return (KeyBinding[])((KeyBinding[])linkedlist.toArray(new KeyBinding[0]));
    }

    public static void registerBlock(Block block)
    {
        registerBlock(block, (Class)null);
    }

    public static void registerBlock(Block block, Class class1)
    {
        try
        {
            if (block == null)
            {
                throw new IllegalArgumentException("block parameter cannot be null.");
            }

            int nosuchmethodexception = block.blockID;
            ItemBlock itemblock = null;

            if (class1 != null)
            {
                itemblock = (ItemBlock)class1.getConstructor(new Class[] {Integer.TYPE}).newInstance(new Object[] {Integer.valueOf(nosuchmethodexception - 256)});
            }
            else
            {
                itemblock = new ItemBlock(nosuchmethodexception - 256);
            }

            if (Block.blocksList[nosuchmethodexception] != null && Item.itemsList[nosuchmethodexception] == null)
            {
                Item.itemsList[nosuchmethodexception] = itemblock;
            }
        }
        catch (IllegalArgumentException var4)
        {
            logger.throwing("ModLoader", "RegisterBlock", var4);
            throwException(var4);
        }
        catch (IllegalAccessException var5)
        {
            logger.throwing("ModLoader", "RegisterBlock", var5);
            throwException(var5);
        }
        catch (SecurityException var6)
        {
            logger.throwing("ModLoader", "RegisterBlock", var6);
            throwException(var6);
        }
        catch (InstantiationException var7)
        {
            logger.throwing("ModLoader", "RegisterBlock", var7);
            throwException(var7);
        }
        catch (InvocationTargetException var8)
        {
            logger.throwing("ModLoader", "RegisterBlock", var8);
            throwException(var8);
        }
        catch (NoSuchMethodException var9)
        {
            logger.throwing("ModLoader", "RegisterBlock", var9);
            throwException(var9);
        }
    }

    public static void registerEntityID(Class class1, String s, int i)
    {
        try
        {
            method_RegisterEntityID.invoke((Object)null, new Object[] {class1, s, Integer.valueOf(i)});
        }
        catch (IllegalArgumentException var4)
        {
            logger.throwing("ModLoader", "RegisterEntityID", var4);
            throwException(var4);
        }
        catch (IllegalAccessException var5)
        {
            logger.throwing("ModLoader", "RegisterEntityID", var5);
            throwException(var5);
        }
        catch (InvocationTargetException var6)
        {
            logger.throwing("ModLoader", "RegisterEntityID", var6);
            throwException(var6);
        }
    }

    public static void registerEntityID(Class class1, String s, int i, int j, int k)
    {
        registerEntityID(class1, s, i);
        EntityList.entityEggs.put(Integer.valueOf(i), new EntityEggInfo(i, j, k));
    }

    public static void registerKey(BaseMod basemod, KeyBinding keybinding, boolean flag)
    {
        Object obj = (Map)keyList.get(basemod);

        if (obj == null)
        {
            obj = new HashMap();
        }

        boolean[] aflag = new boolean[] {flag, false};
        ((Map)obj).put(keybinding, aflag);
        keyList.put(basemod, (Map)obj);
    }

    public static void registerPacketChannel(BaseMod basemod, String s)
    {
        if (s.length() < 16)
        {
            packetChannels.put(s, basemod);
        }
        else
        {
            throw new RuntimeException(String.format("Invalid channel name: %s. Must be less than 16 characters.", new Object[] {s}));
        }
    }

    public static void registerTileEntity(Class class1, String s)
    {
        registerTileEntity(class1, s, (TileEntitySpecialRenderer)null);
    }

    public static void registerTileEntity(Class class1, String s, TileEntitySpecialRenderer tileentityspecialrenderer)
    {
        try
        {
            method_RegisterTileEntity.invoke((Object)null, new Object[] {class1, s});

            if (tileentityspecialrenderer != null)
            {
                TileEntityRenderer invocationtargetexception = TileEntityRenderer.instance;
                Map map = (Map)field_TileEntityRenderers.get(invocationtargetexception);
                map.put(class1, tileentityspecialrenderer);
                tileentityspecialrenderer.setTileEntityRenderer(invocationtargetexception);
            }
        }
        catch (IllegalArgumentException var5)
        {
            logger.throwing("ModLoader", "RegisterTileEntity", var5);
            throwException(var5);
        }
        catch (IllegalAccessException var6)
        {
            logger.throwing("ModLoader", "RegisterTileEntity", var6);
            throwException(var6);
        }
        catch (InvocationTargetException var7)
        {
            logger.throwing("ModLoader", "RegisterTileEntity", var7);
            throwException(var7);
        }
    }

    public static void removeBiome(BiomeGenBase biomegenbase)
    {
        BiomeGenBase[] abiomegenbase = GenLayerBiome.biomeArray;
        List list = Arrays.asList(abiomegenbase);
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(list);

        if (arraylist.contains(biomegenbase))
        {
            arraylist.remove(biomegenbase);
        }

        GenLayerBiome.biomeArray = (BiomeGenBase[])((BiomeGenBase[])arraylist.toArray(new BiomeGenBase[0]));
    }

    public static void removeSpawn(Class class1, EnumCreatureType enumcreaturetype)
    {
        removeSpawn(class1, enumcreaturetype, (BiomeGenBase[])null);
    }

    public static void removeSpawn(Class class1, EnumCreatureType enumcreaturetype, BiomeGenBase[] abiomegenbase)
    {
        if (class1 == null)
        {
            throw new IllegalArgumentException("entityClass cannot be null");
        }
        else if (enumcreaturetype == null)
        {
            throw new IllegalArgumentException("spawnList cannot be null");
        }
        else
        {
            if (abiomegenbase == null)
            {
                abiomegenbase = standardBiomes;
            }

            for (int i = 0; i < abiomegenbase.length; ++i)
            {
                List list = abiomegenbase[i].getSpawnableList(enumcreaturetype);

                if (list != null)
                {
                    Iterator iterator = list.iterator();

                    while (iterator.hasNext())
                    {
                        SpawnListEntry spawnlistentry = (SpawnListEntry)iterator.next();

                        if (spawnlistentry.entityClass == class1)
                        {
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    public static void removeSpawn(String s, EnumCreatureType enumcreaturetype)
    {
        removeSpawn(s, enumcreaturetype, (BiomeGenBase[])null);
    }

    public static void removeSpawn(String s, EnumCreatureType enumcreaturetype, BiomeGenBase[] abiomegenbase)
    {
        Class class1 = (Class)classMap.get(s);

        if (class1 != null && EntityLiving.class.isAssignableFrom(class1))
        {
            removeSpawn(class1, enumcreaturetype, abiomegenbase);
        }
    }

    public static boolean renderBlockIsItemFull3D(int i)
    {
        return !blockSpecialInv.containsKey(Integer.valueOf(i)) ? i == 35 : ((Boolean)blockSpecialInv.get(Integer.valueOf(i))).booleanValue();
    }

    public static void renderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
        BaseMod basemod = (BaseMod)blockModels.get(Integer.valueOf(j));

        if (basemod != null)
        {
            basemod.renderInvBlock(renderblocks, block, i, j);
        }
    }

    public static boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        BaseMod basemod = (BaseMod)blockModels.get(Integer.valueOf(l));
        return basemod == null ? false : basemod.renderWorldBlock(renderblocks, iblockaccess, i, j, k, block, l);
    }

    public static void saveConfig() throws IOException
    {
        cfgdir.mkdir();

        if (cfgfile.exists() || cfgfile.createNewFile())
        {
            if (cfgfile.canWrite())
            {
                FileOutputStream fileoutputstream = new FileOutputStream(cfgfile);
                props.store(fileoutputstream, "ModLoader Config");
                fileoutputstream.close();
            }
        }
    }

    public static void clientChat(String s)
    {
        Iterator i$ = modList.iterator();

        while (i$.hasNext())
        {
            BaseMod mod = (BaseMod)i$.next();
            mod.clientChat(s);
        }
    }

    public static void serverChat(NetServerHandler netserverhandler, String s)
    {
        Iterator i$ = modList.iterator();

        while (i$.hasNext())
        {
            BaseMod mod = (BaseMod)i$.next();
            mod.serverChat(netserverhandler, s);
        }
    }

    public static void clientConnect(NetClientHandler netclienthandler, Packet1Login packet1login)
    {
        clientHandler = netclienthandler;

        if (packetChannels.size() > 0)
        {
            Packet250CustomPayload i$ = new Packet250CustomPayload();
            i$.channel = "REGISTER";
            StringBuilder mod = new StringBuilder();
            Iterator iterator1 = packetChannels.keySet().iterator();
            mod.append((String)iterator1.next());

            while (iterator1.hasNext())
            {
                mod.append("\u0000");
                mod.append((String)iterator1.next());
            }

            i$.data = mod.toString().getBytes(Charset.forName("UTF8"));
            i$.length = i$.data.length;
            clientSendPacket(i$);
        }

        Iterator i$1 = modList.iterator();

        while (i$1.hasNext())
        {
            BaseMod mod1 = (BaseMod)i$1.next();
            mod1.clientConnect(netclienthandler);
        }
    }

    public static void clientDisconnect()
    {
        Iterator i$ = modList.iterator();

        while (i$.hasNext())
        {
            BaseMod mod = (BaseMod)i$.next();
            mod.clientDisconnect(clientHandler);
        }

        clientHandler = null;
    }

    public static void clientSendPacket(Packet packet)
    {
        if (clientHandler != null)
        {
            clientHandler.addToSendQueue(packet);
        }
    }

    public static void serverSendPacket(NetServerHandler serverHandler, Packet packet)
    {
        if (serverHandler != null)
        {
            serverHandler.sendPacketToPlayer(packet);
        }
    }

    public static void setInGameHook(BaseMod basemod, boolean flag, boolean flag1)
    {
        if (flag)
        {
            inGameHooks.put(basemod, Boolean.valueOf(flag1));
        }
        else
        {
            inGameHooks.remove(basemod);
        }
    }

    public static void setInGUIHook(BaseMod basemod, boolean flag, boolean flag1)
    {
        if (flag)
        {
            inGUIHooks.put(basemod, Boolean.valueOf(flag1));
        }
        else
        {
            inGUIHooks.remove(basemod);
        }
    }

    public static void setPrivateValue(Class class1, Object obj, int i, Object obj1) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field illegalaccessexception = class1.getDeclaredFields()[i];
            illegalaccessexception.setAccessible(true);
            int j = field_modifiers.getInt(illegalaccessexception);

            if ((j & 16) != 0)
            {
                field_modifiers.setInt(illegalaccessexception, j & -17);
            }

            illegalaccessexception.set(obj, obj1);
        }
        catch (IllegalAccessException var6)
        {
            logger.throwing("ModLoader", "setPrivateValue", var6);
            throwException("An impossible error has occured!", var6);
        }
    }

    public static void setPrivateValue(Class class1, Object obj, String s, Object obj1) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field illegalaccessexception = class1.getDeclaredField(s);
            int i = field_modifiers.getInt(illegalaccessexception);

            if ((i & 16) != 0)
            {
                field_modifiers.setInt(illegalaccessexception, i & -17);
            }

            illegalaccessexception.setAccessible(true);
            illegalaccessexception.set(obj, obj1);
        }
        catch (IllegalAccessException var6)
        {
            logger.throwing("ModLoader", "setPrivateValue", var6);
            throwException("An impossible error has occured!", var6);
        }
    }

    private static void setupProperties(Class class1) throws IllegalArgumentException, IllegalAccessException, IOException, SecurityException, NoSuchFieldException, NoSuchAlgorithmException, DigestException
    {
        LinkedList linkedlist = new LinkedList();
        Properties properties = new Properties();
        int i = 0;
        int j = 0;
        File file = new File(cfgdir, class1.getSimpleName() + ".cfg");

        if (file.exists() && file.canRead())
        {
            properties.load(new FileInputStream(file));
        }

        if (properties.containsKey("checksum"))
        {
            j = Integer.parseInt(properties.getProperty("checksum"), 36);
        }

        Field[] afield;
        int l = (afield = class1.getDeclaredFields()).length;

        for (int stringbuilder = 0; stringbuilder < l; ++stringbuilder)
        {
            Field iterator = afield[stringbuilder];

            if ((iterator.getModifiers() & 8) != 0 && iterator.isAnnotationPresent(MLProp.class))
            {
                linkedlist.add(iterator);
                Object field1 = iterator.get((Object)null);
                i += field1.hashCode();
            }
        }

        StringBuilder var21 = new StringBuilder();
        Iterator var22 = linkedlist.iterator();

        while (var22.hasNext())
        {
            Field var23 = (Field)var22.next();

            if ((var23.getModifiers() & 8) != 0 && var23.isAnnotationPresent(MLProp.class))
            {
                Class class2 = var23.getType();
                MLProp mlprop = (MLProp)var23.getAnnotation(MLProp.class);
                String s = mlprop.name().length() != 0 ? mlprop.name() : var23.getName();
                Object obj1 = var23.get((Object)null);
                StringBuilder stringbuilder1 = new StringBuilder();

                if (mlprop.min() != Double.NEGATIVE_INFINITY)
                {
                    stringbuilder1.append(String.format(",>=%.1f", new Object[] {Double.valueOf(mlprop.min())}));
                }

                if (mlprop.max() != Double.POSITIVE_INFINITY)
                {
                    stringbuilder1.append(String.format(",<=%.1f", new Object[] {Double.valueOf(mlprop.max())}));
                }

                StringBuilder stringbuilder2 = new StringBuilder();

                if (mlprop.info().length() > 0)
                {
                    stringbuilder2.append(" -- ");
                    stringbuilder2.append(mlprop.info());
                }

                var21.append(String.format("%s (%s:%s%s)%s\n", new Object[] {s, class2.getName(), obj1, stringbuilder1, stringbuilder2}));

                if (j == i && properties.containsKey(s))
                {
                    String s1 = properties.getProperty(s);
                    Object obj2 = null;

                    if (class2.isAssignableFrom(String.class))
                    {
                        obj2 = s1;
                    }
                    else if (class2.isAssignableFrom(Integer.TYPE))
                    {
                        obj2 = Integer.valueOf(Integer.parseInt(s1));
                    }
                    else if (class2.isAssignableFrom(Short.TYPE))
                    {
                        obj2 = Short.valueOf(Short.parseShort(s1));
                    }
                    else if (class2.isAssignableFrom(Byte.TYPE))
                    {
                        obj2 = Byte.valueOf(Byte.parseByte(s1));
                    }
                    else if (class2.isAssignableFrom(Boolean.TYPE))
                    {
                        obj2 = Boolean.valueOf(Boolean.parseBoolean(s1));
                    }
                    else if (class2.isAssignableFrom(Float.TYPE))
                    {
                        obj2 = Float.valueOf(Float.parseFloat(s1));
                    }
                    else if (class2.isAssignableFrom(Double.TYPE))
                    {
                        obj2 = Double.valueOf(Double.parseDouble(s1));
                    }

                    if (obj2 != null)
                    {
                        if (obj2 instanceof Number)
                        {
                            double d = ((Number)obj2).doubleValue();

                            if (mlprop.min() != Double.NEGATIVE_INFINITY && d < mlprop.min() || mlprop.max() != Double.POSITIVE_INFINITY && d > mlprop.max())
                            {
                                continue;
                            }
                        }

                        logger.finer(s + " set to " + obj2);

                        if (!obj2.equals(obj1))
                        {
                            var23.set((Object)null, obj2);
                        }
                    }
                }
                else
                {
                    logger.finer(s + " not in config, using default: " + obj1);
                    properties.setProperty(s, obj1.toString());
                }
            }
        }

        properties.put("checksum", Integer.toString(i, 36));

        if (!properties.isEmpty() && (file.exists() || file.createNewFile()) && file.canWrite())
        {
            properties.store(new FileOutputStream(file), var21.toString());
        }
    }

    private static void sortModList() throws Exception
    {
        HashMap hashmap = new HashMap();
        Iterator linkedlist = getLoadedMods().iterator();

        while (linkedlist.hasNext())
        {
            BaseMod basemod = (BaseMod)linkedlist.next();
            hashmap.put(basemod.getClass().getSimpleName(), basemod);
        }

        LinkedList var18 = new LinkedList();

        for (int i = 0; var18.size() != modList.size() && i <= 10; ++i)
        {
            Iterator iterator1 = modList.iterator();

            while (iterator1.hasNext())
            {
                BaseMod basemod1 = (BaseMod)iterator1.next();

                if (!var18.contains(basemod1))
                {
                    String s = basemod1.getPriorities();

                    if (s != null && s.length() != 0 && s.indexOf(58) != -1)
                    {
                        if (i > 0)
                        {
                            int j = -1;
                            int k = Integer.MIN_VALUE;
                            int l = Integer.MAX_VALUE;
                            String[] as;

                            if (s.indexOf(59) > 0)
                            {
                                as = s.split(";");
                            }
                            else
                            {
                                as = new String[] {s};
                            }

                            int i1 = 0;

                            while (true)
                            {
                                if (i1 < as.length)
                                {
                                    label143:
                                    {
                                        String s1 = as[i1];

                                        if (s1.indexOf(58) != -1)
                                        {
                                            String[] as1 = s1.split(":");
                                            String s2 = as1[0];
                                            String s3 = as1[1];

                                            if (s2.contentEquals("required-before") || s2.contentEquals("before") || s2.contentEquals("after") || s2.contentEquals("required-after"))
                                            {
                                                if (s3.contentEquals("*"))
                                                {
                                                    if (!s2.contentEquals("required-before") && !s2.contentEquals("before"))
                                                    {
                                                        if (s2.contentEquals("required-after") || s2.contentEquals("after"))
                                                        {
                                                            j = var18.size();
                                                        }
                                                    }
                                                    else
                                                    {
                                                        j = 0;
                                                    }

                                                    break label143;
                                                }

                                                if ((s2.contentEquals("required-before") || s2.contentEquals("required-after")) && !hashmap.containsKey(s3))
                                                {
                                                    throw new Exception(String.format("%s is missing dependency: %s", new Object[] {basemod1, s3}));
                                                }

                                                BaseMod basemod2 = (BaseMod)hashmap.get(s3);

                                                if (!var18.contains(basemod2))
                                                {
                                                    break;
                                                }

                                                int j1 = var18.indexOf(basemod2);

                                                if (!s2.contentEquals("required-before") && !s2.contentEquals("before"))
                                                {
                                                    if (s2.contentEquals("required-after") || s2.contentEquals("after"))
                                                    {
                                                        j = j1 + 1;

                                                        if (j > k)
                                                        {
                                                            k = j;
                                                        }
                                                        else
                                                        {
                                                            j = k;
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    j = j1;

                                                    if (j1 < l)
                                                    {
                                                        l = j1;
                                                    }
                                                    else
                                                    {
                                                        j = l;
                                                    }
                                                }
                                            }
                                        }

                                        ++i1;
                                        continue;
                                    }
                                }

                                if (j != -1)
                                {
                                    var18.add(j, basemod1);
                                }

                                break;
                            }
                        }
                    }
                    else
                    {
                        var18.add(basemod1);
                    }
                }
            }
        }

        modList.clear();
        modList.addAll(var18);
    }

    public static void takenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory)
    {
        Iterator iterator = modList.iterator();

        while (iterator.hasNext())
        {
            BaseMod basemod = (BaseMod)iterator.next();
            basemod.takenFromCrafting(entityplayer, itemstack, iinventory);
        }
    }

    public static void takenFromFurnace(EntityPlayer entityplayer, ItemStack itemstack)
    {
        Iterator iterator = modList.iterator();

        while (iterator.hasNext())
        {
            BaseMod basemod = (BaseMod)iterator.next();
            basemod.takenFromFurnace(entityplayer, itemstack);
        }
    }

    public static void throwException(String s, Throwable throwable)
    {
        Minecraft minecraft = getMinecraftInstance();

        if (minecraft != null)
        {
            minecraft.displayCrashReport(new CrashReport(s, throwable));
        }
        else
        {
            throw new RuntimeException(throwable);
        }
    }

    private static void throwException(Throwable throwable)
    {
        throwException("Exception occured in ModLoader", throwable);
    }

    public static String getCrashReport()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Mods loaded: ");
        sb.append(getLoadedMods().size() + 1);
        sb.append('\n');
        sb.append("ModLoader 1.6.2");
        sb.append('\n');
        Iterator i$ = getLoadedMods().iterator();

        while (i$.hasNext())
        {
            BaseMod mod = (BaseMod)i$.next();
            sb.append(mod.getName());
            sb.append(' ');
            sb.append(mod.getVersion());
            sb.append('\n');
        }

        return sb.toString();
    }

    public static void addCustomAnimationLogic(String name, Icon tex)
    {
        customTextures.put(name, tex);
    }

    public static Icon getCustomAnimationLogic(String name)
    {
        return !customTextures.containsKey(name) ? null : (Icon)customTextures.get(name);
    }

    static
    {
        File versionsDir = new File(Minecraft.getMinecraft().mcDataDir, "versions");
        File version = new File(versionsDir, Minecraft.func_110431_a(Minecraft.getMinecraft()));

        if (versionsDir.exists() && versionsDir.isDirectory() && version.exists() && version.isDirectory())
        {
            modDir = new File(version, "/mods/");
        }
        else
        {
            modDir = new File(Minecraft.getMinecraft().mcDataDir, "mods");
        }

        System.out.println(modDir);
        cfgdir = new File(Minecraft.getMinecraft().mcDataDir, "/config/");
        cfgfile = new File(cfgdir, "ModLoader.cfg");
        cfgLoggingLevel = Level.FINER;
    }
}
