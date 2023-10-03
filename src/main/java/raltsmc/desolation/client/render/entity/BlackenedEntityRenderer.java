package raltsmc.desolation.client.render.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import raltsmc.desolation.client.render.entity.feature.GeoGlowLayerRenderer;
import raltsmc.desolation.client.render.entity.model.BlackenedEntityModel;
import raltsmc.desolation.entity.BlackenedEntity;

public class BlackenedEntityRender<T extends BlackenedEntity> extends GeoEntityRenderer {
  public BlackenedEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new BlackenedEntityModel<BlackenedEntity>());
    this.addLayer(new GeoGlowLayerRenderer<BlackenedEntity>(this, "textures/entity/blackened_glow.png"));
  }
}