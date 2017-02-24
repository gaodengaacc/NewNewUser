package com.netease.nim.uikit.session;

import com.netease.nim.uikit.session.module.input.InputPanelCustomization;

import java.io.Serializable;

/**
 * 聊天界面定制化参数。 可定制：<br>
 * 1. 聊天背景 <br>
 * 2. 加号展开后的按钮和动作 <br>
 * 3. ActionBar右侧按钮。
 */
public class SessionCustomization implements Serializable {

    private ToolbarCustomization toolbarCustomization;
    private MessagePanelCustomization messagePanelCustomization;
    private InputPanelCustomization inputPanelCustomization;

    public SessionCustomization() {
    }

    public SessionCustomization(ToolbarCustomization toolbarCustomization, MessagePanelCustomization messagePanelCustomization, InputPanelCustomization inputPanelCustomization) {
        this.toolbarCustomization = toolbarCustomization;
        this.messagePanelCustomization = messagePanelCustomization;
        this.inputPanelCustomization = inputPanelCustomization;
    }

    public ToolbarCustomization getToolbarCustomization() {
        return toolbarCustomization;
    }

    public void setToolbarCustomization(ToolbarCustomization toolbarCustomization) {
        this.toolbarCustomization = toolbarCustomization;
    }

    public MessagePanelCustomization getMessagePanelCustomization() {
        return messagePanelCustomization;
    }

    public void setMessagePanelCustomization(MessagePanelCustomization messagePanelCustomization) {
        this.messagePanelCustomization = messagePanelCustomization;
    }

    public InputPanelCustomization getInputPanelCustomization() {
        return inputPanelCustomization;
    }

    public void setInputPanelCustomization(InputPanelCustomization inputPanelCustomization) {
        this.inputPanelCustomization = inputPanelCustomization;
    }
}
