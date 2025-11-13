package com.pluralsight.ui.gui;

import com.pluralsight.domain.Order;
import com.pluralsight.io.ReceiptFileManager;

public class GUIState {

    public static Order currentOrder = new Order();
    public static final ReceiptFileManager receiptManager = new ReceiptFileManager();
}
