package games.moegirl.sinocraft.sinobrush.item;

import games.moegirl.sinocraft.sinobrush.SBRConstants;
import games.moegirl.sinocraft.sinobrush.utility.TooltipHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XuanPaperItem extends Item implements DyeableLeatherItem {
    public XuanPaperItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getColor(ItemStack stack) {
        if (stack.isEmpty() || !hasCustomColor(stack)) {
            return SBRConstants.COLOR_WHITE;
        }

        return DyeableLeatherItem.super.getColor(stack);
    }

    public static int getExpend(ItemStack stack) {
        if (!stack.is(SBRItems.XUAN_PAPER.get())) {
            return 0;
        }

        if (stack.hasTag()) {
            var tag = stack.getTag();
            assert tag != null;

            var paper = tag.getCompound(SBRConstants.TagName.XUAN_PAPER);
            return paper.getInt(SBRConstants.TagName.XUAN_PAPER_EXPENDS);
        }

        return 0;
    }

    public static boolean canExpend(ItemStack stack) {
        return stack.is(SBRItems.XUAN_PAPER.get()) && getExpend(stack) < SBRConstants.XUAN_PAPER_MAX_EXPEND;
    }

    public static void setExpend(ItemStack stack, int expend) {
        if (!stack.is(SBRItems.XUAN_PAPER.get())) {
            return;
        }

        var tag = stack.getOrCreateTag();
        var paper = tag.getCompound(SBRConstants.TagName.XUAN_PAPER);
        paper.putInt(SBRConstants.TagName.XUAN_PAPER_EXPENDS, expend);
        tag.put(SBRConstants.TagName.XUAN_PAPER, paper);
        stack.setTag(tag);
    }

    public static void expend(ItemStack stack) {
        if (canExpend(stack)) {
            setExpend(stack, getExpend(stack) + 1);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);

        if (stack.getItem() instanceof XuanPaperItem) {
            var color = getColor(stack);
            tooltip.add(TooltipHelper.getColor(color));

            var expend = getExpend(stack);
            if (expend != 0) {
                tooltip.add(Component.translatable(SBRConstants.Translation.DESCRIPTION_XUAN_PAPER_EXPENDED, expend)
                        .withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        var stack = new ItemStack(this);
        setExpend(stack, 0);
        setColor(stack, SBRConstants.COLOR_WHITE);
        return stack;
    }
}
