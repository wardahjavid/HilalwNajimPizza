package com.pluralsight;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Order {
    private final List<MenuItem> items = new ArrayList<>();
    public void addItem(MenuItem item) { items.add(item); }
    public List<MenuItem> getItems() { return items; }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) total += item.getPrice();
        return total;
    }

    public List<MenuItem> query(Predicate<MenuItem> filter, Comparator<MenuItem> sorter) {
        return items.stream()
                .filter(filter == null ? MenuFilters.any() : filter)
                .sorted(sorter == null ? MenuSorters.byLabel() : sorter)
                .collect(Collectors.toList());
    }

    public List<String[]> toRows(List<MenuItem> list) {
        List<String[]> rows = new ArrayList<>();
        for (MenuItem m : list)
            rows.add(new String[]{m.getCategory(), m.getLabel(), String.format("$%.2f", m.getPrice())});
        return rows;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Order Details:\n");
        for (MenuItem item : items) sb.append(item).append("\n");
        sb.append("Total: $").append(String.format("%.2f", calculateTotal()));
        return sb.toString();
    }
}
