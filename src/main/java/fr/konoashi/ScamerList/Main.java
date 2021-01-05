package fr.konoashi.ScamerList;

import com.google.gson.JsonElement;
import fr.konoashi.ScamerList.utils.References;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



import okhttp3.*;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Mod(modid = References.MODID, name = References.NAME , version = References.VERSION)
public class Main<await> {

    private Class<?> lastOpenedInventory;

    @Mod.Instance(References.MODID)
    public static Main instance;

    public Main() throws IOException {
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ChatAutoReport());


    }

     private void sendScammerAlertWebhook(String scammeruuid, String scammername) throws IOException {
        //DiscordWebhook webhook = new DiscordWebhook(References.SCAM_URL);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        Minecraft mc = Minecraft.getMinecraft();

         System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");
        System.out.println(scammername);
        System.out.println(scammeruuid);
        System.out.println(formatter.format(date));
        System.out.println(mc.thePlayer.getUniqueID());
         System.out.println(mc.thePlayer.getName());
         System.out.println("-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-");


         CloseableHttpClient httpclient = HttpClients.createDefault();
         HttpPost httpPost = new HttpPost("https://discord.com/api/webhooks/794670180675878912/PRIVATE_TOKEN");
         String JSON_STRING="\n" +
                 "{\n" +
                 "    \"content\": \"ScamList has stopped a someone to trade with a scammer.\",\n" +
                 "  \"embeds\": [{\n" +
                 "    \"title\": \":warning: ScamList closed a trade\",\n" +
                 "    \"description\": \"We can't know if the player attempted to scam but he is a recognized scammer in our database so the trade was closed.\",\n" +
                 "    \"thumbnail\": {\n" +
                 "      \"url\": \"https://crafatar.com/avatars/" + scammeruuid + "?size=128&amp;overlay.png\"\n" +
                 "    },\n" +
                 "    \"footer\": {\n" +
                 "        \"text\": \"Reported by " + mc.thePlayer.getName() + " | " + formatter.format(date) + "\",\n" +
                 "        \"icon_url\": \"https://crafatar.com/avatars/" + mc.thePlayer.getUniqueID() + "?size=128&amp;overlay.png\"\n" +
                 "      },\n" +
                 "    \"color\": 16761856,\n" +
                 "    \n" +
                 "        \"fields\": [\n" +
                 "          {\n" +
                 "            \"name\": \"Report details\",\n" +
                 "            \"value\": \":arrow_forward: "+ scammername +" attempted to scam " + mc.thePlayer.getName()  +"\"  ,\n" +
                 "            \"inline\": false\n" +
                 "          }\n" +
                 "        ]\n" +
                 "        \n" +
                 "  }]\n" +
                 "}";
         HttpEntity stringEntity = new StringEntity(JSON_STRING, ContentType.APPLICATION_JSON);
         httpPost.setEntity(stringEntity);
         CloseableHttpResponse response2 = httpclient.execute(httpPost);


         /*OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "\n" +
                "{\n" +
                "    \"content\": \"ScamList has stopped a someone to trade with a scammer.\",\n" +
                "  \"embeds\": [{\n" +
                "    \"title\": \":warning: ScamList closed a trade\",\n" +
                "    \"description\": \"We can't know if the player attempted to scam but he is a recognized scammer in our database so the trade was closed.\",\n" +
                "    \"thumbnail\": {\n" +
                "      \"url\": \"https://crafatar.com/avatars/" + scammeruuid + "?size=128&amp;overlay.png\"\n" +
                "    },\n" +
                "    \"footer\": {\n" +
                "        \"text\": \"Reported by " + mc.thePlayer.getName() + " | " + formatter.format(date) + "\",\n" +
                "        \"icon_url\": \"https://crafatar.com/avatars/" + mc.thePlayer.getUniqueID() + "?size=128&amp;overlay.png\"\n" +
                "      },\n" +
                "    \"color\": 16761856,\n" +
                "    \n" +
                "        \"fields\": [\n" +
                "          {\n" +
                "            \"name\": \"Report details\",\n" +
                "            \"value\": \":arrow_forward: "+ scammername +" attempted to scam " + mc.thePlayer.getName()  +"\"  ,\n" +
                "            \"inline\": false\n" +
                "          }\n" +
                "        ]\n" +
                "        \n" +
                "  }]\n" +
                "}");
        Request request = new Request.Builder()
                .url("")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "__cfduid=df598cdc30a11a2eeed0288a538d9d0e91609539720")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(body);
        System.out.println(response);*/


    }







    public static String getUuid(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/"+name;
        try {
            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.isEmpty()) return "invalid name";
            JsonElement jsonElement = new JsonParser().parse(UUIDJson);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            System.out.println("testing for string");
            System.out.println(jsonObject.get("id").toString());
            return jsonObject.get("id").toString();
        } catch (IOException | JsonParseException e) {
            e.printStackTrace();

        }

        return "error";
    }








    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent e) throws IOException, InterruptedException {
        if (e.gui == null && GuiChest.class.equals(lastOpenedInventory)) {

            lastOpenedInventory = null;
        }
        if (e.gui != null) {
            lastOpenedInventory = e.gui.getClass();

            if (e.gui instanceof GuiChest) {
                Minecraft mc = Minecraft.getMinecraft();
                IInventory chestInventory;
                chestInventory = ((GuiChest) e.gui).lowerChestInventory;
                if (chestInventory.hasCustomName()) {
                    if (chestInventory.getDisplayName().getUnformattedText().contains("You")) {
                        String tradeContain = chestInventory.getDisplayName().getUnformattedText();
                        String userWithinFormating = tradeContain.substring(3);
                        String userToScan = userWithinFormating.replaceAll(" ","");
                        String uuidToScan = getUuid(userToScan);
                        System.out.println(uuidToScan);
                        String line = EnumChatFormatting.AQUA + "____________________";
                        String waitingMessage2 = EnumChatFormatting.YELLOW + References.ScammListBrand + References.msg2 + EnumChatFormatting.GOLD + userToScan;
                        String waitingMessage1 = EnumChatFormatting.YELLOW + References.ScammListBrand + References.msg1;
                        mc.thePlayer.addChatMessage(new ChatComponentText(line));
                        mc.thePlayer.addChatMessage(new ChatComponentText(""));
                        mc.thePlayer.addChatMessage(new ChatComponentText(waitingMessage1));
                        mc.thePlayer.addChatMessage(new ChatComponentText(""));
                        mc.thePlayer.addChatMessage(new ChatComponentText(waitingMessage2));
                        mc.thePlayer.addChatMessage(new ChatComponentText(""));



                        String verify = HttpURLConnectionExample.main("https://scamlist.github.io/Scam.json");
                        String verifySbz = HttpURLConnectionExample.main("https://raw.githubusercontent.com/skyblockz/pricecheckbot/master/scammer.json");


                        if (verify.contains(uuidToScan) || verifySbz.contains(uuidToScan)) {










                            String alertScam = EnumChatFormatting.DARK_RED + References.ScammListBrand + userToScan + References.msg4;

                            String subtitle = EnumChatFormatting.DARK_RED +  userToScan + " is a recognized scammer !";
                            String title = EnumChatFormatting.DARK_RED + "WARNING";
                            mc.thePlayer.addChatMessage(new ChatComponentText(alertScam));
                            //mc.thePlayer.addChatMessage(new ChatComponentText(ignore));
                            mc.thePlayer.addChatMessage(new ChatComponentText(line));
                            mc.thePlayer.playSound("mob.wither.spawn", 0.5F, 1);

                            GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
                            gui.displayTitle(title, null, 20, 100, 20);
                            gui.displayTitle(null, subtitle, 20, 100, 20);

                            String uuidToScan0 = uuidToScan.substring(1, 33);

                            System.out.println(uuidToScan0);


                            sendScammerAlertWebhook(uuidToScan0, userToScan);


                                //mc.thePlayer.displayGUIChest(chestInventory);




                            chestInventory.closeInventory(mc.thePlayer);
                            System.out.println(chestInventory);

                            Minecraft.getMinecraft().thePlayer.closeScreen();
                            mc.currentScreen.onGuiClosed();
                            

                            /*mc.thePlayer.closeScreen();
                            Minecraft.getMinecraft().thePlayer.closeScreen();
                            mc.displayGuiScreen(null);*/








                        }else {



                            if (uuidToScan.equals("invalid name")) {
                                String playerUnknowName = EnumChatFormatting.GOLD + References.ScammListBrand + userToScan + " have a non-recognized minecraft name, maybe is a MVP++";
                                mc.thePlayer.addChatMessage(new ChatComponentText(playerUnknowName));
                                mc.thePlayer.addChatMessage(new ChatComponentText(""));
                                mc.thePlayer.addChatMessage(new ChatComponentText(line));
                            } else {

                                ChatComponentText playerSafe = new ChatComponentText(EnumChatFormatting.GREEN + References.ScammListBrand + userToScan + References.msg3 + EnumChatFormatting.DARK_BLUE + "https://discord.gg/5mrpAR3q5D"); // Fill the string with what you want to show up in chat
                                ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/5mrpAR3q5D")); // Fill the string with your URL
                                HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("https://discord.gg/5mrpAR3q5D")); // Fill the string with your URL
                                playerSafe.setChatStyle(style.setChatHoverEvent(hoverEvent));

                                mc.thePlayer.addChatMessage(playerSafe);
                                mc.thePlayer.addChatMessage(new ChatComponentText(""));
                                mc.thePlayer.addChatMessage(new ChatComponentText(line));
                            }



                        }


                    }
                }
            }
        }
    }


}











