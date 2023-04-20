package games.moegirl.sinocraft.sinocalligraphy.gui.screen;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import games.moegirl.sinocraft.sinocalligraphy.SCAConstants;
import games.moegirl.sinocraft.sinocalligraphy.SinoCalligraphy;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocalligraphy.gui.components.BrushCanvas;
import games.moegirl.sinocraft.sinocalligraphy.gui.components.ColorSelectionList;
import games.moegirl.sinocraft.sinocalligraphy.gui.menu.BrushMenu;
import games.moegirl.sinocraft.sinocalligraphy.networking.packet.DrawingSaveC2SPacket;
import games.moegirl.sinocraft.sinocalligraphy.utility.DrawHelper;
import games.moegirl.sinocraft.sinocore.client.GLSwitcher;
import games.moegirl.sinocraft.sinocore.client.TextureMapClient;
import games.moegirl.sinocraft.sinocore.client.component.AnimatedText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.common.util.Lazy;

import java.io.File;
import java.io.IOException;
import java.time.Duration;


public class BrushScreen extends AbstractContainerScreen<BrushMenu> {
    private static final TextureMapClient CLIENT_TEXTURE = new TextureMapClient(BrushMenu.TEXTURE);

    private final Lazy<BrushCanvas> canvas = Lazy.of(() -> new BrushCanvas(this, CLIENT_TEXTURE, menu::getColorLevel, menu::setColorLevel, PaperType.WHITE, InkType.BLACK));
    private final Lazy<AnimatedText> text = Lazy.of(() -> new AnimatedText(130, 130));
    private final Lazy<ColorSelectionList> list = Lazy.of(() -> ColorSelectionList.create(this));

    public BrushScreen(BrushMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        width = 212;
        height = 256;

        imageWidth = 212;
        imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();

        canvas.get().resize(130, 130);
        canvas.get().setLocation(leftPos + 58, topPos + 11);
        addRenderableWidget(canvas.get());

        // qyl27: Ensure the text below the canvas.
        addRenderableOnly(text.get().resize(leftPos + 58 + (130 / 2 - 10), topPos + 11 + 132, font));

        list.get().setRelativeLocation(leftPos, topPos);
        addRenderableWidget(list.get());

        CLIENT_TEXTURE.placeButton("copy_button", this, this::copyDraw, this::pasteDraw);
        CLIENT_TEXTURE.placeButton("output_button", this, this::saveToFile);
        CLIENT_TEXTURE.placeButton("draw_apply_button", this, this::applyDraw);
        CLIENT_TEXTURE.placeButton("draw_clear_button", this, this::clearDraw);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTick);
        renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        CLIENT_TEXTURE.blitTexture(poseStack, "background", this, GLSwitcher.blend().enable(), GLSwitcher.depth().enable());
    }

    /// <editor-fold desc="Handle input.">

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        // Todo: qyl27.
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        canvas.get().mouseDragged(mouseX, mouseY, button, dragX, dragY);
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        canvas.get().mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        if (canvas.get().isMouseOver(mouseX, mouseY)) {
            return list.get().mouseScrolled(mouseX, mouseY, delta);
        }

        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        canvas.get().keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        canvas.get().keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    /// </editor-fold>

    public ColorSelectionList getColorSelection() {
        return list.get();
    }

    public AnimatedText getText() {
        return text.get();
    }

    public BrushCanvas getCanvas() {
        return canvas.get();
    }

    /// <editor-fold desc="Button pressed.">

    private void copyDraw(Button button) {
        var drawing = canvas.get().getDrawing();
        var data = drawing.serializeNBT().getAsString();
        Minecraft.getInstance().keyboardHandler.setClipboard(data);
        text.get().begin(Duration.ofSeconds(1), 0, 0, 255, 0, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_COPIED));
    }

    private void pasteDraw(Button button) {
        String data = Minecraft.getInstance().keyboardHandler.getClipboard();
        try {
            canvas.get().getDrawing().deserializeNBT(TagParser.parseTag(data));
            text.get().begin(Duration.ofSeconds(1), 0, 255, 0, 0, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_PASTED));
        } catch (Exception ex) {
            text.get().begin(Duration.ofSeconds(1), 0, 255, 0, 0, Component.translatable(SCAConstants.GUI_MESSAGE_BRUSH_PASTE_FAILED));
            SinoCalligraphy.LOGGER.warn(ex.toString());
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void saveToFile(Button button) {
        var player = Minecraft.getInstance().player;
        var drawing = canvas.get().getDrawing();
        try (var image = DrawHelper.toNaiveImage(drawing)) {
            File name = new File(Minecraft.getInstance().gameDirectory,
                    "sinoseries/sinocalligraphy/drawings/" + drawing.getAuthor().getString() +
                            "/" + System.currentTimeMillis() + ".png");
            name.getParentFile().mkdirs();
            if (!name.exists()) {
                name.createNewFile();
            }
            image.writeToFile(name);
            if (player != null) {
                var path = Component.literal(name.toString());
                path.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, name.getAbsolutePath()));

                player.displayClientMessage(Component.translatable(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_SUCCESS,
                        path), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (player != null) {
                player.displayClientMessage(Component.translatable(SCAConstants.TRANSLATE_MESSAGE_OUTPUT_SUCCESS,
                        e.getMessage()), false);
            }
        }
    }

    private void clearDraw(Button button) {
        canvas.get().clear();
    }

    private void applyDraw(Button button) {
        SinoCalligraphy.getInstance().getNetworking().send(new DrawingSaveC2SPacket(canvas.get().getDrawing()));
    }

    /// </editor-fold>
}
