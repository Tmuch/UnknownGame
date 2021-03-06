package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class ModelHorse extends ModelBase
{
    private ModelRenderer field_110709_a;
    private ModelRenderer field_110707_b;
    private ModelRenderer field_110708_c;
    private ModelRenderer field_110705_d;
    private ModelRenderer field_110706_e;
    private ModelRenderer field_110703_f;
    private ModelRenderer field_110704_g;
    private ModelRenderer field_110716_h;
    private ModelRenderer field_110717_i;
    private ModelRenderer field_110714_j;
    private ModelRenderer field_110715_k;
    private ModelRenderer field_110712_l;
    private ModelRenderer field_110713_m;
    private ModelRenderer field_110710_n;
    private ModelRenderer field_110711_o;
    private ModelRenderer field_110719_v;
    private ModelRenderer field_110718_w;
    private ModelRenderer field_110722_x;
    private ModelRenderer field_110721_y;
    private ModelRenderer field_110720_z;
    private ModelRenderer field_110688_A;
    private ModelRenderer field_110689_B;
    private ModelRenderer field_110690_C;
    private ModelRenderer field_110684_D;
    private ModelRenderer field_110685_E;
    private ModelRenderer field_110686_F;
    private ModelRenderer field_110687_G;
    private ModelRenderer field_110695_H;
    private ModelRenderer field_110696_I;
    private ModelRenderer field_110697_J;
    private ModelRenderer field_110698_K;
    private ModelRenderer field_110691_L;
    private ModelRenderer field_110692_M;
    private ModelRenderer field_110693_N;
    private ModelRenderer field_110694_O;
    private ModelRenderer field_110700_P;
    private ModelRenderer field_110699_Q;
    private ModelRenderer field_110702_R;
    private ModelRenderer field_110701_S;

    public ModelHorse()
    {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.field_110715_k = new ModelRenderer(this, 0, 34);
        this.field_110715_k.addBox(-5.0F, -8.0F, -19.0F, 10, 10, 24);
        this.field_110715_k.setRotationPoint(0.0F, 11.0F, 9.0F);
        this.field_110712_l = new ModelRenderer(this, 44, 0);
        this.field_110712_l.addBox(-1.0F, -1.0F, 0.0F, 2, 2, 3);
        this.field_110712_l.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.func_110682_a(this.field_110712_l, -1.134464F, 0.0F, 0.0F);
        this.field_110713_m = new ModelRenderer(this, 38, 7);
        this.field_110713_m.addBox(-1.5F, -2.0F, 3.0F, 3, 4, 7);
        this.field_110713_m.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.func_110682_a(this.field_110713_m, -1.134464F, 0.0F, 0.0F);
        this.field_110710_n = new ModelRenderer(this, 24, 3);
        this.field_110710_n.addBox(-1.5F, -4.5F, 9.0F, 3, 4, 7);
        this.field_110710_n.setRotationPoint(0.0F, 3.0F, 14.0F);
        this.func_110682_a(this.field_110710_n, -1.40215F, 0.0F, 0.0F);
        this.field_110711_o = new ModelRenderer(this, 78, 29);
        this.field_110711_o.addBox(-2.5F, -2.0F, -2.5F, 4, 9, 5);
        this.field_110711_o.setRotationPoint(4.0F, 9.0F, 11.0F);
        this.field_110719_v = new ModelRenderer(this, 78, 43);
        this.field_110719_v.addBox(-2.0F, 0.0F, -1.5F, 3, 5, 3);
        this.field_110719_v.setRotationPoint(4.0F, 16.0F, 11.0F);
        this.field_110718_w = new ModelRenderer(this, 78, 51);
        this.field_110718_w.addBox(-2.5F, 5.1F, -2.0F, 4, 3, 4);
        this.field_110718_w.setRotationPoint(4.0F, 16.0F, 11.0F);
        this.field_110722_x = new ModelRenderer(this, 96, 29);
        this.field_110722_x.addBox(-1.5F, -2.0F, -2.5F, 4, 9, 5);
        this.field_110722_x.setRotationPoint(-4.0F, 9.0F, 11.0F);
        this.field_110721_y = new ModelRenderer(this, 96, 43);
        this.field_110721_y.addBox(-1.0F, 0.0F, -1.5F, 3, 5, 3);
        this.field_110721_y.setRotationPoint(-4.0F, 16.0F, 11.0F);
        this.field_110720_z = new ModelRenderer(this, 96, 51);
        this.field_110720_z.addBox(-1.5F, 5.1F, -2.0F, 4, 3, 4);
        this.field_110720_z.setRotationPoint(-4.0F, 16.0F, 11.0F);
        this.field_110688_A = new ModelRenderer(this, 44, 29);
        this.field_110688_A.addBox(-1.9F, -1.0F, -2.1F, 3, 8, 4);
        this.field_110688_A.setRotationPoint(4.0F, 9.0F, -8.0F);
        this.field_110689_B = new ModelRenderer(this, 44, 41);
        this.field_110689_B.addBox(-1.9F, 0.0F, -1.6F, 3, 5, 3);
        this.field_110689_B.setRotationPoint(4.0F, 16.0F, -8.0F);
        this.field_110690_C = new ModelRenderer(this, 44, 51);
        this.field_110690_C.addBox(-2.4F, 5.1F, -2.1F, 4, 3, 4);
        this.field_110690_C.setRotationPoint(4.0F, 16.0F, -8.0F);
        this.field_110684_D = new ModelRenderer(this, 60, 29);
        this.field_110684_D.addBox(-1.1F, -1.0F, -2.1F, 3, 8, 4);
        this.field_110684_D.setRotationPoint(-4.0F, 9.0F, -8.0F);
        this.field_110685_E = new ModelRenderer(this, 60, 41);
        this.field_110685_E.addBox(-1.1F, 0.0F, -1.6F, 3, 5, 3);
        this.field_110685_E.setRotationPoint(-4.0F, 16.0F, -8.0F);
        this.field_110686_F = new ModelRenderer(this, 60, 51);
        this.field_110686_F.addBox(-1.6F, 5.1F, -2.1F, 4, 3, 4);
        this.field_110686_F.setRotationPoint(-4.0F, 16.0F, -8.0F);
        this.field_110709_a = new ModelRenderer(this, 0, 0);
        this.field_110709_a.addBox(-2.5F, -10.0F, -1.5F, 5, 5, 7);
        this.field_110709_a.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110709_a, 0.5235988F, 0.0F, 0.0F);
        this.field_110707_b = new ModelRenderer(this, 24, 18);
        this.field_110707_b.addBox(-2.0F, -10.0F, -7.0F, 4, 3, 6);
        this.field_110707_b.setRotationPoint(0.0F, 3.95F, -10.0F);
        this.func_110682_a(this.field_110707_b, 0.5235988F, 0.0F, 0.0F);
        this.field_110708_c = new ModelRenderer(this, 24, 27);
        this.field_110708_c.addBox(-2.0F, -7.0F, -6.5F, 4, 2, 5);
        this.field_110708_c.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110708_c, 0.5235988F, 0.0F, 0.0F);
        this.field_110709_a.addChild(this.field_110707_b);
        this.field_110709_a.addChild(this.field_110708_c);
        this.field_110705_d = new ModelRenderer(this, 0, 0);
        this.field_110705_d.addBox(0.45F, -12.0F, 4.0F, 2, 3, 1);
        this.field_110705_d.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110705_d, 0.5235988F, 0.0F, 0.0F);
        this.field_110706_e = new ModelRenderer(this, 0, 0);
        this.field_110706_e.addBox(-2.45F, -12.0F, 4.0F, 2, 3, 1);
        this.field_110706_e.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110706_e, 0.5235988F, 0.0F, 0.0F);
        this.field_110703_f = new ModelRenderer(this, 0, 12);
        this.field_110703_f.addBox(-2.0F, -16.0F, 4.0F, 2, 7, 1);
        this.field_110703_f.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110703_f, 0.5235988F, 0.0F, 0.2617994F);
        this.field_110704_g = new ModelRenderer(this, 0, 12);
        this.field_110704_g.addBox(0.0F, -16.0F, 4.0F, 2, 7, 1);
        this.field_110704_g.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110704_g, 0.5235988F, 0.0F, -0.2617994F);
        this.field_110716_h = new ModelRenderer(this, 0, 12);
        this.field_110716_h.addBox(-2.05F, -9.8F, -2.0F, 4, 14, 8);
        this.field_110716_h.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110716_h, 0.5235988F, 0.0F, 0.0F);
        this.field_110687_G = new ModelRenderer(this, 0, 34);
        this.field_110687_G.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.field_110687_G.setRotationPoint(-7.5F, 3.0F, 10.0F);
        this.func_110682_a(this.field_110687_G, 0.0F, ((float)Math.PI / 2F), 0.0F);
        this.field_110695_H = new ModelRenderer(this, 0, 47);
        this.field_110695_H.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3);
        this.field_110695_H.setRotationPoint(4.5F, 3.0F, 10.0F);
        this.func_110682_a(this.field_110695_H, 0.0F, ((float)Math.PI / 2F), 0.0F);
        this.field_110696_I = new ModelRenderer(this, 80, 0);
        this.field_110696_I.addBox(-5.0F, 0.0F, -3.0F, 10, 1, 8);
        this.field_110696_I.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.field_110697_J = new ModelRenderer(this, 106, 9);
        this.field_110697_J.addBox(-1.5F, -1.0F, -3.0F, 3, 1, 2);
        this.field_110697_J.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.field_110698_K = new ModelRenderer(this, 80, 9);
        this.field_110698_K.addBox(-4.0F, -1.0F, 3.0F, 8, 1, 2);
        this.field_110698_K.setRotationPoint(0.0F, 2.0F, 2.0F);
        this.field_110692_M = new ModelRenderer(this, 74, 0);
        this.field_110692_M.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
        this.field_110692_M.setRotationPoint(5.0F, 3.0F, 2.0F);
        this.field_110691_L = new ModelRenderer(this, 70, 0);
        this.field_110691_L.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
        this.field_110691_L.setRotationPoint(5.0F, 3.0F, 2.0F);
        this.field_110694_O = new ModelRenderer(this, 74, 4);
        this.field_110694_O.addBox(-0.5F, 6.0F, -1.0F, 1, 2, 2);
        this.field_110694_O.setRotationPoint(-5.0F, 3.0F, 2.0F);
        this.field_110693_N = new ModelRenderer(this, 80, 0);
        this.field_110693_N.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1);
        this.field_110693_N.setRotationPoint(-5.0F, 3.0F, 2.0F);
        this.field_110700_P = new ModelRenderer(this, 74, 13);
        this.field_110700_P.addBox(1.5F, -8.0F, -4.0F, 1, 2, 2);
        this.field_110700_P.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110700_P, 0.5235988F, 0.0F, 0.0F);
        this.field_110699_Q = new ModelRenderer(this, 74, 13);
        this.field_110699_Q.addBox(-2.5F, -8.0F, -4.0F, 1, 2, 2);
        this.field_110699_Q.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110699_Q, 0.5235988F, 0.0F, 0.0F);
        this.field_110702_R = new ModelRenderer(this, 44, 10);
        this.field_110702_R.addBox(2.6F, -6.0F, -6.0F, 0, 3, 16);
        this.field_110702_R.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.field_110701_S = new ModelRenderer(this, 44, 5);
        this.field_110701_S.addBox(-2.6F, -6.0F, -6.0F, 0, 3, 16);
        this.field_110701_S.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.field_110714_j = new ModelRenderer(this, 58, 0);
        this.field_110714_j.addBox(-1.0F, -11.5F, 5.0F, 2, 16, 4);
        this.field_110714_j.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110714_j, 0.5235988F, 0.0F, 0.0F);
        this.field_110717_i = new ModelRenderer(this, 80, 12);
        this.field_110717_i.addBox(-2.5F, -10.1F, -7.0F, 5, 5, 12, 0.2F);
        this.field_110717_i.setRotationPoint(0.0F, 4.0F, -10.0F);
        this.func_110682_a(this.field_110717_i, 0.5235988F, 0.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        EntityHorse var8 = (EntityHorse)par1Entity;
        int var9 = var8.func_110265_bP();
        float var10 = var8.func_110258_o(0.0F);
        boolean var11 = var8.func_110228_bR();
        boolean var12 = var11 && var8.func_110257_ck();
        boolean var13 = var11 && var8.func_110261_ca();
        boolean var14 = var9 == 1 || var9 == 2;
        float var15 = var8.func_110254_bY();
        boolean var16 = var8.riddenByEntity != null;

        if (var12)
        {
            this.field_110717_i.render(par7);
            this.field_110696_I.render(par7);
            this.field_110697_J.render(par7);
            this.field_110698_K.render(par7);
            this.field_110691_L.render(par7);
            this.field_110692_M.render(par7);
            this.field_110693_N.render(par7);
            this.field_110694_O.render(par7);
            this.field_110700_P.render(par7);
            this.field_110699_Q.render(par7);

            if (var16)
            {
                this.field_110702_R.render(par7);
                this.field_110701_S.render(par7);
            }
        }

        if (!var11)
        {
            GL11.glPushMatrix();
            GL11.glScalef(var15, 0.5F + var15 * 0.5F, var15);
            GL11.glTranslatef(0.0F, 0.95F * (1.0F - var15), 0.0F);
        }

        this.field_110711_o.render(par7);
        this.field_110719_v.render(par7);
        this.field_110718_w.render(par7);
        this.field_110722_x.render(par7);
        this.field_110721_y.render(par7);
        this.field_110720_z.render(par7);
        this.field_110688_A.render(par7);
        this.field_110689_B.render(par7);
        this.field_110690_C.render(par7);
        this.field_110684_D.render(par7);
        this.field_110685_E.render(par7);
        this.field_110686_F.render(par7);

        if (!var11)
        {
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(var15, var15, var15);
            GL11.glTranslatef(0.0F, 1.35F * (1.0F - var15), 0.0F);
        }

        this.field_110715_k.render(par7);
        this.field_110712_l.render(par7);
        this.field_110713_m.render(par7);
        this.field_110710_n.render(par7);
        this.field_110716_h.render(par7);
        this.field_110714_j.render(par7);

        if (!var11)
        {
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            float var17 = 0.5F + var15 * var15 * 0.5F;
            GL11.glScalef(var17, var17, var17);

            if (var10 <= 0.0F)
            {
                GL11.glTranslatef(0.0F, 1.35F * (1.0F - var15), 0.0F);
            }
            else
            {
                GL11.glTranslatef(0.0F, 0.9F * (1.0F - var15) * var10 + 1.35F * (1.0F - var15) * (1.0F - var10), 0.15F * (1.0F - var15) * var10);
            }
        }

        if (var14)
        {
            this.field_110703_f.render(par7);
            this.field_110704_g.render(par7);
        }
        else
        {
            this.field_110705_d.render(par7);
            this.field_110706_e.render(par7);
        }

        this.field_110709_a.render(par7);

        if (!var11)
        {
            GL11.glPopMatrix();
        }

        if (var13)
        {
            this.field_110687_G.render(par7);
            this.field_110695_H.render(par7);
        }
    }

    private void func_110682_a(ModelRenderer par1ModelRenderer, float par2, float par3, float par4)
    {
        par1ModelRenderer.rotateAngleX = par2;
        par1ModelRenderer.rotateAngleY = par3;
        par1ModelRenderer.rotateAngleZ = par4;
    }

    private float func_110683_a(float par1, float par2, float par3)
    {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
        {
            ;
        }

        while (var4 >= 180.0F)
        {
            var4 -= 360.0F;
        }

        return par1 + par3 * var4;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
        super.setLivingAnimations(par1EntityLivingBase, par2, par3, par4);
        float var5 = this.func_110683_a(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par4);
        float var6 = this.func_110683_a(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par4);
        float var7 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par4;
        float var8 = var6 - var5;
        float var9 = var7 / (180F / (float)Math.PI);

        if (var8 > 20.0F)
        {
            var8 = 20.0F;
        }

        if (var8 < -20.0F)
        {
            var8 = -20.0F;
        }

        if (par3 > 0.2F)
        {
            var9 += MathHelper.cos(par2 * 0.4F) * 0.15F * par3;
        }

        EntityHorse var10 = (EntityHorse)par1EntityLivingBase;
        float var11 = var10.func_110258_o(par4);
        float var12 = var10.func_110223_p(par4);
        float var13 = 1.0F - var12;
        float var14 = var10.func_110201_q(par4);
        boolean var15 = var10.field_110278_bp != 0;
        boolean var16 = var10.func_110257_ck();
        boolean var17 = var10.riddenByEntity != null;
        float var18 = (float)par1EntityLivingBase.ticksExisted + par4;
        float var19 = MathHelper.cos(par2 * 0.6662F + (float)Math.PI);
        float var20 = var19 * 0.8F * par3;
        this.field_110709_a.rotationPointY = 4.0F;
        this.field_110709_a.rotationPointZ = -10.0F;
        this.field_110712_l.rotationPointY = 3.0F;
        this.field_110713_m.rotationPointZ = 14.0F;
        this.field_110695_H.rotationPointY = 3.0F;
        this.field_110695_H.rotationPointZ = 10.0F;
        this.field_110715_k.rotateAngleX = 0.0F;
        this.field_110709_a.rotateAngleX = 0.5235988F + var9;
        this.field_110709_a.rotateAngleY = var8 / (180F / (float)Math.PI);
        this.field_110709_a.rotateAngleX = var12 * (0.2617994F + var9) + var11 * 2.18166F + (1.0F - Math.max(var12, var11)) * this.field_110709_a.rotateAngleX;
        this.field_110709_a.rotateAngleY = var12 * (var8 / (180F / (float)Math.PI)) + (1.0F - Math.max(var12, var11)) * this.field_110709_a.rotateAngleY;
        this.field_110709_a.rotationPointY = var12 * -6.0F + var11 * 11.0F + (1.0F - Math.max(var12, var11)) * this.field_110709_a.rotationPointY;
        this.field_110709_a.rotationPointZ = var12 * -1.0F + var11 * -10.0F + (1.0F - Math.max(var12, var11)) * this.field_110709_a.rotationPointZ;
        this.field_110712_l.rotationPointY = var12 * 9.0F + var13 * this.field_110712_l.rotationPointY;
        this.field_110713_m.rotationPointZ = var12 * 18.0F + var13 * this.field_110713_m.rotationPointZ;
        this.field_110695_H.rotationPointY = var12 * 5.5F + var13 * this.field_110695_H.rotationPointY;
        this.field_110695_H.rotationPointZ = var12 * 15.0F + var13 * this.field_110695_H.rotationPointZ;
        this.field_110715_k.rotateAngleX = var12 * -((float)Math.PI / 4F) + var13 * this.field_110715_k.rotateAngleX;
        this.field_110705_d.rotationPointY = this.field_110709_a.rotationPointY;
        this.field_110706_e.rotationPointY = this.field_110709_a.rotationPointY;
        this.field_110703_f.rotationPointY = this.field_110709_a.rotationPointY;
        this.field_110704_g.rotationPointY = this.field_110709_a.rotationPointY;
        this.field_110716_h.rotationPointY = this.field_110709_a.rotationPointY;
        this.field_110707_b.rotationPointY = 0.02F;
        this.field_110708_c.rotationPointY = 0.0F;
        this.field_110714_j.rotationPointY = this.field_110709_a.rotationPointY;
        this.field_110705_d.rotationPointZ = this.field_110709_a.rotationPointZ;
        this.field_110706_e.rotationPointZ = this.field_110709_a.rotationPointZ;
        this.field_110703_f.rotationPointZ = this.field_110709_a.rotationPointZ;
        this.field_110704_g.rotationPointZ = this.field_110709_a.rotationPointZ;
        this.field_110716_h.rotationPointZ = this.field_110709_a.rotationPointZ;
        this.field_110707_b.rotationPointZ = 0.02F - var14 * 1.0F;
        this.field_110708_c.rotationPointZ = 0.0F + var14 * 1.0F;
        this.field_110714_j.rotationPointZ = this.field_110709_a.rotationPointZ;
        this.field_110705_d.rotateAngleX = this.field_110709_a.rotateAngleX;
        this.field_110706_e.rotateAngleX = this.field_110709_a.rotateAngleX;
        this.field_110703_f.rotateAngleX = this.field_110709_a.rotateAngleX;
        this.field_110704_g.rotateAngleX = this.field_110709_a.rotateAngleX;
        this.field_110716_h.rotateAngleX = this.field_110709_a.rotateAngleX;
        this.field_110707_b.rotateAngleX = 0.0F - 0.09424778F * var14;
        this.field_110708_c.rotateAngleX = 0.0F + 0.15707964F * var14;
        this.field_110714_j.rotateAngleX = this.field_110709_a.rotateAngleX;
        this.field_110705_d.rotateAngleY = this.field_110709_a.rotateAngleY;
        this.field_110706_e.rotateAngleY = this.field_110709_a.rotateAngleY;
        this.field_110703_f.rotateAngleY = this.field_110709_a.rotateAngleY;
        this.field_110704_g.rotateAngleY = this.field_110709_a.rotateAngleY;
        this.field_110716_h.rotateAngleY = this.field_110709_a.rotateAngleY;
        this.field_110707_b.rotateAngleY = 0.0F;
        this.field_110708_c.rotateAngleY = 0.0F;
        this.field_110714_j.rotateAngleY = this.field_110709_a.rotateAngleY;
        this.field_110687_G.rotateAngleX = var20 / 5.0F;
        this.field_110695_H.rotateAngleX = -var20 / 5.0F;
        float var21 = ((float)Math.PI / 2F);
        float var22 = ((float)Math.PI * 3F / 2F);
        float var23 = -1.0471976F;
        float var24 = 0.2617994F * var12;
        float var25 = MathHelper.cos(var18 * 0.6F + (float)Math.PI);
        this.field_110688_A.rotationPointY = -2.0F * var12 + 9.0F * var13;
        this.field_110688_A.rotationPointZ = -2.0F * var12 + -8.0F * var13;
        this.field_110684_D.rotationPointY = this.field_110688_A.rotationPointY;
        this.field_110684_D.rotationPointZ = this.field_110688_A.rotationPointZ;
        this.field_110719_v.rotationPointY = this.field_110711_o.rotationPointY + MathHelper.sin(((float)Math.PI / 2F) + var24 + var13 * -var19 * 0.5F * par3) * 7.0F;
        this.field_110719_v.rotationPointZ = this.field_110711_o.rotationPointZ + MathHelper.cos(((float)Math.PI * 3F / 2F) + var24 + var13 * -var19 * 0.5F * par3) * 7.0F;
        this.field_110721_y.rotationPointY = this.field_110722_x.rotationPointY + MathHelper.sin(((float)Math.PI / 2F) + var24 + var13 * var19 * 0.5F * par3) * 7.0F;
        this.field_110721_y.rotationPointZ = this.field_110722_x.rotationPointZ + MathHelper.cos(((float)Math.PI * 3F / 2F) + var24 + var13 * var19 * 0.5F * par3) * 7.0F;
        float var26 = (-1.0471976F + var25) * var12 + var20 * var13;
        float var27 = (-1.0471976F + -var25) * var12 + -var20 * var13;
        this.field_110689_B.rotationPointY = this.field_110688_A.rotationPointY + MathHelper.sin(((float)Math.PI / 2F) + var26) * 7.0F;
        this.field_110689_B.rotationPointZ = this.field_110688_A.rotationPointZ + MathHelper.cos(((float)Math.PI * 3F / 2F) + var26) * 7.0F;
        this.field_110685_E.rotationPointY = this.field_110684_D.rotationPointY + MathHelper.sin(((float)Math.PI / 2F) + var27) * 7.0F;
        this.field_110685_E.rotationPointZ = this.field_110684_D.rotationPointZ + MathHelper.cos(((float)Math.PI * 3F / 2F) + var27) * 7.0F;
        this.field_110711_o.rotateAngleX = var24 + -var19 * 0.5F * par3 * var13;
        this.field_110719_v.rotateAngleX = -0.08726646F * var12 + (-var19 * 0.5F * par3 - Math.max(0.0F, var19 * 0.5F * par3)) * var13;
        this.field_110718_w.rotateAngleX = this.field_110719_v.rotateAngleX;
        this.field_110722_x.rotateAngleX = var24 + var19 * 0.5F * par3 * var13;
        this.field_110721_y.rotateAngleX = -0.08726646F * var12 + (var19 * 0.5F * par3 - Math.max(0.0F, -var19 * 0.5F * par3)) * var13;
        this.field_110720_z.rotateAngleX = this.field_110721_y.rotateAngleX;
        this.field_110688_A.rotateAngleX = var26;
        this.field_110689_B.rotateAngleX = (this.field_110688_A.rotateAngleX + (float)Math.PI * Math.max(0.0F, 0.2F + var25 * 0.2F)) * var12 + (var20 + Math.max(0.0F, var19 * 0.5F * par3)) * var13;
        this.field_110690_C.rotateAngleX = this.field_110689_B.rotateAngleX;
        this.field_110684_D.rotateAngleX = var27;
        this.field_110685_E.rotateAngleX = (this.field_110684_D.rotateAngleX + (float)Math.PI * Math.max(0.0F, 0.2F - var25 * 0.2F)) * var12 + (-var20 + Math.max(0.0F, -var19 * 0.5F * par3)) * var13;
        this.field_110686_F.rotateAngleX = this.field_110685_E.rotateAngleX;
        this.field_110718_w.rotationPointY = this.field_110719_v.rotationPointY;
        this.field_110718_w.rotationPointZ = this.field_110719_v.rotationPointZ;
        this.field_110720_z.rotationPointY = this.field_110721_y.rotationPointY;
        this.field_110720_z.rotationPointZ = this.field_110721_y.rotationPointZ;
        this.field_110690_C.rotationPointY = this.field_110689_B.rotationPointY;
        this.field_110690_C.rotationPointZ = this.field_110689_B.rotationPointZ;
        this.field_110686_F.rotationPointY = this.field_110685_E.rotationPointY;
        this.field_110686_F.rotationPointZ = this.field_110685_E.rotationPointZ;

        if (var16)
        {
            this.field_110696_I.rotationPointY = var12 * 0.5F + var13 * 2.0F;
            this.field_110696_I.rotationPointZ = var12 * 11.0F + var13 * 2.0F;
            this.field_110697_J.rotationPointY = this.field_110696_I.rotationPointY;
            this.field_110698_K.rotationPointY = this.field_110696_I.rotationPointY;
            this.field_110691_L.rotationPointY = this.field_110696_I.rotationPointY;
            this.field_110693_N.rotationPointY = this.field_110696_I.rotationPointY;
            this.field_110692_M.rotationPointY = this.field_110696_I.rotationPointY;
            this.field_110694_O.rotationPointY = this.field_110696_I.rotationPointY;
            this.field_110687_G.rotationPointY = this.field_110695_H.rotationPointY;
            this.field_110697_J.rotationPointZ = this.field_110696_I.rotationPointZ;
            this.field_110698_K.rotationPointZ = this.field_110696_I.rotationPointZ;
            this.field_110691_L.rotationPointZ = this.field_110696_I.rotationPointZ;
            this.field_110693_N.rotationPointZ = this.field_110696_I.rotationPointZ;
            this.field_110692_M.rotationPointZ = this.field_110696_I.rotationPointZ;
            this.field_110694_O.rotationPointZ = this.field_110696_I.rotationPointZ;
            this.field_110687_G.rotationPointZ = this.field_110695_H.rotationPointZ;
            this.field_110696_I.rotateAngleX = this.field_110715_k.rotateAngleX;
            this.field_110697_J.rotateAngleX = this.field_110715_k.rotateAngleX;
            this.field_110698_K.rotateAngleX = this.field_110715_k.rotateAngleX;
            this.field_110702_R.rotationPointY = this.field_110709_a.rotationPointY;
            this.field_110701_S.rotationPointY = this.field_110709_a.rotationPointY;
            this.field_110717_i.rotationPointY = this.field_110709_a.rotationPointY;
            this.field_110700_P.rotationPointY = this.field_110709_a.rotationPointY;
            this.field_110699_Q.rotationPointY = this.field_110709_a.rotationPointY;
            this.field_110702_R.rotationPointZ = this.field_110709_a.rotationPointZ;
            this.field_110701_S.rotationPointZ = this.field_110709_a.rotationPointZ;
            this.field_110717_i.rotationPointZ = this.field_110709_a.rotationPointZ;
            this.field_110700_P.rotationPointZ = this.field_110709_a.rotationPointZ;
            this.field_110699_Q.rotationPointZ = this.field_110709_a.rotationPointZ;
            this.field_110702_R.rotateAngleX = var9;
            this.field_110701_S.rotateAngleX = var9;
            this.field_110717_i.rotateAngleX = this.field_110709_a.rotateAngleX;
            this.field_110700_P.rotateAngleX = this.field_110709_a.rotateAngleX;
            this.field_110699_Q.rotateAngleX = this.field_110709_a.rotateAngleX;
            this.field_110717_i.rotateAngleY = this.field_110709_a.rotateAngleY;
            this.field_110700_P.rotateAngleY = this.field_110709_a.rotateAngleY;
            this.field_110702_R.rotateAngleY = this.field_110709_a.rotateAngleY;
            this.field_110699_Q.rotateAngleY = this.field_110709_a.rotateAngleY;
            this.field_110701_S.rotateAngleY = this.field_110709_a.rotateAngleY;

            if (var17)
            {
                this.field_110691_L.rotateAngleX = -1.0471976F;
                this.field_110692_M.rotateAngleX = -1.0471976F;
                this.field_110693_N.rotateAngleX = -1.0471976F;
                this.field_110694_O.rotateAngleX = -1.0471976F;
                this.field_110691_L.rotateAngleZ = 0.0F;
                this.field_110692_M.rotateAngleZ = 0.0F;
                this.field_110693_N.rotateAngleZ = 0.0F;
                this.field_110694_O.rotateAngleZ = 0.0F;
            }
            else
            {
                this.field_110691_L.rotateAngleX = var20 / 3.0F;
                this.field_110692_M.rotateAngleX = var20 / 3.0F;
                this.field_110693_N.rotateAngleX = var20 / 3.0F;
                this.field_110694_O.rotateAngleX = var20 / 3.0F;
                this.field_110691_L.rotateAngleZ = var20 / 5.0F;
                this.field_110692_M.rotateAngleZ = var20 / 5.0F;
                this.field_110693_N.rotateAngleZ = -var20 / 5.0F;
                this.field_110694_O.rotateAngleZ = -var20 / 5.0F;
            }
        }

        var21 = -1.3089F + par3 * 1.5F;

        if (var21 > 0.0F)
        {
            var21 = 0.0F;
        }

        if (var15)
        {
            this.field_110712_l.rotateAngleY = MathHelper.cos(var18 * 0.7F);
            var21 = 0.0F;
        }
        else
        {
            this.field_110712_l.rotateAngleY = 0.0F;
        }

        this.field_110713_m.rotateAngleY = this.field_110712_l.rotateAngleY;
        this.field_110710_n.rotateAngleY = this.field_110712_l.rotateAngleY;
        this.field_110713_m.rotationPointY = this.field_110712_l.rotationPointY;
        this.field_110710_n.rotationPointY = this.field_110712_l.rotationPointY;
        this.field_110713_m.rotationPointZ = this.field_110712_l.rotationPointZ;
        this.field_110710_n.rotationPointZ = this.field_110712_l.rotationPointZ;
        this.field_110712_l.rotateAngleX = var21;
        this.field_110713_m.rotateAngleX = var21;
        this.field_110710_n.rotateAngleX = -0.2618F + var21;
    }
}
