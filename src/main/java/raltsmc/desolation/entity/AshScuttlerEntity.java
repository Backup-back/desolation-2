package raltsmc.desolation.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import raltsmc.desolation.entity.ai.goal.DigAshGoal;
import raltsmc.desolation.registry.DesolationItems;

public class AshScuttlerEntity extends PathAwareEntity implements IAnimatable {
  private static final TrackedData<Boolean> SEARCHING;
  private static final Ingredient ATTRACTING_INGREDIENT;
  
  static {
    SEARCHING = DataTracker.registerData(AshScuttlerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    ATTRACTING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{DesolationItems.CINDERFRUIT});
  }
  
  private final AnimationFactory factory = new AnimationFactory(this);
  
  public AshScuttlerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
    super(entityType, world);
  }
  
  @Override
  public AnimationFactory getFactory() {
    return this.factory;
  }
  
  protected void initGoals() {
    this.goalSelector.add(1, new DigAshGoal(this, 0.3D, 40, 2));
    this.goalSelector.add(2, new EscapeDangerGoal(this, 0.4F));
    this.goalSelector.add(3, new TemptGoal(this, 0.3D, ATTRACTING_INGREDIENT, false));
    this.goalSelector.add(4, new WanderAroundGoal(this, 0.2F));
    this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8F));
    this.goalSelector.add(6, new LookAroundGoal(this));
  }
  
  protected void initDataTracker() {
    super.initDataTracker();
    this.dataTracker.startTracking(SEARCHING, false);
  }
  
  protected void playStepSound(BlockPos pos, BlockState state) {
    this.playSound(SoundEvents.ENTITY_ENDERMITE_STEP, 0.5F, 1.0F);
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_BAT_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_BAT_DEATH;
  }
  
  public boolean isSearching() {
    return this.dataTracker.get(SEARCHING);
  }
  
  public void setSearching(boolean val) {
    this.dataTracker.set(SEARCHING, val);
  }
  
  public ActionResult interactMob(PlayerEntity player, Hand hand) {
    ItemStack itemStack = player.getStackInHand(hand);
    Item item = itemStack.getItem();
    if (item == DesolationItems.CINDERFRUIT && !this.isSearching()) {
      if (!this.world.isClient) {
        if (!player.getAbilities().creativeMode) {
          itemStack.decrement(1);
        }
        this.dataTracker.set(SEARCHING, true);
        return ActionResult.SUCCESS;
      } else {
        double pVel = random.nextGaussian() * 0.02D;
        world.addParticle(ParticleTypes.HEART, this.getX(), this.getY(), this.getZ(), pVel, pVel, pVel);
        player.playSound(SoundEvents.ITEM_NETHER_WART_PLANT, 1.0F, 1.0F);
        return ActionResult.CONSUME;
      }
    }
    return super.interactMob(player, hand);
  }
  
  public EntityGroup getGroup() {
    return EntityGroup.ARTHROPOD;
  }
  
  private <E extends IAnimatable> PlayState walkPredicate(AnimationEvent<E> event) {
    if (event.isMoving()) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.desolation.ash_scuttler_walk"));
      return PlayState.CONTINUE;
    } else {
      return PlayState.STOP;
    }
  }
  
  private <E extends IAnimatable> PlayState headPredicate(AnimationEvent<E> event) {
    if (!event.isMoving()) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.desolation.ash_scuttler_head", true));
      return PlayState.CONTINUE;
    } else {
      return PlayState.STOP;
    }
  }
  
  @Override
  public void registerControllers(AnimationData data) {
    data.addAnimationController(new AnimationController(this, "walkController", 0, this::walkPredicate));
    data.addAnimationController(new AnimationController(this, "headController", 0, this::headPredicate));
  }
}