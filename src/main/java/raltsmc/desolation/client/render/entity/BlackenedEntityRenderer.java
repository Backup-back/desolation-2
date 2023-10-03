package raltsmc.desolation.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import raltsmc.desolation.client.render.entity.feature.GeoGlowLayerRenderer;
import raltsmc.desolation.client.render.entity.model.BlackenedEntityModel;
import raltsmc.desolation.entity.BlackenedEntity;

public class BlackenedEntityRender extends GeoEntityRenderer<BlackenedEntity> {
  public BlackenedEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new BlackenedEntityModel<BlackenedEntity>());
    this.addLayer(new GeoGlowLayerRenderer<BlackenedEntity>(this, "textures/entity/blackened_glow.png"));
  }
}