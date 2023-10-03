package raltsmc.desolation.client.render.entity.model;

import net.minecraft.util.Identifier;
import raltsmc.desolation.Desolation;
import raltsmc.desolation.entity.AshScuttlerEntity;

public class AshScuttlerEntityModel extends AnimatedGeoModel<AshScuttlerEntity> {
  
  @Override
  public Identifier getModelLocation(AshScuttlerEntity object) {
    return Desolation.id("geo/ash_scuttler.geo.json");
  }
  
  @Override
  public Identifier getTextureLocation(AshScuttlerEntity object) {
    return Desolation.id("textures/entity/ash_scuttler.png");
  }
  
  @Override
  public Identifier getAnimationFileLocation(AshScuttlerEntity object) {
    return Desolation.id("animations/ash_scuttler.json");
  }
  
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public void setLivingAnimations(AshScuttlerEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
    super.setLivingAnimations(entity, uniqueID, customPredicate);
    IBone head = this.getAnimationProcessor().getBone("head");
    EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
    if (head != null) {
      head.setRotationX(extraData.headPitch * ((float) Math.PI / 360F));
      head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 340F));
    }
  }
}