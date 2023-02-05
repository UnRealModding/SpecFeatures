package com.unrealdinnerbone.specfeatures.util;

import net.minecraft.client.Minecraft;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MessageSystem {

    private static final Timer TIMER = new Timer();
    public static final Queue<String> messages = new ArrayDeque<>();

    public static final Queue<String> commands = new ArrayDeque<>();

    public static void addMessage(List<String> messages) {
        add(messages, MessageSystem.messages);
    }


    public static void addCommand(List<String> messages) {
        add(messages, commands);
    }

    private static void add(List<String> messages, Queue<String> queue) {
        List<String> reversed = new ArrayList<>(messages);
        Collections.reverse(reversed);
        for (int i = 0; i < reversed.size(); i++) {
            String message = reversed.get(i);
            String before = i == reversed.size() -1 ? null : reversed.get(i + 1);
            if(before != null && queue.contains(before)) {
                queue.remove(before);
                queue.add(message);
                break;
            }
            if(before == null) {
                queue.add(message);
            }
        }
    }

    public static String getMessage() {
        return messages.poll();
    }

    public static String getCommand() {
        return commands.poll();
    }



    public static void ranSchedule() {
        scheduleRepeatingTask(1, TimeUnit.SECONDS, new TimerTask() {
            @Override
            public void run() {
                String message = getMessage();
                if(message != null) {
                    Minecraft.getInstance().execute(() -> Minecraft.getInstance().player.chat(message, null));
                }
                String command = getCommand();
                if(command != null) {
                    Minecraft.getInstance().execute(() -> Minecraft.getInstance().player.command(command, null));
                }
            }
        });
    }

    public static TimerTask scheduleRepeatingTask(int time, TimeUnit timeUnit, TimerTask task) {
        TIMER.scheduleAtFixedRate(task, 0, timeUnit.toMillis(time));
        return task;
    }

}
